package com.krishnanand.nbcuniversal;

/**
 * An instance of this class encapsulates an answer.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class Answer {
  
  private int questionId;
  
  private boolean response;

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
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
    result = prime * result + questionId;
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
    if (questionId != other.questionId) {
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
    builder.append("Answer [");
    builder.append("questionId=");
    builder.append(questionId);
    builder.append(", response=");
    builder.append(response);
    builder.append("]");
    return builder.toString();
  }

}
