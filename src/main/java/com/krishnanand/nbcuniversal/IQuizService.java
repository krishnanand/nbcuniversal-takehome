package com.krishnanand.nbcuniversal;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IQuizService {
  
  /**
   * An implementation of this method will return a unique question or
   *  {@code null} if the attempts are exhausted.
   *  
   * @param quizId unique quiz id
   * @return instance representing a question; or {@code null}
   */
  QuizQuestion fetchQuestion(String quizId);
  
  /**
   * Generates the registration details.
   * 
   * @param username username for which a quiz id is to be generated
   * @return registration details
   */
  InitRegistration generateQuizId(String username);
  
  /**
   * Verifies the answer against the system.
   * @param answer
   * @return
   */
  Solution checkAnswer(String quizId, Answer answer);
  
  /**
   * Gets the score for quiz id.
   * 
   * @param quizId quiz id
   * @return score
   */
  Score getScore(String quizId);
  
  /**
   * Marks the quiz game as completed
   * 
   * @param quizId quiz is completed
   */
  void markQuizAsCompleted(String quizId);
}
