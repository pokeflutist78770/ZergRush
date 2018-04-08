package model.Maps;

import java.awt.Point;

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
	
	public static double distanceSquared(Point p1, Point p2) {
	  double deltaX = p1.getX() - p2.getX();
	  double deltaY = p1.getY() - p2.getY();
	  
	  return deltaX * deltaX + deltaY * deltaY;
	}
  
  /**
   * Calculate the direction this mob is moving.
   * @return A point representing the unit velocity vector of this mob.
   */
  public static Point getDirectionVector(Point currentLocation, Point targetLocation) {
    // Get coordinates
    Double xDir = targetLocation.getX() - currentLocation.getY();
    Double yDir = targetLocation.getY() - currentLocation.getY();
    
    // Normalize
    Double magnitude = Math.sqrt( xDir * xDir + yDir * yDir );
    xDir = xDir / magnitude;
    yDir = yDir / magnitude;
    
    return new Point((int) Math.round(xDir), (int) Math.round(yDir));
  }
  
  /**
   * Calculate the angle of the velocity of this mob in degrees.
   * @return A double representing the angle in degrees.
   */
  public static double getDirectionAngle(Point currentLocation, Point targetLocation) {
    // Get vector direction
    Point vDir = getDirectionVector(currentLocation, targetLocation);
    
    // Deal with vertical vectors
    if (vDir.getX() == 0) {
      if (vDir.getY() > 0) {
        return 90;
      }
      else {
        return 270;
      }
    }
    
    double ratio = vDir.getY() / vDir.getX();
    double base = Math.atan(ratio);
    double radianAngle = 0;
    
    if (vDir.getX() < 0) {
      radianAngle = base + Math.PI; 
    } else if (vDir.getY() < 0) {
      radianAngle = base + 2 * Math.PI;
    } else {
      radianAngle = base;
    }
    
    return Math.toDegrees(radianAngle);
  }

}
