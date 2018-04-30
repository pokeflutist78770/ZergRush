package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Archon;
import model.BattleCruiser;
import model.TowerGame;

public class BattleCruiserTest {

  @Test
  public void test() {
    TowerGame tg = new TowerGame("Easy", "Terran"); 
    BattleCruiser testBattleCruiser = new BattleCruiser(tg.getMap().getPaths().get(1), tg, false);
  }

}
