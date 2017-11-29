package com.krishnanand.nbcuniversal;

/**
 * An instance of this class encapsulates the current quiz status.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class QuizStatus {
  
  private String quizId;
  
  private long numberOfEligibleQuestions;
  
  private long numberOfAskedQuestions;
  
  private boolean quizEnded;

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public long getNumberOfEligibleQuestions() {
    return numberOfEligibleQuestions;
  }

  public void setNumberOfEligibleQuestions(long numberOfEligibleQuestions) {
    this.numberOfEligibleQuestions = numberOfEligibleQuestions;
  }

  public long getNumberOfAskedQuestions() {
    return numberOfAskedQuestions;
  }

  public void setNumberOfAskedQuestions(long numberOfAskedQuestions) {
    this.numberOfAskedQuestions = numberOfAskedQuestions;
  }

  public boolean isQuizEnded() {
    return quizEnded;
  }

  public void setQuizEnded(boolean quizEnded) {
    this.quizEnded = quizEnded;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuizStatus [quizId=");
    builder.append(quizId);
    builder.append(", numberOfEligibleQuestions=");
    builder.append(numberOfEligibleQuestions);
    builder.append(", numberOfAskedQuestions=");
    builder.append(numberOfAskedQuestions);
    builder.append(", quizEnded=");
    builder.append(quizEnded);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (numberOfAskedQuestions ^ (numberOfAskedQuestions >>> 32));
    result =
        prime * result + (int) (numberOfEligibleQuestions ^ (numberOfEligibleQuestions >>> 32));
    result = prime * result + (quizEnded ? 1231 : 1237);
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
    QuizStatus other = (QuizStatus) obj;
    if (numberOfAskedQuestions != other.numberOfAskedQuestions) {
      return false;
    }
    if (numberOfEligibleQuestions != other.numberOfEligibleQuestions) {
      return false;
    }
    if (quizEnded != other.quizEnded) {
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
