package com.krishnanand.nbcuniversal;

/**
 * Strategy defintion class that manages all CRUD operations related to questions in
 * a quiz.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IQuizQuestionsDao {
  

  /**
   * Fetches the quiz questions that have not been selected for a given quiz selection.
   * 
   * @param quizId quiz id for which the information is to be fetched
   * @return
   */
  QuizQuestion fetchUniqueQuizQuestion(String quizId);
  
  /**
   * Updates the {@code questions_asked} counter of {@code QuizStatus} table by 1 for
   * a quiz id.
   * 
   * @param quizId quiz id
   * @return the number of rows updated.
   */
  int updateQuizStatus(String quizId);
  
  /**
   * Checks if the question has been asked
   * 
   * @param quizId quiz id
   * @param questionId question id to be queried
   */
  boolean isQuestionAsked(String quizId, int questionId);
  
  /**
   * Adds an entry to {@code QuizQuestions} table.
   * 
   * @param quizId quiz id
   * @param questionId question id to be inserted
   */
  int markQuestionAsAsked(String quizId, int questionId);
  
  /**
   * Checks answer against the system.
   * 
   * @param answer answer
   * @return
   */
  Solution checkAnswer(Answer answer);
  
  /**
   * Checks if the question has been asked.
   * 
   * @param quizId unique quiz id associated with the question
   * @param questionId unique question id to be checked
   * @return {@code true} if the question was asked; {@code false} otherwise
   */
  boolean isQuestionAnswered(String quizId, int questionId);
  
  /**
   * Marks question as answered
   * 
   * @param quizId unique quiz id associated with the question
   * @param questionId unique question id to be checked
   * @return number of rows updated
   */
  int markQuestionAsAnswered(String quizId, int question_id);

}
