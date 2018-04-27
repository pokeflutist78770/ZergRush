package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.DemoProjectile;
import model.Hydralisk;
import model.Marine;
import model.Projectile;
import model.SpeedAttribute;
import model.TowerGame;
import model.Ultralisk;
import model.Wraith;
import model.Zealot;
import model.Zergling;

public class ProjectileTest {
  
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

  Projectile demoProj = new DemoProjectile(new Point(0,1), testArchon, tg);
  
  private void objectsToGame() {
    tg.add(testArchon);
    tg.add(testBattleCruiser);
    tg.add(testDarkTemplar);
    tg.add(testHydralisk);
    tg.add(testMarine);
    tg.add(testUltralisk);
    tg.add(testWraith);
    tg.add(testZealot);
    tg.add(testZergling);
    tg.add(demoProj);
  }
  
  

  @Test
  public void testProjectile() {
    // TODO
  }

  @Test
  public void testTerminate() {
    double hp = testArchon.hp;
    for (int i = 0; i < numberOfTries; i++) {
      demoProj.update();
    }
    assertFalse(hp == testArchon.hp);
    assertTrue(demoProj.isDone());
  }

  @Test
  public void testUpdateLocation() {
    // TODO
  }

  @Test
  public void testUpdate() {
    // TODO
  }

  @Test
  public void testIsDone() {
    // TODO
  }

  @Test
  public void testGetDirectionVector() {
    // TODO
  }

  @Test
  public void testGetDirectionAngle() {
    // TODO
  }

  @Test
  public void testGetMob() {
    assertTrue(demoProj.getMob().equals(testArchon));
  }

  @Test
  public void testSetMob() {
    // TODO
  }

  @Test
  public void testGetImageFilePath() {
    String imgFP = new String(demoProj.getImageFilePath());
    demoProj.setImageFilePath(".");
    assertFalse(demoProj.getImageFilePath().equals(imgFP));
    assertTrue(demoProj.getImageFilePath().equals("."));
  }

  @Test
  public void testSetImageFilePath() {
    // TODO
  }

  @Test
  public void testSetSpeed() {
    SpeedAttribute speed = demoProj.getSpeed();
    demoProj.setSpeed(SpeedAttribute.SLOW);
    assertFalse(demoProj.getSpeed().equals(speed));
    assertTrue(demoProj.getSpeed().equals(SpeedAttribute.SLOW));
  }

  @Test
  public void testGetImage() {
    // TODO
  }

  @Test
  public void testGetXY() {
    assertTrue(demoProj.getX() == 0);
    assertTrue(demoProj.getY() == 1);
  }

  @Test
  public void testGetY() {
    // TODO
  }

}
