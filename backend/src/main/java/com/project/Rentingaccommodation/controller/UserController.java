package com.project.Rentingaccommodation.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
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
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.model.DTO.PasswordChangeDTO;
import com.project.Rentingaccommodation.model.DTO.SecurityQuestionDTO;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.service.AdminService;
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
	private CityService cityService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		userService.delete(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		if (user.getName() == null || user.getName() == "" ||
			user.getSurname() == null || user.getSurname() == "" ||
			user.getEmail() == null || user.getEmail() == "" ||
//			user.getCity() == null ||
			user.getStreet() == null || user.getStreet() == "" ||
			user.getPhone() == null || user.getPhone() == "" ||
			user.getPassword() == null || user.getPassword() == "" ||
			user.getQuestion() == null || user.getQuestion() == "" ||
			user.getAnswer() == null || user.getAnswer() == "") {
			return new ResponseEntity<>("All fields are required (name, surname, email, street, phone, password, question, answer).", HttpStatus.FORBIDDEN);
		}
		
		if (UserUtils.userExists(user.getEmail(), userService, adminService)) {
			return new ResponseEntity<>("User with this email already exists.", HttpStatus.FORBIDDEN);
		}
		
		if (user.getPassword().length() < 8) {
			return new ResponseEntity<>("Password must be at least 8 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		Charset charset = Charset.forName("UTF-8");
		
		String name = user.getName();
		String surname = user.getSurname();
		String email = user.getEmail();
		City city = cityService.findOne(Long.valueOf(1));
		String street = user.getStreet();
		String phone = user.getPhone();
		String password = PasswordUtil.hash(user.getPassword().toCharArray(), charset);
		String question = user.getQuestion();
		String answer = PasswordUtil.hash(user.getAnswer().toCharArray(), charset);	
		
		User regUser = new User(name, surname, password, email, city, street, phone, question, answer);	
		userService.save(regUser);
		
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
		
//		if (u.getMax_tries() == 3) {
//			return new ResponseEntity<>("Max tries 3.", HttpStatus.FORBIDDEN);
//		}
		
		String verifyHash = u.getPassword();
		String verifyPass = user.getPassword();
		
		if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset)) {
//			u.setMax_tries(u.getMax_tries() + 1);
//			userService.save(u);
			return new ResponseEntity<>("Password is invalid.", HttpStatus.UNAUTHORIZED);
		}
		
		String token = generate(new JwtUser(u.getEmail(), UserRoles.USER.toString()));
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("token", token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeDTO passDTO) throws ParseException {
		if (passDTO.getOldPassword() == null || passDTO.getOldPassword() == "" ||
			passDTO.getNewPassword() == null || passDTO.getNewPassword() == "" ||
			passDTO.getToken() == null || passDTO.getToken() == "") {
			return new ResponseEntity<>("Old password, new password and token must be provided.", HttpStatus.FORBIDDEN);
		}
		
		if (passDTO.getNewPassword().length() < 8) {
			return new ResponseEntity<>("Password must be at least 8 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String oldPassword = passDTO.getOldPassword();
		String newPassword = passDTO.getNewPassword();
		
		String body = testDecodeJWT(passDTO.getToken());
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(body);
		String email = (String) json.get("email");
		
		User loggedInUser =  userService.findByEmail(email);
		String verifyHash = loggedInUser.getPassword();
		
		if(!PasswordUtil.verify(verifyHash, oldPassword.toCharArray(), charset)) {
			return new ResponseEntity<>("Old password is incorrect.", HttpStatus.FORBIDDEN);
		}

		String password = PasswordUtil.hash(newPassword.toCharArray(), charset);
		
		loggedInUser.setPassword(password);
		userService.save(loggedInUser);
		return new ResponseEntity<>("Password is successfully changed.", HttpStatus.OK);
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
		
		if(!PasswordUtil.verify(user.getAnswer(), answer.toCharArray(), charset)) {
			return new ResponseEntity<>("Answer is incorrect.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String randomPassword = SendMail.sendEmail("\""+email+"\"");
		
		String password = PasswordUtil.hash(randomPassword.toCharArray(), charset);
		user.setPassword(password);
		userService.save(user);
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
