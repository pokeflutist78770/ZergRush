package model;

import java.awt.Point;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;

/**
 * Zerglings are the tier one unit of the Zerg army.  They are relatively fast with low HP and damage.
 * @author Ben
 *
 */
public class Zergling extends Mob{  

	public Zergling(Vector<Point> movementPath, TowerGame game, boolean isDank) {
		super(movementPath, ControllerMain.TILE_SIZE/3, ArmorAttribute.NONE, 
				AttackAttribute.WEAK_ATTACK, DefenseAttribute.SMALL, 
				SpeedAttribute.FAST,   
				new Vector<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.NONE)), 
				"Zergling"+Mob.IDNumber++,
				getPicString(isDank), "zergling_death",
				2.0,
				2.0,
				40.0,
				39.0,
				43.0,
				42.0,
				7,
				5, 
				game,
				isDank
		);
	}
	
	
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/debray.png";
		}
		else {
			return "file:assets/images/mob/zerg/zergling.png";
		}
	}
}
