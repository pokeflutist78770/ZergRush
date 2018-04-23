package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

public class Archon extends Mob{
	
public Archon(List<Point> movementPath) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.HEAVY_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.LARGE, 
				SpeedAttribute.SLOW, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.ELECTRIC)), 
				"Archon"+Mob.IDNumber++,
				"file:assets/images/mob/protoss/archon.png",
        2.0,
        2.0,
        98.0,
        105.0,
        101.0,
        108.0, 
        9
        );
	}
}

