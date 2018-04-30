package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;

/**
 * Archon is the tier three unit of the Protoss army.  They are slow, tanky, and high damage.
 * @author Ben Walters
 *
 */
public class Archon extends Mob implements Serializable {

  public Archon(Vector<Point> movementPath, TowerGame game, boolean isDank) {

    super(movementPath, ControllerMain.TILE_SIZE / 3, 
    		ArmorAttribute.HEAVY_ARMOR,    
    		AttackAttribute.WEAK_ATTACK,   
    		DefenseAttribute.LARGE, 
    		SpeedAttribute.SLOW,
    		new Vector<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.ELECTRIC)),
    		"Archon" + Mob.IDNumber++, 
    		getPicString(isDank), "archon_death", 
    		213.0, 
    		595.0,
    		82.0,
    		89.0, 
    		85.0,
    		92.0,
    		3, 
    		25,
    		game);
  	} 
  
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/debray.png";
		}
		else {
			return "file:assets/images/mob/protoss/archon.png";
		}
	}
}
  