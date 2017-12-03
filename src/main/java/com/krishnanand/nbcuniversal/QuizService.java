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
  
  private final IQuizQuestionsDao questionsDao;
  
  @Autowired
  public QuizService(IQuizDao quizDao, IQuizQuestionsDao questionsDao) {
    this.quizDao = quizDao;
    this.questionsDao = questionsDao;
  }
  
  /**
   * Fetches a unique question by quiz id.
   * 
   * <p>The implementation logic is given below
   * <ul>
   *   <li>Checks how many questions have been asked.</li>
   *   <li>If the quiz has ended, then it returns {@code null}.
   *   <li>If the quiz has not ended, then a unique question has fetched.</li>
   *   <li>the number of questions counter is updated.</li>
   *   <li>The question is marked as asked.<li>
   * </ul>
   */
  @Override
  @Transactional
  public QuizQuestion fetchQuestion(String quizId) {
    QuizStatus current = this.quizDao.getCurrentQuizStatus(quizId);
    if (current.isQuizEnded()) {
      return null;
    }
    //
    QuizQuestion question = this.questionsDao.fetchUniqueQuizQuestion(quizId);
    if (question == null) {
      return null;
    }
    this.questionsDao.updateQuizStatus(quizId);
    this.questionsDao.markQuestionsAsAsked(quizId, question.getQuestionId());
    return question;
  }

  /**
   * Fetches the details pertaining to the registration of quiz.
   */
  @Override
  @Transactional
  public InitRegistration generateQuizId(String username) {
    InitRegistration initRegistration = this.quizDao.registerQuiz(username);
    this.quizDao.initialiseQuizStatus(initRegistration.getQuizId());
    return initRegistration;
  }

  /**
   * Checks the solution against the system.
   * 
   * <p>In addition the implementation updates the score after
   * every answer.
   * 
   * @param answer answer object
   */
  @Override
  @Transactional
  public Solution checkAnswer(Answer answer) {
    Solution solution = this.questionsDao.checkAnswer(answer);
    return solution;
  }

}
