package com.krishnanand.nbcuniversal;

/**
 * Strategy definition that encapsulates all the data enca
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IQuizDao {
  
  /**
   * Implementation of this function will register a quiz for a given user.
   * 
   * @param username username for which a quiz is to be registered
   * @return value object representing the registration details
   */
  InitRegistration registerQuiz(String username);
  
  /**
   * Fetches the status of the quiz for a given quiz id
   * 
   * @param quizId quiz id
   * @return value object representing the quiz status
   */
  QuizStatus getCurrentQuizStatus(String quizId);
  
  /**
   * Initialises the quiz status.
   * 
   * <p>This operation should be executed as soon as the quiz is registered.
   * 
   * @param quizId quiz status
   * @return number of rows updated
   */
  int initialiseQuizStatus(String quizId);

}
