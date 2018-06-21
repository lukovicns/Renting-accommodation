package com.project.Rentingaccommodation.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.model.DTO.AgentDTO;
import com.project.Rentingaccommodation.model.DTO.LoginDTO;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.PasswordUtil;
import com.project.Rentingaccommodation.utils.UserUtils;

@RestController
@RequestMapping(value = "/api/agents")
public class AgentController {

	@Autowired
	private AgentService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	private static final Charset charset = Charset.forName("UTF-8");
	
	public static String url = "http://localhost:8082/certificates/generateCertificate";

	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getAgents() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> registerAgent(@RequestBody AgentDTO agent) {
		
		if (UserUtils.userExists(agent.getEmail(), userService, adminService, service)) {
			return new ResponseEntity<>("User with this email already exists.", HttpStatus.FORBIDDEN);
		}
		
		if (agent.getPassword().length() < 8) {
			return new ResponseEntity<>("Password must be at least 8 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String name = agent.getName();
		String surname = agent.getSurname();
		int businessId = agent.getBusinessId();
		String email = agent.getEmail();
		City city = cityService.findOne(Long.valueOf(agent.getCity()));
		String street = agent.getStreet();
		String phone = agent.getPhone();
		String password = PasswordUtil.hash(agent.getPassword().toCharArray(), charset);
		String question = agent.getQuestion();	
		String answer = PasswordUtil.hash(agent.getAnswer().toCharArray(), charset);	
		
		Agent regAgent = new Agent(name, surname, password, email, city, street, phone, businessId);
		service.save(regAgent);
		
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
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<Object> loginAgent(@RequestBody LoginDTO loginDTO) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		Agent agent = service.findByEmail(loginDTO.getEmail());
		if(agent != null)
		{			
			System.out.println("agent");
			String verifyHash = agent.getPassword();
			String verifyPass = loginDTO.getPassword();
			
			if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Session session = getSession(agent.getEmail());
			session.createNamedQuery("TRUNCATE db"+agent.getId()+".reservation;");
			session.createNamedQuery("TRUNCATE db"+agent.getId()+".message;");
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".reservation (SELECT * FROM renting_accommodation_db.reservation WHERE reservation_id IN (SELECT renting_accommodation_db.reservation.reservation_id FROM renting_accommodation_db.reservation INNER JOIN renting_accommodation_db.apartment ON renting_accommodation_db.reservation.apartment_id = renting_accommodation_db.apartment.apartment_id INNER JOIN renting_accommodation_db.accommodation ON renting_accommodation_db.apartment.accommodation_id = renting_accommodation_db.accommodation.accommodation_id WHERE agent_id="+agent.getId()+";)) order by renting_accommodation_db.reservation.reservation_id DESC;");
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".message (SELECT * FROM renting_accommodation_db.message WHERE agent_id = "+agent.getId()+")");
			session.close();
			
			String token = generate(new JwtUser(agent.getId(), agent.getEmail(), UserRoles.AGENT.toString()));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("token", token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
	}
	
	private void buildSessionFactory(String email) {
		Configuration conf = new Configuration().configure();
	      // <!-- Database connection settings -->
        String url = "jdbc:mysql://localhost:3306/db" + service.findByEmail(email).getId() + "?createDatabaseIfNotExist=true&useSSL=false";

	    conf.setProperty("hibernate.connection.url", url);
	    conf.setProperty("hibernate.hbm2ddl.auto", "create");
		SessionFactory sessionFactory = conf.buildSessionFactory();
		sessionFactory.close();
	}
	
	public Session getSession(String email) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Configuration conf = new Configuration().configure();
			// <!-- Database connection settings -->
	    String url = "jdbc:mysql://localhost:3306/db" + service.findByEmail(email).getId() + "?createDatabaseIfNotExist=true&useSSL=false";
	    conf.setProperty("hibernate.connection.url", url);
		SessionFactory sessionFactory = conf.buildSessionFactory();
			
		return sessionFactory.openSession();
	}
	
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/business-id/{businessId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgentByBusinessId(@PathVariable Long businessId) {
		Agent agent = service.findByBusinessId(businessId);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);	
	}
	
	public void createCertificate(String email) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(email,charset));
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " 
                + response.getStatusLine().getStatusCode());

	}
	
	public String generate(JwtUser jwtUser) {
    	if (jwtUser.getEmail() == null) {
    		return "User with this email doesn't exist.";
    	}
        return jwtGenerator.generateUser(jwtUser);
    }
}
