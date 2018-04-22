package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;

public class Marine extends Mob{

	public Marine(List<Point> movementPath) {
		
		super(movementPath, ControllerMain.TILE_SIZE/3, 
				ArmorAttribute.LIGHT_ARMOR, 
				AttackAttribute.WEAK_ATTACK,
				DefenseAttribute.SMALL, 
				SpeedAttribute.NORMAL, 
				new ArrayList<ResistanceAttribute>(
						Collections.singletonList(ResistanceAttribute.NONE)), 
				"Marine"+Mob.IDNumber++,
				"file:assets/images/mob/terran/marine.png", 22, 30,1,12);
	}

}
