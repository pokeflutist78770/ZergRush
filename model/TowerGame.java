package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.Observable;

import controller.ControllerMain;
import views.MenuView;

/**
 * A TowerGame is a model of the game. 
 * It (should) handle all the business logic of the game. 
 * It should be initialized when a player clicks 'start' from the main menu, or when a player loads a game.
 * @author J. David Taylor
 *
 */
public class TowerGame extends Observable implements Serializable {
  
  private Player thePlayer;
  private Map theMap;
  private boolean gameOver;
  private boolean paused;
  private HashSet<Mob> mobs;
  private HashSet<Projectile> projectiles;
  private HashSet<Tower> towers;

  private int mobsKilled;
  
  // Cash required to win at each difficulty
  final public static int easyCash = 1000;
  final public static int medCash = 100000;
  final public static int hardCash = 1000000;
  final public static int dankCash = 100000000;
  
  private String backgroundImageFilePath;
  
  /**
   * @param difficulty The difficulty is a string, either "Easy", "Medium", "Hard", or "Meme"
   * @param mapSelection The mapSelection is a string, either "Terran", "Protoss", or "Zerg"
   * @throws ClassNotFoundException 
   */
  
  public TowerGame(String difficulty, String mapSelection) {
    
    thePlayer = new Player();
    try {
      theMap = createMap(mapSelection, difficulty);
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
    mainLoop.setDaemon(true);
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
    updateTowers();
    updateMap();   
    updateMobs();
    updateProjectiles();
    
    String difficulty = MenuView.getModeSelection();
    
    if(thePlayer.getHP()<=0) {
      ControllerMain.dealWithDeadPlayer(true);
    }
    
    // Win Conditions
    if(difficulty.equals("Easy") && thePlayer.getCash() >= easyCash)
    	ControllerMain.dealWithDeadPlayer(false);
    else if (difficulty.equals("Medium") && thePlayer.getCash() >= medCash)
    	ControllerMain.dealWithDeadPlayer(false);
    else if (difficulty.equals("Hard") && thePlayer.getCash() >= hardCash)
    	ControllerMain.dealWithDeadPlayer(false);
    else if (difficulty.equals("Fun") && thePlayer.getCash() >= dankCash)
    	ControllerMain.dealWithDeadPlayer(false);
  }


  /**
   * Update all the towers. Any towers that are done are removed from the game.
   */
  private synchronized void updateTowers() {
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
  private synchronized void updateProjectiles() {
    for (Iterator<Projectile> itr = (new HashSet<Projectile>(projectiles)).iterator(); itr.hasNext(); ) {
      Projectile p = itr.next();
      if (p.isDone()) {
        projectiles.remove(p);
      } else {
        p.update();
      }
    }
  }



  /**
   * Update all the mobs. If a mob is done, play its death sound, 
   * increment the kill count, add cash to the player, and remove the mob from the game.
   */
  private synchronized void updateMobs() {
    for (Iterator<Mob> itr = mobs.iterator(); itr.hasNext(); ) {
      Mob m = itr.next();
      if (m.isDone()) {
        ControllerMain.soundEffects.get(m.getDeathSoundStr()).play();
        mobsKilled++;   
        
        thePlayer.addCash(m.getCashPayout());
        itr.remove();
      } else {  
        m.update();
      }
    }
  }


  /**
   * Update the state of the map.
   */
  private synchronized void updateMap() {
    theMap.update();
  }

  /**
   * Is the game paused?
   * @return True, if the game is on pause. False, otherwise.
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * Pause the game.
   */
  public void pause() {
    paused = true;
  }

  /**
   * Unpause the game.
   */
  public void unPause() {
    paused = false;
  }

  /**
   * Get the filepath of the background image
   * @return The desired filepath.
   */
  public String getBackgroundImageFP() {
    return backgroundImageFilePath;
  }

  /**
   * Get a pointer to the player.
   * @return The player.
   */
  public Player getPlayer() {
    return thePlayer;
  }
  
  /**
   * Get a pointer to a Vector of all current mobs.
   * @return A Vector of the current mobs.
   */
  public synchronized Vector<Mob> getMobs() {
    return new Vector<Mob>(mobs);
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
   * Get a pointer to a Vector of all current projectiles.
   * @return A Vector of the current projectiles.
   */
  public synchronized Vector<Projectile> getProjectiles() {
    return new Vector<Projectile>(projectiles);
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
   * Get a pointer to a Vector of all current towers.
   * @return A Vector of the current towers.
   */
  public synchronized Vector<Tower> getTowers() {
    return new Vector<Tower>(towers);
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
  public void addCash(int i) {
    thePlayer.addCash(i);
  }
  
  /**
   * Get the current cash of the player.
   * @return The current cash of the player.
   */
  public double getCash() {
    return thePlayer.getCash();
  }
  
  /**
   * Get the number of mobs killed.
   * @return The number of mobs killed in the current game.
   */
  public int getKillCount() {
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


  public void setMobs(Vector<Mob> inputMobs) {
    mobs = new HashSet(inputMobs);   
  }


  public void setTowers(Vector<Tower> inputTowers) {
    towers = new HashSet(inputTowers);
  }  


  public void setProjectiles(Vector<Projectile> inputProjectiles) {
    projectiles = new HashSet(inputProjectiles);     
  }


  public void setDeathCount(Integer deathCount) {
    mobsKilled = deathCount;
  }


  public void setPlayer(Player inputPlayer) {
    thePlayer = inputPlayer;
  }


  public void setMap(Map inputMap) {
    theMap = inputMap;
  }
}
