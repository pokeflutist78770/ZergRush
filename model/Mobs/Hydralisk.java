package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

public class Hydralisk extends Mob {
	
	public Hydralisk(List<Point> movementPath) {
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.MEDIUM_ARMOR, 
				AttackAttribute.WEAK_ATTACK, 
				DefenseAttribute.MEDIUM, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(Collections.singletonList(
						                           ResistanceAttribute.POISON)), 
				"Hydralisk"+Mob.IDNumber++,

				"file:assets/images/mob/zerg/hydralisk.png",
				2.0,
				2.0,
				42.0,
				55.0,
				45.0,
				58.0, 
				7
				);
	}
}
