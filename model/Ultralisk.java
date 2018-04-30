package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;


/**
 * Ultralisks are the tier three unit of the Zerg army.  They are extremely tanky and high damage.
 * 
 * @author Ben Walters
 *
 */
public class Ultralisk extends Mob implements Serializable {

	public Ultralisk(Vector<Point> movementPath, TowerGame game, boolean isDank) {
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.STRONG_ATTACK,   
				DefenseAttribute.LARGE, 
				SpeedAttribute.SLOW, 
				new Vector<ResistanceAttribute>(Collections.singletonList(
						                           ResistanceAttribute.POISON)), 
				"Ultralisk"+Mob.IDNumber++,
				getPicString(isDank), "ultra_death",
				2.0,
				2.0,
				98.0,
				105.0,
				101.0,
				108.0, 
				9, 
				23, 
				game
        ); 
	}
	
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/debray.png";
		}
		else {
			return "file:assets/images/mob/zerg/ultralisk.png";
		}
	}
}
