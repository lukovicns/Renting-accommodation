package com.project.web_service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.xpath.XPathExpressionException;

import org.assertj.core.api.filter.InFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.w3._2000._09.xmldsig.SignatureType;
import org.xml.sax.SAXException;

import com.project.model.Direction;
import com.project.model.User;
import com.project.model.Accommodation;
import com.project.model.AccommodationCategory;
import com.project.model.AccommodationType;
import com.project.model.AdditionalService;
import com.project.model.Agent;
import com.project.model.Apartment;
import com.project.model.ApartmentAdditionalService;
import com.project.model.BedType;
import com.project.model.City;
import com.project.model.DeleteStatus;
import com.project.model.Image;
import com.project.model.Message;
import com.project.model.MessageStatus;
import com.project.model.PricePlan;
import com.project.model.Reservation;
import com.project.model.ReservationStatus;
import com.project.model.ReservationStatus;
import com.project.model.DTO.AccommodationDTO;
import com.project.model.DTO.ApartmentDTO;
import com.project.model.DTO.MessageDTO;
import com.project.model.DTO.ReservationDTO;
import com.project.repository.AccommodationCategoryRepository;
import com.project.repository.AccommodationRepository;
import com.project.repository.AccommodationTypeRepository;
import com.project.repository.AdditionalServiceRepository;
import com.project.repository.AgentRepository;
import com.project.repository.ApartmentRepository;
import com.project.repository.ApartmentAdditionalServiceRepository;
import com.project.repository.BedTypeRepository;
import com.project.repository.CityRepository;
import com.project.repository.ImageRepository;
import com.project.repository.MessageRepository;
import com.project.repository.PricePlanRepository;
import com.project.repository.ReservationRepository;
import com.project.service.AccommodationCategoryService;
import com.project.service.AccommodationService;
import com.project.service.AccommodationTypeService;
import com.project.service.AdditionalServiceService;
import com.project.service.AgentService;
import com.project.service.ApartmentAdditionalServiceService;
import com.project.service.ApartmentAdditionalServiceService;
import com.project.service.ApartmentService;
import com.project.service.BedTypeService;
import com.project.service.CityService;
import com.project.service.ImageService;
import com.project.service.MessageService;
import com.project.service.PricePlanService;
import com.project.service.ReservationService;
import com.project.service.impl.JpaAccommodationCategoryService;
import com.project.service.impl.JpaAccommodationService;
import com.project.service.impl.JpaAccommodationTypeService;
import com.project.service.impl.JpaAdditionalServiceService;
import com.project.service.impl.JpaAgentService;
import com.project.service.impl.JpaApartmentService;
import com.project.service.impl.JpaApartmentAdditionalServiceService;
import com.project.service.impl.JpaBedTypeService;
import com.project.service.impl.JpaCityService;
import com.project.service.impl.JpaImageService;
import com.project.service.impl.JpaMessageService;
import com.project.service.impl.JpaPricePlanService;
import com.project.service.impl.JpaReservationService;

