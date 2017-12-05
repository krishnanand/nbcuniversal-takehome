package com.krishnanand.nbcuniversal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

/**
 * An instance of this class represents a single point of entry for all database operations
 * related to quiz..
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Repository
public class QuizDao implements IQuizDao {
  
  private final JdbcTemplate jdbcTemplate;
  
  private RandomStringGenerator generator;
  
  /**
   * Constructor for {@link QuizDao}.
   * 
   * @param jdbcTemplate jdbc template instance
   */
  @Autowired
  public QuizDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.generator = new RandomStringGenerator(10);
  }

  /**
   * Registers the quiz into the database and creates an entry into the database for
   * a given username.
   */
  @Override
  public InitRegistration registerQuiz(final String username) {
    // TODO Auto-generated method stub
    
    StringBuilder insertQuery = new StringBuilder();
    insertQuery.append("INSERT INTO Quiz(quiz_id, username) ");
    insertQuery.append("VALUES(?, ?)");
    int retry = 0;
    boolean isRegistrationSuccessful = false;
    while (!isRegistrationSuccessful && retry < 5) {
      String quizId = this.generator.nextString();
      // Check if it exists. Ideally, this would be generated by an external
      // distributed tool like the ZooKeeper.
      int count = 0;
      try {
        count = this.jdbcTemplate.update(
            insertQuery.toString(), new PreparedStatementSetter() {
              
              @Override
              public void setValues(PreparedStatement ps) throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, quizId);
                ps.setString(2, username);
              }
            });
      } catch (DataAccessException dae) {
        retry ++;
        continue;
      }
      if (count == 1) {
        isRegistrationSuccessful = true;
        InitRegistration initRegistration = 
            this.jdbcTemplate.query(
                "SELECT username, quiz_id, number_of_questions FROM Quiz where quiz_id = ? and username = ?",
                new Object[] {quizId, username}, new ResultSetExtractor<InitRegistration>() {

                  @Override
                  public InitRegistration extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                      InitRegistration registration = new InitRegistration();
                      registration.setNumberOfQuestions(rs.getInt("number_of_questions"));
                      registration.setActive(registration.getNumberOfQuestions() > 0);
                      registration.setQuizId(rs.getString("quiz_id"));
                      return registration;
                    }
                    return null;
                  }});
        
        this.initialiseScore(initRegistration.getQuizId());
        return initRegistration;
      }
    }
    return null;
  }
  
  void initialiseScore(String quizId) {
   // Initialise the score.
    this.jdbcTemplate.update(
        "INSERT INTO Score(quiz_id, correct_answers, incorrect_answers) VALUES(?, ?, ?)",
        new Object[] {quizId, 0, 0});
  }

  /**
   * Fetches the current quiz status from the database.
   * 
   * <p>This is done by performing a join operation on quiz id and checking the quiz
   * status. If the total number of questions asked is less than the maximum number of 
   * permissible questions
   */
  @Override
  public QuizStatus getCurrentQuizStatus(String quizId) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT q.quiz_id, q.number_of_questions, qs.questions_asked FROM Quiz q  ");
    sb.append(" JOIN QuizStatus qs on q.quiz_id = qs.quiz_id WHERE q.quiz_id= ?");
    QuizStatus status = this.jdbcTemplate.query(sb.toString(), new Object[] {quizId},
        new ResultSetExtractor<QuizStatus>() {

      @Override
      public QuizStatus extractData(ResultSet rs) throws SQLException, DataAccessException {
        while(rs.next()) {
          QuizStatus qs = new QuizStatus();
          qs.setNumberOfAskedQuestions(rs.getInt("questions_asked"));
          qs.setNumberOfEligibleQuestions(rs.getInt("number_of_questions"));
          qs.setQuizId(rs.getString("quiz_id"));
          qs.setQuizEnded(qs.getNumberOfEligibleQuestions() == 0);
          return qs;
        }
        return null;
      }
      
    });
    return status;
  }

  /**
   * Creates an entry for {@code quizId} in {@code QuizStatus}.
   * 
   * @param quizId quiz id
   * @return number of rows updated
   */
  @Override
  public int initialiseQuizStatus(String quizId) {
    return this.jdbcTemplate.update(
        "INSERT INTO QuizStatus(quiz_id) VALUES(?)",
        new Object[] {quizId});
  }

  /**
   * The function increments the {@code correct_answers} by 1 if the quiz solution is correct, and
   * the {@code incorrect_answers} if the quiz solution is not correct.
   * 
   * @param solution value encapsulating the solution
   * @return score number of scores updated
   */ 
  @Override
  public int updateScore(final Solution solution) {
    StringBuilder sb = new StringBuilder();
    if (solution.getCorrectAnswer() != null &&
        solution.getCorrectAnswer().equals(solution.getPlayerAnswer())) {
      sb.append("UPDATE Score SET correct_answers = correct_answers + 1 WHERE quiz_id = ?");
    } else {
      sb.append("UPDATE Score SET incorrect_answers = incorrect_answers + 1 WHERE quiz_id = ?");
    }
    return this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1, solution.getQuizId());
      }
    });
  }


  @Override
  public Score getCurrentScore(String quizId) {
    Score score = this.jdbcTemplate.query(
        "SELECT correct_answers, incorrect_answers FROM Score where quiz_id = ?",
        new Object[] {quizId}, new ResultSetExtractor<Score>() {

          @Override
          public Score extractData(ResultSet rs) throws SQLException, DataAccessException {
            while(rs.next()) {
              Score score = new Score();
              score.setQuizId(quizId);
              score.setCorrectAnswers(rs.getInt("correct_answers"));
              score.setIncorrectAnswers(rs.getInt("incorrect_answers"));
              return score;
            }
            return null;
          }
        });
    return score;
  }


  /**
   * Marks the quiz as completed.
   * 
   * @param quizId Marks the quiz as completed
   */
  @Override
  public boolean markQuizAsCompleted(String quizId) {
    int count =
        this.jdbcTemplate.update("UPDATE Quiz SET number_of_questions = 0 WHERE quiz_id = ?",
            new Object[] {quizId});
    return count == 1;
  }

}
