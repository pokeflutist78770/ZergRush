package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

/**
 * Zerglings are the tier one unit of the Zerg army.  They are relatively fast with low HP and damage.
 * @author Ben
 *
 */
public class Zergling extends Mob{

	public Zergling(List<Point> movementPath, TowerGame game) {
		super(movementPath, ControllerMain.TILE_SIZE/3, ArmorAttribute.NONE, 
				AttackAttribute.WEAK_ATTACK, DefenseAttribute.SMALL, 
				SpeedAttribute.FAST, 
				new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.NONE)), 
				"Zergling"+Mob.IDNumber++,
				"file:assets/images/mob/zerg/zergling.png", "zergling_death",
				2.0,
				2.0,
				40.0,
				39.0,
				43.0,
				42.0,
				7,
				game
				);
	}
}
