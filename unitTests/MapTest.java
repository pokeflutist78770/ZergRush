package unitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import model.Map;
import model.TerranMap;
import model.TestMap;
import model.TowerGame;
import views.MenuView;

public class MapTest {
  

  TowerGame tg = new TowerGame("Medium", "Protoss");
  
  int numberOfTries = 1000;
  HashMap<List<String>,Map> maps = initializeMaps();

  
  private HashMap<List<String>,Map> initializeMaps() {
    HashMap<List<String>,Map> maps = new HashMap<List<String>,Map>();
    List<String> difficulties = new ArrayList(Arrays.asList("Easy", "Medium", "Hard", "Meme"));
    List<String> mapTypes = new ArrayList(Arrays.asList("Terran", "Protoss", "Zerg"));
    
    for (String d: difficulties) {
      for (String t: mapTypes) {
        TowerGame tg = new TowerGame(d,t);
        maps.put(Arrays.asList(t, d), tg.getMap());
      }
    }
    return maps;
  }
  
  @Test(expected = ClassNotFoundException.class)
  public void testClassNotFoundException() throws ClassNotFoundException {
    Map dummyMap01 = new TestMap(tg,1);
    Map dummyMap02 = new TestMap(tg,0);  
  }

  @Test
  public void testUpdate() {
    Map map = maps.get(Arrays.asList("Terran", "Easy"));
    MenuView.setModeForTest("Easy");
    for (int i = 0; i < numberOfTries; i++) {
      
      map.update();  
    }
  }

  @Test
  public void testUpdateWaveIntensity() {
    Map map = maps.get(Arrays.asList("Terran", "Easy"));
    int WI = map.getWaveIntensity();
    map.setWaveIntensity(500);
    assertFalse(map.getWaveIntensity() == WI);
  }

  @Test
  public void testSetPaths() {
    Map map = maps.get(Arrays.asList("Terran", "Easy"));
    HashMap<Integer, List<Point>> paths = new HashMap<Integer, List<Point>>(map.getPaths());
    map.setPaths(null);
    assertFalse(paths.equals(map.getPaths()));
  }

}
