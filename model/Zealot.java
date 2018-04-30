package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;

public class Zealot extends Mob implements Serializable {

	
/**
 * Zealots are the tier one unit of the Protoss army.  They are relatively slow and tanky with low damage output.
 * 	
 * @param movementPath
 */
public Zealot(Vector<Point> movementPath, TowerGame game) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new Vector<ResistanceAttribute>(
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
        4,
        game
        );
	}
}

