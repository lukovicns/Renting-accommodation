 package com.project.web_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.DTO.AccommodationDTO;
import com.project.model.DTO.ApartmentDTO;
import com.project.model.DTO.PricePlanDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/restIntercepter")
public class IntercepterWebService {

	public static String url = "http://localhost:9090/Agent-backend/AccommodationWebService";
	
	public static String URL_ENCODING = "UTF-8";
	
	public static String CONTENT_TYPE = "text/xml";
	
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	@RequestMapping(value="/addAccommodation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addAccommodation(@RequestBody AccommodationDTO accommodation/*String name, @RequestParam String type, @RequestParam String city, 
			@RequestParam String street, @RequestParam String description, @RequestParam String category*/ ) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "<soap:Body>\r\n" + 
				" <ns2:addAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<name>" + accommodation.getName() + "</name>\r\n" + 
				"<type>" + accommodation.getType() + "</type>\r\n" + 
				"<city>" + accommodation.getCity() + "</city>\r\n" + 
				"<street>" + accommodation.getStreet() + "</street>\r\n" + 
				"<description>" + accommodation.getDescription() + "</description>\r\n" + 
				"<category>" + accommodation.getCategory() + "</category>\r\n" +
				"<image>"  + accommodation.getImage() + "</image>" +
				"</ns2:addAccommodation>\r\n" + 
				"</soap:Body>\r\n" + 
				"</soap:Envelope>";
		// add header
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:addAccommodationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
    
	@RequestMapping(value="/addApartment/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addApartment(@PathVariable String accommodationId, @RequestBody ApartmentDTO apartment) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		System.out.println("rest " + apartment.getAdditionalService());
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "<soap:Body>\r\n" + 
				" <ns2:addApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
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
				"</ns2:addApartment>\r\n" + 
				"</soap:Body>\r\n" + 
				"</soap:Envelope>";
		// add header
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:addApartmentResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addPricePlan/{apartmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addPricePlan(@PathVariable String apartmentId, @RequestBody PricePlanDTO pricePlan) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		System.out.println("tanja");
		System.out.println("rest " + pricePlan.getEndDate());
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "<soap:Body>\r\n" + 
				" <ns2:addPricePlan xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<apartmentId>" + apartmentId + "</apartmentId>\r\n" +
				"<startDate>" + pricePlan.getStartDate() + "</startDate>\r\n" + 
				"<endDate>" + pricePlan.getEndDate() + "</endDate>\r\n" + 
				"<price>" + pricePlan.getPrice() + "</price>\r\n" + 
				"</ns2:addPricePlan>\r\n" + 
				"</soap:Body>\r\n" + 
				"</soap:Envelope>";
		// add header
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:addPricePlanResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodationTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationTypes() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAccommodationTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAccommodationTypes>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAccommodationTypesResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations types available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getReservations() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getReservations xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getReservations>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        System.out.println(xmlJSONObj);
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getReservationsResponse")).get("return"));
        else
        	retVal.put("return", "There are no reservatoions.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodationCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationCategories() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAccommodationCategories xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAccommodationCategories>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAccommodationCategoriesResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations categories available.");
        
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getCities() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getCities xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getCities>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
		
		if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getCitiesResponse")).get("return"));
        else
        	retVal.put("return", "No cities available.");
		
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllAccommodations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllAccommodations() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAllAccommodations xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAllAccommodations>\r\n</soap:Body>\r\n</soap:Envelope>";

		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAllAccommodationsResponse")).get("return"));
        else
        	retVal.put("return", "No accommodations available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getBedTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBedTypes() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getBedTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getBedTypes>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getBedTypesResponse")).get("return"));
        else
        	retVal.put("return", "No bed types available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApartments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getApartments(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getApartments xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:getApartments>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        System.out.println("get " + xmlJSONObj);
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getApartmentsResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApartment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getApartment(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:getApartment>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("error"))
        {	
        	System.out.println("error " + xmlJSONObj);
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getApartmentResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteAccommodation/{id}")
	public ResponseEntity<String> deleteAccommodation(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:deleteAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:deleteAccommodation>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:deleteAccommodationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAccommodation/{id}")
	public ResponseEntity<String> getAccommodation(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:getAccommodation>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		JSONObject retVal =  new JSONObject();
		
		if(xmlJSONObj.toString().contains("return"))
			retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAccommodationResponse")).get("return"));
		else if(!xmlJSONObj.toString().contains("ns2:getAccommodationResponse"))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        	retVal.put("return", "This accommodation has no apartments.");
		
        
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/deleteApartment/{id}")
	public ResponseEntity<String> deleteApartment(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:deleteApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:deleteApartment>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:deleteApartmentResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPricePlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPricePlans() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getPricePlans xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getPricePlans>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getPricePlansResponse")).get("return"));
        else
        	retVal.put("return", "No price plan");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAdditionalServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAdditionalServices() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAdditionalServices xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAdditionalServices>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	 retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAdditionalServicesResponse")).get("return"));
        else
        	retVal.put("return", "No additional services available.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	public static JSONObject httpClientExecute(String soap) throws UnsupportedOperationException, IOException
	{
		HttpClient client = HttpClientBuilder.create().build();
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
}
