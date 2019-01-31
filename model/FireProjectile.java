package model;

import java.awt.Point;


/*============================================================================*
 *  FireProjectile                                                            *
 *  Serves as a basic projectile, normal speed as well as damage, nothing     *
 *  special                                                                   *
 *============================================================================*/
public class FireProjectile extends Projectile {

   
  public FireProjectile(Point startLocation, Mob targetMob, TowerGame game, boolean isDank) {
    super(startLocation,   
        SpeedAttribute.PROJECTILE_SPEED, targetMob.getRadius(), 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/tower/fire.png", game, isDank);
    
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
