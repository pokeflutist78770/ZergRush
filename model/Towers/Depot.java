package model.Towers;

import java.awt.Point;
import java.util.Set;
import controller.ControllerMain;
import model.DemoProjectile;
import model.TowerGame;
import model.Mobs.Mob;
import views.MapView;

/**
 * Depot is the third basic Tower. It upgrades to a Turret, and ultimately, a
 * Bunker.
 * 
 * @author Ben Walters
 */
public class Depot extends Tower {
  
  public static final double COST=150;
  
  public Depot(Point loc, TowerGame game) {
    super(150, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/depot.png", game);
  }

  @Override
  protected void shoot(Set<Mob> nearbyMobs) {
    Mob closest = getClosestMob(nearbyMobs);
    theGame.add(new DemoProjectile(
    							   new Point(
    									(int)(location.getX()+.5*MapView.ghostTowerSize),
    									(int)(location.getY()+.5*MapView.ghostTowerSize)), 
    							   closest, theGame));
  }
}
