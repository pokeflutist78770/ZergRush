package controller;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import model.Mob;
import model.TowerGame;
import model.*;
import model.TerranMap;

public class PersistenceMasterTest {

  @Test
  public void test() throws InterruptedException {
    TowerGame tg = new TowerGame("Easy", "Zerg");
    tg.start();
    tg.unPause();   
    Thread.sleep(1000);
    tg.pause();
    PersistenceMaster.saveGame(tg);
    System.out.println(tg.getMobs().size());
    assertTrue(tg.getMobs().size() == 9);
    for (Iterator<Mob> itr = tg.getMobs().iterator(); itr.hasNext(); ) {
      Mob m = itr.next();
      tg.remove(m);
    }
    System.out.println(tg.getMobs().size());
    assertTrue(tg.getMobs().size() == 0);
    
    tg = PersistenceMaster.loadGame();
    System.out.println(tg.getMobs().size());
    System.out.println(tg.getMap().backgroundImageFilePath);
    //assertTrue(tg.getMobs().size() == 9);
  }

}
