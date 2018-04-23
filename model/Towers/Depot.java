package model.Towers;


import java.awt.Point;
import java.util.Set;

import model.Mobs.Mob;
/**
 * Depot is the third basic Tower.  It upgrades to a turrent, and ultimately, a Bunker.
 * 
 * @author Ben Walters
 */
public class Depot extends Tower{
	 public Depot(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/depot.png");
	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		// TODO Auto-generated method stub
		
	}
}
