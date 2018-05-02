package model;

import java.io.Serializable;

import controller.ControllerMain; 

// Players can buy towers through some form of currency (money, points, mana 
// power, life-force, etc.).
  
// There is a graphical way for the player to place the tower at the desired
// location (e.g. change the cursor to look liek the tower and use a mouse click,
// select a grid square, etc.)

// Players should start with an appropriate amount of currency and can obtain
// this currency during game-play by deteating enemies.

// Player should be able to interact with his tower always.

// Players can see their current amount amount of currency throughout the game.

// Players should largely use the mouse as device to play the game.

public class Player implements Serializable {
	//just a default for now until mob attacks and balances are sorted out
	//Maybe even when difficulties are added, decrease health as such, 
	private final double BASE_HP=10000;
	private final double BASE_CASH=300;
	
	private double CURRENT_HP;
	private double CURRENT_CASH;
	
	public Player() {
	  CURRENT_HP = BASE_HP;
	  CURRENT_CASH=BASE_CASH;
	}
	
	
	/* takeDamage
	 * subtracts a certain amount from the players HP
	 * Parameters: damage: base damage to be taken 
	 * Returns: None
	*/
	public void takeDamage(double damage) {
	  CURRENT_HP -= damage;
		
	}
	
	
	/* addCash
	 * adds cash to the users Bank, allowing them to add more Towers
	 * Parameters: cash: new cash to be added
	 * Returns: None
	*/
	public void addCash(double cash) {
		System.out.println("CASH: "+CURRENT_CASH);
		CURRENT_CASH+=cash;
		System.out.println("\t Incr: "+cash);
		System.out.println("\t NEW_CASH: "+CURRENT_CASH);
	}
	
	/*------------------ Getters/Setters   -----------------*/
	
	public double getHP()
	{
		return CURRENT_HP;
	}
	
	
	public double getCash() {
		return CURRENT_CASH;  
	}

	public void decrementCash(double cash) {
		System.out.println("CASH: "+CURRENT_CASH);
		CURRENT_CASH -= cash;
		System.out.println("\t Decr: "+cash);
		System.out.println("\t NEW_CASH: "+CURRENT_CASH);
	}
	
	//resets HP for a new game
	public void resetStats() {
		CURRENT_HP=BASE_HP;
		CURRENT_CASH=BASE_CASH;
	}
	
	
	//tells if the player is dead yet
	public boolean isDead() {
		return CURRENT_HP<=0;
	}
}
