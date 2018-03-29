package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

// 
/**
 * This class will encapsulate the data of a walkable path from start to finish. 
 * The head of the path is assumed to be the spawning location of a mob.
 * Each map will have a different path. It is deliberately limited in its abilities.
 * @author J. David Taylor
 *
 */
public class Path {
	
	/**
	 * The LinkedList implementation is so that pop() takes O(1) time. 
	 * It would be O(n) for an ArrayList.
	 */
	private ArrayList<Point> points;
	
	/**
	 * 
	 * @param points A list of points that the path connects.
	 */
	public Path(List<Point> points) {
		ListIterator<Point> itr = points.listIterator();
		this.points = new ArrayList<Point>();
		while (itr.hasNext()) {
			this.points.add(itr.next());
		}
	}
	
	public Point get(int index) {
		return points.get(index);
	}
	
	public void add(Point p) {
	  points.add(p);
	}
	
	public int size() {
	  return points.size();
	}

}
