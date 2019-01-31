package controller;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.text.View;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Map;
import model.Mob;
import model.TowerGame;
import views.InstructionView;
import views.MapView;
import views.MenuView;
import views.ScoreView;

/**
 * Central controller for JavaFX Application based TowerDefense game. Players
 * place towers to fend of waves of incoming enemies until such a time as they
 * accrue enough points to win or are overwhelmed by the enemy. Game state is
 * maintained as a collection of objects made available to other classes through
 * static attributes in order to avoid serializability conflicts.
 * 
 * @author Ben Walters
 *
 */

public class ControllerMain extends Application {
  
  // Global Constants
  public final static int GUI_SIZE = 800;
  public final static int MOBS_PER_SCREEN = 50; // How many 1x1 sprites should fit on an axis of the gui.
  public final static int TILE_SIZE = GUI_SIZE / MOBS_PER_SCREEN;
  public final static int UPDATE_FREQUENCY = 17;
  public final static int GUI_WIDTH = GUI_SIZE;
  public final static int GUI_HEIGHT = (int) Math.round(GUI_SIZE * 1.1);

  // Media data
  public static HashMap<String, AudioClip> soundEffects = new HashMap<>();
  public static HashMap<String, Media> songClips= new HashMap<>();
  public static HashMap<String, Image> imageMap;
  public static MediaPlayer songPlayer;

  // Buttons
  private Button startButton;
  private Button instrButton;
  private Button backButtonInstr;
  private Button backButtonMap;

  // Views and windows
  private static Pane currentView;
  private static ScoreView theScoreView;
  private static MapView theMapView;
  private static MenuView theMenuView;
  private static InstructionView theInstrView;
  private static BorderPane window;
  private static Stage stage;
  
  // Model data
  private static TowerGame theGame;
  
  public static Vector<Constructor<Mob>> mobConstructors;
  


  /* Launch the JavaFX application */
  public static void main(String[] args) {
    launch(args);
  }

  
  public void start(Stage stage) throws Exception {
    initializeAssets();
    initializeViews(stage);

    Scene scene = new Scene(window, GUI_WIDTH, GUI_HEIGHT);
    stage.setScene(scene);
    stage.show();
  }
  
  /*
   * initializeAssets Initializes all images and sound, allowing for a flyweight
   * design pattern Parameters: None Returns: None
   */
  private static void play(AudioClip ac) {
    for(AudioClip a: soundEffects.values()) {
      if(a.isPlaying()) {
        a.stop();
      }
      ac.play();
      
    }
  }

  
  /**
   * playBackground
   * Serves to be able to play background music, as MediaPlayer is better suited for it
   * @param song: current song to be played
   */
  private static void playBackground(Media song) {
	  if(songPlayer.getStatus().equals(Status.PLAYING)) {
		  songPlayer.stop();
	  }
	  
      songPlayer=new MediaPlayer(song);
      songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      songPlayer.setOnEndOfMedia(new Runnable() {
          public void run() {
            songPlayer.seek(Duration.ZERO);
          }
      });
      songPlayer.play();
  }
  
  
  /*
   * initializeAssets Initializes all images and sound, allowing for a flyweight
   * design pattern Parameters: None Returns: None
   */
  private void initializeAssets() {
    initializeImages();
    initializeAudio();  
  }

