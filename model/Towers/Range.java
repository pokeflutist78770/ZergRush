package model.Towers;


public enum Range {
  demoRange(200);
  
  private final double distance;
  
  Range(int someNumber) {
    distance = someNumber;
  }
  
  public double toDouble() {
    return distance;
  }

}
