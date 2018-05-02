package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import controller.ControllerMain;
import views.MapView;
import views.MenuView;

/**
 * Depot is the third basic Tower. It upgrades to a Turret, and ultimately, a
 * Bunker.
 * 
 * @author Ben Walters
 */
public class DepotTower extends Tower implements Serializable {
  
  public static final int COST=150;
  public static final int UPGRADE_COST=150;
  public static final RangeAttribute BASE_RANGE=RangeAttribute.SMALL_RANGE;
  
  public DepotTower(Point loc, TowerGame game, boolean isDank) {

    super(COST, UPGRADE_COST,  "Library", loc, RangeAttribute.SMALL_RANGE, getPicString(isDank), 
          game, 120, isDank);
  }
  
  
  protected static String getPicString(boolean isDank) {
		String pic="";
		
		if (isDank) {   
		  pic="file:assets/images/tower/doge.png";
		}
		else {
	  	  pic="file:assets/images/tower/depot.png";
		}
		
		return pic;
  }
  
  
  /**
	 * shoot
	 * allows the tower to shoot at a mob
	 * @param nearbyMobs: a collection of all nearby mobs
	 * @return None
	*/
  @Override
  protected void shoot(Set<Mob> nearbyMobs) {
    ConcurrentLinkedQueue<Mob> targets = new ConcurrentLinkedQueue<Mob>(nearbyMobs);
    
    Projectile projectile=null;
    
    //towewr is at the base stage
    if(rank==0) {
    	projectile=new DepotProjectile0(new Point(
      	                                          (int)(location.getX()+.5*MapView.ghostTowerSize),
                                                  (int)(location.getY()+.5*MapView.ghostTowerSize)),
                                        targets,theGame, isDank);
    }
     
    //tower is upgraded, give a better projectile
    else if(rank>=1) {
    	projectile=new DepotProjectile1(new Point(
					                       	   (int)(location.getX()+.5*MapView.ghostTowerSize),
						                       (int)(location.getY()+.5*MapView.ghostTowerSize)), 
				                     targets, theGame, isDank);
    }
    
    projectile.addDamageBonus(25*rank);  
    theGame.add(projectile);
  }
  
  
  /**  
	 * upgrade
	 * allows the tower to upgrade and gain better stats
	*/
	public void upgrade() {
		String pic = "";
		if (rank == 0) {

			if (MenuView.getModeSelection().equals("Fun")) {
				pic = "file:assets/images/tower/doge2.png";
			} else {
				pic = "file:assets/images/tower/tower.png";
			}
			
			setImageFilePath(pic);
			setRange(RangeAttribute.MEDIUM_RANGE);
			increaseFrequency(40);
		} 
		else if (rank == 1) {
			
			if (MenuView.getModeSelection().equals("Fun")) {
				pic = "file:assets/images/tower/doge3.gif";
			} else {
				pic = "file:assets/images/tower/bunker.png";
			}
			
			setImageFilePath(pic);
			setRange(RangeAttribute.LARGE_RANGE);
			increaseFrequency(30);
			isFullyUpgraded = true;
		}

		rank++;
	}
}
