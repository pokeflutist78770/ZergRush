package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

public class Wraith extends Mob{

	public Wraith(List<Point> movementPath) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Wraith"+Mob.IDNumber++,
				"file:assets/images/mob/terran/wraith.png",
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
