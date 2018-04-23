package model.Towers;


public enum Range {
  DEMO_RANGE(200);
  
  private final double distance;
  
  Range(int someNumber) {
    distance = someNumber;
  }
  
  public double toDouble() {
    return distance;
  }

}
