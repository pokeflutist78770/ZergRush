package model.Maps;

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
	public static boolean closeEnough(double x1, double y1, 
			                          double x2, double y2, 
			                          double distance) {
		double deltaX = x1 - x2;
		double deltaY = y1 - y2;
		
		return deltaX * deltaX + deltaY * deltaY < distance * distance;
	}

}
