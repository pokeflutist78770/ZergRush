package model;

import java.awt.Point;


/*============================================================================*
 *  PoisonProjectile                                                            *
 *  Serves as a basic projectile, normal speed as well as damage, nothing     *
 *  special                                                                   *
 *============================================================================*/
public class PoisonProjectile extends Projectile {

  
  public PoisonProjectile(Point startLocation, Mob targetMob, TowerGame game, boolean isDank) {
    super(startLocation, 
        SpeedAttribute.PROJECTILE_SPEED, targetMob.getRadius(), 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/tower/poison.png", game, isDank);
    
    this.setMob(targetMob);
    this.targetLocation = targetMob.getCurrentLocation();
  }

  /**
   * The demo projectile does damage to a single mob target when it terminates.
   */
  @Override
  protected void terminate() {
    targetMob.takeDamage(baseDmg, dmgType);
    hit = true;
  }
}