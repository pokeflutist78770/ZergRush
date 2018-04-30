package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.TowerGame;
import model.Zealot;
import model.Zergling;

public class ZealotTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    Zealot testZealot = new Zealot(tg.getMap().getPaths().get(1), tg, false); 
  }

}
