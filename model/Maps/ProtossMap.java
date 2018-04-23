package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ControllerMain;
import model.Mobs.Hydralisk;
import model.Mobs.Ultralisk;
import model.Mobs.Zergling;
import model.Towers.DemoTower;

public class ProtossMap extends Map {

  private String soundtrack;;
  
  public ProtossMap(int difficulty) {
    super("file:assets/images/map/protoss_map.jpg", difficulty);
    soundtrack = "protossSoundtrack";
    initializeTowers();
    initializeSpawnCycle(Arrays.asList("Zealot", "DarkTemplar", "Archon"));
    name = "Protoss"+ idNo;
   // ControllerMain.mobs.add(new DemoMob(paths.get(1)));
  }

  
  /* constructMobRoute
   * Creates the route for each mob to walk through
   * Parameters: None
   * Returns: None
  */
  @Override
  void constructMobRoute() {
    List<Point> pathOne = new ArrayList<Point>(Arrays.asList(
        new Point(130, 893), 
        new Point(97, 773),
        new Point(178, 673),
        new Point(323, 539),
        new Point(413, 487),
        new Point(521, 545),
        new Point(516, 374),
        new Point(661, 241),
        new Point(820, 361),
        new Point(974, 205),
        new Point(923, 135)
        ));
    
    List<Point> pathTwo = new ArrayList<Point>(Arrays.asList(
        new Point(130, 893), 
        new Point(97, 773),
        new Point(178, 673),
        new Point(224, 629),
        new Point(281, 662),
        new Point(372, 755),
        new Point(481, 700),
        new Point(569, 781),
        new Point(760, 574),
        new Point(701, 448),
        new Point(820, 361),
        new Point(974, 205),
        new Point(923, 135)
       ));
    
    
    for(Point p: pathOne) {
      Map.scalePoint(p);
    }
    
    for(Point p: pathTwo) {
      Map.scalePoint(p);
    }
    
    
    this.paths.put(1, pathOne);
    this.paths.put(2, pathTwo);
  }

}
