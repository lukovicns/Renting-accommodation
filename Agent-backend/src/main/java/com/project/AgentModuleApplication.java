package com.project;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpsConfigurator;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

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
	
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException, Exception {
		SpringApplication.run(AgentModuleApplication.class, args);
		
		//System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk1.8.0_172\\jre\\lib\\security\\jssecacerts");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		System.setProperty("javax.net.ssl.trustStore", "Agent-backend.p12");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		//java.lang.System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		
		Endpoint endpoint = Endpoint.create(new AccommodationWebService());
        SSLContext ssl =  SSLContext.getInstance("TLSv1.2");
         
         
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); 
        KeyStore store = KeyStore.getInstance("PKCS12");
 
 
        //Load the JKS file (located, in this case, at D:\keystore.jks, with password 'test'
        store.load(new FileInputStream(System.getProperty("user.dir")+"\\WS.p12"), "password".toCharArray()); 
 
        //init the key store, along with the password 'test'
        kmf.init(store, "password".toCharArray());
        KeyManager[] keyManagers = new KeyManager[1];
        keyManagers = kmf.getKeyManagers();
         
         
 
        //Init the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
 
        //It will reference the same key store as the key managers
        tmf.init(store);
         
        TrustManager[] trustManagers = tmf.getTrustManagers();
         
        ssl.init(keyManagers, trustManagers, new SecureRandom());
 
        //Init a configuration with our SSL context
        HttpsConfigurator configurator = new HttpsConfigurator(ssl);
         
 
        //Create a server on localhost, port 443 (https port)
        HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress("localhost", 9090), 9090);
        httpsServer.setHttpsConfigurator(configurator);
         
         
        //Create a context so our service will be available under this context
        HttpContext context = httpsServer.createContext("/Agent-backend/AccommodationWebService");
        httpsServer.start();
         
 
        //Finally, use the created context to publish the service
        endpoint.publish(context);
		
		/*Endpoint.publish("http://localhost:9090/Agent-backend/AccommodationWebService",
				new AccommodationWebService());*/
	
	}
}
