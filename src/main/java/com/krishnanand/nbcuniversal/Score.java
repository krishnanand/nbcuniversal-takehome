package com.krishnanand.nbcuniversal;

/**
 * Encapsulates the score at any given time.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class Score {
  
  private String quizId;
  
  private int incorrectAnswers;
  
  private int correctAnswers;
  
  private int score;

  public String getQuizId() {
    return quizId;
  }

  public void setQuizId(String quizId) {
    this.quizId = quizId;
  }

  public int getIncorrectAnswers() {
    return incorrectAnswers;
  }

  public void setIncorrectAnswers(int incorrectAnswers) {
    this.incorrectAnswers = incorrectAnswers;
  }

  public int getCorrectAnswers() {
    return correctAnswers;
  }

  public void setCorrectAnswers(int correctAnswers) {
    this.correctAnswers = correctAnswers;
  }

  public int getScore() {
    return score;
  }
  
  public void calculateScore() {
    this.score = this.correctAnswers;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + correctAnswers;
    result = prime * result + incorrectAnswers;
    result = prime * result + ((quizId == null) ? 0 : quizId.hashCode());
    result = prime * result + score;
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
    if (correctAnswers != other.correctAnswers) {
      return false;
    }
    if (incorrectAnswers != other.incorrectAnswers) {
      return false;
    }
    if (quizId == null) {
      if (other.quizId != null) {
        return false;
      }
    } else if (!quizId.equals(other.quizId)) {
      return false;
    }
    if (score != other.score) {
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
    builder.append("]");
    return builder.toString();
  }
  
  

}
