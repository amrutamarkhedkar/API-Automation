package testscripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingTest {
	String token;
	int bookingid;
	CreateBookingRequest payload;

	@BeforeMethod
	public void createBookingTokenTest() {

		// Given: all input details -> URL, Headers, path/ query paramerters, payload,
		// When -> submit the API[headerType,endpoint]
		// Then -> validate the API

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Response res = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth").then().log().all().extract().response();
		//.assertThat().statusCode(200)
		System.out.println("=============================================");
		// System.out.println(res.asPrettyString());
		// System.out.println(res.statusCode());
		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
		System.out.println("Token extracted is --> " + token);

	}

	@Test(enabled =false)
	public void createBooking() {
		RestAssured.given().headers("Content-Type", "application/json").headers("Accept", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when().post("/booking").then().assertThat().statusCode(200);

		// System.out.println(res.get);
		
	}

	@Test
	public void createBookingWithPojo() {

		Bookingdates bookingDates = new Bookingdates();
		bookingDates.setCheckin("2023-05-02");
		bookingDates.setCheckout("2023-05-05");

		payload = new CreateBookingRequest();

		payload.setFirstname("Amruta");
		payload.setLastname("Markhedkar");
		payload.setTotalprice(1212);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("dinner");
		payload.setBookingdates(bookingDates);

		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json").body("payload").when().post("/booking").then().extract()
				.response();

		//Assert.assertEquals(res.getStatusCode(), Status_Code.OK);

		
		  bookingid = res.jsonPath().getInt("bookingid");
		  System.out.println("Booking Id is --> " + bookingid);
		  validateResponse(res,payload,"booking.");

		
	}

	/*
	 * public void createBookingTokenTestInPlainMode() { String payload =
	 * "https://restful-booker.herokuapp.com"; RequestSpecification resSpec =
	 * RestAssured.given(); resSpec.baseUri("{\\r\\n\"\r\n" +
	 * "				+ \"    \\\"username\\\" : \\\"admin\\\",\\r\\n\"\r\n" +
	 * "				+ \"    \\\"password\\\" : \\\"password123\\\"\\r\\n\"\r\n"
	 * + "				+ \"}"); resSpec.headers("Content-Type","application/json");
	 * resSpec.body(payload); resSpec.post("/auth");
	 * 
	 * 
	 * //System.out.println(resSpec.asPrettyString()); }
	 */

	
	@Test(priority = 1, enabled =false)
	public void getAllBookingTest() {
		int bookingId = 2522;
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.when().get("/booking")
				.then()
				.extract()
				.response();
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		// we will get an array in the response 
		//System.out.println(res.asPrettyString());
		List<Integer> listOfBookingIds = res.jsonPath().getList("bookingid");
		listOfBookingIds.contains(bookingId);
		Assert.assertTrue(listOfBookingIds.contains(bookingId));
	}
	
	@Test(priority = 2, enabled=false)
	public void getBookingIdTest() {
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.when().get("/booking/"+bookingid)
				.then()
				.extract()
				.response();
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		System.out.println(res.asPrettyString());
		//validateResponse(res,payload,"");
		
	}
	
	@Test(priority = 2)
	public void getBookingIdDeserilizedTest() {
		bookingid = 2522;
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.when().get("/booking/"+bookingid)
				.then()
				.extract()
				.response();
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);
		//payload : all the details of the request
		//responseBody : all details from getBookingId
		/*
		 * Assert.assertEquals(payload.firstname, responseBody.firstname);
		 * Assert.assertEquals(payload.lastname, responseBody.lastname);
		 */
		Assert.assertTrue(responseBody.equals(payload), "Request and response did not match !");
	}
	
	@Test(priority = 3)
	public void updateBookingIdDeserilizedTest() {
		//bookingid = 2522;
		payload.setFirstname("Anu");
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.cookie("Cookie","token="+token)
				.when()
				.put("/booking/"+bookingid)
				.then()
				.extract()
				.response();
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);
		//payload : all the details of the request
		//responseBody : all details from getBookingId
		/*
		 * Assert.assertEquals(payload.firstname, responseBody.firstname);
		 * Assert.assertEquals(payload.lastname, responseBody.lastname);
		 */
		Assert.assertTrue(responseBody.equals(payload), "Request and response did not match !");
	}
	
	private void validateResponse(Response res, CreateBookingRequest payload, String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"), payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"), payload.isDepositpaid());
		Assert.assertEquals(res.jsonPath().getInt(object+"bookingdates.checkin"), payload.getBookingdates().checkin);
		Assert.assertEquals(res.jsonPath().getInt(object+"bookingdates.checkout"), payload.getBookingdates().checkout);
		Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"), payload.additionalneeds);
	}
}
