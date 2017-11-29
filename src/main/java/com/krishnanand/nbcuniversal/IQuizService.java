package com.krishnanand.nbcuniversal;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IQuizService {
  
  QuizQuestion fetchQuestion(String quizId);
  
  InitRegistration generateQuizId(String username);

}
