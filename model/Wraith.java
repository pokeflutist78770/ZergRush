package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;
import views.MenuView;

/**
 * Wraiths are the tier two unit of the Terran army.  They are fast, flying units, with relatively low damage.
 * 
 * @author Ben Walters
 *
 */
public class Wraith extends Mob implements Serializable {

	public Wraith(Vector<Point> movementPath, TowerGame game, boolean isDank) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new Vector<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Wraith"+Mob.IDNumber++,
				getPicString(isDank), "wraith_death",
				688.0,
				0.0,
				49.0,
				30.0,
				0.0,
				0.0,   
				1,
				12,
				game  
        );
	}   
	
	
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/frank.png";
		}
		else {
			return "file:assets/images/mob/terran/wraith.png";
		}
	}
}
