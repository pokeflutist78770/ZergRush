package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.BattleCruiser;
import model.DarkTemplar;
import model.TowerGame;

public class DarkTemplarTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    DarkTemplar testDarkTemplar = new DarkTemplar(tg.getMap().getPaths().get(1), tg, false); 
  }

}
