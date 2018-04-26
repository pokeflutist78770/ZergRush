package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

public class Zealot extends Mob{

	
/**
 * Zealots are the tier one unit of the Protoss army.  They are relatively slow and tanky with low damage output.
 * 	
 * @param movementPath
 */
public Zealot(List<Point> movementPath, TowerGame game) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.ELECTRIC)), 
				"Zealot"+Mob.IDNumber++,
				"file:assets/images/mob/protoss/zealot.png", "zealot_death",
        1.0,
        2.0,
        39.0,
        41.0,
        42.0,
        44.0, 
        8,
        game
        );
	}
}

