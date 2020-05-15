package com.prokarma.customerdetails.producer.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customerdetails.producer.exceptions.GlobalExceptionHandler;
import com.prokarma.customerdetails.producer.model.Address;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest.CustomerStatusEnum;
import com.prokarma.customerdetails.producer.service.KafkaProducer;
import com.prokarma.customerdetails.producer.service.KafkaProducerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
class CustomerDetailsControllerTest {

  private CustomerDetailsController customerDetailsController;

  private KafkaProducer kafkaProducer;

  // private MockMvc mockMvc;
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private CustomerDetailsRequest customerDetailsRequest;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  @Mock
  private KafkaTemplate<String, CustomerDetailsRequest> kafkaTemplate;

  private String authorizationToken;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    kafkaProducer = new KafkaProducerImpl();
    customerDetailsController = new CustomerDetailsController();
    objectMapper = new ObjectMapper();

    mockMvc =
        MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "password");
    params.add("client_id", "client");
    params.add("username", "user");
    params.add("password", "secret");

    ResultActions result = mockMvc
        .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("client", "password"))
            .accept("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    authorizationToken = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString())
        .get("access_token").toString();

    ReflectionTestUtils.setField(kafkaProducer, "kafkaTemplate", kafkaTemplate);
    ReflectionTestUtils.setField(customerDetailsController, "kafkaProducer", kafkaProducer);

    customerDetailsRequest = new CustomerDetailsRequest();

    customerDetailsRequest
        .address(new Address().addressLine1("1234").addressLine2("12345").postalCode("500532")
            .street("1234"))
        .birthDate("29-06-1991").country("India").countryCode("IN").customerNumber("9492050328")
        .customerNumber("C000000001").customerStatus(CustomerStatusEnum.OPEN)
        .email("bharathkidy@gmail.com").firstName("Bharath12345678901234567890")
        .lastName("Mokkal123456789001234567890").mobileNumber("9492050328");
  }

  @Test
  void testPostCustomerDetailResponseCodeByPassingValidRequestBodyAndHeadersExpectingStatusCode200()
      throws JsonProcessingException, Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(customerDetailsController)
        .setControllerAdvice(new GlobalExceptionHandler()).build();


    @SuppressWarnings("unchecked")
    SettableListenableFuture<SendResult<String, CustomerDetailsRequest>> settableListenableFuture =
        Mockito.mock(SettableListenableFuture.class);
    Mockito.when(settableListenableFuture.get(Mockito.anyLong(), Mockito.any()))
        .thenReturn(new SendResult<String, CustomerDetailsRequest>(null, null));
    Mockito.when(kafkaTemplate.send(Mockito.any(Message.class)))
        .thenReturn(settableListenableFuture);

    mockMvc
        .perform(MockMvcRequestBuilders.post("/crud/v1/addCustomer")
            .header("Authorization", "Bearer " + authorizationToken)
            .header("Activity-Id", "Bearer 123").header("Application-Id", "Bearer 123")
            .content(objectMapper.writeValueAsString(customerDetailsRequest))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  void testPostCustomerDetailResponseCodeByPassingValidRequestBodyAndHeadersAndStubbingErrorResponseExpectingStatusCode500()
      throws JsonProcessingException, Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(customerDetailsController)
        .setControllerAdvice(new GlobalExceptionHandler()).build();


    @SuppressWarnings("unchecked")
    SettableListenableFuture<SendResult<String, CustomerDetailsRequest>> settableListenableFuture =
        Mockito.mock(SettableListenableFuture.class);
    Mockito.when(settableListenableFuture.get(Mockito.anyLong(), Mockito.any()))
        .thenThrow(new RuntimeException());
    Mockito.when(kafkaTemplate.send(Mockito.any(Message.class)))
        .thenReturn(settableListenableFuture);

    mockMvc
        .perform(MockMvcRequestBuilders.post("/crud/v1/addCustomer")
            .header("Authorization", "Bearer " + authorizationToken)
            .header("Activity-Id", "Bearer 123").header("Application-Id", "Bearer 123")
            .content(objectMapper.writeValueAsString(customerDetailsRequest))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is5xxServerError());
  }

  @Test
  void testPostCustomerDetailResponseCodeByPassingInvalidAuthorizationTokenExpectedStatusCode401()
      throws JsonProcessingException, Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.post("/crud/v1/addCustomer")
            .header("Authorization", "Bearer 123").content(objectMapper.writeValueAsString(null))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }


  @Test
  void testPostCustomerDetailResponseCodeByPassingInValidParametersInBodyExpectingStatusCode400()
      throws JsonProcessingException, Exception {
    customerDetailsRequest.customerNumber("1111");
    customerDetailsRequest.birthDate("29/06/1991");
    mockMvc
        .perform(MockMvcRequestBuilders.post("/crud/v1/addCustomer")
            .content(objectMapper.writeValueAsString(customerDetailsRequest))
            .header("Authorization", "Bearer " + authorizationToken)
            .header("Activity-Id", "Bearer 123").header("Application-Id", "Bearer 123")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  void testPostCustomerDetailResponseCodeByPassingRequestBodyAsNullExpectingStatusCode400()
      throws JsonProcessingException, Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/crud/v1/addCustomer")
        .content(objectMapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + authorizationToken).header("Activity-Id", "Bearer 123")
        .header("Application-Id", "Bearer 123").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

}
