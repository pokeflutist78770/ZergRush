package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;

/**
 * Hydralisks are the tier two unit of the zerg army. They have very high damage output with relatively slow movement
 * and low HP.  Typical glass cannon.
 * 
 * @author Ben
 *
 */
public class Hydralisk extends Mob implements Serializable {
	
	public Hydralisk(Vector<Point> movementPath, TowerGame game, boolean isDank) {
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new Vector<ResistanceAttribute>(Collections.singletonList(
						                           ResistanceAttribute.POISON)), 
				"Hydralisk"+Mob.IDNumber++,

				getPicString(isDank), "hydra_death",
				2.0,
				2.0,  
				42.0,
				55.0,
				45.0, 
				58.0, 
				7, 
				10,
				game,
				isDank
		);
	}
	
	
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/debray.png";
		}
		else {
			return "file:assets/images/mob/zerg/hydralisk.png";
		}
	}
}
