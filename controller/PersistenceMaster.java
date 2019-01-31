package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
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
  private static Vector saveDataSlots;
  private static Vector loadData;
  
  private static TowerGame theGame;
  private static Vector<Mob> mobs;
  private static Vector<Projectile> projectiles;
  private static Vector<Tower> towers;
  private static Integer deathCount;
  private static Player thePlayer;
  private static Map theMap;

  
  /**
   * Saves a TowerGame.
   * @param tg  
   */
  public static void saveGame(TowerGame tg) {
    // just in case
    tg.pause();
    theGame = tg;
    
    saveMobs();
    saveProjectiles();
    saveTowers();
    saveMobsKilled();
    savePlayer();
    saveMap();
    writeData("THE_IMMORTAL_SPRITESHEET");
  }
  

  /**
   * Loads a TowerGame.
   * @return
   */
  public static TowerGame loadGame() {
    
    readData();
    
    theGame = (TowerGame) loadData.get(0);
    mobs = (Vector<Mob>) loadData.get(1);
    towers = (Vector<Tower>) loadData.get(2);
    deathCount = (Integer) loadData.get(3);
    thePlayer = (Player) loadData.get(4);
    theMap = (Map) loadData.get(5);
    
    theGame.setMobs(mobs);
    theGame.setTowers(towers);
    theGame.setProjectiles(projectiles);
    theGame.setDeathCount(deathCount);
    theGame.setPlayer(thePlayer);
    theGame.setMap(theMap);
    
    return theGame;
  }
  
  private static void writeData(String saveName) {
    saveData = new Vector();
    
    saveData.add(theGame);
    saveData.add(mobs);
    saveData.add(towers);
    saveData.add(deathCount);
    saveData.add(thePlayer);
    saveData.add(theMap);
    
    try {
      FileOutputStream fileOutput = new FileOutputStream(saveName);
      ObjectOutputStream out = new ObjectOutputStream(fileOutput);
      out.writeObject(saveData);
      out.close();   
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  private static void readData() {
    try {
      FileInputStream fileOutput = new FileInputStream("THE_IMMORTAL_SPRITESHEET");
      ObjectInputStream in = new ObjectInputStream(fileOutput);
      
      loadData = (Vector) in.readObject();
      
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void saveMap() {
    theMap = theGame.getMap();
  }

  private static void savePlayer() {
    thePlayer = theGame.getPlayer();
  }

  private static void saveMobsKilled() {
    deathCount = theGame.getKillCount();
    
  }

  private static void saveTowers() {
    towers = new Vector(theGame.getTowers());
    
  }

  private static void saveProjectiles() {
    projectiles = new Vector(theGame.getProjectiles());
    
  }

  private static void saveMobs() {
    mobs = new Vector(theGame.getMobs());
  }

  private static void loadMobs(TowerGame output) {
    Vector<Mob> mobs = (Vector<Mob>) loadData.get(0);
    for (Mob m: mobs) {
      output.add(m);
    }
  }

}
