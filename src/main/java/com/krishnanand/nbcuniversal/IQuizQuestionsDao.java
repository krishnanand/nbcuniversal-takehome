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
   * Adds an entry to {@code QuizQuestions} table.
   * 
   * @param quizId quiz id
   * @param questionId question id to be inserted
   */
  int markQuestionsAsAsked(String quizId, int questionId);

}