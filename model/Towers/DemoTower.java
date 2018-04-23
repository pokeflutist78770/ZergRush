package model.Towers;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.Maps.Metric;
import model.Mobs.Mob;

public class DemoTower extends Tower {
  
  public DemoTower(Point loc) {
    super(0, "Library", loc, Range.DEMO_RANGE, "file:assets/images/Slime.png");
  }

  @Override
  protected void shoot(Set<Mob> nearbyMobs) {
    Mob closest = getClosestMob(nearbyMobs);
    ControllerMain.projectiles.add(new DemoProjectile(new Point(location), closest,0));
  }

}
