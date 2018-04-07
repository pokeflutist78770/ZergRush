package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import model.Mobs.Mob;

public class DemoTower extends Tower {
  
  public DemoTower() {
    super(0, "Library", new Point(400,400), Range.demoRange, "");
  }

  @Override
  public void shoot(Set<Mob> nearbyMobs) {
    // TODO Auto-generated method stub
    
  }
}
