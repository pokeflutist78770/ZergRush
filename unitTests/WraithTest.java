package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.TowerGame;
import model.Wraith;
import model.Zealot;

public class WraithTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran");
    Wraith testWraith = new Wraith(tg.getMap().getPaths().get(1), tg, false); 
  }

}
