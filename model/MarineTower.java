package model;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import views.MapView;
/**
 * Marine is the first Tower type.  It upgrades to a Marauder and Ultimately a Ghost.
 * 
 * @author Ben Walters
 *
 */
public class MarineTower extends Tower{
  
  public static final double COST=100;

	 public MarineTower(Point loc, TowerGame game) {
		 super(100, "Library", loc, RangeAttribute.DEMO_RANGE, "file:assets/images/tower/marine.png", game, 120);

	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		Mob closest = getClosestMob(nearbyMobs);
	    theGame.add(new DemoProjectile(
	    		                       new Point(
	    		                    	   (int)(location.getX()+.5*MapView.ghostTowerSize),
		    		                       (int)(location.getY()+.5*MapView.ghostTowerSize)),
	    		                       closest,theGame));
	}
}
