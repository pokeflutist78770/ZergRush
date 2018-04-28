package model;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import views.MapView;

/**
 * Tank is the second basic Tower of the Terran army.   It upgrades to a Siege Tank and ultimately a Thor tower.
 * 
 * @author Ben Walters
 *
 */
public class TankTower  extends Tower{
  
	public static final double COST=350;
	public static final RangeAttribute BASE_RANGE=RangeAttribute.MEDIUM_RANGE;
	
	public TankTower(Point loc, TowerGame game) {
		super(350, "Library", loc, RangeAttribute.MEDIUM_RANGE, "file:assets/images/tower/tank.png",
		      game, 120);
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
	
	
	public void upgrade() {
		if(rank==0) {
			setImageFilePath("file:assets/images/tower/siege.png");
		}
		else if(rank==1) {
			setImageFilePath("file:assets/images/tower/thor.png");
		}
		
		rank++;
	}
}
