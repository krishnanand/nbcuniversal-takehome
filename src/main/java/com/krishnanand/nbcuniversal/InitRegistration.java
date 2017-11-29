package com.krishnanand.nbcuniversal;

/**
 * An instance of the class encapsulates the output of the quiz registration
 * process.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class InitRegistration {
  
  private String username;
  
  private int numberOfQuestions;
  
  private String quizId;
  
  private boolean isActive;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getNumberOfQuestions() {
    return numberOfQuestions;
  }

  public void setNumberOfQuestions(int numberOfQuestions) {
    this.numberOfQuestions = numberOfQuestions;
  }

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("InitRegistration [username=");
    builder.append(username);
    builder.append(", numberOfQuestions=");
    builder.append(numberOfQuestions);
    builder.append(", quizId=");
    builder.append(quizId);
    builder.append(", isActive=");
    builder.append(isActive);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isActive ? 1231 : 1237);
    result = prime * result + numberOfQuestions;
    result = prime * result + ((quizId == null) ? 0 : quizId.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    InitRegistration other = (InitRegistration) obj;
    if (isActive != other.isActive) {
      return false;
    }
    if (numberOfQuestions != other.numberOfQuestions) {
      return false;
    }
    if (quizId == null) {
      if (other.quizId != null) {
        return false;
      }
    } else if (!quizId.equals(other.quizId)) {
      return false;
    }
    if (username == null) {
      if (other.username != null) {
        return false;
      }
    } else if (!username.equals(other.username)) {
      return false;
    }
    return true;
  }

}
