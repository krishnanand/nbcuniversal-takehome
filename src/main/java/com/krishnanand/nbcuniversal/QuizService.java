package com.krishnanand.nbcuniversal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation to implement the quiz service.
 * @author krishnanand (Kartik Krishnanand)
 */
@Component
public class QuizService implements IQuizService {
  
  private final IQuizDao quizDao;
  
  @Autowired
  public QuizService(IQuizDao quizDao) {
    this.quizDao = quizDao;
  }
  
  
  @Override
  public QuizQuestion fetchQuestion(String quizId) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Fetches the details pertaining to the registration of quiz.
   */
  @Override
  @Transactional
  public InitRegistration generateQuizId(String username) {
    return this.quizDao.registerQuiz(username);
  }

}
