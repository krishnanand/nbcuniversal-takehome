package com.krishnanand.nbcuniversal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Strategy implementation of {@link QuizQuestionsDao}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Repository
public class QuizQuestionsDao implements IQuizQuestionsDao {
  
  private final DataSource dataSource;
  
  private final JdbcTemplate jdbcTemplate;
  
  @Autowired
  public QuizQuestionsDao(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }

  /**
   * This is responsible for execution for the following tasks.
   * 
   * <ul>
   *  <li>Fetch all questions from {@code Questions} table that are not present in
   *  {@code QuizQuestions} table for a given quiz id.</li>
   *  <li>Select a random quiz question among the result set.<li>
   * </ul>
   */
  @Override
  public QuizQuestion fetchUniqueQuizQuestion(String quizId) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT q.question_id, q.question_text, q.answer FROM Questions q ");
    sb.append("WHERE NOT EXISTS (SELECT 1 FROM QuizQuestions WHERE question_id = q.question_id ");
    sb.append(" AND quiz_id = ?) ");
    sb.append("OR NOT EXISTS (SELECT 1 FROM QuizQuestions WHERE quiz_id = ?)");
    List<QuizQuestion> questions = 
        this.jdbcTemplate.query(
            sb.toString(), new Object[] {quizId, quizId}, new RowMapper<QuizQuestion>() {

          @Override
          public QuizQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuizQuestion qq = new QuizQuestion();
            qq.setQuestionId(rs.getInt("question_id"));
            qq.setQuizId(quizId);
            qq.setAnswer(rs.getBoolean("answer"));
            qq.setQuestion(rs.getString("question_text"));
            return qq;
          }
        });
    Collections.shuffle(questions);
    return questions.isEmpty() ? null : questions.get(0);
  }

  /**
   * The implementation increments the column {@code questions_asked} by 1
   * for a given quiz id.
   * 
   * @param quizId quiz id
   * @return number of rows updated
   */
  @Override
  public int updateQuizStatus(String quizId) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE QuizStatus SET questions_asked = questions_asked + 1 ");
    sb.append("WHERE quiz_id = ?");
    return this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1,  quizId);
      }
    });
  }

  /**
   * Inserts the asked quiz questions to {@code QuizQuestions} table.
   * 
   * <p>The implementation assumes that quiz id exists.
   * 
   * @param quizId quiz id for which the questions are to be asked
   * @param questionId question id for which the questions are to be asked
   */
  @Override
  public int markQuestionsAsAsked(String quizId, int questionId) {
    return this.jdbcTemplate.update(
        "INSERT INTO QuizQuestions(quiz_id, question_id) VALUES(?, ?)",
        new Object[] {quizId, questionId});
  }

  /**
   * Checks answer against the system.
   * 
   * @param answer answer
   * @return solution
   */
  @Override
  public Solution checkAnswer(Answer answer) {
    Solution solution = this.jdbcTemplate.query(
        "SELECT question_id, question_text, answer FROM Questions where question_id = ?",
        new Object[] {answer.getQuestionId()}, new ResultSetExtractor<Solution>() {

          @Override
          public Solution extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
              Solution sol = new Solution();
              sol.setQuizId(answer.getQuizId());
              sol.setCorrectAnswer(rs.getBoolean("answer"));
              sol.setPlayerAnswer(answer.isResponse());
              sol.setDescription(rs.getString("question_text"));
              return sol;
            }
            return null;
          }
          
        });
    
    return solution;
  }

  /**
   * Checks if the question has been asked.
   * 
   * @param quizId unique quiz id associated with the question
   * @param questionId unique question id to be checked
   * @return {@code true} if the question was asked; {@code false} otherwise
   */
  @Override
  public boolean isQuestionAsked(String quizId, int questionId) {
    StringBuilder sb = new StringBuilder("SELECT count(question_id) FROM QuizQuestions ");
    sb.append("WHERE quiz_id = ? AND question_id = ?");
    int count = this.jdbcTemplate.queryForObject(
        sb.toString(), new Object[] {quizId, questionId}, int.class); 
    return count == 1;
  }
}
