package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

public class Zealot extends Mob{

	
	
public Zealot(List<Point> movementPath) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.ELECTRIC)), 
				"Zealot"+Mob.IDNumber++,
				"file:assets/images/mob/protoss/zealot.png",
        1.0,
        2.0,
        39.0,
        41.0,
        42.0,
        44.0, 
        8
        );
	}
}

