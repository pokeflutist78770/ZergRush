package model.Towers;

import model.Projectile;
import model.Mobs.Mob;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import controller.ControllerMain;
import javafx.scene.image.Image;
import model.Maps.Metric;
import model.Mobs.Mob;

/**
 * Tower is an abstract class housing the attributes and responsibilities of
 * individual towers. There are three basic tower types, each of which can be
 * upgraded into two different towers, for a total of nine unique towers.
 * 
 * Towers detect enemies in range and instantiated projectiles to deal damage to
 * the nearest Mob.
 * 
 * @author Ben Walters
 *
 */

public abstract class Tower {

  protected String name;
  protected int cost;
  protected Thread towerAnxiety;
  protected Point location;
  protected Range range;
  private String imageFilePath;
  
  /**
   * 
   * @param cost - price to the player to instantiate a tower
   * @param name - name of the tower
   * @param location - the x,y Point location the tower exists on the map
   * @param range - the radius around which a tower can detect and fire at mobs.
   * @param imageFP - the file path to the image representing the tower, images are retrieved by flyweight
   */
  public Tower(int cost, String name, Point location, Range range, String imageFP) {

    this.cost = cost;
    this.name = name;
    this.location = location;
    this.range = range;
    imageFilePath = imageFP;
    initializeTower();
  }

  /*
   * initializeTower initializes tower by starting a thread for it to check for
   * nearby mobs Parameters: None Returns: None
   */
  private void initializeTower() {

    towerAnxiety = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          while (true) {

            Thread.sleep((long) 60 * ControllerMain.UPDATE_FREQUENCY);

            Set nearbyMobs = getNearbyMobs();
            if (!nearbyMobs.isEmpty()) {
              shoot(nearbyMobs);
            }

            if (!ControllerMain.isPlaying) {
              break;
            }
          }

          System.out.println("Tower Thread: Ended");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    towerAnxiety.start();
  }

  abstract protected void shoot(Set<Mob> nearbyMobs);

  /**
   * Tower iterates over all mobs represented in the state of the game, determining which is closes to it at a given
   * instant
   * @param nearbyMobs - a list of the nearest mobs on the map to the tower
   * @return the closest mob to the Tower
   */
  protected Mob getClosestMob(Set<Mob> nearbyMobs) {
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

  /*
   * Compares two mob's locations and determines which is close to the tower.
   * 
   * @param closest the current closest mob to the tower
   * @param nextMob the mob the closest mob is being compared to
   * @return
   */
  private boolean isCloser(Mob closest, Mob nextMob) {
    double winnerDist2 = Metric.distanceSquared(location, closest.getCurrentLocation());
    double nextDist2 = Metric.distanceSquared(location, nextMob.getCurrentLocation());
    return winnerDist2 < nextDist2;
  }

  /*
   * Creates a set of Mobs which are close to the tower, filtering out those that a sufficiently distant by querying
   * the mobs collection in the controller main.
   * @return a HashSet of nearby mobs
   */
  private Set getNearbyMobs() {
    Set nearbyMobs = new HashSet();
    Iterator<Mob> itr = ControllerMain.mobs.iterator();
    while (itr.hasNext()) {
      Mob nextMob = itr.next();
      if (isNear(nextMob)) {
        nearbyMobs.add(nextMob);
      }
    }
    return nearbyMobs;
  }

  /*
   * Determines if a mob is near to the tower by comparison with it's location relative to the range of the tower
   * @param nextMob - Mob to be checked for nearness
   * @return
   */
  private boolean isNear(Mob nextMob) {
    return Metric.closeEnough(nextMob.getCurrentLocation(), location, range.toDouble());
  }

  /** GETTERS AND SETTERS FOLLOW */
  public String getImageFilePath() {
    return imageFilePath;
  }

  public void setImageFilePath(String imageFilePath) {
    this.imageFilePath = imageFilePath;
  }

  public Image getImage() {
    return ControllerMain.getGraphic(this.getImageFilePath());
  }

  public double getX() {
    return location.getX();
  }

  public double getY() {
    return location.getY();
  }

  public int getCost() {
    return cost;
  }

}
