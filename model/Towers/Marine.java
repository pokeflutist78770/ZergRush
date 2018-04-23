package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.Mobs.Mob;
import views.MapView;
/**
 * Marine is the first Tower type.  It upgrades to a Marauder and Ultimately a Ghost.
 * 
 * @author Ben Walters
 *
 */
public class Marine extends Tower{

	 public Marine(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/marine.png");
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
