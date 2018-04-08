package model;

import java.awt.Point;

import controller.ControllerMain;
import model.Mobs.Mob;
import model.Mobs.SpeedAttribute;
import model.Towers.ElementalAttribute;

public class DemoProjectile extends Projectile {

  public DemoProjectile(Point startLocation, Mob targetMob) {
    super(startLocation, 
        SpeedAttribute.NORMAL, targetMob.getRadius(), 10000000000.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets.images/ghost.png");
    
    this.setMob(targetMob);
  }

  @Override
  protected void terminate() {
    targetMob.takeDamage(baseDmg, dmgType);
    ControllerMain.projectiles.remove(this);
    kamakaziImperative.interrupt();
  }

}
