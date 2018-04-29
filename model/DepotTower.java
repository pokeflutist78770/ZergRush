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
    
    //towewr is at the base stage
    if(rank==0) {
    	projectile=new NormalProjectile(new Point(
      	                                          (int)(location.getX()+.5*MapView.ghostTowerSize),
                                                  (int)(location.getY()+.5*MapView.ghostTowerSize)),
                                        closest,theGame);
    }
    
    //tower is upgraded, give a better projectile
    else if(rank>=1) {
    	projectile=new IceProjectile(new Point(
					                       	   (int)(location.getX()+.5*MapView.ghostTowerSize),
						                       (int)(location.getY()+.5*MapView.ghostTowerSize)), 
				                     closest, theGame);
    }
    
    theGame.add(projectile);
  }
  
  
  /**
	 * upgrade
	 * allows the tower to upgrade and gain better stats
	*/
  public void upgrade() {
    if(rank==0) {
		setImageFilePath("file:assets/images/tower/tower.png");
		setRange(RangeAttribute.MEDIUM_RANGE);
		increaseFrequency(20);	
	}
	else if(rank==1) {
		setImageFilePath("file:assets/images/tower/bunker.png");
		setRange(RangeAttribute.LARGE_RANGE);
		increaseFrequency(30);
	}
		
	rank++;
  }
}
