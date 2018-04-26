package Tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.Maps.DemoMap;
import model.Maps.Map;
import model.Maps.Metric;
import model.Maps.ProtossMap;
import model.Maps.TerranMap;
import model.Maps.ZergMap;
import model.Mobs.*;

import org.junit.Test;

import controller.ControllerMain;
import javafx.scene.image.Image;

public class MapTest {

  private HashMap<Integer, List<Point>> paths = 
      new HashMap<Integer, List<Point>>();

  @Test
  public void test() {
  
  
	//Map protossMap = new ProtossMap(1);
	//Map terranMap = new TerranMap(1);
	//Map zergmap = new ZergMap(1);
	//Map demoMap = new DemoMap();
	Metric metric = new Metric();
	
	Point p1 = new Point(876,890);
	Point p2 = new Point(762, 834);
	
	Point p3 = new Point(0,0);
	
	metric.closeEnough(p1, p2, 10000000);
	metric.closeEnough(p1, p2, .00001);
	
	metric.getDirectionVector(p1, p2);
	metric.getDirectionAngle(p1, p2);
	
	metric.getDirectionAngle(p3, p3);
	metric.getDirectionAngle(p3, p2);
	metric.getDirectionAngle(p2, p3);
	


    List<Point> pathOne = new ArrayList<Point>(Arrays.asList(
        new Point(876, 890), 
        new Point(762, 834),
        new Point(684, 883),
        new Point(566, 756),
        new Point(466, 633),
        new Point(521, 403),
        new Point(674, 256),
        new Point(653, 173),
        new Point(562, 130),
        new Point(402, 204),
        new Point(358, 166),
        new Point(316, 89),
        new Point(201, 168),
        new Point(147, 119)
        ));
    
    
    //DemoMob demoMob = new DemoMob(pathOne);
    
    AttackAttribute attack = AttackAttribute.DEMO_ATTACK;
    attack.getAttack();
    attack = AttackAttribute.MEDIUM_ATTACK;
    attack.getAttack();
    attack = AttackAttribute.STRONG_ATTACK;
    attack.getAttack();
    attack = AttackAttribute.WEAK_ATTACK;
    attack.getAttack();
    
    for(Point p: pathOne) {
      Map.scalePoint(p);
    }
    
    
    this.paths.put(1, pathOne);
    
    try {
      Class cls = Class.forName("model.Mobs." + "Ultralisk");
      
      Constructor<Mob> ctor[] = cls.getConstructors();
      Mob ulty = ctor[0].newInstance(paths.get(1));
      System.out.println(ulty.getName());
    } catch (  InvocationTargetException | ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
      // 
      e.printStackTrace();
      System.out.println("Didn't work.");
    }
    
  }

}
