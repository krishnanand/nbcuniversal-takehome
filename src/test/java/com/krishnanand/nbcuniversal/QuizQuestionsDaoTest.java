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
 * Unit test for {@link QuizQuestionsDao}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {QuizQuestionsDao.class, DatabaseCredentials.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts= {"classpath:/schema.sql", "classpath:/data.sql"})
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class QuizQuestionsDaoTest {
  
  @Autowired
  private QuizQuestionsDao questionsDao;
  
  @Autowired
  private DataSource dataSource;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  
  @Test
  public void testFetchUniqueQuestion_Success() throws Exception {
    QuizQuestion question = this.questionsDao.fetchUniqueQuizQuestion("ABCDE12345");
    Assert.assertNotNull(question);
    this.assertCount(9, "ABCDE12345");
  }
  
  private void assertCount(int expected, final String quizId) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(q.answer) FROM Questions q ");
    sb.append("LEFT JOIN QuizQuestions qq ON ");
    sb.append("q.question_id = qq.question_id WHERE q.question_id NOT IN ");
    sb.append("(SELECT qq.question_id FROM Questions WHERE qq.quiz_id = ?)");
    
    int actual = this.jdbcTemplate.queryForObject(sb.toString(), new Object[] {quizId}, int.class);
    Assert.assertEquals(actual, expected);
  }
  
  @Test
  public void testFetchUniqueQuestion_ForNoIds() throws Exception {
    QuizQuestion question = this.questionsDao.fetchUniqueQuizQuestion("missing");
    Assert.assertNotNull(question);
    this.assertCount(10, "missing");
  }
  
  @Test
  public void testUpdateQuizStatus() throws Exception {
    Assert.assertEquals(0,  (int) this.jdbcTemplate.query(
        "SELECT questions_asked from QuizStatus where quiz_id = ?",
        new Object[] {"ABCDE12345"}, new ResultSetExtractor<Integer>() {

          @Override
          public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
            // TODO Auto-generated method stub
            while(rs.next()) {
              return rs.getInt("questions_asked");
            }
            return null;
          }
          
        }));
    int row = this.questionsDao.updateQuizStatus("ABCDE12345");
    Assert.assertEquals(row, 1);
    Assert.assertEquals(1, (int) this.jdbcTemplate.query(
        "SELECT questions_asked from QuizStatus where quiz_id = ?",
        new Object[] {"ABCDE12345"}, new ResultSetExtractor<Integer>() {

          @Override
          public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
            // TODO Auto-generated method stub
            while(rs.next()) {
              return rs.getInt("questions_asked");
            }
            return null;
          }
          
        }));
  }
  
  @Test
  public void testMarkedQuestionsAsAsked() throws Exception {
    Assert.assertEquals(0,  (int) this.jdbcTemplate.queryForObject(
        "SELECT count(question_id) from QuizQuestions where quiz_id = ? AND question_id = ?",
        new Object[] {"ABCDE12345", 2}, int.class));
    Assert.assertEquals(1, this.questionsDao.markQuestionsAsAsked("ABCDE12345", 2));
    Assert.assertEquals(1,  (int) this.jdbcTemplate.queryForObject(
        "SELECT count(question_id) from QuizQuestions where quiz_id = ? AND question_id = ?",
        new Object[] {"ABCDE12345", 2}, int.class));
  }

}
