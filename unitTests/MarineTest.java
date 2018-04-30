package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Marine;
import model.TowerGame;

public class MarineTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    Marine testMarine = new Marine(tg.getMap().getPaths().get(1), tg, false);
  }

}
