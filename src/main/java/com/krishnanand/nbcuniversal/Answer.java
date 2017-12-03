package com.krishnanand.nbcuniversal;

/**
 * An instance of this class encapsulates an answer.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class Answer {
  
  private String quizId;
  
  private int questionId;
  
  private String description;
  
  private boolean response;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isResponse() {
    return response;
  }

  public void setResponse(boolean response) {
    this.response = response;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + questionId;
    result = prime * result + ((quizId == null) ? 0 : quizId.hashCode());
    result = prime * result + (response ? 1231 : 1237);
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
    Answer other = (Answer) obj;
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
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
    if (response != other.response) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Answer [quizId=");
    builder.append(quizId);
    builder.append(", questionId=");
    builder.append(questionId);
    builder.append(", description=");
    builder.append(description);
    builder.append(", response=");
    builder.append(response);
    builder.append("]");
    return builder.toString();
  }

}
