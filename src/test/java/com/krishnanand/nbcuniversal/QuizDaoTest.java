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
    QuizStatus actual = this.quizDao.getCurrentQuizStatus("ABCDE12345");
    QuizStatus expected = new QuizStatus();
    expected.setNumberOfAskedQuestions(1);
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

}
