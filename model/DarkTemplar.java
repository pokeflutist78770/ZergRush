package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Vector;

import controller.ControllerMain;

/**
 * Dark Templar are the tier two units of the Protoss army.  They are fast and deal a great deal of damage, but have 
 * relatively low HP
 * 
 * @author Ben Walters
 *
 */
public class DarkTemplar extends Mob implements Serializable {

  public DarkTemplar(Vector<Point> movementPath, TowerGame game, boolean isDank) {

    super(movementPath, 
    		ControllerMain.TILE_SIZE / 3,  
    		ArmorAttribute.HEAVY_ARMOR,
    		AttackAttribute.WEAK_ATTACK, 
    		DefenseAttribute.MEDIUM, 
    		SpeedAttribute.SLOW,
    		new Vector<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.POISON)),
    		"DarkTemplar" + Mob.IDNumber++,
    		getPicString(isDank), 
    		"dt_death", 
    		5.0, 
    		2.0, 
    		54.0,
    		59.0,    
    		57.0, 
    		62.0,  
    		8, 
    		13,
    		game,
    		isDank
    );
  }
   
  
  
    private static String getPicString(boolean isDank) {
		if(isDank){
			return "file:assets/images/mob/protoss/gotcha.png";
		}
		else {
			return "file:assets/images/mob/protoss/dark_templar.png";
		}
	}  
}  
