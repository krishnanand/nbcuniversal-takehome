package com.krishnanand.nbcuniversal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
    sb.append("LEFT JOIN QuizQuestions qq ON ");
    sb.append("q.question_id = qq.question_id WHERE q.question_id NOT IN ");
    sb.append("(SELECT qq.question_id FROM Questions WHERE qq.quiz_id = ?)");
    List<QuizQuestion> questions = 
        this.jdbcTemplate.query(sb.toString(), new Object[] {quizId}, new RowMapper<QuizQuestion>() {

          @Override
          public QuizQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuizQuestion qq = new QuizQuestion();
            qq.setQuestionId(rs.getString("question_id"));
            qq.setQuizId(quizId);
            qq.setAnswer(rs.getBoolean("answer"));
            qq.setDescription(rs.getString("question_text"));
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
   */
  @Override
  public int updateQuizStatus(String quizId) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE QuizStatus SET questions_asked = questions_asked + 1 ");
    sb.append("WHERE quiz_id = ?");
    this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1,  quizId);
      }
      
    });
    return 0;
  }

  /**
   * Inserts the asked quiz questions to {@code QuizQuestions} table.
   * 
   * @param quizId quiz id for which the questions are to be asked
   * @param questionId question id for which the questions are to be asked
   */
  @Override
  public int markQuestionsAsAsked(String quizId, int questionId) {
    // TODO Auto-generated method stub
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO QuizQuestions(quiz_id, question_id) VALUES(?, ?)");
    return this.jdbcTemplate.update(sb.toString(), new Object[] {quizId, questionId});
  }
}
