package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.TowerGame;
import model.Mobs.Mob;
import views.MapView;

/**
 * Tank is the second basic Tower of the Terran army.   It upgrades to a Siege Tank and ultimately a Thor tower.
 * 
 * @author Ben Walters
 *
 */
public class Tank  extends Tower{
  
  public static final double COST=350;
  
	 public Tank(Point loc, TowerGame game) {
		 super(350, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/tank.png", game);

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
