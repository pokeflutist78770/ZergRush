package model.Towers;

import java.awt.Point;
import java.util.Set;

import model.Mobs.Mob;

public class Depot extends Tower{
	 public Depot(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/depot.png");
	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		// TODO Auto-generated method stub
		
	}
}
