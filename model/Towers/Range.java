package model.Towers;


public enum Range {
  demoRange(5);
  
  private final double distance;
  
  Range(int someNumber) {
    distance = someNumber;
  }
  
  public double toDouble() {
    return distance;
  }

}
