package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.Mobs.Mob;
import views.MapView;

/**
 * Tank is the second basic Tower of the Terran army.   It upgrades to a Siege Tank and ultimately a Thor tower.
 * 
 * @author Ben Walters
 *
 */
public class Tank  extends Tower{
	 public Tank(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/tank.png");
	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		Mob closest = getClosestMob(nearbyMobs);
	    ControllerMain.projectiles.add(new DemoProjectile(
	    		                       new Point(
	    		                    	   (int)(location.getX()+.5*MapView.ghostTowerSize),
	    		                    	   (int)(location.getY()+.5*MapView.ghostTowerSize)), 
	    		                       closest,0));
	}
}
