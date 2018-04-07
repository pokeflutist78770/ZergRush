package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import controller.ControllerMain;


/**
 * This mob illustrates how a specific mob can be designed around the abstract class.
 * So far there are no abstract methods to implement, so making a mob is a matter of choosing 
 * all the parameters.
 * @author J. David Taylor
 *
 */
public class DemoMob extends Mob {
  
  public DemoMob(Point[] path) {
    super(path, ControllerMain.TILE_SIZE, 
        ArmorAttribute.DEMO_ARMOR, AttackAttribute.DEMO_ATTACK, 
        DefenseAttribute.DEMO_DEFENSE, SpeedAttribute.NORMAL, 
        new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.DEMO)),
        "Chad", "");
  }

}
