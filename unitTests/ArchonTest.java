package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Archon;
import model.Map;
import model.TowerGame;

public class ArchonTest {

  @Test
  public void testArchon() {  
    TowerGame tg = new TowerGame("Easy", "Terran");
    Archon testArchon = new Archon(tg.getMap().getPaths().get(1), tg, false);
  }

}
