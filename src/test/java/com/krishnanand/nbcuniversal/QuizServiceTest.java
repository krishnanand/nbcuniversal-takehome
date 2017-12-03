package com.krishnanand.nbcuniversal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Unit test for {@link QuizService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {
    QuizService.class, QuizDao.class, QuizQuestionsDao.class, DatabaseCredentials.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,
  scripts= {"classpath:/schema.sql", "classpath:/data.sql"})
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class QuizServiceTest {
  
  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private QuizService quizService;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  @After
  public void tearDown() throws Exception {
    this.jdbcTemplate = null;
  }
  
  @Test
  public void testInitRegistration() throws Exception {
    InitRegistration actual = this.quizService.generateQuizId("test1");
    InitRegistration expected = this.jdbcTemplate.query(
        "SELECT username, quiz_id, number_of_questions from Quiz where username = ?",
        new Object[] {"test1"}, new ResultSetExtractor<InitRegistration> () {

          @Override
          public InitRegistration extractData(ResultSet rs)
              throws SQLException, DataAccessException {
            // TODO Auto-generated method stub
            while(rs.next()) {
              InitRegistration actual = new InitRegistration();
              actual.setUsername(rs.getString("username"));
              actual.setQuizId(rs.getString("quiz_id"));
              actual.setNumberOfQuestions(rs.getInt("number_of_questions"));
              actual.setActive(actual.getNumberOfQuestions() > 0);
              return actual;
            }
            return null;
          }
          
        });
    Assert.assertEquals(expected, actual);
    // Check if the quiz service has been initialised.
    
    Assert.assertEquals(0,
        (int) this.jdbcTemplate.query(
            "SELECT questions_asked from QuizStatus where quiz_id = ?",
            new Object[] {actual.getQuizId()}, new ResultSetExtractor<Integer>() {

              @Override
              public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                // TODO Auto-generated method stub
                while (rs.next()) {
                  return rs.getInt("questions_asked");
                }
                return null;
              }
              
            }));
  }
  
  @Test
  public void testFetchQuestionsAfterRegistration() throws Exception {
    InitRegistration actual = this.quizService.generateQuizId("test1");
    QuizQuestion question = this.quizService.fetchQuestion(actual.getQuizId());
    Assert.assertNotNull(question);
  }
  
  @Test
  public void testFetchQuestions() throws Exception {
    Assert.assertEquals(1,
        (int) this.jdbcTemplate.query(
            "SELECT questions_asked from QuizStatus where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new ResultSetExtractor<Integer>() {

              @Override
              public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                // TODO Auto-generated method stub
                while (rs.next()) {
                  return rs.getInt("questions_asked");
                }
                return null;
              }
              
            }));
    Assert.assertEquals(5,
        (int) this.jdbcTemplate.query(
            "SELECT question_id from QuizQuestions where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new ResultSetExtractor<Integer>() {

              @Override
              public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                // TODO Auto-generated method stub
                while (rs.next()) {
                  return rs.getInt("question_id");
                }
                return null;
              }
              
            }));
    QuizQuestion actual = this.quizService.fetchQuestion("ABCDE12345");
    Assert.assertNotNull(actual);
    QuizQuestion expected = new QuizQuestion();
    expected.setQuizId("ABCD12345");
    Assert.assertNotEquals(expected.getQuestionId(), 5); // Because it has been asked before.
    
    // Verify that the status has been updated.
    Assert.assertEquals(2,
        (int) this.jdbcTemplate.query(
            "SELECT questions_asked from QuizStatus where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new ResultSetExtractor<Integer>() {

              @Override
              public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                // TODO Auto-generated method stub
                while (rs.next()) {
                  return rs.getInt("questions_asked");
                }
                return null;
              }
              
            }));
    List<Integer> askedQuestions = new ArrayList<>();
    askedQuestions.add(5);
    askedQuestions.add(actual.getQuestionId());
    Assert.assertEquals(askedQuestions,
        this.jdbcTemplate.query(
            "SELECT question_id from QuizQuestions where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new RowMapper<Integer>() {

              @Override
              public Integer mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
                  return rs.getInt("question_id");
              }
            }));
  }
  
  @Test
  public void testQuestionsExhausted_EndToEnd() throws Exception {
    InitRegistration actual = this.quizService.generateQuizId("test1");
    Set<QuizQuestion> quizQuestions = new LinkedHashSet<>();
    for (int i = 0; i < actual.getNumberOfQuestions(); i++) {
      quizQuestions.add(this.quizService.fetchQuestion(actual.getQuizId()));
    }
    Assert.assertEquals(actual.getNumberOfQuestions(), quizQuestions.size());
    // Now send an additional request.
    QuizQuestion shouldBeNull = this.quizService.fetchQuestion(actual.getQuizId());
    Assert.assertNull(shouldBeNull);
  }
  
  @Test
  public void testCheckAnswer_CorrectAnswer() throws Exception {
    InitRegistration actual = this.quizService.generateQuizId("test1");
    QuizQuestion question = this.quizService.fetchQuestion(actual.getQuizId());
    Answer answer = new Answer();
    answer.setQuizId(question.getQuizId());
    answer.setQuestionId(question.getQuestionId());
    answer.setResponse(question.isAnswer());
    answer.setDescription(question.getDescription());
    
    Solution actualSolution = this.quizService.checkAnswer(answer);
    Solution expectedSolution = new Solution();
    expectedSolution.setCorrectAnswer(question.isAnswer());
    expectedSolution.setPlayerAnswer(answer.isResponse());
    expectedSolution.setQuizId(actual.getQuizId());
    expectedSolution.setDescription(answer.getDescription());
    Assert.assertEquals(expectedSolution, actualSolution);
  }

}
