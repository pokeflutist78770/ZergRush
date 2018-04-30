package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.FireProjectile;
import model.NormalProjectile;
import model.PoisonProjectile;
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

  Projectile demoProj = new NormalProjectile(new Point(0,1), testArchon, tg, false);
  Projectile fireProj=new FireProjectile(new Point(0,1), testBattleCruiser, tg, false);
  Projectile poisonProj=new PoisonProjectile(new Point(0,1), testBattleCruiser, tg, false);
  
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
  public void testTerminate() {
    double hp = testArchon.hp;
    for (int i = 0; i < numberOfTries; i++) {
      demoProj.update(); 
    }
    assertFalse(hp == testArchon.hp);
    assertTrue(demoProj.isDone());  
  }

  @Test  
  public void testFireTerminate() {
    double hp = testBattleCruiser.hp;
    for (int i = 0; i < numberOfTries+2000; i++) {
      fireProj.update();
    } 
     
    assertFalse(hp == testBattleCruiser.hp);
    assertTrue(fireProj.isDone());   
  } 
  
  
  @Test
  public void testPoisonTerminate() {
	  double hp = testBattleCruiser.hp;
	  for (int i = 0; i < numberOfTries+2000; i++) {
	    poisonProj.update();
	  } 
	   
	  assertFalse(hp == testBattleCruiser.hp);
	  assertTrue(poisonProj.isDone());   
  } 
  
  
  @Test
  public void testGetMob() {
    assertTrue(demoProj.getMob().equals(testArchon));
  }

  @Test 
  public void testGetImageFilePath() {
    String imgFP = new String(demoProj.getImageFilePath());
    demoProj.setImageFilePath(".");
    assertFalse(demoProj.getImageFilePath().equals(imgFP));
    assertTrue(demoProj.getImageFilePath().equals("."));
  }

  @Test
  public void testSetSpeed() {
    SpeedAttribute speed = demoProj.getSpeed();
    demoProj.setSpeed(SpeedAttribute.SLOW);
    assertFalse(demoProj.getSpeed().equals(speed));
    assertTrue(demoProj.getSpeed().equals(SpeedAttribute.SLOW));
  }
  
  @Test
  public void testGetXY() {
    assertTrue(demoProj.getX() == 0);
    assertTrue(demoProj.getY() == 1);
  }

}
