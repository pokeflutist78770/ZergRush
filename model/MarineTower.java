package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Set;

import controller.ControllerMain;
import views.MapView;
/**
 * Marine is the first Tower type.  It upgrades to a Marauder and Ultimately a Ghost.
 * 
 * @author Ben Walters
 *
 */
public class MarineTower extends Tower implements Serializable {
  
	public static final double COST=100;
	public static final RangeAttribute BASE_RANGE=RangeAttribute.SMALL_RANGE;
	
	public MarineTower(Point loc, TowerGame game) {
		super(100, 100, "Library", loc, RangeAttribute.SMALL_RANGE, "file:assets/images/tower/marine.png", 
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
		
		Projectile projectile=null;
		
		//tower is at base stage
		if(rank==0) {
			projectile=new NormalProjectile(new Point(
	    		                    	        (int)(location.getX()+.5*MapView.ghostTowerSize),
		    		                            (int)(location.getY()+.5*MapView.ghostTowerSize)),
	    		                            closest,theGame);
		}
		
		//tower is upgraded fully
		else if(rank>=1) {
			projectile=new PoisonProjectile(new Point(
          	                                    (int)(location.getX()+.5*MapView.ghostTowerSize),
                                                (int)(location.getY()+.5*MapView.ghostTowerSize)),
                                            closest,theGame);
		}
		
		projectile.addDamageBonus(25*rank);
	    theGame.add(projectile);
	}
	
	
	/**
	 * upgrade
	 * allows the tower to upgrade and gain better stats
	*/
	public void upgrade() {
		if(rank==0) {
			setImageFilePath("file:assets/images/tower/thick.png");
			setRange(RangeAttribute.MEDIUM_RANGE);
			increaseFrequency(50);
		}
		
		else if(rank==1) {
			setImageFilePath("file:assets/images/tower/ghost.png");
			setRange(RangeAttribute.LARGE_RANGE);
			increaseFrequency(60);
			isFullyUpgraded=true;
		}
		
		rank++;
	}
}
