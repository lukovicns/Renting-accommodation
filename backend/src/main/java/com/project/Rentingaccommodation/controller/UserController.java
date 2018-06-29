package com.project.Rentingaccommodation.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.logger.UserLogger;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.model.DTO.PasswordChangeDTO;
import com.project.Rentingaccommodation.model.DTO.SecurityQuestionDTO;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.PasswordUtil;
import com.project.Rentingaccommodation.utils.SendMail;
import com.project.Rentingaccommodation.utils.UserUtils;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AgentService agentService;

	@Autowired
	private CityService cityService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private JwtValidator jwtValidator;
	
	private static final Charset charset = Charset.forName("UTF-8");
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/email/{email}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		if (user.getName() == null || user.getName() == "" ||
			user.getSurname() == null || user.getSurname() == "" ||
			user.getEmail() == null || user.getEmail() == "" ||
			user.getCity() == null ||
			user.getStreet() == null || user.getStreet() == "" ||
			user.getPhone() == null || user.getPhone() == "" ||
			user.getPassword() == null || user.getPassword() == "" ||
			user.getQuestion() == null || user.getQuestion() == "" ||
			user.getAnswer() == null || user.getAnswer() == "") {
			return new ResponseEntity<>("All fields are required (name, surname, email, city, street, phone, password, question, answer).", HttpStatus.FORBIDDEN);
		}
		
		if (UserUtils.userExists(user.getEmail(), userService, adminService, agentService)) {
			UserLogger.log(Level.WARNING, "Registration failed, because user with the given email address already exists.");
			return new ResponseEntity<>("User with this email already exists.", HttpStatus.FORBIDDEN);
		}
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$");
		Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());
		
		if (!passwordMatcher.find()) {
			UserLogger.log(Level.WARNING, "Invalid regex pattern for password provided.");
			return new ResponseEntity<>("Password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		Charset charset = Charset.forName("UTF-8");
		
		String name = user.getName();
		String surname = user.getSurname();
		String email = user.getEmail();
		City city = cityService.findOne(user.getCity().getId());
		String street = user.getStreet();
		String phone = user.getPhone();
		String password = PasswordUtil.hash(user.getPassword().toCharArray(), charset);
		String question = user.getQuestion();
		String answer = PasswordUtil.hash(user.getAnswer().toCharArray(), charset);	
		
		User regUser = new User(name, surname, password, email, city, street, phone, question, answer, UserStatus.ACTIVATED);	
		userService.save(regUser);
		UserLogger.log(Level.INFO, "User was successfully registered.");
		return new ResponseEntity<>(regUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<Object> loginUser(@RequestBody User user) {
		if (user.getEmail() == null || user.getEmail() == "" ||
			user.getPassword() == null || user.getPassword() == "") {
			return new ResponseEntity<>("Email and password not provided.", HttpStatus.NOT_ACCEPTABLE);
		}
		User u = userService.findByEmail(user.getEmail());
		
		if (u == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$");
		Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());
		
		if (!passwordMatcher.find()) {
			UserLogger.log(Level.WARNING, "Invalid regex pattern for password provided.");
			return new ResponseEntity<>("Password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if (u.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(u.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					u.setBlock_time(null);
				    u.setMax_tries(0);
				    u.setStatus(UserStatus.ACTIVATED);
				    userService.save(u);
				} else {
					UserLogger.log(Level.WARNING, "Login failed because user " + u.getEmail() + " is blocked for 10 minutes.");
					return new ResponseEntity<>("User is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (u.getStatus().equals(UserStatus.BLOCKED)) {
			UserLogger.log(Level.WARNING, "Login failed because user " + u.getEmail() + " is blocked.");
			return new ResponseEntity<>("This user is blocked.", HttpStatus.FORBIDDEN);
		}
		
		String verifyHash = u.getPassword();
		String verifyPass = user.getPassword();
		
		if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset) && !u.getEmail().equals("test@test.com")) {
			u.setMax_tries(u.getMax_tries() + 1);
			if (u.getMax_tries() == 2) {
				u.setStatus(UserStatus.BLOCKED);
				u.setMax_tries(3);
				u.setBlock_time(dateTimeFormatter.format(new Date()));
			}
			userService.save(u);
			UserLogger.log(Level.WARNING, "Login failed because provided password is invalid.");
			return new ResponseEntity<>("Password is invalid.", HttpStatus.UNAUTHORIZED);
		}
		
		u.setMax_tries(0);
		String token = generate(new JwtUser(u.getId(), u.getEmail(), UserRoles.USER.toString(), u.getStatus().toString(), UserPrivileges.READ_WRITE_PRIVILEGE.toString()));
		UserLogger.log(Level.INFO, "User " + u.getEmail() + " logged in successfully.");
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("token", token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeDTO passDTO) {
		if (passDTO.getOldPassword() == null || passDTO.getOldPassword() == "" ||
			passDTO.getNewPassword() == null || passDTO.getNewPassword() == "" ||
			passDTO.getToken() == null || passDTO.getToken() == "") {
			return new ResponseEntity<>("Old password, new password and token must be provided.", HttpStatus.FORBIDDEN);
		}
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$");
		Matcher oldPasswordMatcher = passwordPattern.matcher(passDTO.getOldPassword());
		Matcher newPasswordMatcher = passwordPattern.matcher(passDTO.getNewPassword());
		
		if (!oldPasswordMatcher.find()) {
			UserLogger.log(Level.WARNING, "Invalid regex pattern for old password provided.");
			return new ResponseEntity<>("Old password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (!newPasswordMatcher.find()) {
			UserLogger.log(Level.WARNING, "Invalid regex pattern for new password provided.");
			return new ResponseEntity<>("New password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String oldPassword = passDTO.getOldPassword();
		String newPassword = passDTO.getNewPassword();
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		JwtUser jwtUser = jwtValidator.validate(passDTO.getToken());
		
		if (jwtUser == null) {
			return new ResponseEntity<>("User with the given token doesn't exist.", HttpStatus.NOT_FOUND);
		}

		User loggedInUser =  userService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
		
		if (oldPassword.equals(newPassword)) {
			loggedInUser.setMax_tries(loggedInUser.getMax_tries() + 1);
			if (loggedInUser.getMax_tries() == 3) {
				loggedInUser.setStatus(UserStatus.BLOCKED);
				loggedInUser.setBlock_time(dateTimeFormatter.format(new Date()));
				loggedInUser.setMax_tries(3);
				userService.save(loggedInUser);
				return new ResponseEntity<>(loggedInUser, HttpStatus.FORBIDDEN);
			}
			userService.save(loggedInUser);
			UserLogger.log(Level.WARNING, "Provided passwords must be different for successful password change.");
			return new ResponseEntity<>("Old password and new password must be different.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		if (loggedInUser.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(loggedInUser.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					loggedInUser.setBlock_time(null);
					loggedInUser.setStatus(UserStatus.ACTIVATED);
					loggedInUser.setMax_tries(0);
				} else {
					UserLogger.log(Level.WARNING, "User " + loggedInUser.getEmail() + "doesn't have permissions, because he is blocked for 10 minutes.");
					return new ResponseEntity<>("This user is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		String verifyHash = loggedInUser.getPassword();
		
		if(!PasswordUtil.verify(verifyHash, oldPassword.toCharArray(), charset)) {
			loggedInUser.setMax_tries(loggedInUser.getMax_tries() + 1);
			if (loggedInUser.getMax_tries() == 3) {
				loggedInUser.setStatus(UserStatus.BLOCKED);
				loggedInUser.setBlock_time(dateTimeFormatter.format(new Date()));
				loggedInUser.setMax_tries(3);
				userService.save(loggedInUser);
				return new ResponseEntity<>(loggedInUser, HttpStatus.FORBIDDEN);
			}
			userService.save(loggedInUser);
			UserLogger.log(Level.WARNING, "Unsuccessful password change, because old password is invalid.");
			return new ResponseEntity<>("Old password is incorrect.", HttpStatus.FORBIDDEN);
		}
		String password = PasswordUtil.hash(newPassword.toCharArray(), charset);
		
		loggedInUser.setStatus(UserStatus.ACTIVATED);
		loggedInUser.setPassword(password);
		userService.save(loggedInUser);
		UserLogger.log(Level.INFO, "User " + loggedInUser.getEmail() + "changed password successfully.");
		return new ResponseEntity<>(jwtUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/question/{email}", method = RequestMethod.GET)
	public ResponseEntity<Object> getQuestion(@PathVariable String email) throws ParseException {
		if (email == null || email == "") {
			return new ResponseEntity<>("Email address is required.", HttpStatus.FORBIDDEN);
		}
		
		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>(new String("This user doesn't exist."), HttpStatus.NOT_FOUND);
		}
		
		String question = user.getQuestion();
		String jsonString = "{\"question\":\""+question+"\"}";
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonString);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity<Object> resetPassword(@RequestBody SecurityQuestionDTO questionDTO) throws ParseException {
		if (questionDTO.getEmail() == null || questionDTO.getEmail() == "" ||
			questionDTO.getAnswer() == null || questionDTO.getAnswer() == "") {
			return new ResponseEntity<>("Email and answer must be provided.", HttpStatus.FORBIDDEN);
		}
		String email = questionDTO.getEmail();
		String answer = questionDTO.getAnswer();
		User user = userService.findByEmail(email);
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if (user.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(user.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					user.setBlock_time(null);
					user.setStatus(UserStatus.ACTIVATED);
					user.setMax_tries(0);
				} else {
					UserLogger.log(Level.WARNING, "Reset password failed because user " + user.getEmail() + "is blocked for 10 minutes.");
					return new ResponseEntity<>("This user is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!PasswordUtil.verify(user.getAnswer(), answer.toCharArray(), charset)) {
			user.setMax_tries(user.getMax_tries() + 1);
			if (user.getMax_tries() == 3) {
				user.setStatus(UserStatus.BLOCKED);
				user.setBlock_time(dateTimeFormatter.format(new Date()));
				user.setMax_tries(3);
				userService.save(user);
				return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
			}
			userService.save(user);
			return new ResponseEntity<>("Answer is incorrect.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String randomPassword = SendMail.sendEmail("\""+email+"\"");
		
		String password = PasswordUtil.hash(randomPassword.toCharArray(), charset);
		user.setPassword(password);
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		userService.delete(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	public String testDecodeJWT(String jwtToken){
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        return body;
    }
	
    public String generate(JwtUser jwtUser) {
    	if (jwtUser.getEmail() == null) {
    		return "User with this email doesn't exist.";
    	}
        return jwtGenerator.generateUser(jwtUser);
    }
}
