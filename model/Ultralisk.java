package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;


/**
 * Ultralisks are the tier three unit of the Zerg army.  They are extremely tanky and high damage.
 * 
 * @author Ben Walters
 *
 */
public class Ultralisk extends Mob {

	public Ultralisk(List<Point> movementPath, TowerGame game) {
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.STRONG_ATTACK, 
				DefenseAttribute.LARGE, 
				SpeedAttribute.SLOW, 
				new ArrayList<ResistanceAttribute>(Collections.singletonList(
						                           ResistanceAttribute.POISON)), 
				"Ultralisk"+Mob.IDNumber++,
				"file:assets/images/mob/zerg/ultralisk.png", "ultra_death",
        2.0,
        2.0,
        98.0,
        105.0,
        101.0,
        108.0, 
        9,
        game
        );
	}
}
