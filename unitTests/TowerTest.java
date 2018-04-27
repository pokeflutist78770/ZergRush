package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.DepotTower;
import model.Hydralisk;
import model.Marine;
import model.MarineTower;
import model.RangeAttribute;
import model.TankTower;
import model.TowerGame;
import model.Ultralisk;
import model.Wraith;
import model.Zealot;
import model.Zergling;

public class TowerTest {
  
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
  
  private void addMobsToGame() {
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
  
  // Towers for testing
  DepotTower depotTowerTest = new DepotTower(path.get(0), tg);
  MarineTower marineTowerTest = new MarineTower(path.get(0), tg);
  TankTower tankTowerTest = new TankTower(path.get(0), tg);
  
  private void addTowersToGame() {
    tg.add(depotTowerTest);
    tg.add(marineTowerTest);
    tg.add(tankTowerTest);
  }

  @Test
  public void testShoot() {
    // TODO
  }

  @Test
  public void testGetClosestMob() {
    // TODO
  }

  @Test
  public void testIsDone() {
    assertFalse(depotTowerTest.isDone());
  }

  @Test
  public void testUpdate() {
    addMobsToGame();
    
    for (int i = 0; i < numberOfTries; i++) {
      marineTowerTest.update();
      depotTowerTest.update();
      tankTowerTest.update();
    }
  }

  @Test
  public void testGetImageFilePath() {
    String imgFP = new String (depotTowerTest.getImageFilePath());
    depotTowerTest.setImageFilePath(".");
    assertFalse(depotTowerTest.getImageFilePath().equals(imgFP));
    assertTrue(depotTowerTest.getImageFilePath().equals("."));
  }

  @Test
  public void testSetImageFilePath() {
    // TODO
  }

  @Test
  public void testGetImage() {
    // TODO
  }

  @Test
  public void testGetXY() {
    assertTrue(depotTowerTest.getX() == path.get(0).getX());
    assertTrue(depotTowerTest.getY() == path.get(0).getY());
  }

  @Test
  public void testGetY() {
    // TODO
  }

  @Test
  public void testGetRange() {
    assertTrue(depotTowerTest.getRange() == RangeAttribute.DEMO_RANGE.toDouble());
  }

  @Test
  public void testGetCost() {
    assertTrue(marineTowerTest.getCost() == 100);
    assertTrue(depotTowerTest.getCost() == 150);
    assertTrue(tankTowerTest.getCost() == 350);
  }

}
