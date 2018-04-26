package model.Maps;

import java.awt.Point; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import controller.ControllerMain;
import model.TowerGame;
import model.Mobs.Archon;
import model.Mobs.BattleCruiser;
import model.Mobs.DarkTemplar;
import model.Mobs.Hydralisk;
import model.Mobs.Marine;
import model.Mobs.SpeedAttribute;
import model.Mobs.Ultralisk;
import model.Mobs.Wraith;
import model.Mobs.Zealot;
import model.Mobs.Zergling;

public class DemoMap extends Map {
  
  
  public DemoMap(TowerGame game) {
    super("file:assets/images/map/demoMap.png","Easy", game);
    
    
    //ControllerMain.towers.add(new DemoTower(new Point(ControllerMain.GUI_SIZE/2, ControllerMain.GUI_SIZE/2)));
    initializeSpawnCycle(Arrays.asList("Zergling"));
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
        new Point(950, 950), 
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
        new Point(128, 92)));
    
    for(Point p: pathOne) {
      Map.scalePoint(p);
    }
    
    this.getPaths().put(1, pathOne);
  }
}
