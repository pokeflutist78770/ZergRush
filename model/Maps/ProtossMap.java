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

  private long spawnFreq = 750;
  private String soundtrack;;
  
  public ProtossMap() {
    super("file:assets.images.map/protoss_map.jpg");
    soundtrack = "protossSoundtrack";
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
            
            System.out.println("Protoss Map is spawning: "+mob.toString());
            
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
