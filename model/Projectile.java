package model;

import java.awt.Point;

import controller.ControllerMain;
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
  

  protected String imgStr;
	
	public Projectile(Point startLocation, SpeedAttribute spd,
	    double radius,
	    double baseDamage,  ElementalAttribute ea,
	    String imgFilePath
	    ) {
    currentLocation = startLocation;
    speed = spd;
    blastRadius = radius;
    

    baseDmg = baseDamage;
		dmgType = ea;

    imgStr = imgFilePath;
		
		initializeProjectile();
	}
	
	private void initializeProjectile() {
	  
	  ControllerMain.projectiles.add(this);
	  
	  kamakaziImperative = new Thread(new Runnable() {

      @Override
      public void run() {
        while(true) {
          try {
            Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
            
            if (!hasReachedTarget()) {
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
    
  }
  
  public Point getDirectionVector() {
    return Metric.getDirectionVector(currentLocation, getTargetLocation());
  }
  
  public double getDirectionAngle() {
    return Metric.getDirectionAngle(currentLocation, getTargetLocation());
  }

  abstract protected void terminate();

  protected void updateLocation() {
    double oldX = currentLocation.getX();
    double oldY = currentLocation.getY();
    
    double spd = this.speed.toDouble();
    Point unitV = getDirectionVector();
    
    double newX = oldX + spd * unitV.getX();
    double newY = oldY + spd * unitV.getY();
    
    currentLocation.setLocation(newX, newY);
    
  }

  private boolean hasReachedTarget() {
    if (targetMob == null) {
       return Metric.closeEnough(currentLocation, targetLocation, blastRadius);
    } else {
      return Metric.closeEnough(currentLocation, targetMob.getLocation(), blastRadius);
    }
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
	
	
	

}
