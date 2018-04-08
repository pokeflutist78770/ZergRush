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

// Towers are purchased by the player and placed strategically along the paths.

// Towers will attack enemeies that pass by (in its range of attack). Examples:
// * Gemcraft uses towers powered by gems and the combination of gems determine
//   damage, range, etc.
// * Kingdon Rush uses a medieval setting with archery towers and warrior towers.
// * Be creative! You can decide how towers should attack enemies. 
//   It can be single target, or multi-target!

// Each tower has a different damage amount, range of attack, and rate of attack.

// Each tower has a different way of attacking enemies. Possibilities include
// * attack multiple enemies
// * teleport enemies
// * slow enemies
// * poison enemies
// * confusing enemies so that they either walk back towards their start or maybe
//   they spin in a circle for a few seconds
// * Be Creative!

// A player can view information about a tower before purchasing it and/or
// by clicking one that has been placed.

// Each tower should cost the player something, could be gold, etc.

// Towers must be upgradeable to have more range, fire rate, or damage.

// Tower upgrades change the look of the tower.

// Towers must have a minimum of 1 tower upgrade.

// A tower's range is visible when the tower is clicked. If a tower is upgraded,
// this information should also be updated.

// Tower attacks are animated.

// Towers cannot be built such that they block the path of enemies.

// A player can build at least three different towers. These three towers must 
// be inherently different. You cannot just change the initial damage and range 
// of a tower, for example, for a tower to qualify as different. A different 
// tower might involve a different type of attack or a different animation. 
// (e.g. a lightning tower that attacks 3 enemies at the same time)

// All attacks from the towers must be animated.

public abstract class Tower {
	
  protected String name;
  protected int cost;

  protected Thread towerAnxiety;
  protected Point location;
  protected Range range;


  private String imageFilePath;
	
	public Tower(int cost, String name,
	    Point location, Range range, 
	    String imageFP
	    ) {
	  this.cost = cost;
	  this.name = name;
	  
	  this.location = location;
	  this.range = range;
	  
	  imageFilePath = imageFP;
	  
	  initializeTower();
	}

  private void initializeTower() {
    ControllerMain.towers.add(this);
    
    towerAnxiety = new Thread(new Runnable() {

      @Override
      public void run() {
        while(true) {
          try {
            Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
            
            Set nearbyMobs = getNearbyMobs();
            if (!nearbyMobs.isEmpty()) {
              shoot(nearbyMobs);
            }
            
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  

  abstract public void shoot(Set<Mob> nearbyMobs);

  private Set getNearbyMobs() {
    Set nearbyMobs = new HashSet();
    Iterator<Mob> itr = ControllerMain.mobs.iterator();
    while(itr.hasNext()) {
       Mob nextMob = itr.next();
       if (isNear(nextMob)) {
         nearbyMobs.add(nextMob);
       }
    }
    return nearbyMobs;
  }

  private boolean isNear(Mob nextMob) {
    return Metric.closeEnough(nextMob.getCurrentLocation(), location, range.toDouble());
  }

  public String getImageFilePath() {
    return imageFilePath;
  }

  public void setImageFilePath(String imageFilePath) {
    this.imageFilePath = imageFilePath;
  }
  
  public Image getImage() {
    return ControllerMain.getGraphic(this.getImageFilePath());
  }
}


