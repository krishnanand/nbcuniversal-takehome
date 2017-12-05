package com.krishnanand.nbcuniversal;

import java.util.List;

/**
 * A strategy implementation that handles all error scenarios.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IError {
  static final class Error {
    private final int code;
    
    private final String message;

    public Error(int code, String message) {
      this.code = code;
      this.message = message;
    }

    public int getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Error [code=");
      builder.append(code);
      builder.append(", message=");
      builder.append(message);
      builder.append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + code;
      result = prime * result + ((message == null) ? 0 : message.hashCode());
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
      Error other = (Error) obj;
      if (code != other.code) {
        return false;
      }
      if (message == null) {
        if (other.message != null) {
          return false;
        }
      } else if (!message.equals(other.message)) {
        return false;
      }
      return true;
    }
  }
  
  /**
   * Returns a list of errors.
   * @return
   */
  List<Error> getErrors();
  
  /**
   * Adds error the list of errors.
   * 
   * @param code error code
   * @param message error message
   */
  void addError(int code,  String message);
}
