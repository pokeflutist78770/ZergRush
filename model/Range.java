package model;

/**
 * Range Enums represent the distance a tower can detect units and fire at them.
 * 
 * @author Ben Walters
 *
 */
public enum Range {
  DEMO_RANGE(200), SMALL_RANGE(75), MEDIUM_RANGE(125), LARGE_RANGE(200);
  
  private final double distance;
  
  Range(int someNumber) {
    distance = someNumber;
  }
  
  public double toDouble() {
    return distance;
  }

}
