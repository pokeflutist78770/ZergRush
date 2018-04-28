package model;

import java.awt.Point;
import java.util.Set;
import controller.ControllerMain;
import views.MapView;

/**
 * Depot is the third basic Tower. It upgrades to a Turret, and ultimately, a
 * Bunker.
 * 
 * @author Ben Walters
 */
public class DepotTower extends Tower {
  
  public static final double COST=150;
  public static final RangeAttribute BASE_RANGE=RangeAttribute.SMALL_RANGE;
  
  public DepotTower(Point loc, TowerGame game) {
    super(150, "Library", loc, RangeAttribute.SMALL_RANGE, "file:assets/images/tower/depot.png", 
          game, 120);
  }

  @Override
  protected void shoot(Set<Mob> nearbyMobs) {
    Mob closest = getClosestMob(nearbyMobs);
    theGame.add(new IceProjectile(
    							   new Point(
    									(int)(location.getX()+.5*MapView.ghostTowerSize),
    									(int)(location.getY()+.5*MapView.ghostTowerSize)), 
    							   closest, theGame));
  }
  
  
	public void upgrade() {
		setImageFilePath("file:assets/images/tower/tower.png");
	}
}
