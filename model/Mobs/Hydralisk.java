package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

/**
 * Hydralisks are the tier two unit of the zerg army. They have very high damage output with relatively slow movement
 * and low HP.  Typical glass cannon.
 * 
 * @author Ben
 *
 */
public class Hydralisk extends Mob {
	
	public Hydralisk(List<Point> movementPath, TowerGame game) {
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(Collections.singletonList(
						                           ResistanceAttribute.POISON)), 
				"Hydralisk"+Mob.IDNumber++,

				"file:assets/images/mob/zerg/hydralisk.png", "hydra_death",
				2.0,
				2.0,
				42.0,
				55.0,
				45.0,
				58.0, 
				7,
				game
				);
	}
}
