package com.krishnanand.nbcuniversal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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
 * Unit test for {@link QuizDao}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {QuizDao.class, DatabaseCredentials.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,
    scripts= {"classpath:/schema.sql", "classpath:/data.sql"})
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class QuizDaoTest {
  
  @Autowired
  private QuizDao quizDao;
  
  @Autowired
  private DataSource dataSource;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  @Test
  public void testInitRegistration() throws Exception {
    InitRegistration registration = this.quizDao.registerQuiz("test");
    Assert.assertEquals(3, registration.getNumberOfQuestions());
    Assert.assertTrue(registration.isActive());
    InitRegistration expected = this.jdbcTemplate.query(
        "SELECT username, quiz_id, number_of_questions FROM Quiz where quiz_id = ?" ,
        new Object[] {registration.getQuizId()}, new ResultSetExtractor<InitRegistration>() {

          @Override
          public InitRegistration extractData(ResultSet rs)
              throws SQLException, DataAccessException {
            while(rs.next()) {
              InitRegistration initRegistration = new InitRegistration();
              initRegistration.setActive(true);
              initRegistration.setNumberOfQuestions(rs.getInt("number_of_questions"));
              initRegistration.setQuizId(rs.getString("quiz_id"));
              return initRegistration;
            }
            return null;
          }
        });
    Assert.assertEquals(expected, registration);
    Score actualScore = this.jdbcTemplate.query(
        "SELECT quiz_id, correct_answers, incorrect_answers FROM Score where quiz_id = ?",
        new Object[] {registration.getQuizId()}, new ResultSetExtractor<Score>() {

          @Override
          public Score extractData(ResultSet rs) throws SQLException, DataAccessException {
            while(rs.next()) {
              Score score = new Score();
              score.setIncorrectAnswers(rs.getInt("incorrect_answers"));
              score.setCorrectAnswers(rs.getInt("correct_answers"));
              score.setQuizId(registration.getQuizId());
              return score;
            }
            return null;
          }
        });
    Score expectedScore = new Score();
    expectedScore.setCorrectAnswers(0);
    expectedScore.setIncorrectAnswers(0);
    expectedScore.setQuizId(registration.getQuizId());
    Assert.assertEquals(expectedScore, actualScore);
  }
  
  @Test
  public void testGetCurrentQuizStatus() throws Exception {
    QuizStatus actual = this.quizDao.getCurrentQuizStatus("ABCDE12345");
    QuizStatus expected = new QuizStatus();
    expected.setNumberOfAskedQuestions(2);
    expected.setNumberOfAnsweredQuestions(1);
    expected.setNumberOfEligibleQuestions(3);
    expected.setQuizId("ABCDE12345");
    expected.setQuizEnded(false);
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testInitialiseQuizStatus() throws Exception {
    InitRegistration registration = this.quizDao.registerQuiz("test");
    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    this.quizDao.initialiseQuizStatus(registration.getQuizId());
    Assert.assertEquals(expected,
        this.jdbcTemplate.query(
            "SELECT questions_asked from QuizStatus where quiz_id = ? ",
            new Object[] {registration.getQuizId()}, new RowMapper<Integer>() {

              @Override
              public Integer mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
                  return rs.getInt("questions_asked");
              }
              
            }));
  }
  
  @Test
  public void testUpdateScore_CorrectAnswer() throws Exception {
    Solution expected = new Solution();
    expected.setQuizId("ABCDE12345");
    expected.setCorrectAnswer(true);
    expected.setPlayerAnswer(true);
    Assert.assertEquals(1, this.quizDao.updateScore(expected));
    Score expectedScore = new Score();
    expectedScore.setCorrectAnswers(1);
    expectedScore.setIncorrectAnswers(1); // Existing.
    expectedScore.setQuizId("ABCDE12345");
    Score actual = this.jdbcTemplate.query(
            "SELECT incorrect_answers, correct_answers FROM Score where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new ResultSetExtractor<Score>() {

              @Override
              public Score extractData(ResultSet rs) throws SQLException, DataAccessException {
                while(rs.next()) {
                  Score score = new Score();
                  score.setIncorrectAnswers(rs.getInt("incorrect_answers"));
                  score.setCorrectAnswers(rs.getInt("correct_answers"));
                  score.setQuizId("ABCDE12345");
                  return score;
                }
                return null;
              }
              
            });
    Assert.assertEquals(expectedScore, actual);
  }
  
  @Test
  public void testUpdateScore_IncorrectAnswer() throws Exception {
    Solution expected = new Solution();
    expected.setQuizId("ABCDE12345");
    expected.setCorrectAnswer(true);
    expected.setPlayerAnswer(false);
    expected.setQuestion("Is earth round?");
    this.quizDao.updateScore(expected);
    Score expectedScore = new Score();
    expectedScore.setCorrectAnswers(0);
    expectedScore.setIncorrectAnswers(2);
    expectedScore.setQuizId("ABCDE12345");
    Score actual = this.jdbcTemplate.query(
            "SELECT incorrect_answers, correct_answers FROM Score where quiz_id = ?",
            new Object[] {"ABCDE12345"}, new ResultSetExtractor<Score>() {

              @Override
              public Score extractData(ResultSet rs) throws SQLException, DataAccessException {
                while(rs.next()) {
                  Score score = new Score();
                  score.setIncorrectAnswers(rs.getInt("incorrect_answers"));
                  score.setCorrectAnswers(rs.getInt("correct_answers"));
                  score.setQuizId("ABCDE12345");
                  return score;
                }
                return null;
              }
              
            });
    Assert.assertEquals(expectedScore, actual);
  }
  
  @Test
  public void testMarkQuizAsCompleted() throws Exception {
    String query = "SELECT number_of_questions FROM Quiz WHERE quiz_id = ?";
    Assert.assertEquals(
        3,  
       (int) this.jdbcTemplate.query(
           query, new Object[] {"ABCDE12345"},
           new ResultSetExtractor<Integer>() {

          @Override
          public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
              return rs.getInt("number_of_questions");
            }
            return null;
          }
        }));
        this.quizDao.markQuizAsCompleted("ABCDE12345");
        Assert.assertEquals(
            0,  
           (int) this.jdbcTemplate.query(
               query, new Object[] {"ABCDE12345"},
               new ResultSetExtractor<Integer>() {

              @Override
              public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                  return rs.getInt("number_of_questions");
                }
                return null;
              }
            }));
    }
    
  

}
