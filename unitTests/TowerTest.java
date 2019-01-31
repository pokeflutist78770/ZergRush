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
  DepotTower depotTowerTest = new DepotTower(path.get(0), tg, false);
  MarineTower marineTowerTest = new MarineTower(path.get(0), tg, false);
  TankTower tankTowerTest = new TankTower(path.get(0), tg, false);
  
  private void addTowersToGame() {
    tg.add(depotTowerTest);
    tg.add(marineTowerTest);
    tg.add(tankTowerTest);
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
      marineTowerTest.upgrade();
      depotTowerTest.update();
      depotTowerTest.upgrade();
      tankTowerTest.update();
      tankTowerTest.upgrade();
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
  public void testGetXY() {
    assertTrue(depotTowerTest.getX() == path.get(0).getX());
    assertTrue(depotTowerTest.getY() == path.get(0).getY());
  }

  @Test
  public void testGetRange() {
    assertTrue(depotTowerTest.getRange() == RangeAttribute.SMALL_RANGE.toDouble());
  }

  @Test
  public void testGetCost() {
    assertTrue(marineTowerTest.getCost() == 100);
    assertTrue(depotTowerTest.getCost() == 150);
    assertTrue(tankTowerTest.getCost() == 350);
  }
  
  @Test
  public void testTankUpgrade() {
	  assertTrue(tankTowerTest.getUpgradeCost()==200);
	  tankTowerTest.upgrade();
	  assertTrue(tankTowerTest.getRank()==1);
	  assertTrue(tankTowerTest.getUpgradeCost()==400);
	  
	  tankTowerTest.upgrade();
	  assertTrue(tankTowerTest.getRank()==2);
	  assertTrue(tankTowerTest.isFullyUpgraded());
  }
  
  @Test
  public void testDepotUpgrade() {
	  
	  assertTrue(depotTowerTest.getUpgradeCost()==150);
	  depotTowerTest.upgrade();
	  assertTrue(depotTowerTest.getRank()==1);
	  assertTrue(depotTowerTest.getUpgradeCost()==300);
	  
	  depotTowerTest.upgrade();
	  assertTrue(depotTowerTest.getRank()==2);
	  assertTrue(depotTowerTest.isFullyUpgraded());
  } 
  
  
  @Test 
  public void testMarineUpgrade() {
	  
	  assertTrue(marineTowerTest.getUpgradeCost()==100);
	  marineTowerTest.upgrade();
	  assertTrue(marineTowerTest.getRank()==1);
	  assertTrue(marineTowerTest.getUpgradeCost()==200);
	  
	  marineTowerTest.upgrade();
	  assertTrue(marineTowerTest.getRank()==2);
	  assertTrue(marineTowerTest.isFullyUpgraded());
  } 

}
