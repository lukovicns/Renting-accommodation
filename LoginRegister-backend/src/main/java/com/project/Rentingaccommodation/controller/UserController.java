package com.project.Rentingaccommodation.controller;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.project.Rentingaccommodation.LoginRegisterBackendApplication;
import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.model.Token;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.model.DTO.AgentDTO;
import com.project.Rentingaccommodation.model.DTO.LoginDTO;
import com.project.Rentingaccommodation.model.DTO.PasswordChangeDTO;
import com.project.Rentingaccommodation.model.DTO.SecurityQuestionDTO;
import com.project.Rentingaccommodation.model.DTO.UserDTO;
import com.project.Rentingaccommodation.security.JWTGenerator;
import com.project.Rentingaccommodation.security.JWTUser;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.CountryService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.PasswordUtil;
import com.project.Rentingaccommodation.utils.SendMail;

import net.bytebuddy.utility.visitor.StackAwareMethodVisitor;

@RestController
@RequestMapping(value="/api/users")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	private static final Charset charset = Charset.forName("UTF-8");
	
	public static String url = "http://localhost:8081/certificates/generateCertificate";
	
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
	
	@RequestMapping(value = "/register/agent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerAgent(@RequestBody AgentDTO agent) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException, ParserConfigurationException, SAXException
	{
		System.out.println("agent "+agent.getEmail());
		Agent unique = agentService.findByEmail(agent.getEmail());
		Charset charset = Charset.forName("UTF-8");
		
		if(unique != null)
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		
		String name = agent.getName();
		String surname = agent.getSurname();
		String businessId = agent.getBusinessId();
		String email = agent.getEmail();
		City city = cityService.findOne(Long.valueOf(agent.getCity()));
		String street = agent.getStreet();
		String phone = agent.getPhone();
		String password = PasswordUtil.hash(agent.getPassword().toCharArray(), charset);
		String question = agent.getQuestion();	
		String answer = PasswordUtil.hash(agent.getAnswer().toCharArray(), charset);	
		
		Agent regAgent = new Agent(name, surname, password, email, city, street, phone, question, answer, businessId);
		agentService.save(regAgent);
		
		try {
			createCertificate(regAgent.getEmail());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buildSessionFactory(agent.getEmail());
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	private void buildSessionFactory(String email) {
		Configuration conf = new Configuration().configure();
	      // <!-- Database connection settings -->
        String url = "jdbc:mysql://localhost:3306/db" + agentService.findByEmail(email).getId() + "?createDatabaseIfNotExist=true&useSSL=false";

	    conf.setProperty("hibernate.connection.url", url);
	    conf.setProperty("hibernate.hbm2ddl.auto", "create");
		SessionFactory sessionFactory = conf.buildSessionFactory();
		sessionFactory.close();
	}
	
	public Session getSession(String email) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
			Configuration conf = new Configuration().configure();
				// <!-- Database connection settings -->
	        String url = "jdbc:mysql://localhost:3306/db" + agentService.findByEmail(email).getId() + "?createDatabaseIfNotExist=true&useSSL=false";
	
		    conf.setProperty("hibernate.connection.url", url);
			SessionFactory sessionFactory = conf.buildSessionFactory();
			
			return sessionFactory.openSession();
	}

	@RequestMapping(value = "/register/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody UserDTO user)
	{
		System.out.println("user "+user.getEmail());
		User unique = userService.findByEmail(user.getEmail());
		Charset charset = Charset.forName("UTF-8");
		
		if(unique != null)
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		
		String name = user.getName();
		String surname = user.getSurname();
		String email = user.getEmail();
		System.out.println("grad "+user.getCity());
		City city = cityService.findOne(Long.valueOf(user.getCity()));
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
	public ResponseEntity<Token> loginUser(@RequestBody LoginDTO loginDTO) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		User user = userService.findByEmail(loginDTO.getEmail());
		if(user != null)
		{			
			String verifyHash = user.getPassword();
			String verifyPass = loginDTO.getPassword();
			
			if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Token token = generate(new JWTUser(user.getEmail()));
			
			return new ResponseEntity<Token>(token, HttpStatus.OK);
		}
		
		Agent agent = agentService.findByEmail(loginDTO.getEmail());
		if(agent != null)
		{			
			System.out.println("agent");
			String verifyHash = agent.getPassword();
			String verifyPass = loginDTO.getPassword();
			
			if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			System.out.println(agent.getEmail());
			JWTUser jwtUser = new JWTUser(agent.getEmail());
			Token token = new Token(jwtGenerator.generateAgentToken(jwtUser), null);
			
			Session session = getSession(agent.getEmail());
			session.createNamedQuery("TRUNCATE db"+agent.getId()+".reservation;");
			session.createNamedQuery("TRUNCATE db"+agent.getId()+".message;");
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".reservation (SELECT * FROM renting_accommodation_db.reservation WHERE reservation_id IN (SELECT renting_accommodation_db.reservation.reservation_id FROM renting_accommodation_db.reservation INNER JOIN renting_accommodation_db.apartment ON renting_accommodation_db.reservation.apartment_id = renting_accommodation_db.apartment.apartment_id INNER JOIN renting_accommodation_db.accommodation ON renting_accommodation_db.apartment.accommodation_id = renting_accommodation_db.accommodation.accommodation_id WHERE agent_id="+agent.getId()+";)) order by renting_accommodation_db.reservation.reservation_id DESC;");
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".message (SELECT * FROM renting_accommodation_db.message WHERE agent_id = "+agent.getId()+")");
			session.close();
			
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
        return new Token(jwtGenerator.generateUserToken(jwtUser), null);
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
	
	@RequestMapping(value = "/getCountries",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Country>> getCountries() 
	{
		List<Country> countries = countryService.findAll();
        return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<City>> getCities(@PathVariable Long id) 
	{
		List<City> cities = cityService.findByCountry(id);
		
        return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	public void createCertificate(String email) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(email,charset));
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " 
                + response.getStatusLine().getStatusCode());

	}
}
