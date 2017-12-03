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
   * @return
   */
  @RequestMapping(name="/quiz", method=RequestMethod.POST,
       produces="application/json; charset=UTF-8")
  ResponseEntity<InitRegistration> initialiseQuiz() {
    InitRegistration registration = this.quizService.generateQuizId("test");
    return new ResponseEntity<InitRegistration>(registration, HttpStatus.CREATED);
  }
  

  /**
   * Fetches the questions to be played.
   * @param quizId
   * @return
   */
  @RequestMapping(name="/quiz/{quizId}", method=RequestMethod.GET, 
      produces="application/json; charset=UTF-8")
  ResponseEntity<QuizQuestion> questions(@PathVariable("quizId") String quizId) {
    QuizQuestion question = this.quizService.fetchQuestion(quizId);
    if (question == null) {
      return new ResponseEntity<QuizQuestion>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<QuizQuestion>(question, HttpStatus.OK);
  }
  
  @RequestMapping(name="/quiz/{quizId}", method=RequestMethod.POST,
      produces="application/json;charset=UTF-8", consumes="application/json;charset=UTF-8")
  ResponseEntity<Solution> answerQuestion(@PathVariable("quizId") String quizId,
      @RequestBody final Answer answer) {
    this.quizService.checkAnswer(answer);
    return null;
  }

}
