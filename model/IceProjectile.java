package model;

import java.awt.Point;


/*============================================================================*
 *  IceProjectile                                                            *
 *  Serves as a basic projectile, normal speed as well as damage, nothing     *
 *  special                                                                   *
 *============================================================================*/
public class IceProjectile extends Projectile {

  
  public IceProjectile(Point startLocation, Mob targetMob, TowerGame game, boolean isDank) {
    super(startLocation,   
        SpeedAttribute.PROJECTILE_SPEED, targetMob.getRadius(), 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/tower/ice.png", game, isDank);
    
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