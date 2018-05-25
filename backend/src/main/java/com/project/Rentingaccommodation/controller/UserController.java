package com.project.Rentingaccommodation.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.Token;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.model.DTO.PasswordChangeDTO;
import com.project.Rentingaccommodation.model.DTO.SecurityQuestionDTO;
import com.project.Rentingaccommodation.security.JWTGenerator;
import com.project.Rentingaccommodation.security.JWTUser;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.PasswordUtil;
import com.project.Rentingaccommodation.utils.SendMail;

@RestController
@RequestMapping(value="/api/users")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	private static final Charset charset = Charset.forName("UTF-8");
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody User user)
	{
		System.out.println("user "+user.getEmail());
		User unique = userService.findByEmail(user.getEmail());
		Charset charset = Charset.forName("UTF-8");
		
		if(unique != null)
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		
		String name = user.getName();
		String surname = user.getSurname();
		String email = user.getEmail();
		City city = cityService.findOne(Long.valueOf(1));
		String street = user.getStreet();
		String phone = user.getPhone();
		String password = PasswordUtil.hash(user.getPassword().toCharArray(), charset);
		String question = user.getQuestion();	
		System.out.println("que "+user.getQuestion());
		System.out.println("ans "+user.getAnswer());
		String answer = PasswordUtil.hash(user.getAnswer().toCharArray(), charset);	
		
		User regUser = new User(name, surname, password, email, city, street, phone, question ,answer);
		
		regUser.setStatus(UserStatus.ACTIVATED);		
		userService.save(regUser);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Token> loginUser(@RequestBody User user)
	{
		if(user != null)
		{
			User u = userService.findByEmail(user.getEmail());
			
			if(u == null)
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			String verifyHash = u.getPassword();
			String verifyPass = user.getPassword();
			
			if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Token token = generate(new JWTUser(u.getEmail()));
			
			return new ResponseEntity<Token>(token, HttpStatus.OK);
		}
		
		return new ResponseEntity<Token>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDTO passDTO) throws ParseException
	{
		String oldPassword = passDTO.getOldPassword();
		String newPassword = passDTO.getNewPassword();
		
		String body = testDecodeJWT(passDTO.getToken());
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(body);
		String email = (String) json.get("email");
		
		User loggedInUser =  userService.findByEmail(email);
		String verifyHash = loggedInUser.getPassword();
		
		if(!PasswordUtil.verify(verifyHash, oldPassword.toCharArray(), charset))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		String password = PasswordUtil.hash(newPassword.toCharArray(), charset);
		
		loggedInUser.setPassword(password);
		userService.save(loggedInUser);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public String testDecodeJWT(String jwtToken){
        String[] split_string = jwtToken.split("\\.");
//        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
//        String base64EncodedSignature = split_string[2];

        Base64 base64Url = new Base64(true);
//        String header = new String(base64Url.decode(base64EncodedHeader));
        String body = new String(base64Url.decode(base64EncodedBody));
        
        return body;
    }
	
	@RequestMapping(value = "/question/{email}", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getQuestion(@PathVariable String email) throws ParseException{
		System.out.println("email "+email);
		User user = userService.findByEmail(email);
		String question = user.getQuestion();//.replaceAll("\\?", "%3F");
		System.out.println("quest "+question);
		String jsonString = "{\"question\":\""+question+"\"}";
		System.out.println(jsonString);
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonString);
		return new ResponseEntity<JSONObject>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<Void> resetPassword(@RequestBody SecurityQuestionDTO questionDTO) throws ParseException
	{
		String email = questionDTO.getEmail();
		String answer = questionDTO.getAnswer();
		System.out.println("ans "+questionDTO.getAnswer());
		User user = userService.findByEmail(email);
		System.out.println(PasswordUtil.verify(user.getAnswer(), answer.toCharArray(), charset));
		if(!PasswordUtil.verify(user.getAnswer(), answer.toCharArray(), charset)) {
			System.out.println("netacno");
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		/*JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(email);*/
		//String jsonEmail = (String) json.get("email");
		String randomPassword = SendMail.sendEmail("\""+email+"\"");
		
		String password = PasswordUtil.hash(randomPassword.toCharArray(), charset);
		user.setPassword(password);
		userService.save(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    public Token generate(JWTUser jwtUser) {
    	if (jwtUser.getEmail() == null) {
    		return null;
    	}
        return new Token(jwtGenerator.generate(jwtUser), null);
    }
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User user = userService.delete(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
