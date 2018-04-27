package model;

import java.awt.Point; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import controller.ControllerMain;

/**
 * A TowerGame is a model of the game. 
 * It (should) handle all the business logic of the game. 
 * It should be initialized when a player clicks 'start' from the main menu, or when a player loads a game.
 * @author J. David Taylor
 *
 */
public class TowerGame extends Observable {
  
  private Player thePlayer;
  private Map theMap;
  private boolean gameOver;
  private boolean paused;
  private HashSet<Mob> mobs;
  private HashSet<Projectile> projectiles;
  private HashSet<Tower> towers;

  private int mobsKilled;
  
  private String backgroundImageFilePath;
  
  /**
   * @param difficulty The difficulty is a string, either "Easy", "Medium", "Hard", or "Meme"
   * @param mapSelection The mapSelection is a string, either "Terran", "Protoss", or "Zerg"
   * @throws ClassNotFoundException 
   */
  
  public TowerGame(String difficulty, String mapSelection) {
    
    thePlayer = new Player();
    try {
      theMap = createMap(mapSelection,difficulty);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    backgroundImageFilePath = theMap.backgroundImageFilePath;
    gameOver = false;
    mobs = new HashSet<Mob>();
    projectiles = new HashSet<Projectile>();
    towers = new HashSet<Tower>();
    
    mobsKilled = 0;
    
    gameOver = false;
    paused = true;
  }
  

/**
 * Creates a map with of the given type with the given difficulty.
 * @param mapSelection The type of map to create. "Terran", "Protoss", or "Zerg"
 * @param difficulty The difficulty of map to create. "Easy", "Medium", "Hard", or "Meme"
 * @return
 * @throws ClassNotFoundException 
 */
  private Map createMap(String mapSelection, String difficulty) throws ClassNotFoundException {
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
  
/**
 * This method starts the game. The game is a loop that can be paused, unpaused, or ended.
 */
  public void start() {
    Thread mainLoop = new Thread(new TheLoop(this));
    mainLoop.start();
  }
  
  /**
   * TheLoop is the main loop of the game logic.
   * @author J. David Taylor
   *
   */
  private class TheLoop implements Runnable {
    
    private TowerGame tg;
    
    private TheLoop(TowerGame tGame) {
      tg = tGame;
    }

    @Override
    public void run() {
      
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

  /**
   * Tells if the game is still going.
   * @return True, if the game is not over. False, otherwise.
   */
  public boolean gameInProgress() {
    return !gameOver;
  }
  
  public void gameOver() {
    gameOver = true;
  }

  /**
   * This method is a single update step of the game state.
   */
  private synchronized void updateGameState() {
    updateMap();
    updateMobs();
    updateProjectiles();
    updateTowers();
    
    if(thePlayer.getHP()<=0) {
      ControllerMain.dealWithDeadPlayer();
    }
  }


  /**
   * Update all the towers. Any towers that are done are removed from the game.
   */
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


  /**
   * Update all the projectiles. Any projectile that is done is removed from the game.
   */
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
   * Update all the mobs. If a mob is done, play its death sound, 
   * increment the kill count, add cash to the player, and remove the mob from the game.
   */
  private void updateMobs() {
    for (Iterator<Mob> itr = mobs.iterator(); itr.hasNext(); ) {
      Mob m = itr.next();
      if (m.isDone()) {
        ControllerMain.soundEffects.get(m.getDeathSoundStr()).play();
        mobsKilled++;
        thePlayer.addCash(50);
        itr.remove();
      } else {
        m.update();
      }
    }
  }


  /**
   * Update the state of the map.
   */
  private void updateMap() {
    theMap.update();
  }

  /**
   * Is the game paused?
   * @return True, if the game is on pause. False, otherwise.
   */
  public synchronized boolean isPaused() {
    return paused;
  }

  /**
   * Pause the game.
   */
  public synchronized void pause() {
    paused = true;
  }

  /**
   * Unpause the game.
   */
  public synchronized void unPause() {
    paused = false;
  }

  /**
   * Get the filepath of the background image
   * @return The desired filepath.
   */
  public synchronized String getBackgroundImageFP() {
    return backgroundImageFilePath;
  }

  /**
   * Get a pointer to the player.
   * @return The player.
   */
  public synchronized Player getPlayer() {
    return thePlayer;
  }
  
  /**
   * Get a pointer to a list of all current mobs.
   * @return A list of the current mobs.
   */
  public synchronized List<Mob> getMobs() {
    return new ArrayList<Mob>(mobs);
  }
  
  /**
   * Add a mob to the game.
   * @param m The mob to add.
   */
  public synchronized void add(Mob m) {
    mobs.add(m);
  }
  
  /**
   * Remove a mob from the game
   * @param m The mob to remove.
   */
  public synchronized void remove(Mob m) {
    mobs.remove(m);
  }

  
  /**
   * Get a pointer to a list of all current projectiles.
   * @return A list of the current projectiles.
   */
  public synchronized List<Projectile> getProjectiles() {
    return new ArrayList<Projectile>(projectiles);
  }
  
  /**
   * Add a projectile to the game.
   * @param p The projectile to add.
   */
  public synchronized void add(Projectile p) {
    projectiles.add(p);
  }
  
  /**
   * Remove a projectile from the game.
   * @param p The projectile to remove.
   */
  public synchronized void remove(Projectile p) {
    projectiles.remove(p);
  }

  
  /**
   * Get a pointer to a list of all current towers.
   * @return A list of the current towers.
   */
  public synchronized List<Tower> getTowers() {
    return new ArrayList<Tower>(towers);
  }
  
  /**
   * Add a tower to the game.
   * @param t The tower to add.
   */
  public synchronized void add(Tower t) {
    towers.add(t);
  }
  
  /** 
   * Remove a tower from the game.
   * @param t The tower to remove.
   */
  public synchronized void remove(Tower t) {
    towers.remove(t);
  }

  /**
   * Increase the player's cash.
   * @param i The amount to increase the player's cash by.
   */
  public synchronized void addCash(int i) {
    thePlayer.addCash(i);
  }
  
  /**
   * Get the current cash of the player.
   * @return The current cash of the player.
   */
  public synchronized double getCash() {
    return thePlayer.getCash();
  }
  
  /**
   * Get the number of mobs killed.
   * @return The number of mobs killed in the current game.
   */
  public synchronized int getKillCount() {
    return mobsKilled;
  }
  
  /**
   * Reduce the player's cash.
   * @param cost The amount to decrement the players cash by.
   */
  public void decrementCash(double cost) {
    thePlayer.decrementCash(cost);
    
  }


  public Map getMap() {
    return theMap;
  }
  
}
