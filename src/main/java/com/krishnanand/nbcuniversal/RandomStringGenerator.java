package com.krishnanand.nbcuniversal;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility helper class.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class RandomStringGenerator {
  
  private static final String STRING =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnonpqrstuvwxyz0123456789";
  
  private final Random random = new SecureRandom();
  
  private final int maxLength;
  
  public RandomStringGenerator(int maxLength) {
    this.maxLength = maxLength;
  }
  
  /**
   * Random alphanumeric value to be generated.
   */
  public String nextString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < maxLength; i++) {
      sb.append(STRING.charAt(this.random.nextInt(STRING.length())));
    }
    return sb.toString();
  }

}
