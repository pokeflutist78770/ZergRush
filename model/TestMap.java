package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

import controller.ControllerMain;

import java.util.Arrays;
import java.util.Vector;

public class TestMap extends Map implements Serializable {

  private String soundtrack;;
  
  public TestMap(TowerGame game, int i) throws ClassNotFoundException {
    super("Test", "file:assets/images/map/terran_map.jpg", "Easy", game);
    soundtrack = "terranSoundtrack";
    Vector<String> VectorOfMobs = new Vector<String>();
    if (i==0) {
      VectorOfMobs = new Vector<String>(Arrays.asList("Belch", "Wraith", "BattleCruiser"));
    } else {
      VectorOfMobs = new Vector<String>(Arrays.asList("Marine", "Wraith", "BattleCruiser"));
    }
    ControllerMain.mobConstructors = initializeSpawnConstructors(VectorOfMobs);
    name = "Terran"+ idNo;
  }

  
  /* constructMobRoute
   * Creates the route for each mob to walk through
   * Parameters: None
   * Returns: None
  */
  @Override
  void constructMobRoute() {
    Vector<Point> pathOne = new Vector<Point>(Arrays.asList(
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
    
    
    this.getPaths().put(1, pathOne);
  }

}
