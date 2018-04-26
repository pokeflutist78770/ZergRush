package model;

import java.awt.Point;
import java.util.Arrays;
import java.util.Vector;

/**
 * This class exists to provide a static method that determines if two points 
 * are within some distance of one another.
 * @author J. David Taylor
 *
 */
public class Metric {

	/**
	 * This method determines when two points are sufficiently close.
	 * @param x1 The x-coordinate of the first point.
	 * @param y1 The y-coordinate of the first point.
	 * @param x2 The x-coordinate of the second point.
	 * @param y2 The y-coordinate of the second point.
	 * @param distance The distance that you are comparing to.
	 * @return True, if the distance between the two points is less than the 
	 * distance provided. False, otherwise.
	 */
	public static boolean closeEnough(Point p1, Point p2, double distance) {
		return distanceSquared(p1, p2) < distance * distance;
	}
	
	
	/* distanceSquared
	 * calculates two distances squared
	 * Parameters: p1: point one
	 *             p2: another point to calculate distance from
	 * Returns: double representing the equation if squared distance
	*/
	public static double distanceSquared(Point p1, Point p2) {
	  double deltaX = p1.getX() - p2.getX();
	  double deltaY = p1.getY() - p2.getY();
	  
	  return deltaX * deltaX + deltaY * deltaY;
	}
  
	
  /**
   * Calculate the direction this mob is moving.
   * @return A point representing the unit velocity vector of this mob.
   */
  public static Vector<Double> getDirectionVector(Point currentLocation, Point targetLocation) {
    // Get coordinates
    Double xDir = targetLocation.getX() - currentLocation.getX();
    Double yDir = targetLocation.getY() - currentLocation.getY();
    
    // Normalize
    Double magnitude = Math.sqrt( xDir * xDir + yDir * yDir );
    xDir = xDir / magnitude;
    yDir = yDir / magnitude;
    
    return new Vector(Arrays.asList(xDir, yDir));
  }
  
  
  /**
   * Calculate the angle of the velocity of this mob in degrees.
   * @return A double representing the angle in degrees.
   */
  public static double getDirectionAngle(Point currentLocation, Point targetLocation) {
    // Get vector direction
    Vector<Double> vDir = getDirectionVector(currentLocation, targetLocation);
    
    // Deal with vertical vectors
    if (vDir.get(0) == 0) {
      if (vDir.get(1) > 0) {
        return 270;
      }
      else {
        return 90;
      }
    }
    
    double ratio = vDir.get(1) / vDir.get(0);
    double base = Math.atan(ratio);  //solves for base angle 
    double radianAngle = 0;
    
    //some nice redirecting of the angle to prevent negatives (I'm 98% sure)
    if (vDir.get(0) < 0) {
      radianAngle = base + Math.PI; 
    } else if (vDir.get(1) < 0) {
      radianAngle = base + 2 * Math.PI;
    
    //angle is good as is
    } else {
      radianAngle = base;
    }
    
    if (Math.toDegrees(radianAngle) == 0.0) {
      return 0.0;
    }
    
    return 360 - Math.toDegrees(radianAngle);
  }

}