  /*
   * Instantiates and stores all audio clips for gameplay for flyweight retrieval.
   */
  private void initializeAudio() {
	songClips.put("menu_soundtrack",
			      new Media(new File("assets/audio/map/menu.mp3").toURI().toString()));
	songClips.put("marauder", 
			      new Media(new File("assets/audio/marauder.mp3").toURI().toString()));
	songClips.put("Terran", 
			      new Media(new File("assets/audio/map/terran.mp3").toURI().toString())); 
	songClips.put("Protoss", 
			      new Media(new File("assets/audio/map/protoss.mp3").toURI().toString()));
	songClips.put("Zerg", 
			      new Media(new File("assets/audio/map/zerg.mp3").toURI().toString()));
    songClips.put("trolo", 
    		       new Media(new File("assets/audio/map/trolo.mp3").toURI().toString()));
    songClips.put("defeat", 
    		      new Media(new File("assets/audio/map/defeat.mp3").toURI().toString()));
    songClips.put("victory", 
    		      new Media(new File("assets/audio/map/victory.mp3").toURI().toString()));
    songClips.put("dankVictory", 
		      new Media(new File("assets/audio/map/heya.mp3").toURI().toString()));
	
    soundEffects.put("zergling_death", new AudioClip("file:assets/audio/mob/zerg/zergling_death.wav"));
    soundEffects.put("hydra_death", new AudioClip("file:assets/audio/mob/zerg/hydra_death.wav"));
    soundEffects.put("ultra_death", new AudioClip("file:assets/audio/mob/zerg/ultra_death.wav"));
    soundEffects.put("archon_death", new AudioClip("file:assets/audio/mob/protoss/archon_death.wav"));
    soundEffects.put("zealot_death", new AudioClip("file:assets/audio/mob/protoss/zealot_death.wav"));
    soundEffects.put("dt_death", new AudioClip("file:assets/audio/mob/protoss/dt_death.wav"));
    soundEffects.put("marine_death", new AudioClip("file:assets/audio/mob/terran/marine_death.wav"));
    soundEffects.put("wraith_death", new AudioClip("file:assets/audio/mob/terran/wraith_death.wav"));
    soundEffects.put("bc_death", new AudioClip("file:assets/audio/mob/terran/bc_death.wav"));
    soundEffects.put("mins", new AudioClip("file:assets/audio/map/notenoughminerals.mp3"));
    soundEffects.put("underattack", new AudioClip("file:assets/audio/map/underattack.mp3"));
    soundEffects.put("resumed", new AudioClip("file:assets/audio/map/Alert_TerranGameResumed.mp3"));
    soundEffects.put("paused", new AudioClip("file:assets/audio/map/Alert_TerranGamePaused.mp3"));
    soundEffects.put("upgrade", new AudioClip("file:assets/audio/map/Alert_TerranUpgradeComplete.mp3"));
    soundEffects.put("time", new AudioClip("file:assets/audio/map/TimeToStop.mp3")); 
    soundEffects.put("swamp", new AudioClip("file:assets/audio/map/swamp.mp3"));
    soundEffects.put("dankDeath", new AudioClip("file:assets/audio/mob/roblox.mp3"));
    soundEffects.put("dankUpgrade", new AudioClip("file:assets/audio/tower/whoah.mp3"));
  }
 
  /*
   * Initializes and stores images for flyweight retrieval.
   */
  private void initializeImages() {
    imageMap = new HashMap<String, Image>();
  }

  /*
   * Initializes the views.
   */
  private void initializeViews(Stage stage) {
    theScoreView = new ScoreView();

    // Initialize window
    window = new BorderPane();
    ControllerMain.stage = stage;

    // Initialize menu buttons
    startButton = new Button("Start");
    instrButton = new Button("Instructions");
    backButtonMap = new Button("Quit");
    backButtonInstr = new Button("Back");

    // Initialize Menu View
    theMenuView = new MenuView(startButton, instrButton);
    ControllerMain.setViewTo(theMenuView);
    songPlayer=new MediaPlayer(songClips.get("menu_soundtrack"));
    songPlayer.play();

    menuButtonListener menuHandler = new menuButtonListener();
    startButton.setOnAction(menuHandler);
    instrButton.setOnAction(menuHandler);
    backButtonMap.setOnAction(menuHandler);
    backButtonInstr.setOnAction(menuHandler);  

    // Initialize Instruction View
    theInstrView = new InstructionView(backButtonInstr);
  }
  
  
  /**
   * Allows access to be able to chnge the vieew when needed
   * @param view: the view to be switched to
   * @return None
   */
  public static void changeCurrentView(StackPane view) {
	  setViewTo(view);
  }
  
  
  /*
   * setViewTo changes the current view that the usere can see Parameters:
   * newView: the new view to be able to change Returns: None
   */
  private static void setViewTo(StackPane newView) {
    // Adjust the view to newView
    window.setCenter(null);
    window.setTop(newView);
    currentView = newView;
  }

