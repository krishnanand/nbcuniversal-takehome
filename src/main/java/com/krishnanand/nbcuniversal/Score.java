package com.krishnanand.nbcuniversal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Encapsulates the score at any given time.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class Score implements IError {
  
  @JsonIgnore
  private String quizId;
  
  private Integer incorrectAnswers;
  
  private Integer correctAnswers;
  
  private Integer score;
  
  @JsonInclude(Include.NON_EMPTY)
  private List<Error> errors;
  
  public Score() {
    this.errors = new ArrayList<>();
  }

  public String getQuizId() {
    return quizId;
  }

  public Integer getIncorrectAnswers() {
    return incorrectAnswers;
  }

  public void setIncorrectAnswers(Integer incorrectAnswers) {
    this.incorrectAnswers = incorrectAnswers;
  }

  public Integer getCorrectAnswers() {
    return correctAnswers;
  }

  public void setCorrectAnswers(Integer correctAnswers) {
    this.correctAnswers = correctAnswers;
    this.score = correctAnswers;
  }
  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }

  @Override
  public List<Error> getErrors() {
    return this.errors;
  }

  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((correctAnswers == null) ? 0 : correctAnswers.hashCode());
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    result = prime * result + ((incorrectAnswers == null) ? 0 : incorrectAnswers.hashCode());
    result = prime * result + ((quizId == null) ? 0 : quizId.hashCode());
    result = prime * result + ((score == null) ? 0 : score.hashCode());
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
    Score other = (Score) obj;
    if (correctAnswers == null) {
      if (other.correctAnswers != null) {
        return false;
      }
    } else if (!correctAnswers.equals(other.correctAnswers)) {
      return false;
    }
    if (errors == null) {
      if (other.errors != null) {
        return false;
      }
    } else if (!errors.equals(other.errors)) {
      return false;
    }
    if (incorrectAnswers == null) {
      if (other.incorrectAnswers != null) {
        return false;
      }
    } else if (!incorrectAnswers.equals(other.incorrectAnswers)) {
      return false;
    }
    if (quizId == null) {
      if (other.quizId != null) {
        return false;
      }
    } else if (!quizId.equals(other.quizId)) {
      return false;
    }
    if (score == null) {
      if (other.score != null) {
        return false;
      }
    } else if (!score.equals(other.score)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Score [quizId=");
    builder.append(quizId);
    builder.append(", incorrectAnswers=");
    builder.append(incorrectAnswers);
    builder.append(", correctAnswers=");
    builder.append(correctAnswers);
    builder.append(", score=");
    builder.append(score);
    builder.append(", errors=");
    builder.append(errors);
    builder.append("]");
    return builder.toString();
  }
}
