package model.Towers;

import java.awt.Point;
import java.util.Set;
import controller.ControllerMain;
import model.DemoProjectile;
import model.Mobs.Mob;

/**
 * Depot is the third basic Tower. It upgrades to a Turret, and ultimately, a
 * Bunker.
 * 
 * @author Ben Walters
 */
public class Depot extends Tower {
  public Depot(Point loc) {
    super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/depot.png");
  }

  @Override
  protected void shoot(Set<Mob> nearbyMobs) {
    Mob closest = getClosestMob(nearbyMobs);
    ControllerMain.projectiles.add(new DemoProjectile(new Point(location), closest, 0));
  }
}
