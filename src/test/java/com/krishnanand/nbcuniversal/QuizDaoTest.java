package com.krishnanand.nbcuniversal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    Assert.assertNotNull(actual);
    QuizStatus expected = new QuizStatus();
    expected.setNumberOfAskedQuestions(0);
    expected.setNumberOfEligibleQuestions(3);
    expected.setQuizId("ABCDE12345");
    expected.setQuizEnded(false);
    Assert.assertEquals(expected, actual);
    
  }

}
