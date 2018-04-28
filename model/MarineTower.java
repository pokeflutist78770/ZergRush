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
	public static final RangeAttribute BASE_RANGE=RangeAttribute.SMALL_RANGE;
	
	public MarineTower(Point loc, TowerGame game) {
		super(100, "Library", loc, RangeAttribute.SMALL_RANGE, "file:assets/images/tower/marine.png", 
			  game, 120);
	}

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		Mob closest = getClosestMob(nearbyMobs);
		
		Projectile projectile=null;
		
		if(rank==0) {
			projectile=new NormalProjectile(new Point(
	    		                    	      (int)(location.getX()+.5*MapView.ghostTowerSize),
		    		                          (int)(location.getY()+.5*MapView.ghostTowerSize)),
	    		                            closest,theGame);
		}
		else if(rank==1 || rank==2) {
			projectile=new PoisonProjectile(new Point(
          	      (int)(location.getX()+.5*MapView.ghostTowerSize),
                    (int)(location.getY()+.5*MapView.ghostTowerSize)),
                  closest,theGame);
		}

	    theGame.add(projectile);
	}
	
	
	public void upgrade() {
		if(rank==0) {
			setImageFilePath("file:assets/images/tower/thick.png");
			
		}
		
		else if(rank==1) {
			setImageFilePath("file:assets/images/tower/ghost.png");
		}
		
		rank++;
	}
}
