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
  private DataSource dataSource;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  @Test
  public void testInitialiseQuiz() throws Exception {
    ResponseEntity<InitRegistration> entity = this.quizController.initialiseQuiz();
    InitRegistration response = entity.getBody();
    Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    Assert.assertEquals(response.getNumberOfQuestions(), 3);
    Assert.assertTrue(response.isActive());
    Assert.assertEquals(1, (int) this.jdbcTemplate.queryForObject(
        "SELECT COUNT(quiz_id) from Quiz where username = ? AND quiz_id = ?",
        new Object[] {"test", response.getQuizId()}, int.class));
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
    Assert.assertEquals(HttpStatus.TOO_MANY_REQUESTS, questionEntity.getStatusCode());
  }
  
  @Test
  public void testQuestionInCorrectly() throws Exception {
    Answer answer = new Answer();
    answer.setQuestionId(4);
    answer.setResponse(false);
    ResponseEntity<Solution> actualSolution =
        this.quizController.answerQuestion("ABCDE12345", answer);
    Assert.assertNull(actualSolution.getBody());
  }
  
  @Test
  public void testQuestionCorrectly() throws Exception {
    Answer answer = new Answer();
    answer.setQuestionId(5);
    answer.setResponse(true);
    this.quizController.answerQuestion("ABCDE12345", answer);
    ResponseEntity<Solution> actualSolution =
        this.quizController.answerQuestion("ABCDE12345", answer);
    Solution expectedSolution = new Solution();
    expectedSolution.setQuizId("ABCDE12345");
    expectedSolution.setQuestion("Does January have 31 days in a month?");
    expectedSolution.setCorrectAnswer(true);
    expectedSolution.setPlayerAnswer(answer.isResponse());
    Assert.assertEquals(expectedSolution, actualSolution.getBody());
  }
  
  @Test
  public void testGetScore() throws Exception {
    ResponseEntity<InitRegistration> responseEntity =
        this.quizController.initialiseQuiz();
    InitRegistration registration = responseEntity.getBody();
    Score actual = this.quizController.getScore(registration.getQuizId());
    Score expected = new Score();
    expected.setQuizId(registration.getQuizId());
    expected.setCorrectAnswers(0);
    expected.setIncorrectAnswers(0);
    expected.calculateScore();
    Assert.assertEquals(expected, actual);
  }
  
}
