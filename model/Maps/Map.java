package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.lang.reflect.*;

import controller.ControllerMain;
import javafx.scene.image.Image;
import model.TowerGame;
import model.Mobs.Hydralisk;
import model.Mobs.Mob;
import model.Mobs.Ultralisk;
import model.Mobs.Zergling;

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

  public final static int DEFAULT_SPAWN_FREQUENCY =   10* 100;
  public final static int DEFAULT_SPAWN_INTENSITY = 3;
  
  private static int waveIntensity;
  private int waveRatio;
  private List<Constructor<Mob>> mobConstructors;
  private static Random rng = new Random();
  
  public String backgroundImageFilePath;
  public static int idNo = 0;
  protected String name;
  
  private HashMap<Integer, List<Point>> paths; // Each map class should have its own hardcoded path setup.

  protected TowerGame theGame;
  private int mapClock;
  
  public Map (String imgFp, String difficulty, TowerGame game) {
    initializePathing();
    setBackground(imgFp);
    initializeSpawning(difficulty);
    theGame = game;
    mapClock = DEFAULT_SPAWN_FREQUENCY;
    
  }


  private void initializeSpawning(String difficulty) {
    waveIntensity =  DEFAULT_SPAWN_INTENSITY;
    if (difficulty.equals("Easy")) {
      waveRatio = 1;
    } else if (difficulty.equals("Medium")) {
      waveRatio = 2;
    } else if (difficulty.equals("Hard")) {
      waveRatio = 3;
    } else {
      waveRatio = 2;
    }
  }


  private void setBackground(String imgFp) {
    backgroundImageFilePath = imgFp;
  }


  public void initializePathing() {
    paths = new HashMap<Integer, List<Point>>();
    constructMobRoute();
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
    return ControllerMain.getGraphic(backgroundImageFilePath);
  }

  abstract void constructMobRoute();
  

  /** initializeSpawnCycle
   *  Starts the spawn cycle for the given mmap, periodiclaly creating new mob objects
   *  Parameters: mobTypes: list containing all the mob types fortge map represented as Strings
   *  Returns: None
  */
  protected void initializeSpawnCycle(List<String> mobTypes) {
    
    List<Class> mobClasses = new ArrayList<Class>();
    
    //attempts to add all mob classes to a new list
    for (String mType: mobTypes) {
      try {
        mobClasses.add(Class.forName("model.Mobs." + mType));
      } catch (ClassNotFoundException e) {
        System.out.println("mobTypes to mobClasses conversion in model.Map failed.");
        e.printStackTrace();
      }
    }
    
    mobConstructors = new ArrayList<Constructor<Mob>>();
    for (Class cls: mobClasses) {
      mobConstructors.add(cls.getConstructors()[0]);
    }
  }


  public void update() {
    mapClock++;
    if (mapClock > DEFAULT_SPAWN_FREQUENCY && theGame.getMobs().size() < 5000) {
      mapClock = 0;
      spawnWave(mobConstructors, waveIntensity);
      updateWaveIntensity();  //allows waves to get harder as time goes on
    }
  }


  /* updates the wave intensity, increasing it */
  protected void updateWaveIntensity() {
    waveIntensity = 3*waveIntensity;
  }

  
  /* spawnWave
   * Spawns a new wave of enemies for the player to fight
   * Parameters: mobConstructors: list of mob constructors in order to spawn them
   *             intensity: the intensity of a wave, how hard it is
   * Returns: None
  */
  protected void spawnWave(List<Constructor<Mob>> mobConstructors, int intensity) {

    int numberOfPaths = paths.size();
    int numberOfMobTypes = mobConstructors.size();
    int spawnCount = waveIntensity;
    
    //lets start spawning enemies shall we!
    for (int i = 0; i < numberOfMobTypes; i++) {
      try {
        for (int j = 0; j < spawnCount; j++) {
          theGame.add(mobConstructors.get(i).newInstance(
        		                  paths.get(1+ rng.nextInt(numberOfPaths)), theGame));
        }
        spawnCount = spawnCount / waveRatio;
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        System.out.println("Failure in spawnWave method of Map class");
        e.printStackTrace();
      }
    } 
  }

  /*-------------------    Getters/Setters    ----------------*/
  
  public static int getWaveIntensity() {
    return waveIntensity;
  }


  public static void setWaveIntensity(int input) {
    waveIntensity = input;
  }


  public HashMap<Integer, List<Point>> getPaths() {
    return paths;
  }


  public void setPaths(HashMap<Integer, List<Point>> paths) {
    this.paths = paths;
  }
	
}
