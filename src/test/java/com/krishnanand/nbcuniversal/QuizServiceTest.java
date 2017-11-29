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
 * Unit test for {@link QuizService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {QuizService.class, QuizDao.class, DatabaseCredentials.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/schema.sql")
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
  
  @Test
  public void testInitRegistration() throws Exception {
    InitRegistration actual = this.quizService.generateQuizId("test");
    InitRegistration expected = this.jdbcTemplate.query(
        "SELECT username, quiz_id, number_of_questions from Quiz where username = ?",
        new Object[] {"test"}, new ResultSetExtractor<InitRegistration> () {

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
    
  }

}
