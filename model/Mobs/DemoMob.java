package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import views.MapView;


/**
 * This mob illustrates how a specific mob can be designed around the abstract class.
 * So far there are no abstract methods to implement, so making a mob is a matter of choosing 
 * all the parameters.
 * @author J. David Taylor
 *
 */
public class DemoMob extends Mob {
  
  public DemoMob(List<Point> path) {
    super(path, ControllerMain.TILE_SIZE/3, 
        ArmorAttribute.DEMO_ARMOR, AttackAttribute.DEMO_ATTACK, 
        DefenseAttribute.LARGE, SpeedAttribute.NORMAL, 
        new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.DEMO)),
        "Chad", "file:assets/images/TheHunter.png");
  }

}
