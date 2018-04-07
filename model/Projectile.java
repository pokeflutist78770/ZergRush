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

  private Point currentLocation;
  private Point targetLocation = null;
	private Mob targetMob = null;
  private String imgStr;
  private ElementalAttribute dmgType;
  private int baseDmg;
  private Thread kamakaziImperative;
  private SpeedAttribute speed;
  private double blastRadius;
	
	public Projectile(String imgFilePath, ElementalAttribute ea, Point startLocation, 
	    int baseDamage, SpeedAttribute spd) {
		imgStr = imgFilePath;
		dmgType = ea;
		currentLocation = startLocation;
		baseDmg = baseDamage;
		speed = spd;
		
		initializeProjectile();
	}
	
	private void initializeProjectile() {
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

  protected void terminate() {
    // TODO Auto-generated method stub
    
  }

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
       return Metric.closeEnough(currentLocation.getX(), currentLocation.getY(), targetLocation.getX(), targetLocation.getY(), blastRadius);
    } else {
      return Metric.closeEnough(currentLocation.getX(), currentLocation.getY(), targetMob.getX(), targetMob.getY(), blastRadius);
    }
  }

  private Mob getMob() {
    return targetMob;
  }

  private void setMob(Mob mob) {
    this.targetMob = mob;
  }

  private Point getTargetLocation() {
    return targetLocation;
  }

  private void setTargetLocation(Point targetLocation) {
    this.targetLocation = targetLocation;
  }
	
	
	

}
