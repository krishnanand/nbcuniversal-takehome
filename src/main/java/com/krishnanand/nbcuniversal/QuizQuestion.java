package com.krishnanand.nbcuniversal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of this class encapsulates a single question for a quiz.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class QuizQuestion implements IError {
  
  @JsonIgnore
  private String quizId;
  
  private Integer questionId;
  
  private String question;
  
  @JsonIgnore
  private boolean answer;

  public String getQuizId() {
    return quizId;
  }
  
  @JsonInclude(value=Include.NON_EMPTY)
  private List<IError.Error> errors;
  
  public QuizQuestion() {
    this.errors = new ArrayList<>();
  }
  
  public void addError(int code, String message) {
    this.errors.add(new IError.Error(code, message));
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String description) {
    this.question = description;
  }

  public boolean isAnswer() {
    return answer;
  }

  public void setAnswer(boolean answer) {
    this.answer = answer;
  }

  @Override
  public List<IError.Error> getErrors() {
    return errors;
  }

  public void setErrors(List<IError.Error> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuizQuestion [quizId=");
    builder.append(quizId);
    builder.append(", questionId=");
    builder.append(questionId);
    builder.append(", question=");
    builder.append(question);
    builder.append(", answer=");
    builder.append(answer);
    builder.append(", errors=");
    builder.append(errors);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (answer ? 1231 : 1237);
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    result = prime * result + ((question == null) ? 0 : question.hashCode());
    result = prime * result + questionId;
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
    QuizQuestion other = (QuizQuestion) obj;
    if (answer != other.answer) {
      return false;
    }
    if (errors == null) {
      if (other.errors != null) {
        return false;
      }
    } else if (!errors.equals(other.errors)) {
      return false;
    }
    if (question == null) {
      if (other.question != null) {
        return false;
      }
    } else if (!question.equals(other.question)) {
      return false;
    }
    if (questionId != other.questionId) {
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
