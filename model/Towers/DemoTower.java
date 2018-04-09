package model.Towers;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import controller.ControllerMain;
import model.DemoProjectile;
import model.Maps.Metric;
import model.Mobs.Mob;

public class DemoTower extends Tower {
  
  public DemoTower() {
    super(0, "Library", new Point(400,400), Range.demoRange, "file:assets.images/thick.png");
  }

  @Override
  public void shoot(Set<Mob> nearbyMobs) {
    Mob closest = getClosestMob(nearbyMobs);
    new DemoProjectile(location, closest);
  }

  private Mob getClosestMob(Set<Mob> nearbyMobs) {
    Iterator<Mob> itr = nearbyMobs.iterator();
    Mob closest = itr.next();
    while (itr.hasNext()) {
      Mob nextMob = itr.next();
      if (isCloser(closest, nextMob)) {
        continue;
      } else {
        closest = nextMob;
      }
    }
    return closest;
  }

  private boolean isCloser(Mob closest, Mob nextMob) {
    double winnerDist2 = Metric.distanceSquared(location, closest.getCurrentLocation());
    double nextDist2 = Metric.distanceSquared(location, nextMob.getCurrentLocation());
    return winnerDist2 < nextDist2;
  }
}
