package controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import model.Player;
import model.Maps.Map;
import model.Maps.Map01;
import model.Mobs.BasicMob01;
import model.Mobs.Mob;
import model.Towers.BasicTower01;
import model.Towers.Tower;
import views.MapView;
import views.MenuView;
import views.ScoreView;

// The player loses the game after a certain number of enemies breach the
// defenses and reaches the End-Zone.

// The player can win the game by fullfilling some objective. Example:
// * Successfully defeating some number of enemies
// * Defeating a boss enemy
// * Defending the destination for a certain amount of time
// * Be Creative!

// You game should have all sound effects for major events as discussed with
// your Scrum Master. Examples:
// * Spawning of enemies
// * Each enemy dying
// * Enemies entering the spawn zone.
// * General Sound music throughout the game
// * Think Creatively!

// The player should be able to save the game and load a saved game outside 
// gameplay of the map or when the game is paused. That is, after completion
// (win or lose) of a map, at the starting-menu, a save option should be 
// available (to keep update on his latest progress), or when the game is 
// paused mmomentarily while in the map.

// The player first reaches the Starting Menu before selecting a Map

// Persistence is implemented in the Starting Menu

// In-game message should be displayed after a map is won/lost. This can be 
// in the formof statistics (tower kills, etc...)

// You may invent a version of gameplay or use one of the of the two popular
// versions of game-play:
// * Continuous: Enemies continuously spawn and the player builds towers to 
//               defend the destination. The game ends when the destination 
//               has been defended for a specified time period or when a 
//               certain number of enemies have been defeated.
// * Waves: A collection of enemies form one wave. Each wave of enemies spawns
//               with a short pause in between waves. The game ends when all 
//               waves have been defeated.

// The player can pause and resume the game in single-player mode.

// The player can toggle the speed of the game between normal speed and a fast speed.
// This feature is not required for multi-player mode

// Save/Load Games: When playing single-player mode, the player can choose to save
// the game and play at a later time. A Load Game option is in the main menu. This
// feature works well with the Pause Game feature, since it is more elegant to 
// load a game, have it paused initially, and then resume to play the game. 

public class ControllerMain extends Application {
	public final static int GUI_SIZE= 1000;
	public final static int MOBS_PER_SCREEN = 50; // How many 1x1 sprites should fit on an axis of the gui.
	public final static int TILE_SIZE= GUI_SIZE/MOBS_PER_SCREEN;
  public static final int UPDATE_FREQUENCY = 17;
  
  public static HashSet mobs;
  public static HashSet projectiles; 
  public static ArrayList<Tower> towers;
  
	private Map theMap;
	private Player thePlayer;
	private MapView theMapView;
	private MenuView theMenuView;
	private ScoreView theScoreView;

//	private HashMap<Integer, Projectile> projectiles;
	private HashMap<String, Image> images;
	private HashMap<String, Media> audio;
	
	private void initializeAssets() {
	  initializeImages();
	  initializeAudio();
	}
	
	public void initializeAudio() {
	  
	}
	
	public void initializeImages() {
	  
	}
	
	public static void main(String[] args) {
	  launch(args);
	}
	
	public void start(Stage stage) throws Exception {
	  initializeAssets();
		theMap = new Map01();
		thePlayer = new Player();
		Tower theTower = new BasicTower01();
		Mob theMob = new BasicMob01();
		theMapView = new MapView();
		theMenuView = new MenuView();
		theScoreView = new ScoreView();
	}

}
