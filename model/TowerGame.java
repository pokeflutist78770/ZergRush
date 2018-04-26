package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import controller.ControllerMain;
import model.Maps.Map;
import model.Maps.ProtossMap;
import model.Maps.TerranMap;
import model.Maps.ZergMap;
import model.Mobs.Mob;
import model.Towers.Tower;
import views.MapView;

/**
 * A TowerGame is a model of the game. 
 * It (should) handle all the business logic of the game. 
 * It should be initialized when a player clicks 'start' from the main menu, or when a player loads a game.
 * @author J. David Taylor
 *
 */

//TODO: make sure that everything is commented
public class TowerGame extends Observable {
  
  private Player thePlayer;
  private Map theMap;
  private boolean gameOver;
  private boolean paused;
  private HashSet<Mob> mobs;
  private HashSet<Projectile> projectiles;
  private HashSet<Tower> towers;

  private int deadMobs;
  
  private String backgroundImageFilePath;
  
  /**
   * The difficulty is a string, either "Easy", "Medium", "Hard", or "Meme"
   * The mapSelection is a string, either "Terran", "Protoss", or "Zerg"
   * @param difficulty
   * @param mapSelection
   */
  
  public TowerGame(String difficulty, String mapSelection) {
    
    thePlayer = new Player();
    theMap = createMap(mapSelection,difficulty);
    backgroundImageFilePath = theMap.backgroundImageFilePath;
    gameOver = false;
    mobs = new HashSet<Mob>();
    projectiles = new HashSet<Projectile>();
    towers = new HashSet<Tower>();
    
    deadMobs = 0;
    
    gameOver = false;
    paused = true;
  }
  
  
/**
 * Creates a map of type mapSelection with the given difficulty.
 * @param mapSelection
 * @param difficulty
 * @return
 */
  private Map createMap(String mapSelection, String difficulty) {
    if (mapSelection.equals("Protoss")) {
      return new ProtossMap(difficulty, this);
    }
    if (mapSelection.equals("Terran") ) {
      return new TerranMap(difficulty, this);
    }
    if (mapSelection.equals("Zerg")) {
      return new ZergMap(difficulty, this);
    }
    return null;
  }

  public void start() {
    Thread mainLoop = new Thread(new TheLoop(this));
    mainLoop.start();
  }
  
  private class TheLoop implements Runnable {
    
    private TowerGame tg;
    
    private TheLoop(TowerGame tGame) {
      tg = tGame;
    }

    @Override
    public void run() {
      System.out.println("The loop started.");
      while(tg.gameInProgress()) {
        if (!tg.isPaused()) {
          tg.updateGameState();
          setChanged();
          notifyObservers();
        }
        try {
          Thread.sleep(ControllerMain.UPDATE_FREQUENCY);
        } catch (InterruptedException e) {
          System.out.println("The main loop was interrupted.");
          e.printStackTrace();
        }
      }
    }
  }

  private boolean gameInProgress() {
    return !gameOver;
  }

  private void updateGameState() {
    updateMap();
    updateMobs();
    updateProjectiles();
    updateTowers();
  }


  private void updateTowers() {
    for (Iterator<Tower> itr = towers.iterator(); itr.hasNext(); ) {
      Tower t = itr.next();
      if (t.isDone()) {
        itr.remove();
      } else {
        t.update();
      }
    }
  }


  private void updateProjectiles() {
    for (Iterator<Projectile> itr = projectiles.iterator(); itr.hasNext(); ) {
      Projectile p = itr.next();
      if (p.isDone()) {
        itr.remove();
      } else {
        p.update();
      }
    }
  }



  /**
   * If a mob's HP reaches zero, it's death sound clip will trigger, and the
   * player's score will be incremented. After that point, the mob is removed from
   * the current state of the game.
   */
  private void updateMobs() {
    for (Iterator<Mob> itr = mobs.iterator(); itr.hasNext(); ) {
      Mob m = itr.next();
      if (m.isDone()) {
        ControllerMain.soundEffects.get(m.getDeathSoundStr()).play();
        deadMobs++;
        thePlayer.addCash(50);
        itr.remove();
      } else {
        m.update();
      }
    }
  }


  private void updateMap() {
    theMap.update();
  }

  public boolean isPaused() {
    return paused;
  }

  public void pause() {
    paused = true;
  }

  public void unPause() {
    paused = false;
  }

  public String getBackgroundImageFP() {
    return backgroundImageFilePath;
  }

  public Player getPlayer() {
    return thePlayer;
  }
  
  public List<Mob> getMobs() {
    return new ArrayList<Mob>(mobs);
  }
  
  public void add(Mob m) {
    mobs.add(m);
  }
  
  public void remove(Mob m) {
    mobs.remove(m);
  }
  
  public List<Projectile> getProjectiles() {
    return new ArrayList<Projectile>(projectiles);
  }
  
  public void add(Projectile p) {
    projectiles.add(p);
  }
  
  public void remove(Projectile p) {
    mobs.remove(p);
  }
  
  public List<Tower> getTowers() {
    return new ArrayList<Tower>(towers);
  }
  
  public void add(Tower t) {
    towers.add(t);
  }
  
  public void remove(Tower t) {
    mobs.remove(t);
  }

  public void addCash(int i) {
    thePlayer.addCash(i);
  }
  
  public double getCash() {
    return thePlayer.getCash();
  }
  
  public int getKillCount() {
    return deadMobs;
  }


  public void decrementCash(double cost) {
    thePlayer.decrementCash(cost);
    
  }
  
}
