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

public class TerranMap extends Map {

  private String soundtrack;;
  
  public TerranMap() {
    super("file:assets/images/map/terran_map.jpg");
    soundtrack = "terranSoundtrack";
    initializeTowers();
    initializeSpawnCycle(Arrays.asList("Marine", "Wraith", "BattleCruiser"));
    name = "Terran"+ idNo;
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
        new Point(876, 890), 
        new Point(762, 834),
        new Point(684, 883),
        new Point(566, 756),
        new Point(466, 633),
        new Point(521, 403),
        new Point(674, 256),
        new Point(653, 173),
        new Point(562, 130),
        new Point(402, 204),
        new Point(358, 166),
        new Point(316, 89),
        new Point(201, 168),
        new Point(147, 119)
        ));
    
    
    for(Point p: pathOne) {
      Map.scalePoint(p);
    }
    
    
    this.paths.put(1, pathOne);
  }

}
