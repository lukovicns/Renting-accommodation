 package com.project.web_service;

 import java.io.BufferedInputStream;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
 import java.io.PrintWriter;
 import java.security.KeyStore;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.NoSuchProviderException;
 import java.security.UnrecoverableEntryException;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
 import java.util.Date;
 import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.xml.bind.DatatypeConverter;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
 import javax.xml.bind.DatatypeConverter;
 import javax.xml.bind.JAXBException;
 import javax.xml.crypto.MarshalException;
 import javax.xml.crypto.dsig.CanonicalizationMethod;
 import javax.xml.crypto.dsig.DigestMethod;
 import javax.xml.crypto.dsig.Reference;
 import javax.xml.crypto.dsig.SignatureMethod;
 import javax.xml.crypto.dsig.SignedInfo;
 import javax.xml.crypto.dsig.Transform;
 import javax.xml.crypto.dsig.XMLSignature;
 import javax.xml.crypto.dsig.XMLSignatureException;
 import javax.xml.crypto.dsig.XMLSignatureFactory;
 import javax.xml.crypto.dsig.dom.DOMSignContext;
 import javax.xml.crypto.dsig.keyinfo.KeyInfo;
 import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
 import javax.xml.crypto.dsig.keyinfo.X509Data;
 import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
 import javax.xml.crypto.dsig.spec.TransformParameterSpec;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import javax.xml.soap.SOAPException;
 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerException;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.dom.DOMSource;
 import javax.xml.transform.stream.StreamResult;

 import org.apache.http.HttpResponse;
 import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
 import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.jni.SSLContext;
import org.json.JSONException;
 import org.json.JSONObject;
 import org.json.XML;
 import org.json.simple.parser.ParseException;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestHeader;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.project.config.BlankingResolver;
import com.project.model.UserPrivileges;
import com.project.model.UserRoles;
import com.project.model.DTO.AccommodationDTO;
 import com.project.model.DTO.ApartmentDTO;
import com.project.model.DTO.MakeReservationDTO;
import com.project.model.DTO.PricePlanDTO;

import io.jsonwebtoken.Claims;
 import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(value="/restIntercepter")
public class IntercepterWebService {

	public static String url = "https://localhost:9090/Agent-backend/AccommodationWebService";
	
	public static String URL_ENCODING = "UTF-8";
	
	public static String CONTENT_TYPE = "text/xml";
	
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	public boolean hasRoleAndPrivilege(String token, UserRoles checkRole, UserPrivileges checkPrivilege) 
	{
		String role = getRoleFromToken(token);
		String privilege = getPrivilegeFromToken(token);
		
		if(!role.equals(checkRole.toString()))
			return false;
		if(privilege.equals(checkPrivilege.toString()) || privilege.equals(UserPrivileges.READ_WRITE_PRIVILEGE.toString()))
			return true;
		return false;
	}
	
