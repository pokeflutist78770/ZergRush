package Tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import controller.ControllerMain;
import model.Mobs.ArmorAttribute;
import model.Mobs.AttackAttribute;
import model.Mobs.DefenseAttribute;
import model.Mobs.DemoMob;
import model.Mobs.Mob;
import model.Mobs.ResistanceAttribute;
import model.Mobs.SpeedAttribute;
import model.Towers.ElementalAttribute;

public class MobModelTests {

	@Test
	public void FullResistance() {
		DemoMob mob=new DemoMob(new Point[10]);
		mob.takeDamage(20000, ElementalAttribute.NONE);
		assertFalse(mob.isDead());
	}
}
