package model;

import java.awt.Point;

import controller.ControllerMain;
import model.Mobs.SpeedAttribute;
import model.Towers.ElementalAttribute;

public class DemoProjectile extends Projectile {

  public DemoProjectile(Point startLocation) {
    super(startLocation, 
        SpeedAttribute.NORMAL, ControllerMain.TILE_SIZE, 10000000000.0, 
        ElementalAttribute.DEMO_ELEMENT, "");
  }

  @Override
  protected void terminate() {
    // TODO Auto-generated method stub
    
  }

}
