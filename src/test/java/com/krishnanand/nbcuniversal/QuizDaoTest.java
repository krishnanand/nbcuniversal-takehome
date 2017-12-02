package com.krishnanand.nbcuniversal;

import java.sql.ResultSet;
import java.sql.SQLException;

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
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts= {"classpath:/schema.sql", "classpath:/data.sql"})
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
    Assert.assertEquals("test", registration.getUsername());
    Assert.assertEquals(3, registration.getNumberOfQuestions());
    Assert.assertTrue(registration.isActive());
  }
  
  @Test
  public void getCurrentQuizStatus() throws Exception {
    Assert.assertEquals(1, this.jdbcTemplate.update(
        "INSERT INTO QuizStatus(quiz_id) VALUES(?)", new Object[] {"ABCDE12345"}));
    QuizStatus actual = this.quizDao.getCurrentQuizStatus("ABCDE12345");
    QuizStatus expected = new QuizStatus();
    expected.setNumberOfAskedQuestions(0);
    expected.setNumberOfEligibleQuestions(3);
    expected.setQuizId("ABCDE12345");
    expected.setQuizEnded(false);
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testInitialiseQuizStatus() throws Exception {
    Assert.assertEquals(1, this.quizDao.initialiseQuizStatus("ABCDE12345"));
    Assert.assertEquals(0,
        (int) this.jdbcTemplate.query(
            "SELECT questions_asked from QuizStatus where quiz_id = ? ",
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
  }

}
