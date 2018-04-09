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
  
<<<<<<< HEAD
  public DemoMob(Point[] path) {
=======
  public DemoMob(List<Point> path) {
>>>>>>> f0bc51179bb1c04bf6d7b212bc489a026058592d
    super(path, ControllerMain.TILE_SIZE, 
        ArmorAttribute.DEMO_ARMOR, AttackAttribute.DEMO_ATTACK, 
        DefenseAttribute.DEMO_DEFENSE, SpeedAttribute.NORMAL, 
        new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.DEMO)),
        "Chad", "file:assets/images/thick.png");
  }

}
