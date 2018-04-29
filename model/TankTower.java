package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Set;

import controller.ControllerMain;
import views.MapView;

/**
 * Tank is the second basic Tower of the Terran army.   It upgrades to a Siege Tank and ultimately a Thor tower.
 * 
 * @author Ben Walters
 *
 */
public class TankTower  extends Tower implements Serializable {
  
	public static final double COST=350;
	public static final RangeAttribute BASE_RANGE=RangeAttribute.MEDIUM_RANGE;
	
	public TankTower(Point loc, TowerGame game) {
		super(350, "Library", loc, RangeAttribute.MEDIUM_RANGE, "file:assets/images/tower/tank.png",
		      game, 120);
	}

	
	/**
	 * shoot
	 * allows the tower to shoot at a mob
	 * @param nearbyMobs: a collection of all nearby mobs
	 * @return None
	*/
	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		Mob closest = getClosestMob(nearbyMobs);
		
		Projectile projectile=new SiegeProjectile(new Point(
                 	                                       (int)(location.getX()+.5*MapView.ghostTowerSize),
                 	                                       (int)(location.getY()+.5*MapView.ghostTowerSize)), 
                                                 closest.getCurrentLocation(),theGame);
		projectile.addDamageBonus(25*rank);
	    theGame.add(projectile);
	}
	
	
	/**
	 * upgrade
	 * allows the tower to upgrade and gain better stats
	*/
	public void upgrade() {
		if(rank==0) {
			setImageFilePath("file:assets/images/tower/siege.png");
			setRange(RangeAttribute.LARGE_RANGE);
			increaseFrequency(30);
		}
		else if(rank==1) {
			setImageFilePath("file:assets/images/tower/thor.png");
			increaseFrequency(60);
			isFullyUpgraded=true;
		}
		
		rank++;
	}
}
