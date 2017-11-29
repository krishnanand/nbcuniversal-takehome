package com.krishnanand.nbcuniversal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class, QuizService.class, QuizDao.class, DatabaseCredentials.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/schema.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class QuizControllerTest {
  
  @InjectMocks
  private QuizController quizController;
  
  @Autowired
  private WebApplicationContext context;
  
  private MockMvc mvc;
  
  @Before
  public void setUp() throws Exception {
    this.mvc =  MockMvcBuilders.webAppContextSetup(context).build();
  }
  
  @Test
  public void testInitialiseQuiz() throws Exception {
    MvcResult result = this.mvc.perform(
        MockMvcRequestBuilders.post("/nbcuniversal/quiz")).
        andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    byte[] response = result.getResponse().getContentAsByteArray();
    ObjectMapper objectMapper = new ObjectMapper();
    InitRegistration registrationResponse =
        objectMapper.readValue(response, InitRegistration.class);
    Assert.assertEquals(registrationResponse.getUsername(), "test");
    Assert.assertEquals(registrationResponse.getNumberOfQuestions(), 3);
  }
}
