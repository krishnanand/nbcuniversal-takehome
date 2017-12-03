package com.krishnanand.nbcuniversal;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
 * Unit test for {@link QuizControllerTest}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,
   scripts= {"classpath:/schema.sql", "classpath:/data.sql"})
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class QuizControllerTest {
  
  @Autowired
  private QuizController quizController;
  
  @Autowired
  private WebApplicationContext context;
  
  @Autowired
  private DataSource dataSource;
  
  private MockMvc mvc;
  
  private ObjectMapper mapper;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.mvc =  MockMvcBuilders.webAppContextSetup(context).build();
    this.mapper = new ObjectMapper();
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  @Test
  public void testInitialiseQuiz() throws Exception {
    MvcResult result = this.mvc.perform(
        MockMvcRequestBuilders.post("/nbcuniversal/quiz")).
        andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    byte[] response = result.getResponse().getContentAsByteArray();
    
    InitRegistration registrationResponse =
        mapper.readValue(response, InitRegistration.class);
    Assert.assertEquals(registrationResponse.getUsername(), "test");
    Assert.assertEquals(registrationResponse.getNumberOfQuestions(), 3);
    Assert.assertTrue(registrationResponse.isActive());
    Assert.assertEquals(1, (int) this.jdbcTemplate.queryForObject(
        "SELECT COUNT(quiz_id) from Quiz where username = ? AND quiz_id = ?",
        new Object[] {"test", registrationResponse.getQuizId()}, int.class));
  }
  
  @Test
  public void testFetchQuestions() throws Exception {
    MvcResult initResult = this.mvc.perform(
        MockMvcRequestBuilders.post("/nbcuniversal/quiz")).
        andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    byte[] initResponse = initResult.getResponse().getContentAsByteArray();
    InitRegistration registrationResponse =
        mapper.readValue(initResponse, InitRegistration.class);
    MvcResult result = this.mvc.perform(
        MockMvcRequestBuilders.get("/nbcuniversal/quiz/" + registrationResponse.getQuizId())).
        andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    byte[] response = result.getResponse().getContentAsByteArray();
    QuizQuestion quizQuestion = this.mapper.readValue(response, QuizQuestion.class);
    Assert.assertNotNull(quizQuestion);
  }
  
  @Test
  public void testFetchQuestions_Mock() throws Exception {
    ResponseEntity<InitRegistration> responseEntity = this.quizController.initialiseQuiz();
    InitRegistration response = responseEntity.getBody();
    ResponseEntity<QuizQuestion> questionEntity =
        this.quizController.questions(response.getQuizId());
    Assert.assertNotNull(questionEntity.getBody());
  }
  
  @Test
  public void testFetchQuestions_Exhausted() throws Exception {
    ResponseEntity<InitRegistration> responseEntity = this.quizController.initialiseQuiz();
    InitRegistration response = responseEntity.getBody();
    for (int i = 0; i < response.getNumberOfQuestions(); i++) {
      ResponseEntity<QuizQuestion> questionEntity =
          this.quizController.questions(response.getQuizId());
      Assert.assertNotNull(questionEntity.getBody());
    }
    // Once more
    ResponseEntity<QuizQuestion> questionEntity =
        this.quizController.questions(response.getQuizId());
    Assert.assertEquals(HttpStatus.NOT_FOUND, questionEntity.getStatusCode());
  }
  
  @Test
  public void testQuestionCorrectly() throws Exception {
    ResponseEntity<InitRegistration> responseEntity =
        this.quizController.initialiseQuiz();
    InitRegistration response = responseEntity.getBody();
    Answer answer = new Answer();
    answer.setQuestionId(4);
    answer.setDescription("Is July 25 independence day of United States of America?");
    answer.setResponse(false);
    this.quizController.answerQuestion(response.getQuizId(), answer);
    
    
  }
  
}
