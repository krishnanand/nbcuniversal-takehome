package com.krishnanand.nbcuniversal;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of this class encapsulates a single question for a quiz.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class QuizQuestion {
  
  private String quizId;
  
  @JsonIgnore
  private int questionId;
  
  private String question;
  
  @JsonIgnore
  private boolean answer;

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
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
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuizQuestion [quizId=");
    builder.append(quizId);
    builder.append(", questionId=");
    builder.append(questionId);
    builder.append(", description=");
    builder.append(question);
    builder.append(", answer=");
    builder.append(answer);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.answer, this.question, this.questionId, this.quizId);
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
    if (question == null) {
      if (other.question != null) {
        return false;
      }
    } else if (!question.equals(other.question)) {
      return false;
    }
    if (this.questionId != other.getQuestionId()) {
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
