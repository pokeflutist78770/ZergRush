package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;
import views.MenuView;

/**
 * Marines are the tier one unit of the Terran army.  They have low HP and movement speed and deal reltively little
 * damage.
 * 
 * @author Ben
 *
 */
public class Marine extends Mob implements Serializable {

	public Marine(Vector<Point> movementPath, TowerGame game, boolean isDank) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR,    
				AttackAttribute.WEAK_ATTACK,  
				DefenseAttribute.SMALL, 
				SpeedAttribute.NORMAL, 
				new Vector<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Marine"+Mob.IDNumber++,
				getPicString(isDank), "marine_death",
				2.0,
				2.0,
				25.0,
				33.0,
				26.0,
				36.0, 
				8,
				6,  
				game,
				isDank
        );
	}
	
	
	private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/terran/crash.png";
		}
		else {
			return "file:assets/images/mob/terran/marine.png";
		}
	}
}
