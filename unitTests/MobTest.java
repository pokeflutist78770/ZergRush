package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import controller.ControllerMain;
import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.ElementalAttribute;
import model.Hydralisk;
import model.Marine;
import model.SpeedAttribute;
import model.TowerGame;
import model.Ultralisk;
import model.Wraith;
import model.Zealot;
import model.Zergling;

public class MobTest {
  
  int numberOfTries = 1000;

  TowerGame tg = new TowerGame("Easy", "Terran");
  Vector<Point> path = tg.getMap().getPaths().get(1);
  
    
  Archon testArchon = new Archon(path, tg, false);  
  BattleCruiser testBattleCruiser = new BattleCruiser(path, tg, false);
  DarkTemplar testDarkTemplar = new DarkTemplar(path, tg, false);
  Hydralisk testHydralisk = new Hydralisk(path, tg, false);
  Marine testMarine = new Marine(path, tg, false);
  Ultralisk testUltralisk = new Ultralisk(path, tg, false);  
  Wraith testWraith = new Wraith(path, tg, false);
  Zealot testZealot = new Zealot(path, tg, false);
  Zergling testZergling = new Zergling(path, tg, false);

  @Test
  public void testUpdate() {
    Point start = new Point(testZergling.getCurrentLocation());
    for (int i = 0; i < numberOfTries; i++) {
      testZergling.update();
    }
    assertFalse(testZergling.getCurrentLocation().equals(start));

    Point finish = new Point(testZergling.getCurrentLocation());
    assertTrue(testZergling.getCurrentLocation().equals(finish));
  }

  @Test
  public void testIsDone() {
    assertFalse(testZergling.isDone());
    testZergling.takeDamage(testZergling.hp-1, ElementalAttribute.NONE);
    assertFalse(testZergling.isDone());
    testZergling.takeDamage(.5, ElementalAttribute.NONE);
    assertFalse(testZergling.isDone());
    testZergling.takeDamage(.5, ElementalAttribute.NONE);
    assertTrue(testZergling.isDone());
  }

  @Test
  public void testTakeDamage() {
    assertTrue(testZealot.hp == (new Zealot(path, tg, false)).hp);
    testZealot.takeDamage(1, ElementalAttribute.NONE);
    assertFalse(testZealot.hp == (new Zealot(path, tg, false)).hp);
    assertFalse(testZealot.hp+1 == (new Zealot(path, tg, false)).hp);
  }

  @Test
  public void testGetX() {
    for (int i = 0; i < numberOfTries; i++) {
      Marine nextMarine = new Marine(path, tg, false);
      assertTrue(Math.abs(nextMarine.getX() - path.get(0).getX()) < 2* ControllerMain.TILE_SIZE);
    }
  }

  @Test
  public void testGetY() {
    for (int i = 0; i < numberOfTries; i++) {
      Marine nextMarine = new Marine(path, tg, false);
      assertTrue(Math.abs(nextMarine.getY() - path.get(0).getY()) < 2* ControllerMain.TILE_SIZE);
    }
  }

  @Test
  public void testGetRadius() {
    assertTrue(testZealot.getRadius() == ControllerMain.TILE_SIZE/3);
  }

  @Test
  public void testSetImageFilePath() {
    String initial = new String(testZealot.getImageFilePath());
    testZealot.setImageFilePath(".");  
    assertTrue(testZealot.getImageFilePath().equals("."));
    assertFalse(testZealot.getImageFilePath().equals(initial));
  }

  @Test
  public void testStep() {
    int initial = testZealot.getStepCount();
    testZealot.step();
    assertTrue(initial + 1 == testZealot.getStepCount()); 
  }

  @Test
  public void testGetName() {
    assertTrue(testZealot.getName().contains("Zealot"));
  }

  @Test
  public void testGetCashPayout() {
    assertTrue(testZealot.getCashPayout() == 12);  
  }

  @Test
  public void testSetSpeed() {
    SpeedAttribute initial = testZealot.getSpeed();
    testZealot.setSpeed(SpeedAttribute.SLOW);
    assertTrue(testZealot.getSpeed() == SpeedAttribute.SLOW);
    assertTrue(testZealot.getSpeed() != initial);
  }

}
