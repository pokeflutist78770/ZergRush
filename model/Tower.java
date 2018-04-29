package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import controller.ControllerMain;
import javafx.scene.image.Image;

/**
 * Tower is an abstract class housing the attributes and responsibilities of
 * individual towers. There are three basic tower types, each of which can be
 * upgraded into two different towers, for a total of nine unique towers.
 * 
 * Each tower may be configured to shoot any type of projectile, possibly more than one.
 * Projectiles may have location targets or mob targets.
 * 
 * @author Ben Walters
 *
 */

public abstract class Tower {
  
  protected int firing_frequency;
  protected int fire_counter = firing_frequency;
  
  protected String name;
  protected int cost;
  protected Point location;
  protected RangeAttribute range;
  protected String imageFilePath;
  protected TowerGame theGame;
  protected int rank;
  
  /**
   * 
   * @param cost - price to the player to instantiate a tower
   * @param name - name of the tower
   * @param location - the x,y Point location the tower exists on the map
   * @param range - the radius around which a tower can detect and fire at mobs.
   * @param imageFP - the file path to the image representing the tower, images are retrieved by flyweight
   */
  public Tower(int cost, String name, Point location, RangeAttribute range, String imageFP, TowerGame game, int fireRate) {
	
    this.cost = cost;
    this.name = name;
    this.location = location;
    this.range = range;
    rank=0;
    imageFilePath = imageFP;
    theGame = game;
    firing_frequency = fireRate;
  }

  /**
   * The shoot method is implemented by each tower class. 
   * It is called whenever at least one mob is sufficiently close to the tower.
   * @param nearbyMobs A set of mobs. They are assumed to be close to the tower.
   */
  abstract protected void shoot(Set<Mob> nearbyMobs);

  
  /**
   * Upgrade
   * method to be implemented for each tower in order to upgrade to the next 
  */
  abstract public void upgrade();
  
  
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
  private Set<Mob> getNearbyMobs() {
    Set<Mob> nearbyMobs = new HashSet<Mob>();
    for (Iterator<Mob> itr = theGame.getMobs().iterator(); itr.hasNext(); ) {
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

  /**
   * Answers the question: Is it ok to remove this tower from the game?
   * One reason we may want to ask this question: If we implement the ability to sell towers, 
   *   then wherever in the program the tower is sold, 
   *   some (not yet implemented) boolean attribute of the tower can be toggled.
   *   isDone will then tell the main game loop to remove the tower in the next iteration.
   * @return Whether or not the main game loop is in the clear to remove this tower.
   */
  public boolean isDone() {
    return false;
  }

  /**
   * A single update step for the tower.
   * For most updates, nothing happens.
   * Every time the fire_counter exceeds the firing_frequency, 
   *   the tower shoots and the fire_counter is reset.
   */
  public void update() {
    fire_counter++;
    
    Set<Mob> nearbyMobs = getNearbyMobs();
    if (!nearbyMobs.isEmpty() && theGame.getProjectiles().size() < 5000) {
      if (fire_counter < firing_frequency) {
        return;
      }
      fire_counter = 0;

      shoot(nearbyMobs);
    }
  }

  
  public void increaseFrequency(int amtIncrease) {
	  firing_frequency-=amtIncrease;
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

  public double getRange() {
	  return range.toDouble();
  }
  
  public int getCost() {
    return cost;
  }
  
  public void setRange(RangeAttribute newRange) {
	  range=newRange;
  }
}
