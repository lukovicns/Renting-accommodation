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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.AgentStatus;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.model.DTO.AgentDTO;
import com.project.Rentingaccommodation.model.DTO.LoginDTO;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.CountryService;
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
	
	@Autowired
	private JwtValidator jwtValidator;
	
	@Autowired
	private CountryService countryService;
	
	private static final Charset charset = Charset.forName("UTF-8");
	
	public static String url = "http://localhost:8082/certificates/generateCertificate";

	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getAgents() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCountries",method = RequestMethod.GET)
	public ResponseEntity<List<Country>> getCountries() 
	{
		List<Country> countries = countryService.findAll();
        return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<City>> getCities(@PathVariable String id) 
	{
		List<City> cities = cityService.findByCountryId(Long.valueOf(id));
		
        return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getApprovedAgents() {
		return new ResponseEntity<>(service.findApprovedAgents(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/approved", method=RequestMethod.GET)
	public ResponseEntity<Object> getApprovedAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findApprovedAgent(agent), HttpStatus.OK);
	}
	
	@RequestMapping(value="/waiting", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getWaitingAgents() {
		return new ResponseEntity<>(service.findWaitingAgents(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/waiting", method=RequestMethod.GET)
	public ResponseEntity<Object> getWaitingAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findWaitingAgent(agent), HttpStatus.OK);
	}
	
	@RequestMapping(value="/declined", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getDeclinedAgents() {
		return new ResponseEntity<>(service.findDeclinedAgents(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/declined", method=RequestMethod.GET)
	public ResponseEntity<Object> getDeclinedAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findDeclinedAgent(agent), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/approve", method=RequestMethod.PUT)
	public ResponseEntity<Object> approveAgent(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (admin == null) {
					return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
				}
				Agent agent = service.findOne(id);
				if (agent == null) {
					return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
				}
				agent.setStatus(AgentStatus.APPROVED);
				return new ResponseEntity<>(service.save(agent), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/{id}/decline", method=RequestMethod.PUT)
	public ResponseEntity<Object> declineAgent(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (admin == null) {
					return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
				}
				Agent agent = service.findOne(id);
				if (agent == null) {
					return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
				}
				agent.setStatus(AgentStatus.DECLINED);
				return new ResponseEntity<>(service.save(agent), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/{id}/remove-approval", method=RequestMethod.PUT)
	public ResponseEntity<Object> removeApprovalOfAgent(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (admin == null) {
					return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
				}
				Agent agent = service.findOne(id);
				if (agent == null) {
					return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
				}
				agent.setStatus(AgentStatus.REMOVED_APPROVAL);
				return new ResponseEntity<>(service.save(agent), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
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
//		String question = agent.getQuestion();	
//		String answer = PasswordUtil.hash(agent.getAnswer().toCharArray(), charset);	
		
		Agent regAgent = new Agent(name, surname, password, email, city, street, phone, businessId, AgentStatus.WAITING);
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
		System.out.println("jfkajfkl " + loginDTO.getEmail() + " " + loginDTO.getPassword());
		Agent agent = service.findByEmail(loginDTO.getEmail());
		System.out.println("aaaa " + agent);
		System.out.println("aaaa " + agent.getEmail());
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		if (agent.getStatus().equals(AgentStatus.APPROVED)) {
			return new ResponseEntity<>("This agent is either declined or don't have approval.", HttpStatus.FORBIDDEN);
		}
		
		if(agent != null)
		{			
			System.out.println("agent");
			String verifyHash = agent.getPassword();
			String verifyPass = loginDTO.getPassword();
			
			if(!PasswordUtil.verify(verifyHash, verifyPass.toCharArray(), charset))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Session session = getSession(agent.getEmail());
			Transaction tx = session.beginTransaction();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".reservation;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".accommodation;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".apartment;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".message;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".accommodation_type;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".accommodation_category;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".image;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".city;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".country;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".bed_type;").executeUpdate();
			session.createNativeQuery("TRUNCATE db"+agent.getId()+".user;").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".reservation (SELECT * FROM renting_accommodation_db.reservation "
					+ "WHERE reservation_id IN (SELECT renting_accommodation_db.reservation.reservation_id FROM renting_accommodation_db.reservation "
					+ "INNER JOIN renting_accommodation_db.apartment ON renting_accommodation_db.reservation.apartment_id = renting_accommodation_db.apartment.apartment_id "
					+ "INNER JOIN renting_accommodation_db.accommodation "
					+ "ON renting_accommodation_db.apartment.accommodation_id = renting_accommodation_db.accommodation.accommodation_id "
					+ "WHERE agent_id="+agent.getId()+"));").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".accommodation (SELECT * FROM renting_accommodation_db.accommodation WHERE agent_id = "+ agent.getId()+")").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".apartment (SELECT * FROM renting_accommodation_db.apartment WHERE accommodation_id IN "
					+ "(SELECT renting_accommodation_db.accommodation.accommodation_id FROM renting_accommodation_db.accommodation "
					+ "INNER JOIN renting_accommodation_db.apartment ON "
					+ "renting_accommodation_db.accommodation.accommodation_id = renting_accommodation_db.apartment.accommodation_id WHERE agent_id= " + agent.getId() +"));").executeUpdate();
					
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".message (SELECT * FROM renting_accommodation_db.message WHERE agent_id = "+agent.getId()+")").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".accommodation_type (SELECT * FROM renting_accommodation_db.accommodation_type)").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".accommodation_category (SELECT * FROM renting_accommodation_db.accommodation_category)").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".image (SELECT * FROM renting_accommodation_db.image "
					+ "WHERE accommodation_id IN (SELECT renting_accommodation_db.accommodation.accommodation_id FROM renting_accommodation_db.accommodation "
					+ "WHERE agent_id ="+ agent.getId()+"));").executeUpdate();
					 
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".image (SELECT * FROM renting_accommodation_db.image "
					+ "WHERE apartment_id IN (SELECT renting_accommodation_db.apartment.apartment_id FROM renting_accommodation_db.apartment "
					+ "INNER JOIN renting_accommodation_db.accommodation ON renting_accommodation_db.accommodation.accommodation_id = renting_accommodation_db.apartment.accommodation_id WHERE agent_id ="+ agent.getId()+"));").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".city (SELECT * FROM renting_accommodation_db.city)").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".country (SELECT * FROM renting_accommodation_db.country)").executeUpdate();
			
			session.createNativeQuery("INSERT INTO db"+agent.getId()+".user (SELECT * FROM renting_accommodation_db.user)").executeUpdate();
			tx.commit();
			session.close();
			
			String token = generate(new JwtUser(agent.getId(), agent.getEmail(), UserRoles.AGENT.toString()));
			System.out.println("jwt " + new JwtUser(agent.getId(), agent.getEmail(), UserRoles.AGENT.toString()));
			
			
			System.out.println("toke " + token);
			response = new HashMap<String, Object>();
			response.put("token", token);
			System.out.println("res " + response);
			
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
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
        return jwtGenerator.generateAgent(jwtUser);
    }
}
