package model;

import java.awt.Point;
import java.io.Serializable;

import controller.ControllerMain;


/*============================================================================*
 * 	DemoProjectile                                                            *
 *  Serves as a basic projectile, normal speed as well as damage, nothing     *
 *  special                                                                   *
 *============================================================================*/
<<<<<<< HEAD:model/DemoProjectile.java
public class DemoProjectile extends Projectile implements Serializable {
=======
public class NormalProjectile extends Projectile {
>>>>>>> e1375cfa1d4858906a0a32e0823536f59f50c8fd:model/NormalProjectile.java

  
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
