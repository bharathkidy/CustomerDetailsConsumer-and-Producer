package com.example.demo123;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IntegrationTestScriptsCustomerDetailsAPI {


  private String accessToken;

  private JSONObject customerDetailsRequest;

  @BeforeClass
  public void settingAccessTokenByPassingValidAuthenticationDetailsExpected200()
      throws JSONException {

    Response response = given().auth().preemptive().basic("client", "password")
        .contentType("application/x-www-form-urlencoded").formParam("grant_type", "password")
        .formParam("username", "user").formParam("password", "secret")
        .formParam("client_id", "client").contentType("application/x-www-form-urlencoded").when()
        .post("http://localhost:8080/oauth/token");

    accessToken = new JSONObject(response.getBody().asString()).get("access_token").toString();

  }

  @BeforeMethod
  public void setUp() throws JSONException {
    customerDetailsRequest = new JSONObject()
        .put("address",
            new JSONObject().put("addressLine1", "1234").put("addressLine2", "1234")
                .put("postalCode", "500532").put("street", "1234"))
        .put("birthDate", "29-06-1991").put("country", "INDIA").put("countryCode", "IN")
        .put("customerNumber", "C000000001").put("customerStatus", "Open")
        .put("email", "bharathkidy@gmail.com").put("firstName", "Bharath12345678901234567890")
        .put("lastName", "Mokkal123456789001234567890").put("mobileNumber", "9492050328");

  }


  @Test
  public void testAuthServerExpectedWithoutPassingAuthenticationDetailsExpecting401() {

    expect().statusCode(401).when().post("http://localhost:8080/oauth/token");

  }


  @Test
  public void testCustomerDetailsAPIWithCorrectRequestBodyAndHeadersExpectingStatus200()
      throws JSONException {



    expect().statusCode(200).contentType("application/json").request()
        .contentType("application/json").header("Authorization", "bearer " + accessToken)
        .header("Activity-Id", "1234").header("Application-Id", "1234")
        .body(customerDetailsRequest.toString()).log().all().when()
        .post("http://localhost:8080/crud/v1/addCustomer");


  }

  @Test
  public void testCustomerDetailsAPIWhenInvalidParametersArePassedExpectedStatus400()
      throws JSONException {

    // passing Invalid Inputs
    customerDetailsRequest.put("customerNumber", "1000000001").put("birthDate", "AA-BB-CCCC");

    expect().statusCode(400).contentType("application/json").request()
        .contentType("application/json").header("Authorization", "bearer " + accessToken)
        .header("Activity-Id", "1234").header("Application-Id", "1234")
        .body(customerDetailsRequest.toString()).log().all().when()
        .post("http://localhost:8080/crud/v1/addCustomer");


  }

  @Test
  public void testCustomerDetailsAPIWhenRequestBodyIsNullExpectedStatus400() throws JSONException {


    expect().statusCode(400).contentType("application/json").request()
        .contentType("application/json").header("Authorization", "bearer " + accessToken)
        .header("Activity-Id", "1234").header("Application-Id", "1234").log().all().when()
        .post("http://localhost:8080/crud/v1/addCustomer");


  }


  @Test
  public void testCustomerDetailsAPIWhenRequiredHeaersMissedExpectedStatus400()
      throws JSONException {


    expect().statusCode(400).contentType("application/json").request()
        .contentType("application/json").header("Authorization", "bearer " + accessToken)
        .body(customerDetailsRequest.toString()).log().all().when()
        .post("http://localhost:8080/crud/v1/addCustomer");


  }


  @Test
  public void testCustomerDetailsAPIWhenAuthorizationTokenMissingExpectedStatus401()
      throws JSONException {

    expect().statusCode(401).contentType("application/json").request()
        .contentType("application/json").header("Activity-Id", "1234")
        .header("Application-Id", "1234").body(customerDetailsRequest.toString()).log().all().when()
        .post("http://localhost:8080/crud/v1/addCustomer");


  }
}

