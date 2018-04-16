package model;

import java.awt.Point;

import controller.ControllerMain;
import javafx.scene.image.Image;
import model.Maps.Metric;
import model.Mobs.Mob;
import model.Mobs.SpeedAttribute;
import model.Towers.ElementalAttribute;



// This is the object that is instantiated when the tower shoots. 
//It keeps track of its location and it moves along until it reaches its target.
abstract public class Projectile {
  
  protected Thread kamakaziImperative;
  protected SpeedAttribute speed;
  protected Point currentLocation;
  protected Point targetLocation = null;
  protected Mob targetMob = null;

  protected double baseDmg;
  protected ElementalAttribute dmgType;
  protected double blastRadius;
 
  private String imageFilePath;

  
  
  public Projectile(Point startLocation, SpeedAttribute spd,
                    double radius, double baseDamage,  ElementalAttribute ea,
                    String imgFilePath
                    ) {
		
    currentLocation = startLocation;
    speed = spd;
    blastRadius = radius;
    baseDmg = baseDamage;
    dmgType = ea;

    imageFilePath = imgFilePath;
		
    initializeProjectile();
  }
	

  abstract protected void terminate();
	
  /* initializeProjectile
   * starts a thread for a new projectile and shows an animation
   * Parameters: None
   * Returns: None
  */
  private void initializeProjectile() {
	  
    kamakaziImperative = new Thread(new Runnable() {
      @Override
      public void run() {
    	while(!Thread.interrupted()) {
          try {
            Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
            
            if (!hasReachedTarget()) { //projectile still hasnt reached mob yet
              updateLocation();
            } else {
              terminate();
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    
    kamakaziImperative.start();
  }

  
  /* updateLocation
   * updates the current location of a projectile
   * Parameters: None
   * Return: None
  */
  protected void updateLocation() {
    double oldX = currentLocation.getX();
    double oldY = currentLocation.getY();
    
    double spd = this.speed.toDouble();
    Point unitV = getDirectionVector();
    
    double newX = oldX + spd * unitV.getX();
    double newY = oldY + spd * unitV.getY();
    
    currentLocation.setLocation(newX, newY);  
  }

  
  /* hasReachedTarget
   * tells if a projectile has reached its destined target
   * Parameters: None
   * Returns: None
  */
  private boolean hasReachedTarget() {
	//area of effect projectile
    if (targetMob == null) {
       return Metric.closeEnough(currentLocation, targetLocation, blastRadius);
    
    //target is a mob
    } else {
      return Metric.closeEnough(currentLocation, targetMob.getCurrentLocation(), blastRadius);
    }
  }

  
  /*----------     Getters/Setters      ----------*/
  
  
  public Point getDirectionVector() {
    return Metric.getDirectionVector(currentLocation, getTargetLocation());
  }
	  
  public double getDirectionAngle() {
    return Metric.getDirectionAngle(currentLocation, getTargetLocation());
  }
	  
  protected Mob getMob() {
    return targetMob;
  }

  protected void setMob(Mob mob) {
    this.targetMob = mob;
  }

  private Point getTargetLocation() {
    return targetLocation;
  }

  private void setTargetLocation(Point targetLocation) {
    this.targetLocation = targetLocation;
  }

  public String getImageFilePath() {
    return imageFilePath;
  }

  public void setImageFilePath(String imgStr) {
    this.imageFilePath = imgStr;
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
}
