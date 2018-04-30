package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import controller.ControllerMain;

public class SiegeProjectile extends Projectile {
  
  private int blastRadius = ControllerMain.TILE_SIZE * 2;
  
    

  public SiegeProjectile(Point startLocation, Point targetLocation, TowerGame game, boolean isDank) {
    super(startLocation, 
        SpeedAttribute.PROJECTILE_SPEED, 5, 100.0, 
        ElementalAttribute.DEMO_ELEMENT, "file:assets/images/tower/fire.png", game, isDank);
    
    this.setMob(null);
    this.targetLocation = targetLocation;
  }  

  /**
   * The siege projectile does damage to a every mob target in its area of effect when it terminates.
   */
  @Override
  protected void terminate() {
    Set<Mob> targets = getNearbyMobs();
    for (Mob m: targets) {
      m.takeDamage(baseDmg, dmgType);
    }
    hit = true;
  }

  /*
   * Creates a set of Mobs which are close to the tower, filtering out those that a sufficiently distant by querying
   * the mobs collection in the controller main.
   * @return a HashSet of nearby mobs
   */
  private Set<Mob> getNearbyMobs() {
    Set<Mob> nearbyMobs = new HashSet<Mob>();
    for (Iterator<Mob> itr = theGame.getMobs().iterator(); itr.hasNext(); ) {
      Mob nextMob = itr.next();
      if (isNear(nextMob)) {
        nearbyMobs.add(nextMob);
      }
    }
    return nearbyMobs;
  }

  /*
   * Determines if a mob is near to the tower by comparison with it's location relative to the range of the tower
   * @param nextMob - Mob to be checked for nearness
   * @return
   */
  private boolean isNear(Mob nextMob) {
    return Metric.closeEnough(nextMob.getCurrentLocation(), targetLocation, blastRadius);
  }

}
