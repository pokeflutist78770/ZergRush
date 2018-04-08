package model.Maps;

import java.awt.Point;
import java.util.HashMap;

public class DemoMap extends Map {
  private HashMap<Integer, Point[]> paths;
  @Override
  void constructMobRoute() {
    Point[] pathOne = {new Point(950, 950), 
        new Point(883, 820),
        new Point(817, 820),
        new Point(781, 931),
        new Point(651, 839),
        new Point(581, 739),
        new Point(378, 899),
        new Point(177, 685),
        new Point(207, 496),
        new Point(194, 367),
        new Point(235, 278),
        new Point(266, 162),
        new Point(185, 75),
        new Point(128, 92)};
    
    for(Point p: pathOne) {
      Map.scalePoint(p);
    }
    
    paths = new HashMap<Integer, Point[]>();
    this.paths.put(1, pathOne);
  }
  
  

}
