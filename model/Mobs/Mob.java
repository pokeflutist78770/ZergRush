package model.Mobs;

import java.awt.Point;
import java.util.List;

import controller.ControllerMain;
import model.Player;
import model.Maps.Metric;
import model.Towers.ElementalAttribute;

//Enemies move towards the destination that the player will defend. We call
//this the End-Zone.

//Movement of enemies is animated and should not move like the hunter in your
//Wumpus Project.

// Each enemey has certain stats/characteristics. Example:
//    * Speed
//    * Defense
//    * High/Low Armor
//    * Resistance to certain buffs
//    * Be Creative!
// You can choose whether this difference applies to either each individual
// enemy that is spawned on the map or whether it applies to each type of 
// enemy.

//Each map should have 3 different types of enemies. A minimum of total 9 
//enemies. You can mix and match this maps.

public abstract class Mob {
  
  // Movement related fields
  private Thread mobWalk; 
  private Point currentLocation;
  private Point targetLocation;
  private Point[] movementPath;
  private int pathIndex; 
	

  private double radius;
  private ArmorAttribute armor;
	private AttackAttribute attack;
  private double hp;
	private SpeedAttribute speed;
	private List<ResistanceAttribute> resistances;

	// String data of the mob.
  private String name;
	private String imageFilePath; 

  
	public Mob(Point[] movementPath, double radius, 
	    ArmorAttribute armor, AttackAttribute attack, 
	    DefenseAttribute defense, SpeedAttribute speed,  
	    List<ResistanceAttribute> resistances, 
		  String name, String imageFP
			) {
		
		// Initialize Attributes
    this.movementPath = movementPath;
    this.pathIndex = 0;
    this.currentLocation = this.movementPath[0];
    this.pathIndex++;

    this.radius = radius;

    this.armor = armor;
		this.attack=attack;
    this.hp=defense.getDefense();
		this.speed = speed;
		this.resistances = resistances;

    this.name = name;
		this.imageFilePath = imageFP;

		initializeMovement();
	}

	/**
	 * This method gets the mob walking from its spawn location to the End-Zone.
	 * Each mob runs its movement on its own thread and will tell you where it
	 * is, if asked.
	 */
	private void initializeMovement() {
		
		targetLocation = movementPath[pathIndex];
		pathIndex++;
		
		mobWalk = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
						
						if (reachedTarget()) {
							updateTarget();
						}
						
						takeStep();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		mobWalk.start();
	}
	
	/**
	 * Moves this mob a step toward its target.
	 * This method assumes that the mob does not move further than its
	 * radius in a single step.
	 */
	protected void takeStep() {
		double oldX = currentLocation.getX();
		double oldY = currentLocation.getY();
		
		double spd = this.speed.toDouble();
		Point unitV = getDirectionVector();
		
		double newX = oldX + spd * unitV.getX();
		double newY = oldY + spd * unitV.getY();
		
		currentLocation.setLocation(newX, newY);
	}

	/**
	 * A getter method
	 * @return The current X-coordinate
	 */
	public double getX() {
		return currentLocation.getX();
	}
	
	/**
	 * A getter method
	 * @return The current Y-coordinate
	 */
	public double getY() {
		return currentLocation.getY();
	}
	
	public Point getDirectionVector() {
	  return Metric.getDirectionVector(currentLocation, targetLocation);
	}
	
	public double getDirectionAngle() {
	  return Metric.getDirectionAngle(currentLocation, targetLocation);
	}

	/**
	 * Updates the mob to have the next target, if there is one. If the mob
	 * arrived at the End-Zone, then it calls the cleanup method.
	 */
	private void updateTarget() {
		if (pathIndex < movementPath.length) {
      targetLocation = movementPath[pathIndex];
      pathIndex++;
		} else {
      cleanupMobEndZone();
		}
	}

	
	// What do we do when a mob reaches the End-Zone???
	private void cleanupMobEndZone() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Determines if this mob has reached its target yet. 
	 * Takes into account the radius of the mob.
	 * @return True, if the mob has reached its target. False, otherwise.
	 */
	private boolean reachedTarget() {
		return Metric.closeEnough(currentLocation.getX(), currentLocation.getY(), 
				targetLocation.getX(), targetLocation.getY(), radius);
	}
	
	
	/* takeDamage
	 * calculates and subtracts the damage from a Projectile object
	 * Parameters: damage: base damage to be taken
	 *             element: elemental multiplier for the damage
	 * Returns: None
	*/
	public void takeDamage(double damage, ElementalAttribute element) {
		double newDamage = calculateNewDamage( damage, element);
		
		if(newDamage>=hp) {
			hp=0;  //in case of some weird random bugs with oveflow, or underflow in this case
			
			
		}
		
		
	}
	
	
	/* Attack
	 * attacks the main player
	 * Parameters: player - current player
	 * Returns: None
	*/
	public void attack(Player player) {
		double damage=this.attack.getAttack();
		player.takeDamage(damage);
	}
	
	
	private double calculateNewDamage(double damage, ElementalAttribute element) {
		double newDamage=damage*element.getElementalMultiplier();
		
		for(ResistanceAttribute resistance: ResistanceAttribute.values()) {
			if(resistance.name().equals(element.name())) {
				newDamage*=(1-resistance.getResistance());
			}
		}
		
		return newDamage;
	}
}





