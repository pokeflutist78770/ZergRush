package model;

import java.awt.Point;
import java.util.Vector;

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
 
  protected String imageFilePath;
  protected TowerGame theGame;
  private boolean hit = false;


  
  
  public Projectile(Point startLocation, SpeedAttribute spd,
                    double radius, double baseDamage,  ElementalAttribute ea,
                    String imgFilePath,
                    TowerGame game) {
		
    currentLocation = startLocation;
    speed = spd;
    blastRadius = radius;
    baseDmg = baseDamage;
    dmgType = ea;

    imageFilePath = imgFilePath;
    
    theGame = game;
  }
	

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
  private boolean hasReachedTarget() {
	//area of effect projectile
    if (targetMob == null) {
       return Metric.closeEnough(currentLocation, targetLocation, speed.getSpeed()/2);
    
    //target is a mob
    } else {
      return Metric.closeEnough(currentLocation, targetMob.getCurrentLocation(), blastRadius+speed.getSpeed()/2);
    }
  }

  
  /*----------     Getters/Setters      ----------*/
  
  
  public Vector<Double> getDirectionVector() {
    return Metric.getDirectionVector(currentLocation, targetLocation);
  }
	  
  public double getDirectionAngle() {
    return Metric.getDirectionAngle(currentLocation, targetLocation);
  }
	  
  protected Mob getMob() {
    return targetMob;
  }

  protected void setMob(Mob mob) {
    this.targetMob = mob;
  }

  public String getImageFilePath() {
    return imageFilePath;
  }

  public void setImageFilePath(String imgStr) {
    this.imageFilePath = imgStr;
  }
  
  public void setSpeed(SpeedAttribute s) {
	  this.speed = s;
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


  public void update() {
    if (hasReachedTarget()) {
      terminate();
      hit = true;
    } else {
      updateLocation();
    }

  }


  public boolean isDone() {
    return hit;
  }
}
