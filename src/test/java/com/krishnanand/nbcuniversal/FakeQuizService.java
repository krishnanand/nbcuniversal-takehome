package com.krishnanand.nbcuniversal;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
public class FakeQuizService implements IQuizService {
  private List<QuizQuestion> quizQuestions;
  
  /**
   * @throws Exception 
   * 
   */
  public FakeQuizService() throws Exception {
    this.loadQuestions();
  }
  
  private void loadQuestions() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    this.quizQuestions = 
        objectMapper.readValue(ClassLoader.getSystemResource("questions.json"),
            new TypeReference<List<QuizQuestion>>() { });
    
  }

  /* (non-Javadoc)
   * @see com.krishnanand.nbcuniversal.IQuizService#fetchQuestion(java.lang.String)
   */
  @Override
  public QuizQuestion fetchQuestion(String quizId) {
    // TODO Auto-generated method stub
    Random random = new Random();
    int index = random.nextInt(this.quizQuestions.size());
    return this.quizQuestions.get(index);
  }

  /* (non-Javadoc)
   * @see com.krishnanand.nbcuniversal.IQuizService#generateQuizId()
   */
  @Override
  public InitRegistration generateQuizId(String username) {
    // TODO Auto-generated method stub
    InitRegistration registration = new InitRegistration();
    registration.setUsername(username);
    registration.setNumberOfQuestions(3);
    registration.setActive(true);
    return new InitRegistration();
  }

  /* (non-Javadoc)
   * @see com.krishnanand.nbcuniversal.IQuizService#checkAnswer(com.krishnanand.nbcuniversal.Answer)
   */
  @Override
  public Solution checkAnswer(String quizId, Answer answer) {
    // TODO Auto-generated method stub
    Solution solution = new Solution();
    solution.setCorrectAnswer(true);
    solution.setQuestion("Is Earth round");
    solution.setPlayerAnswer(true);
    solution.setQuizId(quizId);
    return solution;
  }

  /* (non-Javadoc)
   * @see com.krishnanand.nbcuniversal.IQuizService#getScore(java.lang.String)
   */
  @Override
  public Score getScore(String quizId) {
    // TODO Auto-generated method stub
    return null;
  }

}
