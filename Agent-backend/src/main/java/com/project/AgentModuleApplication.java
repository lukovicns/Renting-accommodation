package com.project;


import javax.xml.ws.Endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.project.web_service.AccommodationWebService;
//import com.project.web_service.impl.AccommodationWebServiceImpl;
import com.project.web_service.AccommodationWebService;

@SpringBootApplication
//@EnableJpaRepositories("com.project.repository")
public class AgentModuleApplication {

//	@Autowired
//	private static AccommodationWebService accWebService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AgentModuleApplication.class, args);
		
		Endpoint.publish("http://localhost:9090/Agent-backend/AccommodationWebService",
				new AccommodationWebService());
//		
//		Endpoint.publish("http://localhost:8090/Agent-backend/AccommodationWebService",
//				new AccommodationWebService());
//		
	}
	
	/*public static void test() {
		try {
			//Kreiranje web servisa
			URL wsdlLocation = new URL("http://localhost:8090/Agent-backend/accommodationWebService?wsdl");//
			QName serviceName = new QName("", "AccommodationWebService");
			QName portName = new QName("", "AccommodationWebServicePort");

			Service service = Service.create(wsdlLocation, serviceName);
			
			AccommodationWebService hello = service.getPort(portName, AccommodationWebService.class); 
			
			//Poziv web servisa
			String response = hello.print("string");
			System.out.println("Response 1 from WS: " + response);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}*/
}
