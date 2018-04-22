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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import model.Player;
import model.Projectile;
import model.Maps.DemoMap;
import model.Maps.Map;
import model.Maps.ProtossMap;
import model.Maps.TerranMap;
import model.Maps.ZergMap;
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
  public final static int GUI_SIZE= 800;
  public final static int MOBS_PER_SCREEN = 50; // How many 1x1 sprites should fit on an axis of the gui.
  public final static int TILE_SIZE= GUI_SIZE/MOBS_PER_SCREEN;
  public static final int UPDATE_FREQUENCY = 17;
  
  public static HashSet mobs = new HashSet<Mob>();
  public static HashSet projectiles = new HashSet<Projectile>(); 
  public static ArrayList<Tower> towers = new ArrayList<Tower>();
  public static Player thePlayer;
  public static Thread playingNow;
  public static Pane currentView;
  public static Stage stage; 
  public static boolean isPlaying;
  
	private Map theMap;
	private Tower theTower;
	private Mob theMob;
	private MapView theMapView;
	private static MenuView theMenuView;
	private InstructionView theInstrView;
	private Button startButton;
	private Button instrButton;
	private Button backButtonInstr;
	private Button backButtonMap;
	private ScoreView theScoreView;

//	private HashMap<Integer, Projectile> projectiles;
	private HashMap<String, Media> audio;
	
	private static BorderPane window;
	public static final int width = 800;
	public static final int height = 880;
    private static HashMap<String,Image> imageMap;
	
    
    /* initializeAssets
     * Initializes all images and sound, allowing for a flyweight design pattern
     * Parameters: None
     * Returns: None
    */
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
		thePlayer = new Player();
	    
		towers = new ArrayList<Tower>();
		mobs = new HashSet<Mob>();
		projectiles = new HashSet<Projectile>();
		
		
		//Tower theTower = new DemoTower();
		theScoreView = new ScoreView();
		
		// Initialize window
	    window = new BorderPane();
		this.stage=stage;
		
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
		theMapView = new MapView(backButtonMap, mobs, projectiles, towers);
		isPlaying=false;
		
	    Scene scene = new Scene(window, width, height);
		stage.setScene(scene);
		stage.show();
	}
	
	
	/* setViewTo
	 * changes the current view that the usere can see
	 * Parameters: newView: the new view to be able to change
	 * Returns: None
	*/
	private static void setViewTo(StackPane newView) 
	{
		// Adjust the view to newView
		window.setCenter(null);
		window.setTop(newView);
		currentView=newView;
	}

	
	/* menuButtonListener
	 * Listener to check different button clicks
	*/
	private class menuButtonListener implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e) 
		{
		  // Change to MapView or or InstructionsView depending on button
		  String buttonText = ((Button) e.getSource()).getText();
			
		  //User wishes to start a game
		  if (buttonText.equals("Start"))
		  {
			// No difficulty selected
			if (theMenuView.getModeSelection() == null)
				return;
			
			// No map selected
			String mapSelection = theMenuView.getMapSelection();
			if (mapSelection == null)
				return;
			
			isPlaying = true;
			
			// Set background for MapView based on Map Selection
			if (mapSelection.equals("Terran"))
				theMap = new TerranMap();
			else if (mapSelection.equals("Protoss"))
				theMap = new ProtossMap();
			else
				theMap = new ZergMap();
			theMapView.setMapSelection(theMap.imageFilePath);
			
			// Set Wave Difficulty
			theMapView.setWaveNum(theMenuView.getModeSelection());
			
			// Set Kills
			theMapView.setKillsNum(0);
			
			// Set Cash
			theMapView.setCashNum(100);
			
			// Pass Player to MapView
			theMapView.setPlayer(thePlayer);
			
			
			setViewTo(theMapView);
		    

		    
		    //gotta start with a fresh new game :)
		    thePlayer.resetStats();
		    //towers.clear();
		    mobs.clear();
		    projectiles.clear();
		    
		    //thread to show a playing game
		    playingNow = new Thread(new Runnable() {
	          @Override
	          public void run() {
	        	try {
	              while(isPlaying) {
	              
	                Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
	                theMapView.drawMap();
	              }
	              
	              System.out.println("Gameplay Thread: Ended");
	            } catch (InterruptedException e) {
	            }
	          }});
		    
		    playingNow.start();
		  }
		  
		  //user wants to access instructions
		  else if (buttonText.equals("Instructions")) {
			setViewTo(theInstrView);
		  }
		  
		  //button to go back from the gameplay (might be a optional button)
		  else if (buttonText.equals("Back")) {
			isPlaying=false;
			setViewTo(theMenuView);
		  }
		}	
	}
	
	
	/* getGraphic
	 * Gets a asset image for a given string
	 * Parameters: imgfp: filepath of the image
	 * Returns: None
	*/
	public static Image getGraphic(String imgfp) {
	  if (!imageMap.containsKey(imgfp)) {
        imageMap.put(imgfp, new Image(imgfp));
	  }
	  return imageMap.get(imgfp);
	}
	 
	/* resetMainMenu
	 * brings the game back to the main menu
	*/
	public static void resetMainMenu() {
		setViewTo(theMenuView);
	}
	
	//gets the mai nstage for the game
	public static Stage getStage() {
		return stage;
	}
}
