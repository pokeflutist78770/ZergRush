package model.Towers;

import model.Projectile;
import model.Mobs.Mob;

// Towers are purchased by the player and placed strategically along the paths.

// Towers will attack enemeies that pass by (in its range of attack). Examples:
// * Gemcraft uses towers powered by gems and the combination of gems determine
//   damage, range, etc.
// * Kingdon Rush uses a medieval setting with archery towers and warrior towers.
// * Be creative! You can decide how towers should attack enemies. 
//   It can be single target, or multi-target!

// Each tower has a different damage amount, range of attack, and rate of attack.

// Each tower has a different way of attacking enemies. Possibilities include
// * attack multiple enemies
// * teleport enemies
// * slow enemies
// * poison enemies
// * confusing enemies so that they either walk back towards their start or maybe
//   they spin in a circle for a few seconds
// * Be Creative!

// A player can view information about a tower before purchasing it and/or
// by clicking one that has been placed.

// Each tower should cost the player something, could be gold, etc.

// Towers must be upgradeable to have more range, fire rate, or damage.

// Tower upgrades change the look of the tower.

// Towers must have a minimum of 1 tower upgrade.

// A tower's range is visible when the tower is clicked. If a tower is upgraded,
// this information should also be updated.

// Tower attacks are animated.

// Towers cannot be built such that they block the path of enemies.

// A player can build at least three different towers. These three towers must 
// be inherently different. You cannot just change the initial damage and range 
// of a tower, for example, for a tower to qualify as different. A different 
// tower might involve a different type of attack or a different animation. 
// (e.g. a lightning tower that attacks 3 enemies at the same time)

// All attacks from the towers must be animated.

public abstract class Tower {
	
	private int cost;
	
	private Range range;
	private int numberOfAttacks;
	private Ammunition ammmo; // This is meant to represent the type of thing a tower shoots.
	
	private String imageFilePath;
	private String ammoImageFilePath;

	
	/*
	 * 
	*/
	public void shoot(Mob targetMob, targetX, targetY) {
	  Projectile projectile=new Projectile(targetMob, targetX, targetY);
		
	}
}


