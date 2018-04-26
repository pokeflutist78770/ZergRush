package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

/**
 * Wraiths are the tier two unit of the Terran army.  They are fast, flying units, with relatively low damage.
 * 
 * @author Ben Walters
 *
 */
public class Wraith extends Mob{

	public Wraith(List<Point> movementPath, TowerGame game) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Wraith"+Mob.IDNumber++,
				"file:assets/images/mob/terran/wraith.png", "wraith_death",
        688.0,
        0.0,
        49.0,
        30.0,
        0.0,
        0.0, 
        1,
        game
        );
	}
}
