package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.lang.reflect.*;

import controller.ControllerMain;
import javafx.scene.image.Image;
import model.Mobs.Hydralisk;
import model.Mobs.Mob;
import model.Mobs.Ultralisk;
import model.Mobs.Zergling;
import model.Towers.DemoTower;

// You should have at least 3 maps.

// Each map has a background image and contains a pre-defined path for enemies
// such that enemies originate at their start location and move along this path
// toward the player's destination and toward the end-zone.

// Different maps have different backgrounds and paths that enemies travel.

// One of the maps contains more than one path for enemies to travel on ( so it
// presents more difficult gameplay, not a linear path! The path should diverge
// at some point).

// A player can select and play on at least three maps. The path that enemies
// travel must be different -- you cannot just change a background image and
// call it a new map. These 3 maps should be built-in and selectable at 
// game startup.

// Maps should have space on the sides of the path for players to build towers.

// Enemies continuously Spawn from the map at some location. We call this the 
// Spawn-Zone.




public abstract class Map {
  
  public String imageFilePath;
  public static int idNo = 0;
  protected String name;
  protected HashMap<Integer, List<Point>> paths; // Each map class should have its own hardcoded path setup.
  public static long SPAWN_FREQUENCY =   10* 1000;

  private static int waveIntensity;
  private int waveRatio;
  
  public Map (String imgFp, int difficulty) {
    paths = new HashMap<Integer, List<Point>>();
    constructMobRoute();
    imageFilePath = imgFp;
    waveIntensity = 3;
    waveRatio = difficulty;
    
  }
  
  
  /* scalePoint
   * scales a given x and y position to the screen resolution
   * Parameters: p: a gien Point object
   * Returns: None
  */
  public static void scalePoint(Point p) {
	int x = (int) p.getX();
	int y = (int) p.getY();
	p.setLocation((int) x*ControllerMain.GUI_SIZE/1000, (int)y*ControllerMain.GUI_SIZE/1000);
  }
  
  //gets the map image
  public Image getImage() {
    return ControllerMain.getGraphic(imageFilePath);
  }

  abstract void constructMobRoute();
  

  
  /* initializeTowers
   * initializes the towers for the map
  */
  protected void initializeTowers() {
    ControllerMain.towers.add(new DemoTower(new Point(651*800/1000, 839*800/1000)));
  }
  

  protected void initializeSpawnCycle(List<String> mobTypes) {
    
    List<Class> mobClasses = new ArrayList<Class>();
    for (String mType: mobTypes) {
      try {
        mobClasses.add(Class.forName("model.Mobs." + mType));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        System.out.println("mobTypes to mobClasses conversion in model.Map failed.");
      }
    }
    
    List<Constructor<Mob>> mobConstructors = new ArrayList<Constructor<Mob>>();
    for (Class cls: mobClasses) {
      mobConstructors.add(cls.getConstructors()[0]);
    }
    
    Thread spawnCycle = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e1) {
          // e1.printStackTrace();
        }
        do {
          try {
            
            spawnWave(mobConstructors, waveIntensity);
            updateWaveIntensity();
            
            Thread.sleep(SPAWN_FREQUENCY );
          } catch (InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
          } 
        } while(ControllerMain.isPlaying);
        
      }
    });
    
    spawnCycle.start();
  }


  protected void updateWaveIntensity() {
    waveIntensity = 3*waveIntensity;
    
  }


  protected void spawnWave(List<Constructor<Mob>> mobConstructors, int intensity) {

    int numberOfPaths = paths.size();
    int numberOfMobTypes = mobConstructors.size();
    int spawnCount = waveIntensity;
    for (int i = 0; i < numberOfMobTypes; i++) {
      try {
        for (int j = 0; j < spawnCount; j++) {
          ControllerMain.mobs.add(mobConstructors.get(i).newInstance(paths.get(1+ ControllerMain.getRandom().nextInt(numberOfPaths))));
        }
        spawnCount = spawnCount / waveRatio;
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        System.out.println("Failure in spawnWave method of Map class");
        e.printStackTrace();
      }
    }
    
  }


  public static int getWaveIntensity() {
    return waveIntensity;
  }


  public static void setWaveIntensity(int input) {
    waveIntensity = input;
  }
	
}
