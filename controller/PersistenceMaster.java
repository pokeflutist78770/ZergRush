package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import model.Map;
import model.Mob;
import model.Player;
import model.Projectile;
import model.Tower;
import model.TowerGame;

/**
 * The persistence master allows the functionality of loading or saving a game.
 * @author J. David Taylor
 *
 */
public class PersistenceMaster {
  
  private static Vector saveData;
  private static TowerGame theGame;
  private static Vector loadData;

  /**
   * Saves a TowerGame.
   * @param tg
   */
  public static void saveGame(TowerGame tg) {
    // just in case
    tg.pause();
    theGame = tg;
    
    saveData = new Vector();
    saveMobs();
    saveProjectiles();
    saveTowers();
    
    saveMobsKilled();
    
    savePlayer();
    saveMap();
    
    writeData("THE_IMMORTAL_SPRITESHEET");
    
  }
  
  private static void writeData(String saveName) {
    try {
      FileOutputStream fileOutput = new FileOutputStream(saveName);
      ObjectOutputStream out = new ObjectOutputStream(fileOutput);
      out.writeObject(saveData);
      out.close();   
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  private static void saveMap() {
    
    // TODO Auto-generated method stub
    
  }

  private static void savePlayer() {
    // TODO Auto-generated method stub
    
  }

  private static void saveMobsKilled() {
    // TODO Auto-generated method stub
    
  }

  private static void saveTowers() {
    // TODO Auto-generated method stub
    
  }

  private static void saveProjectiles() {
    // TODO Auto-generated method stub
    
  }

  private static void saveMobs() {
    Vector<Mob> mobs = new Vector(theGame.getMobs());
    saveData.add(mobs);
  }

  /**
   * Loads a TowerGame.
   * @return
   */
  public static TowerGame loadGame() {
    
    // create a new game
    String mapSelection = loadMapSelection();
    String difficulty = loadDifficulty();
    TowerGame output = new TowerGame(difficulty, mapSelection);

    try {
      FileInputStream fileOutput = new FileInputStream("THE_IMMORTAL_SPRITESHEET");
      ObjectInputStream in = new ObjectInputStream(fileOutput);
      
      loadData = (Vector) in.readObject().
      
      // configure its parameters
      loadMobs(output);
      loadProjectiles(output);
      loadTowers(output);
      
      loadMobsKilled(output);
      
      loadPlayer(output);
      loadMap(output);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return output;
  }

  private static void loadMap(TowerGame output) {
    // TODO Auto-generated method stub
    
  }

  private static void loadPlayer(TowerGame output) {
    // TODO Auto-generated method stub
    
  }

  private static void loadMobsKilled(TowerGame output) {
    // TODO Auto-generated method stub
    
  }

  private static void loadTowers(TowerGame output) {
    // TODO Auto-generated method stub
    
  }

  private static void loadProjectiles(TowerGame output) {
    // TODO Auto-generated method stub
    
  }

  private static void loadMobs(TowerGame output) {
    
  }

  private static String loadDifficulty() {
    // TODO Auto-generated method stub
    return null;
  }

  private static String loadMapSelection() {
    // TODO Auto-generated method stub
    return null;
  }

}
