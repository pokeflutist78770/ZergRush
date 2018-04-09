package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import controller.ControllerMain;
import model.Mobs.DemoMob;
import model.Towers.DemoTower;

public class DemoMap extends Map {
  
  private long spawnFreq = 5000;
  
  public DemoMap() {
    super("file:assets.images.map/demoMap.png");
    
    initializeTowers();
    //initializeSpawnCycle();
    ControllerMain.mobs.add(new DemoMob(paths.get(1)));
  }
  
  
  private void initializeSpawnCycle() {
    Thread spawnCycle = new Thread(new Runnable() {

      @Override
      public void run() {
        do {
          try {
            System.out.println("demomap is spawning");
            ControllerMain.mobs.add(new DemoMob(paths.get(1)));
            
            
            Thread.sleep(spawnFreq);
          } catch (InterruptedException e) {
            e.printStackTrace();
          } 
        } while(true);
        
      }
      
    });
    spawnCycle.start();
    
  }

  private void initializeTowers() {
    ControllerMain.towers.add(new DemoTower(new Point(651*800/1000, 839*800/1000)));
  }

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
    
    this.paths.put(1, pathOne);
  }
  
  

}
