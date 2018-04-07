package model;

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

public class Player {
	//just a default for now until mob attacks and balances are sorted out
	private double HP=100;   
	
	
	public void takeDamage(double damage) {
		if(damage<=HP) {
			HP-=damage;
		}
		
		if(HP<=0) {
			System.out.println("Player lost");
			
		}
		
	}
}
