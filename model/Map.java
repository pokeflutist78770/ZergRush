package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.HashMap;
import java.util.Vector;
import java.util.Random;
import java.lang.reflect.*;

import controller.ControllerMain;
import javafx.scene.image.Image;

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



/**
 * The map contains the data and methods used to carry out spawning of mobs. 
 * It also contains the image of the play space.
 * @author J. David Taylor
 *
 */
public abstract class Map implements Serializable {
  
  public final static int DEFAULT_SPAWN_FREQUENCY =   1000;
  public final static int DEFAULT_SPAWN_INTENSITY = 3;
  
  protected int waveIntensity; 
  protected int waveRatio;
  
  public String backgroundImageFilePath;
  public static int idNo = 0;
  protected String name;
  protected String soundTrackName;
  
  protected HashMap<Integer, Vector<Point>> paths; // Each map class should have its own hardcoded path setup.

  protected TowerGame theGame;
  protected int mapClock;
  
  /**
   * Create a map with the given difficulty, game, and image filepath.
   * @param imgFp The filepath for the background image.
   * @param difficulty "Easy", "Medium", "Hard", or "Meme"
   * @param game The game instantiating this map.
   */
  public Map (String soundTrack, String imgFp, String difficulty, TowerGame game) {
    initializePathing();
    setSoundTrackName(soundTrack);
    setBackground(imgFp);
    setWaveRatio(difficulty);
    theGame = game;
    mapClock = DEFAULT_SPAWN_FREQUENCY;
    
  }


  /**
   * Sets the waveRatio. The Wave Ratio is the rate at which each wave is larger than the last. 
   * @param difficulty 
   */
  private void setWaveRatio(String difficulty) {
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


  /**
   * Set the background image.
   * @param imgFp The filepath of the new image.
   */
  private void setBackground(String imgFp) {
    backgroundImageFilePath = imgFp;
  }


  /**
   * Get all the paths constructed and bundled into a Map<Integer,Vector<Point>>.
   */
  public void initializePathing() {
    paths = new HashMap<Integer, Vector<Point>>();
    constructMobRoute();
  }
  
  
  /* scalePoint
   * scales a given x and y position to the screen resolution
   * Parameters: p: a given Point object
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

  /**
   * Each map should handle this differently.
   * It should configure the HashMap "paths" to hold 1 or more paths of 2 or more vertices.
   */
  abstract void constructMobRoute();
  

  /** initializeSpawnCycle
   *  Starts the spawn cycle for the given mmap, periodicaly creating new mob objects
   *  After running this method, the map can spawn waves from the Vector of constructors this method initializes.
   *  Parameters: mobTypes: Vector containing all the mob types for the map represented as Strings
   *    e.g. "Zergling" translates into the constructor for Zergline.java
   *  Returns: None
   * @throws ClassNotFoundException 
  */
  public static Vector<Constructor<Mob>> initializeSpawnConstructors(Vector<String> mobTypes) throws ClassNotFoundException {
    
    Vector<Class> mobClasses = new Vector<Class>();
    
    //attempts to add all mob classes to a new Vector
    for (String mType: mobTypes) {
      mobClasses.add(Class.forName("model." + mType));
    }
    
    Vector<Constructor<Mob>> mobConstructors = new Vector<Constructor<Mob>>();
    for (Class cls: mobClasses) {
      mobConstructors.add(cls.getConstructors()[0]);
    }
    return mobConstructors;
  }


  /**
   * A single update of the game state.
   * The mapClock increments. Whenever it passes the spawn frequency, mobs are spawned and the mapClock is reset to 0.
   * If there are more than 5000 mobs, nothing happens.
   */
  public void update() {
    mapClock++;
    if (theGame.getMobs().size() > 5000) {
      mapClock = 0;
      return;
    }
    if (mapClock > DEFAULT_SPAWN_FREQUENCY) {
      mapClock = 0;
      
      spawnWave(ControllerMain.mobConstructors, waveIntensity);
      updateWaveIntensity();  //allows waves to get harder as time goes on
    }
  }


  /* updates the wave intensity, increasing it */
  protected void updateWaveIntensity() {
    waveIntensity = waveRatio*waveIntensity;
  }

  
  /* spawnWave
   * Spawns a new wave of enemies for the player to fight
   * Parameters: mobConstructors: Vector of mob constructors in order to spawn them
   *             intensity: the intensity of a wave, how hard it is
   * Returns: None
  */
  protected void spawnWave(Vector<Constructor<Mob>> mobConstructors, int intensity) {

    int numberOfPaths = paths.size();
    int numberOfMobTypes = mobConstructors.size();
    int spawnCount = waveIntensity;
    
    //lets start spawning enemies shall we!
    for (int i = 0; i < numberOfMobTypes; i++) {
      try {
        for (int j = 0; j < spawnCount; j++) {
          theGame.add(mobConstructors.get(i).newInstance(
        		                  paths.get(1+ (new Random()).nextInt(numberOfPaths)), theGame));
        }
        spawnCount = spawnCount / 3;
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        System.out.println("Failure in spawnWave method of Map class");
        e.printStackTrace();
      }
    } 
  }

  /*-------------------    Getters/Setters    ----------------*/
  
  public int getWaveIntensity() {
    return waveIntensity;
  }


  public void setWaveIntensity(int input) {
    waveIntensity = input;
  }


  public HashMap<Integer, Vector<Point>> getPaths() {
    return paths;
  }


  public void setPaths(HashMap<Integer, Vector<Point>> paths) {
    this.paths = paths;
  }


  public String getSoundTrackName() {
    return soundTrackName;
  }


  public void setSoundTrackName(String soundTrackName) {
    this.soundTrackName = soundTrackName;
  }
	
}
