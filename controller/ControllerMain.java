package controller;

import java.util.HashMap;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
  public static HashMap<String, Image> imageMap;

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
  private void initializeAssets() {
    initializeImages();
    initializeAudio();
  }

  /*
   * Instantiates and stores all audio clips for gameplay for flyweight retrieval.
   */
  private void initializeAudio() {
    soundEffects.put("zergling_death", new AudioClip("file:assets/audio/mob/zerg/zergling_death.wav"));
    soundEffects.put("hydra_death", new AudioClip("file:assets/audio/mob/zerg/hydra_death.wav"));
    soundEffects.put("ultra_death", new AudioClip("file:assets/audio/mob/zerg/ultra_death.wav"));
    soundEffects.put("archon_death", new AudioClip("file:assets/audio/mob/protoss/archon_death.wav"));
    soundEffects.put("zealot_death", new AudioClip("file:assets/audio/mob/protoss/zealot_death.wav"));
    soundEffects.put("dt_death", new AudioClip("file:assets/audio/mob/protoss/dt_death.wav"));
    soundEffects.put("marine_death", new AudioClip("file:assets/audio/mob/terran/marine_death.wav"));
    soundEffects.put("wraith_death", new AudioClip("file:assets/audio/mob/terran/wraith_death.wav"));
    soundEffects.put("bc_death", new AudioClip("file:assets/audio/mob/terran/bc_death.wav"));
    soundEffects.put("terran_soundtrack", new AudioClip("file:assets/audio/map/terran.mp3"));
    soundEffects.put("defeat", new AudioClip("file:assets/audio/map/defeat.mp3"));

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
    backButtonMap = new Button("Back");
    backButtonInstr = new Button("Back");

    // Initialize Menu View
    theMenuView = new MenuView(startButton, instrButton);
    ControllerMain.setViewTo(theMenuView);
    soundEffects.get("terran_soundtrack").play();

    menuButtonListener menuHandler = new menuButtonListener();
    startButton.setOnAction(menuHandler);
    instrButton.setOnAction(menuHandler);
    backButtonMap.setOnAction(menuHandler);
    backButtonInstr.setOnAction(menuHandler);

    // Initialize Instruction View
    theInstrView = new InstructionView(backButtonInstr);
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
        runSelectedGame();
      }

      // user wants to access instructions
      else if (buttonText.equals("Instructions")) {
        setViewTo(theInstrView);
      }

      // button to go back from the gameplay (might be a optional button)
      else if (buttonText.equals("Back")) {
        setViewTo(theMenuView);
      }
    }

    /*
     * This method is self-descriptive. 
     * It handles what should happen when someone clicks 'start' from the main menu.
     */
    private void runSelectedGame() {
      System.out.println("runSelectedGame() is called.");
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
      System.out.println("The game was initialized without issue.");
      theMapView = new MapView(backButtonMap, theGame);
      System.out.println("The mapView was initialized without issue.");
      theGame.addObserver(theMapView);
      System.out.println("Observers have been set.");
      theGame.start();
      
      initializeMapView();
      System.out.println("MapView has been configured.");
      
      theGame.unPause(); //The mapview needs to be initialized before the game unpauses.
    }

    /*
     * Initializes the map after a game has been started.
     */
    private void initializeMapView() {
      //TODO: probably can refactor this.
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
      imageMap.put(imgfp, new Image(imgfp, 100, 0, true, false)); 
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
   * resetting the game state.
   */
  public static void dealWithDeadPlayer() {
    //TODO: clean this method
    theGame.pause();
    if(soundEffects.get("terran_soundtrack").isPlaying()) {
      soundEffects.get("terran_soundtrack").stop();
    }
    soundEffects.get("defeat").play();
    System.out.println("Player lost");

    // display loss screen


    Platform.runLater(() -> {
      // This code will be moved to when a player reaches a set amount of waves,
      // but for the demo this will suffice
      ControllerMain.currentView.setEffect(new GaussianBlur());

      VBox pauseRoot = new VBox(5);
      pauseRoot.getChildren().add(new Label("You lost!"));
      pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
      pauseRoot.setAlignment(Pos.CENTER);
      pauseRoot.setPadding(new Insets(20));

      Button resume = new Button("Main Menu");
      pauseRoot.getChildren().add(resume);

      Stage popupStage = new Stage(StageStyle.TRANSPARENT);
      popupStage.initOwner(ControllerMain.getStage());
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

      resume.setOnAction(event -> {
        ControllerMain.currentView.setEffect(null);
        ControllerMain.resetMainMenu();
        popupStage.hide();
        theGame = null;
        theMapView = null;
      });

      popupStage.show();
    });

  }

}
