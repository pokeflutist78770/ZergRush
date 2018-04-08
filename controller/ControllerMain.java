package controller;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import model.Player;
import model.Maps.DemoMap;
import model.Maps.Map;
import model.Mobs.DemoMob;
import model.Mobs.Mob;
import model.Towers.DemoTower;
import model.Towers.Tower;
import views.InstructionView;
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
  public static Player thePlayer;
  
	private Map theMap;
	//private Player thePlayer;
	private Tower theTower;
	private Mob theMob;
	private MapView theMapView;
	private MenuView theMenuView;
	private InstructionView theInstrView;
	private Button startButton;
	private Button instrButton;
	private Button backButtonInstr;
	private Button backButtonMap;
	private ScoreView theScoreView;

//	private HashMap<Integer, Projectile> projectiles;
	private HashMap<String, Media> audio;
	
	private BorderPane window;
	public static final int width = 800;
	public static final int height = 800;
  private static HashMap<String,Image> imageMap;
	
	private void initializeAssets() {
	  imageMap = new HashMap<String,Image>();
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
		theMap = new DemoMap();
		thePlayer = new Player();
	    
		mobs = new HashSet<Mob>();
		//Tower theTower = new DemoTower();
		//Mob theMob = new BasicMob01();
		theScoreView = new ScoreView();
		
		// Initialize window
	    window = new BorderPane();
		
	    // Initialize menu buttons
		startButton = new Button("Start");
		instrButton = new Button("Instructions");
		backButtonMap = new Button("Back");
		backButtonInstr = new Button("Back");
		
		menuButtonListener menuHandler = new menuButtonListener();
		startButton.setOnAction(menuHandler);
		instrButton.setOnAction(menuHandler);
		backButtonMap.setOnAction(menuHandler);
		backButtonInstr.setOnAction(menuHandler);
		
		// Initialize Menu View
		theMenuView = new MenuView(startButton, instrButton);
		this.setViewTo(theMenuView);
		
		// Initialize Instruction View
		theInstrView = new InstructionView(backButtonInstr);
		
		// Initialize Map View
		theMapView = new MapView(backButtonMap);
		
	    Scene scene = new Scene(window, width, height);
		stage.setScene(scene);
		stage.show();
	}
	
	private void setViewTo(StackPane newView) 
	{
		// Adjust the view to newView
		window.setCenter(null);
		window.setCenter(newView);
	}

	private class menuButtonListener implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent e) 
		{
			// Change to MapView or or InstructionsView depending on button
			String buttonText = ((Button) e.getSource()).getText();
			
			if (buttonText.equals("Start"))
				setViewTo(theMapView);
			else if (buttonText.equals("Instructions"))
				setViewTo(theInstrView);
			else if (buttonText.equals("Back"))
				setViewTo(theMenuView);
		}
		
	}
	
	public static Image getGraphic(String imgfp) {
	  if (!imageMap.containsKey(imgfp)) {
      imageMap.put(imgfp, new Image("file:images/"  + imgfp + ".png"));
	  }
	  return imageMap.get(imgfp);
	}
	
}
