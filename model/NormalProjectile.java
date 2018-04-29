package model;

import java.awt.Point;
import java.io.Serializable;

import controller.ControllerMain;


/*============================================================================*
 * 	DemoProjectile                                                            *
 *  Serves as a basic projectile, normal speed as well as damage, nothing     *
 *  special                                                                   *
 *============================================================================*/

public class NormalProjectile extends Projectile {


  
  public NormalProjectile(Point startLocation, Mob targetMob, TowerGame game) {
    super(startLocation, 
        SpeedAttribute.PROJECTILE_SPEED, targetMob.getRadius(), 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/normal.png", game);
    
    this.setMob(targetMob);
    this.targetLocation = targetMob.getCurrentLocation();
  }

  /**
   * The demo projectile does damage to a single mob target when it terminates.
   */
  @Override
  protected void terminate() {
    targetMob.takeDamage(baseDmg, dmgType);
  }
}
