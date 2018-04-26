package model.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.TowerGame;

/**
 * Archon is the tier three unit of the Protoss army.  They are slow, tanky, and high damage.
 * @author Ben Walters
 *
 */
public class Archon extends Mob {

  public Archon(List<Point> movementPath, TowerGame game) {

    super(movementPath, ControllerMain.TILE_SIZE / 3, ArmorAttribute.HEAVY_ARMOR, AttackAttribute.WEAK_ATTACK,
        DefenseAttribute.LARGE, SpeedAttribute.SLOW,
        new ArrayList<ResistanceAttribute>(Collections.singletonList(ResistanceAttribute.ELECTRIC)),
        "Archon" + Mob.IDNumber++, "file:assets/images/mob/protoss/archon.png", "archon_death", 213.0, 595.0, 82.0,
        89.0, 85.0, 92.0, 4, game);
  }
}