	public String getRoleFromToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRETKEY"))
			       .parseClaimsJws(token.split(" ")[1]).getBody();
		return (String) claims.get("role");
	}
	
	public String getPrivilegeFromToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRETKEY"))
			       .parseClaimsJws(token.split(" ")[1]).getBody();
		return (String) claims.get("privilege");
	}
	
	@RequestMapping(value="/addAccommodation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addAccommodation(@Valid @RequestBody AccommodationDTO accommodation, BindingResult bindingResult, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid data pattern.", HttpStatus.BAD_REQUEST);
        }
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = " <ns2:addAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">" + 
				"<name>" + accommodation.getName() + "</name>" + 
				"<type>" + accommodation.getType() + "</type>" + 
				"<city>" + accommodation.getCity() + "</city>" + 
				"<street>" + accommodation.getStreet() + "</street>" + 
				"<description>" + accommodation.getDescription() + "</description>" + 
				"<category>" + accommodation.getCategory() + "</category>" +
				"<image>"  + accommodation.getImage() + "</image>" +
				"</ns2:addAccommodation>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		
		String filepath = "out.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);

		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
        
		System.out.println("poruka "+soap);
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
		if(xmlJSONObj.toString().contains("error"))
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:addAccommodationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
    
	public ResponseEntity<String> addApartment(@PathVariable String accommodationId, @Valid @RequestBody ApartmentDTO apartment, BindingResult bindingResult, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (bindingResult.hasErrors()) {
			System.out.println("add apartment " + bindingResult);
            return new ResponseEntity<>("Invalid data pattern.", HttpStatus.BAD_REQUEST);
        }
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = " <ns2:addApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<accommodationId>" + accommodationId + "</accommodationId>\r\n" +
				"<name>" + apartment.getName() + "</name>\r\n" + 
				"<bedType>" + apartment.getBedType() + "</bedType>\r\n" + 
				"<size>" + apartment.getSize() + "</size>\r\n" + 
				"<numOfRooms>" + apartment.getNumOfRooms() + "</numOfRooms>\r\n" + 
				"<numOfGuests>" + apartment.getNumOfGuests() + "</numOfGuests>\r\n" +
				"<description>" + apartment.getDescription() + "</description>\r\n" + 
				"<image>"  + apartment.getImage() + "</image>" +
				"<additionalService>"  + apartment.getAdditionalService() + "</additionalService>" +
				"<pricePlans>"  + apartment.getPricePlans() + "</pricePlans>" +
				"</ns2:addApartment>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		System.out.println("inte " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("error"))
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:addApartmentResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addReservation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addReservation(@PathVariable String id, @Valid @RequestBody MakeReservationDTO reservation, BindingResult bindingResult, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException, java.text.ParseException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		System.out.println(reservation.getStartDate() + " aa " + reservation.getEndDate());
		System.out.println("bb " + bindingResult);
		if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid data pattern.", HttpStatus.BAD_REQUEST);
        }
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		System.out.println("reservatio "+reservation.getStartDate());
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd/MM/yyyy";

		// August 12, 2010
		String oldDateString = reservation.getStartDate();
		String newDateString;
		
		String oldDateString1 = reservation.getEndDate();
		String newDateString1;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		SimpleDateFormat sdf1 = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		Date d1 = sdf1.parse(oldDateString1);
		sdf1.applyPattern(NEW_FORMAT);
		newDateString1 = sdf1.format(d1);
		
		String body = " <ns2:addReservation xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<apartmentId>" + id + "</apartmentId>\r\n" +
				"<startDate>" +newDateString+ "</startDate>\r\n" + 
				"<endDate>" + newDateString1 + "</endDate>\r\n" + 
				"</ns2:addReservation>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        System.out.println(xmlJSONObj);
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:addReservationResponse")).get("return"));
        System.out.println(xmlJSONObj);
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/addPricePlan/{apartmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addPricePlan(@PathVariable String apartmentId, @Valid @RequestBody PricePlanDTO pricePlan, BindingResult bindingResult, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		System.out.println("add price plan " + pricePlan.getEndDate() + pricePlan.getStartDate() + pricePlan.getPrice());
		
		if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid data pattern.", HttpStatus.BAD_REQUEST);
        }
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = " <ns2:addPricePlan xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<apartmentId>" + apartmentId + "</apartmentId>\r\n" +
				"<startDate>" + pricePlan.getStartDate() + "</startDate>\r\n" + 
				"<endDate>" + pricePlan.getEndDate() + "</endDate>\r\n" + 
				"<price>" + pricePlan.getPrice() + "</price>\r\n" + 
				"</ns2:addPricePlan>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
		if(xmlJSONObj.toString().contains("error"))
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:addPricePlanResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/confirmReservation/{id}")
	public ResponseEntity<String> confirmReservation(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = " <ns2:confirmReservation xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<id>" + id + "</id>\r\n" +
				"</ns2:confirmReservation>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		System.out.println("za rezervaciju " + xmlJSONObj);
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:confirmReservationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodationTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationTypes(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAccommodationTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAccommodationTypes>";
		
//		PrintWriter pwo = new PrintWriter("out.xml");
//		pwo.write("");
//		pwo.close();
//		
//		PrintWriter pw = new PrintWriter("test.xml");
//		pw.write("");
//		pw.close();
		System.out.println("types b " + body);
		signXml(body, email,"test2.xml", "out2.xml");
		File file = new File("out2.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        System.out.println("xml " + xmlJSONObj);
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAccommodationTypesResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations types available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getReservations(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getReservations xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getReservations>"; 
		
		signXml(body, email,"test.xml","out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        System.out.println(xmlJSONObj);
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getReservationsResponse")).get("return"));
        else
        	retVal.put("return", "There are no reservatoions.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodationCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationCategories(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAccommodationCategories xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAccommodationCategories>"; 

		signXml(body, email, "test1.xml", "out1.xml");
		File file = new File("out1.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAccommodationCategoriesResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations categories available.");
        
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getCities(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		System.out.println("token "+token);
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getCities xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getCities>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
//		fileStr = "<ns2" + fileStr.split("<ns2")[1];
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml cities " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getCitiesResponse")).get("return"));
        else
        	retVal.put("return", "No cities available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllAccommodations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllAccommodations(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAllAccommodations xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAllAccommodations>";
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAllAccommodationsResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getBedTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBedTypes(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body =  "<ns2:getBedTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getBedTypes>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getBedTypesResponse")).get("return"));
        else
        	retVal.put("return", "No bed types available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApartments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getApartments(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		System.out.println("id " + id);
		String body = "<ns2:getApartments xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "</ns2:getApartments>"; 
		
		signXml(body, email,"test1.xml", "out1.xml");
		File file = new File("out1.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        System.out.println("get " + xmlJSONObj);
        
        if(xmlJSONObj.toString().contains("error"))
        {	
        	System.out.println("error " + xmlJSONObj);
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getApartmentsResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApartment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getApartment(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ " </ns2:getApartment>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("error"))
        {	
        	System.out.println("error " + xmlJSONObj);
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getApartmentResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteAccommodation/{id}")
	public ResponseEntity<String> deleteAccommodation(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body =  "<ns2:deleteAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "</ns2:deleteAccommodation>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:deleteAccommodationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodation/{id}")
	public ResponseEntity<String> getAccommodation(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "</ns2:getAccommodation>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
		if(xmlJSONObj.toString().contains("error"))
        {	
        	System.out.println("error " + xmlJSONObj);
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
		
		JSONObject retVal =  new JSONObject();
		System.out.println("x " + xmlJSONObj);
		if(xmlJSONObj.toString().contains("return"))
			retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAccommodationResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
		
        
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteApartment/{id}")
	public ResponseEntity<String> deleteApartment(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:deleteApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "</ns2:deleteApartment>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:deleteApartmentResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPricePlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPricePlans(@RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getPricePlans xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getPricePlans>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getPricePlansResponse")).get("return"));
        else
        	retVal.put("return", "No price plan");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAdditionalServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAdditionalServices(@RequestHeader(value="Authorization") String token) throws KeyStoreException, ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAdditionalServices xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAdditionalServices>"; 
		
		signXml(body, email,"test1.xml", "out1.xml");
		File file = new File("out1.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();

		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	 retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAdditionalServicesResponse")).get("return"));
        else
        	retVal.put("return", "No additional services available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getAgentSentMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAgentSentMessages(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAgentSentMessages xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAgentSentMessages>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml sent messages " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAgentSentMessagesResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAgentReceivedMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAgentReceivedMessages(@RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAgentReceivedMessages xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "</ns2:getAgentReceivedMessages>"; 
		
		signXml(body, email,"test1.xml", "out1.xml");
		File file = new File("out1.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml received messages " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAgentReceivedMessagesResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAgentSentMessage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAgentSentMessage(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAgentSentMessage xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>"+id+"</id>"
				+ "</ns2:getAgentSentMessage>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml sent message " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAgentSentMessageResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAgentReceivedMessage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAgentReceivedMessage(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.READ_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:getAgentReceivedMessage xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>"+id+"</id>"
				+ "</ns2:getAgentReceivedMessage>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml received message " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:getAgentReceivedMessageResponse")).get("return"));
        else
        	retVal.put("return", "No messages available.");
		
		System.out.println(retVal.get("return"));
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/markAsReadAgentMessage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> markAsReadAgentMessage(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:markAsReadAgentMessage xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>"+id+"</id>"
				+ "</ns2:markAsReadAgentMessage>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml received message " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:markAsReadAgentMessageResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteAgentSentMessage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteAgentSentMessage(@PathVariable String id, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:deleteAgentSentMessage xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>"+id+"</id>"
				+ "</ns2:deleteAgentSentMessage>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml received message " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:deleteAgentSentMessageResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sendMessageToUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendMessageToUser(@PathVariable (name = "id") String id, @RequestBody String text, @RequestHeader(value="Authorization") String token) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, UnrecoverableEntryException, SAXException, ParserConfigurationException, MarshalException, XMLSignatureException, TransformerException
	{
		if(!hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		
		String email = getEmailFromToken(token);
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		
		String body = "<ns2:sendMessageToUser xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<messageId>"+id+"</messageId>" 
				+ "<messageText>"+text+"</messageText>"
				+ "</ns2:sendMessageToUser>"; 
		
		signXml(body, email,"test.xml", "out.xml");
		File file = new File("out.xml");
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
		                file));
		byte[] buffer = new byte[(int) file.length()];
		bin.read(buffer);
		String fileStr = new String(buffer);
		fileStr = fileStr.substring(54, fileStr.length());
		soap +=fileStr;
		soap += "</soap:Body></soap:Envelope>";
		bin.close();
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		System.out.println("xml received message " + xmlJSONObj);
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns3:sendMessageToUserResponse")).get("return"));
        else
        	retVal.put("return", "No messagess available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	public static JSONObject httpClientExecute(String soap) throws UnsupportedOperationException, IOException
	{
		HttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		
		HttpPost post = new HttpPost(url);
		
		// add header
		post.setHeader("Content-Type", CONTENT_TYPE);

		post.setEntity(new StringEntity(soap, URL_ENCODING));
		
		HttpResponse response = client.execute(post);
		
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		
		while ((line = rd.readLine()) != null) {
			result.append(line);
//			System.out.println("res " + result);
 		}

//		String json = "";
		JSONObject xmlJSONObj = null;
		
        try {
            xmlJSONObj = XML.toJSONObject(result.toString());
//            json = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        
        return xmlJSONObj;
	}
	
	public String getEmailFromToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRETKEY"))
			       .parseClaimsJws(token.split(" ")[1]).getBody();
		return (String) claims.get("email");
	}
	
	public void signXml(String xml,String email,String path, String outPath) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException, InvalidAlgorithmParameterException, SAXException, ParserConfigurationException, UnrecoverableEntryException, MarshalException, XMLSignatureException, TransformerException {
		
		KeyStore ks = KeyStore.getInstance("JKS", "SUN");
		String location = System.getProperty("user.dir").replace("Agent-backend", "Certificate-Generator");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(location + "\\KeyStore.jks"));
		ks.load(in, "sale131195".toCharArray());
		System.out.println(email);
		X509Certificate cert = (X509Certificate)ks.getCertificate(email);
		
		// Create a DOM XMLSignatureFactory that will be used to
		// generate the enveloped signature.
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Create a Reference to the enveloped document (in this case,
		// you are signing the whole document, so a URI of "" signifies
		// that, and also specify the SHA1 digest algorithm and
		// the ENVELOPED Transform.
		Reference ref = fac.newReference
		 ("", fac.newDigestMethod(DigestMethod.SHA1, null),
		  Collections.singletonList
		   (fac.newTransform
		    (Transform.ENVELOPED, (TransformParameterSpec) null)),
		     null, null);

		// Create the SignedInfo.
		SignedInfo si = fac.newSignedInfo
		 (fac.newCanonicalizationMethod
		  (CanonicalizationMethod.INCLUSIVE,
		   (C14NMethodParameterSpec) null),
		    fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
		     Collections.singletonList(ref));
		
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();
		x509Content.add(cert.getSubjectX500Principal().getName());
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
		
		FileWriter fw = new FileWriter(path);
        fw.write(xml);
        fw.close();

        File file1 = new File(path);
		BufferedInputStream bin1 = new BufferedInputStream(new FileInputStream(
		                file1));
		byte[] buffer1 = new byte[(int) file1.length()];
		bin1.read(buffer1);
		String fileStr1 = new String(buffer1);
		System.out.println("testfole " + fileStr1);
		bin1.close();		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String FEATURE = null;
	     try {
	       // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML entity attacks are prevented
	       // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
	       FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
	       dbf.setFeature(FEATURE, true);
	 
	       // If you can't completely disable DTDs, then at least do the following:
	       // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
	       // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
	       // JDK7+ - http://xml.org/sax/features/external-general-entities    
	       FEATURE = "http://xml.org/sax/features/external-general-entities";
	       dbf.setFeature(FEATURE, false);
	 
	       // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
	       // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
	       // JDK7+ - http://xml.org/sax/features/external-parameter-entities    
	       FEATURE = "http://xml.org/sax/features/external-parameter-entities";
	       dbf.setFeature(FEATURE, false);
	 
	       // Disable external DTDs as well
	       FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
	       dbf.setFeature(FEATURE, false);
	 
	       // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
	       dbf.setXIncludeAware(false);
	       dbf.setExpandEntityReferences(false);
	  
	       // And, per Timothy Morgan: "If for some reason support for inline DOCTYPEs are a requirement, then 
	       // ensure the entity settings are disabled (as shown above) and beware that SSRF attacks
	       // (http://cwe.mitre.org/data/definitions/918.html) and denial 
	       // of service attacks (such as billion laughs or decompression bombs via "jar:") are a risk."
	 
	       // remaining parser logic
	       } catch (ParserConfigurationException e) {
	             // This should catch a failed setFeature feature
	             System.out.println("ParserConfigurationException was thrown. The feature '" +
	                 FEATURE + "' is probably not supported by your XML processor.");
	       }
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		builder.setEntityResolver( new BlankingResolver() );
		Document doc = builder.parse
		    (new FileInputStream(path));
		
		KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(email, new KeyStore.PasswordProtection("sale131195".toCharArray()));
		
		// Create a DOMSignContext and specify the RSA PrivateKey and
		// location of the resulting XMLSignature's parent element.
		DOMSignContext dsc = new DOMSignContext
		    (keyEntry.getPrivateKey(), doc.getDocumentElement());

		// Create the XMLSignature, but don't sign it yet.
		XMLSignature signature = fac.newXMLSignature(si, ki);

		// Marshal, generate, and sign the enveloped signature.
		signature.sign(dsc);
		
		OutputStream os = new FileOutputStream(outPath);
		System.out.println("path out "+outPath);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		System.out.println(doc.toString());
		System.out.println(dsc);
	}
	
}
