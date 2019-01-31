package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.TowerGame;
import model.Ultralisk;
import model.Zergling;

public class ZerglingTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    Zergling testZergling = new Zergling(tg.getMap().getPaths().get(1), tg, false); 
  }

}
