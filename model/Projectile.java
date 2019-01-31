package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

import controller.ControllerMain;
import javafx.scene.image.Image;
import views.MenuView;

/**
 * This is the object that is instantiated when the tower shoots. 
 * It keeps track of its location and it moves along until it reaches its target.
 * The code allows two types of projectiles: 
 *   projectiles that target specific mobs, 
 *   and projectiles that target locations. 
 * @author J. David Taylor
 *
 */
abstract public class Projectile implements Serializable {
  
  protected SpeedAttribute speed;
  protected Point currentLocation;
  protected Point targetLocation = null;
  protected Mob targetMob = null;
  protected int projSize;
  protected double baseDmg;
  protected ElementalAttribute dmgType;
  protected double blastRadius;
 
  protected String imageFilePath;
  protected TowerGame theGame;
  protected boolean hit = false;
  protected boolean isDank;
  
  /**
   * 
   * @param startLocation Where the projectile spawns
   * @param spd The speed attribute of the projectile
   * @param radius The radius of the projectile. This argument determines how close the projectile needs to be to a mob to hit it.
   * @param baseDamage How much damage the projectile does, not taking resistance or armor into account.
   * @param ea What type of elemental damage the projectile does, if any.
   * @param imgFilePath The filepath of the image to display for the projectile.
   * @param game The game the projectile came from.
   */
  public Projectile(Point startLocation, SpeedAttribute spd,
                    double radius, double baseDamage,  ElementalAttribute ea,
                    String imgFilePath,
                    TowerGame game, boolean isDank) {
		
    currentLocation = startLocation;
    speed = spd;
    blastRadius = radius;
    baseDmg = baseDamage;
    dmgType = ea;

    imageFilePath = imgFilePath;
    this.isDank=isDank;
    
    if(isDank) {
    	projSize=50;
    }
    else {
    	projSize=30;
    }
    
    theGame = game;
  }
	

  /**
   * This method should fill in the details of how a specific projectile reacts to reaching its target.
   * It does NOT need to handle cleanup. It does not need to remove the projectile from the game.
   * It ONLY needs to specify how the projectile affects the mob or mobs that it hits when it reaches its target.
   */
  abstract protected void terminate();

  
  /* updateLocation
   * updates the current location of a projectile
   * Parameters: None
   * Return: None
  */
  protected void updateLocation() {
    double oldX = currentLocation.getX();
    double oldY = currentLocation.getY();
    
    double spd = this.speed.getSpeed();
    Vector<Double> unitV = getDirectionVector();
    
    double newX = oldX + spd * unitV.get(0);
    double newY = oldY + spd * unitV.get(1);
    
    currentLocation.setLocation(newX, newY);  
  }

  
  /* hasReachedTarget
   * tells if a projectile has reached its destined target
   * Parameters: None
   * Returns: None
  */
  private boolean hasReachedTarget(Point targetLocation) {
      return Metric.closeEnough(currentLocation, targetLocation, blastRadius+speed.getSpeed()/2);
  }


  /**
   * Updates the state of this projectile.
   */
  public void update() {
    if (targetMob != null) {
      targetLocation = targetMob.getCurrentLocation();  
    }
    
    if (hasReachedTarget(targetLocation)) {
      terminate();
    } else {
      updateLocation();
    }

  }


  /**
   * addDamageBonus
   * adds a bonus to the damage for when a tower is more upgraded
   * @param moreDamage: the increased amount to add to damage
   */
  public void addDamageBonus(double moreDamage) {
	  baseDmg+=moreDamage;
  }


  /**
   * Is this projectile done with everything it should do in the game?
   * @return True, if the projectile has done everything it should and is ready for removal. False, otherwise.
   */
  public boolean isDone() {
    return hit;
  }

  
  /*----------     Getters/Setters      ----------*/
  
  
  public Vector<Double> getDirectionVector() {
    return Metric.getDirectionVector(currentLocation, targetLocation);
  }
	  
  public double getDirectionAngle() {
    return Metric.getDirectionAngle(currentLocation, targetLocation);
  }
	  
  public Mob getMob() {
    return targetMob;
  }

  protected void setMob(Mob mob) {
    this.targetMob = mob;
  }

  public String getImageFilePath() {
	if(isDank) {
		return "file:assets/images/tower/dickbutt.png";
	}
    return imageFilePath;
  }

  public void setImageFilePath(String imgStr) {
    this.imageFilePath = imgStr;
  }
  
  public void setSpeed(SpeedAttribute s) {  
	  this.speed = s;
  }
  
  public SpeedAttribute getSpeed() {
    return speed; 
  }
  
  public Image getImage() {
    return ControllerMain.getGraphic(this.getImageFilePath());
  }
	
  public double getX() {
    return currentLocation.getX();  
  }
	
  public double getY() {
    return currentLocation.getY();
  }
   
  public int getProjSize() {
	  return projSize;
  }
}