  /*
   * menuButtonListener Listener to check different button clicks
   */
  private class menuButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      // Change to MapView or or InstructionsView depending on button
      String buttonText = ((Button) e.getSource()).getText();

      // User wishes to start a game
      if (buttonText.equals("Start")) {
        try {
          runSelectedGame(theMenuView.getLoadStatus());
        } catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
        theMenuView.setLoadStatus(false);
      }
 
      // user wants to access instructions
      else if (buttonText.equals("Instructions")) {
        setViewTo(theInstrView);
      }

      // button to go back from the gameplay (might be a optional button)
      else if (buttonText.equals("Quit") || buttonText.equals("Back")) {
        setViewTo(theMenuView);
        if (buttonText.equals("Quit")) {
        	playBackground(ControllerMain.songClips.get("menu_soundtrack"));
        	theGame.pause();
        }
      }
  }

    /*
     * This method is self-descriptive. 
     * It handles what should happen when someone clicks 'start' from the main menu.
     */
    private void runSelectedGame(Boolean loading) throws ClassNotFoundException {
      
      System.out.println("runSelectedGame() is called.");
      
      if (loading) {
        theGame = PersistenceMaster.loadGame();
        if (theGame.getMap().getSoundTrackName().equals("Zerg")) {
          mobConstructors = Map.initializeSpawnConstructors(new Vector<String>(Arrays.asList("Zergling", "Hydralisk", "Ultralisk")));
        } else if (theGame.getMap().getSoundTrackName().equals("Terran")) {
          mobConstructors = Map.initializeSpawnConstructors(new Vector<String>(Arrays.asList("Marine", "Wraith", "BattleCruiser")));
        } else if (theGame.getMap().getSoundTrackName().equals("Protoss")) {
          mobConstructors = Map.initializeSpawnConstructors(new Vector<String>(Arrays.asList("Zealot", "DarkTemplar", "Archon")));
        }
        
        
      } else {
        // No difficulty selected
        String difficulty = theMenuView.getModeSelection();
        if (difficulty == null)
          return;

        // No map selected
        String mapSelection = theMenuView.getMapSelection();
        if (mapSelection == null)
          return;

        System.out.println("This was selected: " + difficulty + ", " + mapSelection);
        theGame = null;
        theGame = new TowerGame(difficulty, mapSelection);
      }
      
      //dealWithDeadPlayer(false);
      
      System.out.println("The game was initialized without issue.");
      theMapView = new MapView(backButtonMap, theGame);
      System.out.println("The mapView was initialized without issue.");
      theGame.addObserver(theMapView);
      System.out.println("Observers have been set.");
      theGame.start();
      theGame.getPlayer().resetStats();
      initializeMapView();
      System.out.println("MapView has been configured.");
      playBackground(songClips.get(theGame.getMap().getSoundTrackName()));
      theGame.unPause(); //The mapview needs to be initialized before the game unpauses.
    }

    /*
     * Initializes the map after a game has been started.
     */
    private void initializeMapView() {
      theMapView.setMapSelection(theGame.getBackgroundImageFP());
      System.out.println("map selection set");
      // Set Wave Difficulty 
      theMapView.setWaveNum(theMenuView.getModeSelection());
      System.out.println("wave difficulty set");

      // Set Cash
      theMapView.setCashNum(100);
      System.out.println("can set");

      // Pass Player to MapView
      theMapView.setPlayer(theGame.getPlayer());
      System.out.println("player set");

      // Pass Game Speed to MapView
      theMapView.setGameSpeed(theMenuView.getSpeedSelection());
      
      setViewTo(theMapView);
      System.out.println("view successfully swapped.");
    }
  }

  /*
   * getGraphic Gets a asset image for a given string Parameters: imgfp: filepath
   * of the image Returns: None
   */
  public static Image getGraphic(String imgfp) {
    if (!imageMap.containsKey(imgfp)) {
    //TODO: Ben, will you figure out the appropriate resolution?
      imageMap.put(imgfp, new Image(imgfp)); 
    }
    return imageMap.get(imgfp);
  }

  /*
   * resetMainMenu brings the game back to the main menu
   */
  public static void resetMainMenu() {
    setViewTo(theMenuView);
  }

  // Getter for main stage of game
  public static Stage getStage() {
    return stage;
  }

  /*
   * Handles loss conditions for the player, terminating active mob threads and
   * resetting the game state. Display loss if playerLost true, otherwise display win.
   */
  public static void dealWithDeadPlayer(boolean playerLost) {
    theGame.pause();
    songPlayer.stop();
    
    System.out.println("Game over");

    // Label - Win or Loss
    Label gameStatus = new Label();
    Image statusImage=null;
    
    if (playerLost)
    {
    	statusImage=new Image("file:assets/images/gameOver.png",false);
    	if(MenuView.getModeSelection().equals("Fun")) {
    		playBackground(songClips.get("trolo"));
    	}
    	else {
    		playBackground(songClips.get("defeat"));
    	}
    }
    else
    {
    	statusImage=new Image("file:assets/images/win.png",false);
    	gameStatus.setText("You win!");
    	if(MenuView.getModeSelection().equals("Fun")) {
    		playBackground(songClips.get("dankVictory"));
    	}
    	else {
    		playBackground(songClips.get("victory"));
    	}
    }
    
    final Image status=statusImage;
    
    // display win or loss screen
    Platform.runLater(() -> {
      // This code will be moved to when a player reaches a set amount of waves,
      // but for the demo this will suffice
      ControllerMain.currentView.setEffect(new GaussianBlur());

      VBox pauseRoot = new VBox(5);
      
      ImageView image=new ImageView(status);
      pauseRoot.getChildren().add(image);
      pauseRoot.setStyle("-fx-background-color: #970EF200;");
      pauseRoot.setAlignment(Pos.CENTER);
      pauseRoot.setPadding(new Insets(20));

      
      if(MenuView.getModeSelection().equals("Fun")) {
    	  Image troll=null;
    	  if(playerLost) {
	    	  troll=new Image("file:assets/images/troll.png",200,200,false, false);
	    	 
    	  } else {
    		  troll=new Image("file:assets/images/winTroll.png",200,200,false, false);
    	  }
    	  
    	  ImageView trollView=new ImageView(troll);
    	  pauseRoot.getChildren().add(trollView);
	      startTroll(trollView);
      }
      
      VBox buttonBox=new VBox(5);
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.setPadding(new Insets(20));
      buttonBox.setMaxWidth(200);
      
      Button resume = new Button("Main Menu");
      
      resume.setStyle("-fx-font: 15 serif; -fx-base: #000000;");
      buttonBox.getChildren().add(resume);
      
      pauseRoot.getChildren().add(buttonBox);
      
      Stage popupStage = new Stage(StageStyle.TRANSPARENT);
      popupStage.initOwner(ControllerMain.getStage());
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

      resume.setOnAction(event -> {
        ControllerMain.currentView.setEffect(null);
        ControllerMain.resetMainMenu();
        playBackground(ControllerMain.songClips.get("menu_soundtrack"));
        popupStage.hide();
        theGame = null;
        theMapView = null;
      });

      popupStage.show();
    });

  }
  
  
  /**
   * startTroll
   * starts the animation for the troll image upon game over
   * @param troll: the image used as a troll
   * @return None
   */
  private static void startTroll(ImageView troll) {
	  RotateTransition rt = new RotateTransition();
      rt.setNode(troll);
      rt.setFromAngle(-20);
      rt.setToAngle(20);
      rt.setInterpolator(Interpolator.LINEAR);
      rt.setAutoReverse(true);
      rt.setCycleCount(Timeline.INDEFINITE);
      
      rt.setDuration(new Duration(2000));
      rt.play();
  }

}
