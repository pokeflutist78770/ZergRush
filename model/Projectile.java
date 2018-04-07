package model;

import model.Mobs.Mob;

// This is the object that is instantiated when the tower shoots. 
//It keeps track of its location and it moves along until it reaches its target.
public class Projectile {
	private int currX, currY;  //feel free to change this as needed if implementation is different :)
	private int targetX, targetY;
	private Mob mob;
	
	public Projectile() {
		
	}
	
	private boolean hasReachedTarget() {
		if(mob==null) {
			if(currX!=targetX || currY!=targetY) {
				return null;
			}
		}
		
		return false;
	}
	
}