@CrossOrigin(origins = "http://localhost:4200")
@WebService(serviceName = "AccommodationWebService",
			targetNamespace = "http://com.project/web_service/wrappers")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class AccommodationWebService {
	
	private HashMap<String, SessionFactory> sessions = new HashMap<>();
	
//	instantiating repositories and services
	@Autowired
	private AccommodationRepository accRepository;
	
	@Autowired
	private AccommodationTypeRepository accTypeRepository;
	
	@Autowired
	private AccommodationCategoryRepository accCatRepository;
	
	@Autowired 
	private CityRepository cityRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ApartmentAdditionalServiceRepository apartmentServiceRepository;
	
	@Autowired
	private BedTypeRepository bedTypeRepository;
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	@Autowired
	private PricePlanRepository pricePlanRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	AccommodationService accService = new JpaAccommodationService(accRepository);
	
	AccommodationTypeService accTypeService = new JpaAccommodationTypeService(accTypeRepository);
	
	AccommodationCategoryService accCategoryService = new JpaAccommodationCategoryService(accCatRepository);
	
	CityService cityService = new JpaCityService(cityRepository);
	
	AgentService agentService = new JpaAgentService(agentRepository);
	
	ImageService imageService = new JpaImageService(imageRepository);
	
	ApartmentService apartmentService = new JpaApartmentService(apartmentRepository);
	
	ApartmentAdditionalServiceService apartmentAdditionalService = new JpaApartmentAdditionalServiceService(apartmentServiceRepository);
	
	BedTypeService bedTypeService = new JpaBedTypeService(bedTypeRepository);
	
	AdditionalServiceService additionalServiceService = new JpaAdditionalServiceService(additionalServiceRepository);
	
	PricePlanService pricePlanService = new JpaPricePlanService(pricePlanRepository);
	
	ReservationService reservationService = new JpaReservationService(reservationRepository);
	
	MessageService messageService = new JpaMessageService(messageRepository);
	
//	instantiating repositories and services
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddAccommodationRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddAccommodationResponse")
	public String addAccommodation(@WebParam(name = "name") String name, @WebParam(name = "type") String type,
			@WebParam(name = "city") String city, @WebParam(name = "street") String street, @WebParam(name = "description") String description,
			@WebParam(name = "category") String category, @WebParam(name = "image") String image,
			@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		
		AccommodationType accType = accTypeService.findOne(Long.valueOf(type));
		AccommodationCategory accCategory = accCategoryService.findOne(Long.valueOf(category));
		Object accCity = cityService.findOne(Long.valueOf(city));
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		System.getProperty("user.dir");
		System.out.println("location " + System.getProperty("user.dir"));
		
//		Agent agent = agentService.findOne(Long.valueOf("1"));
		Agent agent = session.createNativeQuery("select * from agent", Agent.class).getSingleResult();
				
		System.out.println("agent u acc " + agent.getId() + " " + agent.getEmail() );
		Accommodation newAccommodation = new Accommodation(name, accType, (City) accCity, street, description, accCategory, agent);

		
		String projectLocation = System.getProperty("user.dir").toString().replace("backend", "frontend");
		System.out.println("prm " + projectLocation);
		System.out.println(image);
		String retVal = "";
		String[] splits = image.split("ovo-je-separator");
		
		Accommodation saved = accService.save(newAccommodation);
		Long id = null;
		File outputfile = null;
		int size = -1;
		int imgNameCounter = -1;
		
//		List<Image> images= imageService.findAll();
		System.out.println("split "+splits.length);
		for(int i = 0; i < splits.length; i++)
		{
			List<Image> images= imageService.findAll();
			System.out.println("sss " + splits[i]);
			String[] parts = splits[i].split(",");
			String imageString = parts[1];

			// create a buffered image
			BufferedImage img = null;
			byte[] imageByte;
	
			imageByte = Base64.getDecoder().decode(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			img = ImageIO.read(bis);
			bis.close();
			
			if(images.size() == 0)
			{	
				outputfile = new File(projectLocation + "\\src\\assets\\imgs\\out0.png");
				System.out.println("i tu 1" + imgNameCounter);
				ImageIO.write(img, "png", outputfile);
			}else 
			{
				outputfile = new File(projectLocation + "\\src\\assets\\imgs\\out" + images.size() + ".png");
				System.out.println("i tu 1" + imgNameCounter);
				ImageIO.write(img, "png", outputfile);
			}
			
			if(saved != null)
			{	
				Image addImg = new Image(outputfile.toString(), saved, null);
				Image im = imageService.save(addImg);
				
				if(im != null)
					session.createNativeQuery("insert into image values(" + im.getId() + ",'" +
							im.getUrl() + "'," + saved.getId() + "," + null + ")").executeUpdate();
			}
		}
		
		System.out.println("aaa id " + id);
		session.createNativeQuery("insert into accommodation values(" + saved.getId() + ",'" +
		saved.getDescription() + "','" + saved.getName() + "','ACTIVE','" + saved.getStreet() + "'," + saved.getAgent().getId() + "," + 
		saved.getCategory().getId() + "," +
		saved.getCity().getId() + "," + saved.getType().getId() + ")").executeUpdate();
		
		retVal = "Accommodation successfully added";
		
		tx.commit();
		session.close();
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddApartmentRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddApartmentResponse")
	public String addApartment(@WebParam(name="accommodationId") String accommodationId, @WebParam(name = "name") String name, @WebParam(name = "bedType") String bedType,
			@WebParam(name = "size") String size, 
			@WebParam(name = "numOfRooms") String numOfRooms, 
			@WebParam(name = "numOfGuests") String numOfGuests,
			@WebParam(name = "description") String description,
			@WebParam(name = "image") String image, @WebParam(name = "additionalService") String additionalJSON,
			@WebParam(name = "pricePlans") String pricePlansJSON,
			@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws IOException, ParseException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		BedType bt = bedTypeService.findOne(Long.valueOf(bedType));
		Optional<Accommodation> accDb= accService.findOne(Long.valueOf(accommodationId));
		Accommodation accommodation = accDb.get();
		Apartment newApartment = new Apartment(name, bt, description, accommodation, Integer.parseInt(size), Integer.parseInt(numOfGuests), Integer.parseInt(numOfRooms));
		
		List<AdditionalService> tempArray = new ArrayList<>();
		Optional<AdditionalService> additionalService = null;
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		String temp = additionalJSON.substring(1, additionalJSON.length() - 1);
		String[] array = temp.split(",");
		
		JSONParser parser = new JSONParser();
		JSONArray json = (JSONArray) parser.parse(pricePlansJSON);

		String projectLocation = System.getProperty("user.dir").toString().replace("backend", "frontend");
		
		List<PricePlan> pricePlans = new ArrayList<>();
		
		for(int i = 0; i < json.size(); i++)
		{
			String str = json.get(i).toString();
			String s = str.substring(1, str.length() - 1);
			String[] strs = s.split(",");
			String endDate = strs[0].split(":")[1];
			String price = strs[1].split(":")[1];
			String startDate = strs[2].split(":")[1];
			
			pricePlans.add(new PricePlan(startDate.substring(1, startDate.length() - 1), endDate.substring(1, endDate.length() - 1), Integer.parseInt(price)));
		}

		for(int i = 0; i < array.length; i++)
		{
			additionalService = additionalServiceService.findOne(Long.valueOf(array[i]));
			tempArray.add(additionalService.get());
		}
		
		// tokenize the data
		String retVal = "";
		String[] splits = image.split("ovo-je-separator");
		File outputfile = null;
		
		System.out.println("image ceo " + image);
		
		Apartment saved = apartmentService.save(newApartment);
		
		if(saved != null)
		{
			System.out.println("splits " + splits.length);
			for(int i = 0; i < splits.length; i++)
			{
				String[] parts = splits[i].split(",");
				String imageString = parts[1];
				System.out.println("sss " + splits[i]);
				// create a buffered image
				BufferedImage img = null;
				byte[] imageByte;
		
				imageByte = Base64.getDecoder().decode(imageString);
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				img = ImageIO.read(bis);
				bis.close();
			
				List<Image> images= imageService.findAll();
				int imgNameCounter = -1;
				
				if(images.size() != 0)
				{	System.out.println("usao");
					for(int  j = 0; j < images.size() - 1; j++)
					{	if(images.get(j).getId() < images.get(j+1).getId())
							imgNameCounter = images.get(j+1).getId().intValue() + 1;
						System.out.println("i tu " );
						
						outputfile = new File(projectLocation + "\\src\\assets\\imgs\\out" + imgNameCounter + ".png");
						ImageIO.write(img, "png", outputfile);
					}
					
				}
				
				Image addImg = new Image(outputfile.toString(), saved);
				Image im = imageService.save(addImg);
				System.out.println("im " + im.getId());
				
				if(saved != null)
				if(im != null)
					session.createNativeQuery("insert into image values(" + im.getId() + ",'" +
							im.getUrl() + "'," + null + "," + saved.getId() + ")").executeUpdate();
				
			}
			
			System.out.println("sabed " + saved.getId());
			
			//session.save(saved);
			
			for(AdditionalService ads : tempArray)
			{	
				System.out.println("adf " + ads.getId() + " " + ads.getName());
				
				ApartmentAdditionalService ap = apartmentAdditionalService.save(new ApartmentAdditionalService(saved, ads));
				System.out.println("ap " + ap.getId());
				session.createNativeQuery("insert into apartment_additional_service values(" + ap.getId() + "," +
					ads.getId() + "," + saved.getId() + ")").executeUpdate();
			}
			
			for(PricePlan pp : pricePlans)
			{	
				PricePlan p = pricePlanService.save(new PricePlan(saved, pp.getStartDate(), pp.getEndDate(), pp.getPrice()));
				session.createNativeQuery("insert into price_plan values(" + p.getId() + ",'" +
						p.getEndDate() + "'," + p.getPrice() + ",'" + p.getStartDate() + "','ACTIVE'," + saved.getId() + ")").executeUpdate();
			}
			
			session.createNativeQuery("insert into apartment values(" + saved.getId() + ",'" +
					saved.getDescription() + "'," + saved.getMaxNumberOfGuests() + ",'" + saved.getName() + "'," + saved.getNumberOfRooms() + "," + saved.getSize() + ",'ACTIVE',"
					+ saved.getAccommodation().getId() + "," + saved.getType().getId() + ")").executeUpdate();
					
			tx.commit();
			session.close();
			
			retVal = "Apartment successfully added";
		}else
			return "Failed";
		
		return retVal;
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddPricePlan")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddPricePlanResponse")
	public String addPricePlan(@WebParam(name="apartmentId") String apartmentId, @WebParam(name = "startDate") String startDate, 
			@WebParam(name = "endDate") String endDate, @WebParam(name = "price") String price, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		System.out.println("tuu sm");
		Optional<Apartment> optional = apartmentService.findOne(Long.valueOf(apartmentId));
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		if(!optional.isPresent())
			return "error";
		Apartment apartment = optional.get();
		PricePlan newPricePlan = new PricePlan(apartment, startDate, endDate, Integer.parseInt(price));
		
		PricePlan saved = pricePlanService.save(newPricePlan);
		if(saved == null)
			return "error";
		
		session.createNativeQuery("insert into price_plan values(" + saved.getId() + ",'" +
				saved.getEndDate() + "'," + saved.getPrice() + ",'" + saved.getStartDate() + "','ACTIVE'," + saved.getApartment().getId()).executeUpdate();
		
		tx.commit();
		session.close();
		
		return "New price plan successfully added.";
	}
	
	@ResponseWrapper(className="com.project.web_service.wrappers.response.GetAccommodationTypesResponse")
	public List<AccommodationType> getAccommodationTypes(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		List<AccommodationType> accommodationTypes = session.createNativeQuery("select * from accommodation_type", AccommodationType.class).getResultList();
		tx.commit();
		session.close();
//				accTypeService.findAll();
		
		return accommodationTypes;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAccommodationCategoriesRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationCategoriesResponse")
	public List<AccommodationCategory> getAccommodationCategories(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		List<AccommodationCategory> accommodationCategories = session.createNativeQuery("select * from accommodation_category", AccommodationCategory.class).getResultList();
//				accCategoryService.findAll();
		tx.commit();
		session.close();
		
		return accommodationCategories;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetCitiesRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetCitiesResponse")
	public List<City> getCities(){
		
		List<City> cities = cityService.findAll();
		List<City> retVal = new ArrayList<>();
		
		for (City c : cities)
			retVal.add(new City(c.getId(), c.getName(), c.getZipcode()));
		
		return retVal;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.GetAccommodationsRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationsResponse")
	public List<AccommodationDTO> getAllAccommodations(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		System.out.println("jjjjj " + email);
		
		List<Accommodation> accommodations = session.createNativeQuery("select * from accommodation where status='ACTIVE'", Accommodation.class).getResultList();
		List<AccommodationDTO> retVal = new ArrayList<>();
		System.out.println(accommodations.size());
		tx.commit();
		session.close();

		
		for(Accommodation acc : accommodations)
		{
			System.out.println("aaaaaaaa " + acc.getName());
			System.out.println("aaaaaaaa " + acc.getName());
			List<Image> images = imageService.findAll();
			
			AccommodationDTO accDTO = new AccommodationDTO(acc.getId().toString(), acc.getName(), acc.getType().getName(), acc.getCity().getName(), 
					acc.getCity().getCountry().getName(), acc.getStreet(), acc.getDescription(), acc.getCategory().getName(), "");
			
			String temp = "";
			for(Image img : images)
			{
				if(img.getAccommodation() != null)
				{
					if(img.getAccommodation().getId().equals(acc.getId()))
					{	
						temp += img.getUrl();
					  	accDTO.setImage(temp);
					  	temp += "; ";
					}
				}
			}
			
			retVal.add(accDTO);
		}
		
		for(AccommodationDTO a : retVal)
			System.out.println("pppp " + a.getImage() + " " + a.getName() + a.getId());
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.GetReservations")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetReservationsResponse")
	public List<ReservationDTO> getReservations(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
//		List<Reservation> allReservations = reservationService.findAll();
		List<ReservationDTO> retVal = new ArrayList<>();
		
		List<Reservation> allReservations = session.createNativeQuery("select * from reservation", Reservation.class).getResultList();
		tx.commit();
		session.close();
		
		for(Reservation res : allReservations)
		{	retVal.add(new ReservationDTO(res.getId().toString(), res.getUser().getName(), res.getUser().getSurname(), 
					res.getApartment().getName(), res.getApartment().getAccommodation().getName(), res.getStartDate(),
					res.getEndDate(), String.valueOf(res.getPrice()), res.getStatus().toString()));
			System.out.println("res " + retVal);
		}
		
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.ConfirmReservation")
	@ResponseWrapper(className="com.project.web_service.wrappers.ConfirmReservationResponse")
	public String confirmReservation(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		System.out.println("resrvatio id " + id);
		
		Reservation reservation = reservationService.findOne(Long.valueOf(id));
		reservation.setStatus(ReservationStatus.VISIT);
		reservationService.save(reservation);
		session.update(reservation);
		
		tx.commit();
		session.close();
		
		return "Confirmed";
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetBedTypes")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetBedTypesResponse")
	public List<BedType> getBedTypes(){
		
		List<BedType> bedTypes = bedTypeService.findAll();
		
		return bedTypes;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetApartments")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetApartmentsResponse")
	public List<ApartmentDTO> getApartments(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws JSONException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
//		Accommodation accommodation = accService.findOne(Long.valueOf(id));
//		List<Apartment> all = apartmentService.findByAccommodationId(accommodation);
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
//		List<Accommodation> allAccommodations = accService.findAll();
		List<Accommodation> allAccommodations = session.createNativeQuery("select * from accommodation", Accommodation.class).getResultList();
		
		List<Long> ids = new ArrayList<>();
		List<ApartmentDTO> retVal = new ArrayList<>();
		
		for(Accommodation a: allAccommodations)
			ids.add(a.getId());
		
		if(!ids.contains(Long.valueOf(id)))
			return retVal;
		
		List<Apartment> all = session.createNativeQuery("select * from apartment where status='ACTIVE'", Apartment.class).getResultList();
//		
//				apartmentService.findByAccommodationId(Long.valueOf(id));
		
		List<ApartmentAdditionalService> apartmentServices = new ArrayList<>();
		List<PricePlan> pricePlans = new ArrayList<>();
		List<Image> images = new ArrayList<>();
//		List<PricePlanDTO> retDTO = new ArrayList<>();
		String apServices = "";
		String pp = "";
		
		JSONArray jarray = new JSONArray();
		final JSONObject res = new JSONObject();
		
		for(Apartment ap : all)
		{	
			apartmentServices = apartmentAdditionalService.findByApartmentId(ap.getId());
			pricePlans = pricePlanService.findByApartmentId(ap.getId());
			
			for(ApartmentAdditionalService as : apartmentServices)
			{
				apServices += as.getAdditionalService().getName() + ";";
//				System.out.println("serr " + apServices);
			}
		
			for(PricePlan p : pricePlans)
			{
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("startDate", p.getStartDate());
				jsonOb.put("endDate", p.getEndDate());
				jsonOb.put("price", p.getPrice());
				
				jarray.add(jsonOb);
//				retDTO.add(new PricePlanDTO(p.getStartDate(), p.getEndDate(), p.getPrice()));
				pp += p.getStartDate() + " " + p.getEndDate() + " " + p.getPrice() + ";";
//				System.out.println("pri " + pp);
			}
			
			
//			TODO
			images =  imageService.findByApartmentId(ap.getId());
			
			String temp = "";
			for(Image img : images)
			{
				temp += img.getUrl();
			  	temp += "; ";
			}
			
//			res.put("pricePlans", jarray);
			System.out.println("ress " + jarray.toString());
//			pronacii odgovarajuce add service i price plans!!!
			System.out.println(ap.getName());
			retVal.add(new ApartmentDTO(String.valueOf(ap.getId()), ap.getName(), ap.getType().getName(), String.valueOf(ap.getSize()), String.valueOf(ap.getNumberOfRooms()), 
					String.valueOf(ap.getMaxNumberOfGuests()), ap.getDescription(), temp, apServices, jarray.toString()));
		}
		
		tx.commit();
		session.close();
	
		
		return retVal;
	} 
	
	@SuppressWarnings("unchecked")
	@RequestWrapper(className="com.project.web_service.wrappers.GetApartment")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetApartmentResponse")
	public ApartmentDTO getApartment(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws JSONException, XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
//		Optional<Apartment> optional = apartmentService.findOne(Long.valueOf(id));
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
//		if(!optional.isPresent())
//			return new ApartmentDTO("error");
		
//		Apartment ap = optional.get();
		
		Apartment ap = session.get(Apartment.class, Long.valueOf(id));
		
		if(ap == null)
			return new ApartmentDTO("error");
			
		List<ApartmentAdditionalService> apartmentServices = new ArrayList<>();
		List<PricePlan> pricePlans = new ArrayList<>();
		List<Image> images = new ArrayList<>();
//		List<PricePlanDTO> retDTO = new ArrayList<>();
		String apServices = "";
		String pp = "";
		
		JSONArray jarray = new JSONArray();
		final JSONObject res = new JSONObject();
			
		apartmentServices = apartmentAdditionalService.findByApartmentId(ap.getId());
		pricePlans = session.createNativeQuery("select * from price_plan where apartment_id=" + ap.getId(), PricePlan.class).getResultList();
//				pricePlanService.findByApartmentId(ap.getId());
		tx.commit();
		session.close();
		
		for(ApartmentAdditionalService as : apartmentServices)
		{
			apServices += as.getAdditionalService().getName() + ";";
//			System.out.println("serr " + apServices);
		}
	
		for(PricePlan p : pricePlans)
		{
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("startDate", p.getStartDate());
			jsonOb.put("endDate", p.getEndDate());
			jsonOb.put("price", p.getPrice());
			
			jarray.add(jsonOb);
//				retDTO.add(new PricePlanDTO(p.getStartDate(), p.getEndDate(), p.getPrice()));
			pp += p.getStartDate() + " " + p.getEndDate() + " " + p.getPrice() + ";";
//			System.out.println("pri " + pp);
		}
		
//			TODO
		images =  imageService.findByApartmentId(ap.getId());
		
		String temp = "";
		for(Image img : images)
		{
			temp += img.getUrl();
		  	temp += "; ";
		}
		
//			res.put("pricePlans", jarray);
		
		ApartmentDTO retVal = new ApartmentDTO(String.valueOf(ap.getId()), ap.getName(), ap.getType().getName(), 
				String.valueOf(ap.getSize()), String.valueOf(ap.getNumberOfRooms()), 
				String.valueOf(ap.getMaxNumberOfGuests()), ap.getDescription(), temp, 
				apServices, jarray.toString());
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAdditionalServices")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAdditionalServicesResponse")
	public List<AdditionalService> getAdditionalServices(){
		
		List<AdditionalService> additionalServices = additionalServiceService.findAll();
		
		return additionalServices;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetPricePlans")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetPricePlansResponse")
	public List<PricePlan> getPricePlans(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
//		List<PricePlan> pricePlans = pricePlanService.findAll();
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		List<PricePlan> pricePlans = session.createNativeQuery("select * from price_plan", PricePlan.class).getResultList();
		tx.commit();
		session.close();
		
		
		return pricePlans;
	} 
	
	@SuppressWarnings("unchecked")
	@RequestWrapper(className="com.project.web_service.wrappers.DeleteAccommodation")
	@ResponseWrapper(className="com.project.web_service.wrappers.DeleteAccommodationResponse")
	public String deleteAccommodation(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
		List<PricePlan> pricePlans = new ArrayList<>();
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		Accommodation acc = session.get(Accommodation.class, Long.valueOf(id));
		
		List<Apartment> apartments = session.createNativeQuery("select * from apartment where accommodation_id=" + acc.getId(), Apartment.class).getResultList();
		
		for(Apartment apartment : apartments)
		{
			if(apartment.getStatus().equals(DeleteStatus.ACTIVE))
			{
				pricePlans = session.createNativeQuery("select * from price_plan where apartment_id=" + apartment.getId(), PricePlan.class).getResultList();
				
				for(PricePlan pp : pricePlans)
				{	
					pp.setStatus(DeleteStatus.INACTIVE);
					pricePlanService.save(pp);
					session.update(pp);
				}
			}
			apartment.setStatus(DeleteStatus.INACTIVE);
			apartmentService.save(apartment);
			session.update(apartment);
		}
		
		acc.setStatus(DeleteStatus.INACTIVE);
		accService.save(acc);
		session.update(acc);

		tx.commit();
		session.close();
		
		return "Accommodation deleted";
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAccommodation")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationResponse")
	public AccommodationDTO getAccommodation(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
		AccommodationDTO retVal = null;
		
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		Accommodation acc = session.get(Accommodation.class, Long.valueOf(id));
		
		System.out.println("aaa " + acc);
		if(acc.getStatus().equals(DeleteStatus.ACTIVE))
			retVal = new AccommodationDTO(acc.getId().toString(), acc.getName(), acc.getType().getName(), acc.getCity().getName(),
					acc.getCity().getCountry().getName(), acc.getStreet(), acc.getDescription(), acc.getCategory().getName(), "");
		
//		TODO sredi za vracanje slike
		
		List<Image> images = session.createNativeQuery("select * from image where accommodation_id=" + acc.getId(), Image.class).getResultList();
		String temp = "";
		
		for(Image img : images)
		{
			temp += img.getUrl();
		  	temp += "; ";
		}

		retVal.setImage(temp);	
		
		tx.commit();
		session.close();
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.DeleteApartment")
	@ResponseWrapper(className="com.project.web_service.wrappers.DeleteApartmentResponse")
	public String deleteApartment(@WebParam(name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		
		List<PricePlan> pricePlans = new ArrayList<>();
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session session = getSession(email);
		Transaction tx = session.beginTransaction();
		
		Apartment apartment = session.get(Apartment.class, Long.valueOf(id));
		
		if(apartment.getStatus().equals(DeleteStatus.ACTIVE))
		{
			pricePlans = session.createNativeQuery("select * from price_plan where apartment_id=" + apartment.getId(), PricePlan.class).getResultList();
			
			for(PricePlan pp : pricePlans)
			{	
				pp.setStatus(DeleteStatus.INACTIVE);
				pricePlanService.save(pp);
				session.update(pp);
			}
			
			apartment.setStatus(DeleteStatus.INACTIVE);
			apartmentService.save(apartment);
			session.update(apartment);
			tx.commit();
			session.close();
		}	
		
		return "Apartment deleted";
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAgentSentMessages")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAgentSentMessagesResponse")
	public List<MessageDTO> getAgentSentMessages(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		List<Message> messages = s.createNativeQuery("select * from  message where direction = 'AGENT_TO_USER' and status != 'DELETED_FOR_AGENT'", Message.class).getResultList();
		tx.commit();
		s.close();

		List<MessageDTO> retVal = new ArrayList<MessageDTO>();
		for(Message message : messages) {
			retVal.add(new MessageDTO(message.getId().toString(),message.getUser().getName(),message.getUser().getSurname(),
					message.getText(),message.getDate(),message.getTime(), message.getAgent().getEmail(), message.getUser().getEmail()));
		}
		System.out.println(retVal.size());
		return retVal;
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAgentSentMessage")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAgentSentMessageResponse")
	public MessageDTO getAgentSentMessage(@WebParam (name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		Message message = s.get(Message.class, Long.valueOf(id));
		tx.commit();
		s.close();
		return new MessageDTO(message.getId().toString(), message.getUser().getName(), message.getUser().getSurname(), message.getText(), message.getDate(), message.getTime(), 
				message.getAgent().getEmail(), message.getUser().getEmail());
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAgentReceivedMessage")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAgentReceivedMessageResponse")
	public MessageDTO getAgentReceivedMessage(@WebParam (name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		Message message = s.get(Message.class, Long.valueOf(id));
		tx.commit();
		s.close();
		return new MessageDTO(message.getId().toString(), message.getUser().getName(), message.getUser().getSurname(), message.getText(), message.getDate(), message.getTime(), 
				message.getAgent().getEmail(), message.getUser().getEmail());
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAgentReceivedMessages")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAgentReceivedMessagesResponse")
	public List<MessageDTO> getAgentReceivedMessages(@WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		List<Message> messages = s.createNativeQuery("select * from  message where direction = 'USER_TO_AGENT' and status!='DELETED_FOR_AGENT'", Message.class).getResultList();
		tx.commit();
		s.close();

		List<MessageDTO> retVal = new ArrayList<MessageDTO>();
		for(Message message : messages) {
			retVal.add(new MessageDTO(message.getId().toString(),message.getUser().getName(),message.getUser().getSurname(),
					message.getText(),message.getDate(),message.getTime(),message.getAgent().getEmail(), message.getUser().getEmail()));
		}
		return retVal;
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.MarkAsReadAgentMessage")
	@ResponseWrapper(className="com.project.web_service.wrappers.MarkAsReadAgentMessageResponse")
	public String markAsReadAgentMessage(@WebParam (name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		System.out.println("id "+id);
		Message message = s.get(Message.class, Long.valueOf(id));
		message.setStatus(MessageStatus.READ);
		messageService.save(message);
		s.update(message);

		tx.commit();
		s.close();
		return "Set read";
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.DeleteAgentSentMessage")
	@ResponseWrapper(className="com.project.web_service.wrappers.DeleteAgentSentMessageResponse")
	public String deleteAgentSentMessage(@WebParam (name = "id") String id, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		
		Message message = s.get(Message.class, Long.valueOf(id));
		message.setStatus(MessageStatus.DELETED_FOR_AGENT);;
		messageService.save(message);
		s.update(message);

		tx.commit();
		s.close();
		return "Set read";
		
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddReservationRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddReservationResponse")
	public String addReservation(@WebParam(name="apartmentId") String apartmentId, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws SQLException, XPathExpressionException, ParserConfigurationException, SAXException, IOException, java.text.ParseException
	{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		
		Apartment apartment = s.get(Apartment.class, Long.valueOf(apartmentId));
		Reservation reservation = new Reservation(null, apartment, startDate, endDate, 0, ReservationStatus.RESERVATION);

		Pattern pattern = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
		Matcher startDateMatcher = pattern.matcher(reservation.getStartDate());
		Matcher endDateMatcher = pattern.matcher(reservation.getEndDate());
		
		// Check date formats.
		if (!startDateMatcher.find()) {
			return "Wrong format";
		}
		if (!endDateMatcher.find()) {
			return "Wrong format";
		}
		
		// Check if dates are past dates.
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkStartDate = dateFormatter.parse(reservation.getStartDate());
		Date checkEndDate = dateFormatter.parse(reservation.getEndDate());
		if (checkStartDate.before(new Date()) || checkEndDate.before(new Date())) {
			return "You must enter future dates.";
		}
		
		// Check if start date is before end date.
		if (!checkStartDate.before(checkEndDate)) {
			return "Start date must be before end date.";
		}
		
		// Check if apartment is available in that period.
		if (!reservationService.isAvailable(reservation.getApartment(), reservation.getStartDate(), reservation.getEndDate())) {
			return "Apartment is not available at the given period.";
		}
		
		Reservation r = reservationService.save(reservation);
		
		s.createNativeQuery("insert into reservation values("+r.getId()+",'"+r.getEndDate()+"',"+0+",'"+r.getStartDate()+"','RESERVATION',"+apartmentId+",NULL)").executeUpdate();
		
		tx.commit();
		s.close();
		
		return "Reservation successfully added";
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.SendMessageToUser")
	@ResponseWrapper(className="com.project.web_service.wrappers.SendMessageToUserResponse")
	public String sendMessageToUser(@WebParam (name = "messageId") String messageId, @WebParam (name = "messageText") String messageText, @WebParam(name = "Signature", targetNamespace = "http://www.w3.org/2000/09/xmldsig#") SignatureType signature) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		String subjectData = signature.getKeyInfo().getX509Data().getName();
		String email = subjectData.split("=")[8];
		Session s = getSession(email);
		Transaction tx = s.beginTransaction();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		
		String date = dateFormatter.format(new Date());
		String time = timeFormatter.format(new Date());
		
		Message message = s.get(Message.class, Long.valueOf(messageId));
		
		Message messageResponse = new Message(message.getUser(), message.getAgent(), message.getApartment(), date, time, messageText, MessageStatus.UNREAD, Direction.AGENT_TO_USER);
		Message m = messageService.save(messageResponse);
		s.createNativeQuery("insert into message values("+m.getId()+",'"+date+"','"+m.getDirection()+"','"+ m.getStatus()+"','"+messageText+"','"+time+"',"+m.getAgent().getId()+","+m.getApartment().getId()+","+ m.getUser().getId()+")").executeUpdate();
		tx.commit();
		s.close();

		return "Message sent";
	}
	
	@WebMethod(exclude = true)
	public Session getSession(String email) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		System.out.println("sesije "+sessions.keySet());
		if (sessions.containsKey(email)) {
			System.out.println("postoji "+ email);
			return sessions.get(email).openSession();
		}
		Configuration conf = new Configuration().configure();
			// <!-- Database connection settings -->
		System.out.println("aaa " + agentService.findByEmail(email).getId());
        String url = "jdbc:mysql://localhost:3306/db" + agentService.findByEmail(email).getId() + "?createDatabaseIfNotExist=true&useSSL=false";

	    conf.setProperty("hibernate.connection.url", url);
		SessionFactory sessionFactory = conf.buildSessionFactory();
		
		sessions.put(email, sessionFactory);
		return sessionFactory.openSession();
	}
	
}
