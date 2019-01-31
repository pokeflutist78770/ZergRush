package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DarkTemplar;
import model.Hydralisk;
import model.TowerGame;

public class HydraliskTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    Hydralisk testHydralisk = new Hydralisk(tg.getMap().getPaths().get(1), tg, false); 
  }

}
