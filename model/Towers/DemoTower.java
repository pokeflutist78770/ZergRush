package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;

public class DemoTower extends Tower {
  
  public DemoTower() {
    super(0, "Library", new Point(400,400), Range.demoRange, "");
  }

  @Override
  public void shoot(Set nearbyMobs) {
    // TODO Auto-generated method stub
    
  }
}
