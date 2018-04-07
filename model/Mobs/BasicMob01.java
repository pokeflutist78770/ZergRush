package model.Mobs;

import java.util.ArrayList;

import model.Maps.Path;

/**
 * This mob illustrates how a specific mob can be designed around the abstract class.
 * So far there are no abstract methods to implement, so making a mob is a matter of choosing 
 * all the parameters.
 * @author J. David Taylor
 *
 */
public class BasicMob01 extends Mob {
  
  public BasicMob01() {
    super("DemoMob", AttackAttribute.DEMO_ATTACK,
    		SpeedAttribute.NORMAL, DefenseAttribute.DEMO_DEFENSE, 
    		ArmorAttribute.DEMO_ARMOR, new ArrayList<ResistanceAttribute>(), "", 
    		new Path(null), 100);
  }

}
