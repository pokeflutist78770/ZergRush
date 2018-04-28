package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

/**
 * Marines are the tier one unit of the Terran army.  They have low HP and movement speed and deal reltively little
 * damage.
 * 
 * @author Ben
 *
 */
public class Marine extends Mob{

	public Marine(List<Point> movementPath, TowerGame game) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR, 
				AttackAttribute.WEAK_ATTACK,
				DefenseAttribute.SMALL, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Marine"+Mob.IDNumber++,
				"file:assets/images/mob/terran/marine.png", "marine_death",
        2.0,
        2.0,
        25.0,
        33.0,
        26.0,
        36.0, 
        8,
        game
        );



	}

}
