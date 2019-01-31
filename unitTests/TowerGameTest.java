package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.DepotTower;
import model.Hydralisk;
import model.Map;
import model.Marine;
import model.MarineTower;
import model.Mob;
import model.NormalProjectile;
import model.Player;
import model.Projectile;
import model.ProtossMap;
import model.SiegeProjectile;
import model.TankTower;
import model.Tower;
import model.TowerGame;
import model.Ultralisk;
import model.Wraith;
import model.Zealot;
import model.Zergling;

/**
 * Tests the TowerGame class.
 * The 5 second wait is deliberate. It allows the game to develop to test features.
 * @author J. David Taylor
 *
 */

public class TowerGameTest {  
  
  int numberOfTries = 1000;

  TowerGame tg = new TowerGame("Medium", "Protoss");
  Vector<Point> path = tg.getMap().getPaths().get(1);
  
  // Mobs for testing
  Archon testArchon = new Archon(path, tg, false);
  BattleCruiser testBattleCruiser = new BattleCruiser(path, tg, false);
  DarkTemplar testDarkTemplar = new DarkTemplar(path, tg, false);
  Hydralisk testHydralisk = new Hydralisk(path, tg, false);
  Marine testMarine = new Marine(path, tg, false);
  Ultralisk testUltralisk = new Ultralisk(path, tg, false);
  Wraith testWraith = new Wraith(path, tg, false);
  Zealot testZealot = new Zealot(path, tg, false);
  Zergling testZergling = new Zergling(path, tg, false);  

  // Towers for testing
  DepotTower depotTowerTest = new DepotTower(path.get(0), tg, false);
  MarineTower marineTowerTest = new MarineTower(path.get(0), tg, false);
  TankTower tankTowerTest = new TankTower(path.get(0), tg, false);

  // Projectile for testing
  Projectile demoProj = new NormalProjectile(new Point(0,1), testArchon, tg, false);
  
  private void addMobsToGAme() {
    tg.add(testArchon);
    tg.add(testBattleCruiser);
    tg.add(testDarkTemplar);
    tg.add(testHydralisk);
    tg.add(testMarine);
    tg.add(testUltralisk);
    tg.add(testWraith);
    tg.add(testZealot);
    tg.add(testZergling);
  }
  
  private void addProjectilesToGame() {
    tg.add(demoProj);
  }
  
  private void addTowersToGame() {
    tg.add(depotTowerTest);
    tg.add(marineTowerTest);
    tg.add(tankTowerTest);
  }
  
  @Test
  public void testAddRemoveGet() {
    
    // With respect to mobs.
    assertTrue(tg.getMobs().size() == 0);
    tg.add(testArchon);
    assertTrue(tg.getMobs().size() == 1);
    tg.remove(testArchon);
    assertTrue(tg.getMobs().size() == 0);
    
    // With respect to towers.
    assertTrue(tg.getTowers().size() == 0);
    tg.add(depotTowerTest);
    assertTrue(tg.getTowers().size() == 1);
    tg.remove(depotTowerTest);
    assertTrue(tg.getTowers().size() == 0);
    
    // With respect to projectiles.
    assertTrue(tg.getProjectiles().size() == 0);
    tg.add(demoProj);
    assertTrue(tg.getProjectiles().size() == 1);
    tg.remove(demoProj);
    assertTrue(tg.getProjectiles().size() == 0);   
  }

  @Test
  public void testStart() throws InterruptedException {
    addMobsToGAme();
    addProjectilesToGame();
    assertTrue(tg.getProjectiles().size() == 1);
    tg.start();
    tg.unPause();
    Thread.sleep((long) 4*numberOfTries);
    tg.pause();
    assertTrue(tg.getProjectiles().size() == 0);
    
    addTowersToGame();
    tg.unPause();
    Thread.sleep((long) numberOfTries);
    tg.pause();
  }

  @Test
  public void testGetBackgroundImageFP() {
    assertTrue(tg.getBackgroundImageFP().equals("file:assets/images/map/protoss_map.jpg"));
  }

  @Test
  public void testAddCash() {
    double cash = tg.getCash();
    tg.addCash(200);
    assertTrue(cash + 200 == tg.getCash());
    tg.decrementCash(200);
    assertTrue(tg.getCash() == cash);
  }

  @Test
  public void testGetKillCount() throws InterruptedException {
    assertTrue(tg.getKillCount() == 0);
    // Can't kill a mob in testing. The reason is that the update plays a sound on death.
  }

  @Test
  public void testGameInProgree() {
    assertTrue(tg.gameInProgress());
    tg.gameOver();
    assertFalse(tg.gameInProgress());
  }

  
  @Test
  public void KillCountTest() {
	  tg.setDeathCount(20);
	  assertTrue(tg.getKillCount()==20);
  }
  
  @Test
  public void testSetPlayer() {
	  Player player=new Player();
	  tg.setPlayer(player);
	  assertTrue(tg.getPlayer().equals(player));
  }
  
  @Test 
  public void testMapSet() {
	  Map map=null;
	  
	  try {
		map=new ProtossMap("Easy", tg);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	tg.setMap(map);
	assertTrue(tg.getMap().equals(map));
  }  
  
  @Test
  public void testSetProjetiles() {
	  Vector<Projectile> newProjs=new Vector<Projectile>();
	  newProjs.add((new SiegeProjectile(new Point(0,0), new Point(0,0), tg, false)));
	  
	  tg.setProjectiles(newProjs);
	  
	  assertTrue(tg.getProjectiles().equals(newProjs));
	  assertEquals(tg.getProjectiles().size(), newProjs.size());	  
  }  
  
  @Test
  public void testSetTowers() {
	  Vector<Tower> newTowers=new Vector<Tower>();
	  newTowers.add((new DepotTower(new Point(0,0), tg, true)));
	  
	  tg.setTowers(newTowers);
	  
	  assertTrue(tg.getTowers().equals(newTowers));
	  assertEquals(tg.getTowers().size(), newTowers.size());	   
  }
  
  
  @Test
  public void testSetMobs() {
	  Vector<Mob> newMobs=new Vector<Mob>();
	  Vector<Point> points=new Vector<Point>();  
	  points.add(new Point(0,0));
	  points.add(new Point(1,0));
	  
	  newMobs.add((new Zealot(points, tg, true)));
	  
	  tg.setMobs(newMobs);
	  
	  assertTrue(tg.getMobs().equals(newMobs));
  }
}
