package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.DemoProjectile;
import model.DepotTower;
import model.Hydralisk;
import model.Marine;
import model.MarineTower;
import model.Projectile;
import model.TankTower;
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
  List<Point> path = tg.getMap().getPaths().get(1);
  
  // Mobs for testing
  Archon testArchon = new Archon(path, tg);
  BattleCruiser testBattleCruiser = new BattleCruiser(path, tg);
  DarkTemplar testDarkTemplar = new DarkTemplar(path, tg);
  Hydralisk testHydralisk = new Hydralisk(path, tg);
  Marine testMarine = new Marine(path, tg);
  Ultralisk testUltralisk = new Ultralisk(path, tg);
  Wraith testWraith = new Wraith(path, tg);
  Zealot testZealot = new Zealot(path, tg);
  Zergling testZergling = new Zergling(path, tg);

  // Towers for testing
  DepotTower depotTowerTest = new DepotTower(path.get(0), tg);
  MarineTower marineTowerTest = new MarineTower(path.get(0), tg);
  TankTower tankTowerTest = new TankTower(path.get(0), tg);

  // Projectile for testing
  Projectile demoProj = new DemoProjectile(new Point(0,1), testArchon, tg);
  
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
  public void testTowerGame() {
    // TODO
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
  public void testIsPaused() {
    // TODO
  }

  @Test
  public void testPause() {
    // TODO
  }

  @Test
  public void testUnPause() {
    // TODO
  }

  @Test
  public void testGetBackgroundImageFP() {
    assertTrue(tg.getBackgroundImageFP().equals("file:assets/images/map/protoss_map.jpg"));
  }

  @Test
  public void testGetPlayer() {
    // TODO
  }

  @Test
  public void testGetMobs() {
    // TODO
  }

  @Test
  public void testAddMob() {
    // TODO
  }

  @Test
  public void testRemoveMob() {
    // TODO
  }

  @Test
  public void testGetProjectiles() {
    // TODO
  }

  @Test
  public void testAddProjectile() {
    // TODO
  }

  @Test
  public void testRemoveProjectile() {
    // TODO
  }

  @Test
  public void testGetTowers() {
    // TODO 
  }

  @Test
  public void testAddTower() {
    // TODO
  }

  @Test
  public void testRemoveTower() {
    // TODO
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
  public void testGetCash() {
    // TODO
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
  public void testGetMap() {
    // TODO
  }

}
