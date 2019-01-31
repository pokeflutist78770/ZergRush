package model;

import java.awt.Point;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DepotProjectile1 extends Projectile {

  private ConcurrentLinkedQueue<Mob> myTargets;
  
  public DepotProjectile1(Point startLocation, ConcurrentLinkedQueue<Mob> targets, TowerGame game, boolean isDank) {
    super(startLocation,   
        SpeedAttribute.VERY_FAST, targets.peek().getRadius(), 150.0, 
        ElementalAttribute.ICE, "file:assets/images/tower/ice.png", game, isDank);
    myTargets = targets; 
    this.setMob(myTargets.poll());
    this.targetLocation = targetMob.getCurrentLocation();
  }

  /**
   * The demo projectile does damage to a single mob target when it terminates.
   */
  @Override
  protected void terminate() {
    targetMob.takeDamage(baseDmg, dmgType);
    if (myTargets.isEmpty()) {
      hit = true;
      return;
    }
    while (!myTargets.isEmpty() && myTargets.peek().isDone()) {
      myTargets.poll();
    }
    if (!myTargets.isEmpty()) {
      theGame.add(new DepotProjectile1(currentLocation, myTargets, theGame, isDank));
    }
    hit = true;
  }
  
}
