package Tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Mobs.DemoMob;
import model.Mobs.Mob;
import model.Towers.ElementalAttribute;

public class MobModelTests {

	@Test
	public void MobDeath() {
		Mob mob=new DemoMob(null);
		
		mob.takeDamage(200, ElementalAttribute.NONE);
		assertTrue(mob.isDead());
	}
}
