package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;

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
  Archon testArchon = new Archon(tg.getMap().getPaths().get(1), tg);
  BattleCruiser testBattleCruiser = new BattleCruiser(tg.getMap().getPaths().get(1), tg);
  DarkTemplar testDarkTemplar = new DarkTemplar(tg.getMap().getPaths().get(1), tg);
  Hydralisk testHydralisk = new Hydralisk(tg.getMap().getPaths().get(1), tg);
  Marine testMarine = new Marine(tg.getMap().getPaths().get(1), tg);
  Ultralisk testUltralisk = new Ultralisk(tg.getMap().getPaths().get(1), tg);
  Wraith testWraith = new Wraith(tg.getMap().getPaths().get(1), tg);
  Zealot testZealot = new Zealot(tg.getMap().getPaths().get(1), tg);
  Zergling testZergling = new Zergling(tg.getMap().getPaths().get(1), tg);

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
    assertTrue(testZealot.hp == (new Zealot(tg.getMap().getPaths().get(1), tg)).hp);
    testZealot.takeDamage(1, ElementalAttribute.NONE);
    assertFalse(testZealot.hp == (new Zealot(tg.getMap().getPaths().get(1), tg)).hp);
    assertFalse(testZealot.hp+1 == (new Zealot(tg.getMap().getPaths().get(1), tg)).hp);
  }

  @Test
  public void testGetX() {
    for (int i = 0; i < numberOfTries; i++) {
      Marine nextMarine = new Marine(tg.getMap().getPaths().get(1), tg);
      assertTrue(Math.abs(nextMarine.getX() - tg.getMap().getPaths().get(1).get(0).getX()) < 2* ControllerMain.TILE_SIZE);
    }
  }

  @Test
  public void testGetY() {
    for (int i = 0; i < numberOfTries; i++) {
      Marine nextMarine = new Marine(tg.getMap().getPaths().get(1), tg);
      assertTrue(Math.abs(nextMarine.getY() - tg.getMap().getPaths().get(1).get(0).getY()) < 2* ControllerMain.TILE_SIZE);
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
    assertTrue(testZealot.getCashPayout() == 0);
  }

  @Test
  public void testSetSpeed() {
    SpeedAttribute initial = testZealot.getSpeed();
    testZealot.setSpeed(SpeedAttribute.SLOW);
    assertTrue(testZealot.getSpeed() == SpeedAttribute.SLOW);
    assertTrue(testZealot.getSpeed() != initial);
  }

}
