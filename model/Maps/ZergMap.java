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

public class ZergMap extends Map {

  private long spawnFreq = 750;
  private String soundtrack;
  
  public ZergMap() {
    super("file:assets/images/map/zerg_map.jpg");
    soundtrack = "zergSoundtrack";
    initializeTowers();
    initializeSpawnCycle();
   // ControllerMain.mobs.add(new DemoMob(paths.get(1)));
  }
  
  
  /* initializeSpawnCycle
   * Starts the spawn cycle for the gameplay
   * Parameters: None
   * Returns: None
  */
  private void initializeSpawnCycle() {
    Thread spawnCycle = new Thread(new Runnable() {
      @Override
      public void run() {
        do {
          try {
            
            Zergling mob=new Zergling(paths.get(1));
            Hydralisk mob2=new Hydralisk(paths.get(1));
            Ultralisk mob3=new Ultralisk(paths.get(1));
           // DemoMob mob=new DemoMob(paths.get(1));
            
            System.out.println("DemoMap is spawning: "+mob.toString());
            
            ControllerMain.mobs.add(mob);
            ControllerMain.mobs.add(mob2);
            ControllerMain.mobs.add(mob3);
            
            if(!ControllerMain.isPlaying) {
              break;
            }
            
            Thread.sleep(spawnFreq);
          } catch (InterruptedException e) {
            e.printStackTrace();
          } 
        } while(true);
        
      }
    });
    
    spawnCycle.start();
  }

  
  /* initializeTowers
   * initializes the towers for the map
  */
  private void initializeTowers() {
    ControllerMain.towers.add(new DemoTower(new Point(651*800/1000, 839*800/1000)));
  }

  
  /* constructMobRoute
   * Creates the route for each mob to walk through
   * Parameters: None
   * Returns: None
  */
  @Override
  void constructMobRoute() {
    List<Point> pathOne = new ArrayList<Point>(Arrays.asList(
        new Point(770, 894), 
        new Point(875, 772),
        new Point(856, 670),
        new Point(751, 600),
        new Point(570, 759),
        new Point(451, 643),
        new Point(612, 478),
        new Point(528, 387),
        new Point(506, 292),
        new Point(415, 248),
        new Point(240, 377),
        new Point(177, 353),
        new Point(88, 258),
        new Point(192, 136)));
    
    List<Point> pathTwo = new ArrayList<Point>(Arrays.asList(
        new Point(898, 101), 
        new Point(725, 102),
        new Point(642, 209),
        new Point(557, 480),
        new Point(459, 263),
        new Point(354, 295),
        new Point(236, 363),
        new Point(166, 341),
        new Point(88, 257),
        new Point(192, 236)
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
