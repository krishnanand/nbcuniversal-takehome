package com.krishnanand.nbcuniversal;

/**
 * Represents the solution to the answer.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class Solution {
  
  private String quizId;
  
  private String question;
  
  private boolean correctAnswer;
  
  private boolean playerAnswer;

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

  public boolean isCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(boolean correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public boolean isPlayerAnswer() {
    return playerAnswer;
  }

  public void setPlayerAnswer(boolean playerAnswer) {
    this.playerAnswer = playerAnswer;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (correctAnswer ? 1231 : 1237);
    result = prime * result + ((question == null) ? 0 : question.hashCode());
    result = prime * result + (playerAnswer ? 1231 : 1237);
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
    if (correctAnswer != other.correctAnswer) {
      return false;
    }
    if (question == null) {
      if (other.question != null) {
        return false;
      }
    } else if (!question.equals(other.question)) {
      return false;
    }
    if (playerAnswer != other.playerAnswer) {
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Solution [quizId=");
    builder.append(quizId);
    builder.append(", description=");
    builder.append(question);
    builder.append(", correctAnswer=");
    builder.append(correctAnswer);
    builder.append(", playerAnswer=");
    builder.append(playerAnswer);
    builder.append("]");
    return builder.toString();
  }

}
