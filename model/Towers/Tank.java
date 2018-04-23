package model.Towers;

import java.awt.Point;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.Mobs.Mob;

public class Tank  extends Tower{
	 public Tank(Point loc) {
		 super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/tower/tank.png");
	 }

	@Override
	protected void shoot(Set<Mob> nearbyMobs) {
		Mob closest = getClosestMob(nearbyMobs);
	    ControllerMain.projectiles.add(new DemoProjectile(new Point(location), closest,0));
	}
}
