package com.krishnanand.nbcuniversal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry for all quiz controllers.
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController("/nbcuniversal")
public class QuizController {
  
  @Autowired
  private IQuizService quizService;
  
  public void setQuizService(IQuizService quizService) {
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
  @ResponseBody
  @RequestMapping(name="/quiz/{quizId}", method=RequestMethod.GET, 
      produces="application/json")
  QuizQuestion fetchNextQuestion(@PathVariable("quizId") String quizId) {
    return this.quizService.fetchQuestion(quizId);
  }

}
