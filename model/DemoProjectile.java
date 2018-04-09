package model;

import java.awt.Point;

import controller.ControllerMain;
import model.Mobs.Mob;
import model.Mobs.SpeedAttribute;
import model.Towers.ElementalAttribute;

public class DemoProjectile extends Projectile {

  public DemoProjectile(Point startLocation, Mob targetMob) {
    super(startLocation, 
        SpeedAttribute.PROJECTILE_SPEED, targetMob.getRadius(), 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/Goop.png");
    
    this.setMob(targetMob);
    this.targetLocation = targetMob.getCurrentLocation();
  }

  @Override
  protected void terminate() {
    targetMob.takeDamage(baseDmg, dmgType);
    ControllerMain.projectiles.remove(this);
    kamakaziImperative.interrupt();
  }

}
