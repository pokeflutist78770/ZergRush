package model.Towers;

import java.awt.Point;
import java.util.Set;

import model.Mobs.Mob;

public class Marine extends Tower{

	 public Marine(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/marine.png");
	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
	
		
	}
}
