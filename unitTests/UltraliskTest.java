package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Marine;
import model.TowerGame;
import model.Ultralisk;

public class UltraliskTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");  
    Ultralisk testUltralisk = new Ultralisk(tg.getMap().getPaths().get(1), tg, false);
  }

}
