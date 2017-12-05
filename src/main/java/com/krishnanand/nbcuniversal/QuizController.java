package com.krishnanand.nbcuniversal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry for all quiz controllers.
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController("/nbcuniversal")
public class QuizController {
  
  private final IQuizService quizService;
  
  @Autowired
  public QuizController(IQuizService quizService) {
    this.quizService = quizService;
  }
  

  /**
   * Initialises the quiz.
   * 
   * @return registration information
   */
  @RequestMapping(value="/quiz", method=RequestMethod.POST)
  public ResponseEntity<InitRegistration> initialiseQuiz() {
    InitRegistration registration = this.quizService.generateQuizId("test");
    return new ResponseEntity<InitRegistration>(registration, HttpStatus.CREATED);
  }

  /**
   * Fetches the questions to be answered.
   * 
   * @param quizId unique quiz identifier
   * @return unique question
   */
  @RequestMapping(value="/quiz/{quizId}/questions", method=RequestMethod.GET)
  public ResponseEntity<QuizQuestion> questions(@PathVariable String quizId) {
    QuizQuestion question = this.quizService.fetchQuestion(quizId);
    if (question == null) {
      return new ResponseEntity<QuizQuestion>(HttpStatus.TOO_MANY_REQUESTS);
    }
    return new ResponseEntity<QuizQuestion>(question, HttpStatus.OK);
  }
  
  @RequestMapping(value="/quiz/{quizId}/questions", method=RequestMethod.POST)
  public ResponseEntity<Solution> answerQuestion(@PathVariable String quizId,
      @RequestBody final Answer answer) {
    Solution solution = this.quizService.checkAnswer(quizId, answer);
    if (solution == null) {
      // The question was not answered.
      return new ResponseEntity<Solution>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(solution, HttpStatus.OK);
  }
  
  @RequestMapping(value="/quiz/{quizId}/score", method=RequestMethod.GET)
  public Score getScore(@PathVariable String quizId) {
    return this.quizService.getScore(quizId);
  }
}
