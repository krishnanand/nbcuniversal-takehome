package com.krishnanand.nbcuniversal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of this class represents a system generated solution for a single question.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class Solution implements IError {
  
  @JsonIgnore
  private String quizId;
  
  private String question;
  
  private Boolean correctAnswer;
  
  private Boolean playerAnswer;

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String description) {
    this.question = description;
  }

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }
  
  public Boolean getCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(Boolean correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public Boolean getPlayerAnswer() {
    return playerAnswer;
  }

  public void setPlayerAnswer(Boolean playerAnswer) {
    this.playerAnswer = playerAnswer;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }


  @JsonInclude(Include.NON_EMPTY)
  private List<Error> errors;

  @Override
  public List<Error> getErrors() {
    // TODO Auto-generated method stub
    return this.errors;
  }
  
  public Solution() {
    this.errors = new ArrayList<>();
  }

  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Solution [quizId=");
    builder.append(quizId);
    builder.append(", question=");
    builder.append(question);
    builder.append(", correctAnswer=");
    builder.append(correctAnswer);
    builder.append(", playerAnswer=");
    builder.append(playerAnswer);
    builder.append(", errors=");
    builder.append(errors);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((correctAnswer == null) ? 0 : correctAnswer.hashCode());
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    result = prime * result + ((playerAnswer == null) ? 0 : playerAnswer.hashCode());
    result = prime * result + ((question == null) ? 0 : question.hashCode());
    result = prime * result + ((quizId == null) ? 0 : quizId.hashCode());
    return result;
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
    Solution other = (Solution) obj;
    if (correctAnswer == null) {
      if (other.correctAnswer != null) {
        return false;
      }
    } else if (!correctAnswer.equals(other.correctAnswer)) {
      return false;
    }
    if (errors == null) {
      if (other.errors != null) {
        return false;
      }
    } else if (!errors.equals(other.errors)) {
      return false;
    }
    if (playerAnswer == null) {
      if (other.playerAnswer != null) {
        return false;
      }
    } else if (!playerAnswer.equals(other.playerAnswer)) {
      return false;
    }
    if (question == null) {
      if (other.question != null) {
        return false;
      }
    } else if (!question.equals(other.question)) {
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
