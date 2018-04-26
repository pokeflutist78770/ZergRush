package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;


/*============================================================================*
 *                                                                            |
 * 	BattleCruiser Mob                                                         |
 *  Highest tier mob of fthe Terran category, slow moving with a fast but     |
 *  weak atack to simulate gunfire attacks. High armor and HP. Tough to beat  |
 *  Resistance:                                                               |
 *============================================================================*/

public class BattleCruiser extends Mob{

	public BattleCruiser(List<Point> movementPath, TowerGame game) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.HEAVY_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.LARGE, 
				SpeedAttribute.SLOW, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.ELECTRIC)), 
				"BattleCruiser"+Mob.IDNumber++,
				"file:assets/images/mob/terran/battlecruiser.png", "bc_death",
        265.0,
        209.0,
        78.0,
        77.0,
        0.0,
        0.0, 
        1,
        game
        );
	}
}
