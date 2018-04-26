package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

/**
 * Dark Templar are the tier two units of the Protoss army.  They are fast and deal a great deal of damage, but have 
 * relatively low HP
 * 
 * @author Ben Walters
 *
 */
public class DarkTemplar extends Mob {

  public DarkTemplar(List<Point> movementPath, TowerGame game) {

    super(movementPath, ControllerMain.TILE_SIZE / 3, ArmorAttribute.HEAVY_ARMOR, AttackAttribute.WEAK_ATTACK,
        DefenseAttribute.MEDIUM, SpeedAttribute.SLOW,
        new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.POISON)),
        "DarkTemplar" + Mob.IDNumber++, "file:assets/images/mob/protoss/dark_templar.png", "dt_death", 5.0, 2.0, 54.0,
        59.0, 57.0, 62.0, 8, game);
  }
}
