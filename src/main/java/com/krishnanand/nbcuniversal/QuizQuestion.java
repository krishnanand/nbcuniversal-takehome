package com.krishnanand.nbcuniversal;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class QuizQuestion {
  
  private String quizId;
  
  private String questionId;
  
  private String description;
  
  private boolean answer;
  
  private Integer currentQuestion;
  
  private Integer numberOfQuestionsRemaining;

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getCurrentQuestion() {
    return currentQuestion;
  }

  public void setCurrentQuestion(Integer currentQuestion) {
    this.currentQuestion = currentQuestion;
  }

  public Integer getNumberOfQuestionsRemaining() {
    return numberOfQuestionsRemaining;
  }

  public void setNumberOfQuestionsRemaining(Integer numberOfQuestionsRemaining) {
    this.numberOfQuestionsRemaining = numberOfQuestionsRemaining;
  }

  public boolean isAnswer() {
    return answer;
  }

  public void setAnswer(boolean answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuizQuestion [quizId=");
    builder.append(quizId);
    builder.append(", questionId=");
    builder.append(questionId);
    builder.append(", description=");
    builder.append(description);
    builder.append(", answer=");
    builder.append(answer);
    builder.append(", currentQuestion=");
    builder.append(currentQuestion);
    builder.append(", numberOfQuestionsRemaining=");
    builder.append(numberOfQuestionsRemaining);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.answer, this.currentQuestion, this.description,
        this.numberOfQuestionsRemaining, this.questionId, this.quizId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    QuizQuestion other = (QuizQuestion) obj;
    if (answer != other.answer) {
      return false;
    }
    if (currentQuestion == null) {
      if (other.currentQuestion != null) {
        return false;
      }
    } else if (!currentQuestion.equals(other.currentQuestion)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (numberOfQuestionsRemaining == null) {
      if (other.numberOfQuestionsRemaining != null) {
        return false;
      }
    } else if (!numberOfQuestionsRemaining.equals(other.numberOfQuestionsRemaining)) {
      return false;
    }
    if (questionId == null) {
      if (other.questionId != null) {
        return false;
      }
    } else if (!questionId.equals(other.questionId)) {
      return false;
    }
    if (quizId == null) {
      if (other.quizId != null) {
        return false;
      }
    } else if (!quizId.equals(other.quizId)) {
      return false;
    }
    return true;
  }
  
  

}