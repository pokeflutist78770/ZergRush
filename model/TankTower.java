package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Set;

import controller.ControllerMain;
import views.MapView;
import views.MenuView;

/**
 * Tank is the second basic Tower of the Terran army.   It upgrades to a Siege Tank and ultimately a Thor tower.
 * 
 * @author Ben Walters
 *
 */
public class TankTower  extends Tower implements Serializable {  
  
	public static final int COST=350;
	public static final int UPGRADE_COST=200;
	public static final RangeAttribute BASE_RANGE=RangeAttribute.MEDIUM_RANGE;
	
	public TankTower(Point loc, TowerGame game, boolean isDank) {
		super(COST, UPGRADE_COST, "Library", loc, RangeAttribute.MEDIUM_RANGE, 
			  getPicString(isDank),
		      game, 120, isDank);
	}
   
	
	protected static String getPicString(boolean isDank) {
		String pic="";
			  
		if (isDank) {
			pic="file:assets/images/tower/shrek.png";
		}
		else {
			pic="file:assets/images/tower/tank.png";
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
		Mob closest = getClosestMob(nearbyMobs); 
		
		Projectile projectile=new SiegeProjectile(new Point(
                 	                                       (int)(location.getX()+.5*MapView.ghostTowerSize),
                 	                                       (int)(location.getY()+.5*MapView.ghostTowerSize)), 
                                                 closest.getCurrentLocation(),theGame, isDank);
		projectile.addDamageBonus(25*rank);
	    theGame.add(projectile);
	}
	
	
	/**
	 * upgrade
	 * allows the tower to upgrade and gain better stats
	*/
	public void upgrade() {
		String pic="";
		
		if(rank==0) {
			
			if (MenuView.getModeSelection().equals("Fun")) {
				pic = "file:assets/images/tower/shrek2.png";
			} else {
				pic = "file:assets/images/tower/siege.png";
			}
			
			setImageFilePath(pic);
			setRange(RangeAttribute.LARGE_RANGE);
			increaseFrequency(30);
		}
		else if(rank==1) {
			
			if (MenuView.getModeSelection().equals("Fun")) {
				pic = "file:assets/images/tower/shrek3.png";
			} else {
				pic = "file:assets/images/tower/thor.png";
			}
			
			setImageFilePath(pic);
			increaseFrequency(60);
			isFullyUpgraded=true;
		}
		
		rank++;
	}
}
