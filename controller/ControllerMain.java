package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;
import model.Projectile;
import model.Maps.DemoMap;
import model.Maps.Map;
import model.Maps.ProtossMap;
import model.Maps.TerranMap;
import model.Maps.ZergMap;
import model.Mobs.Mob;
import model.Towers.Tower;
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
  public final static int GUI_SIZE = 800;
  public final static int MOBS_PER_SCREEN = 50; // How many 1x1 sprites should fit on an axis of the gui.
  public final static int TILE_SIZE = GUI_SIZE / MOBS_PER_SCREEN;
  public static final int UPDATE_FREQUENCY = 17;

  private static Random random = new Random();

  public static HashSet<Mob> mobs = new HashSet<Mob>();
  public static HashSet<Projectile> projectiles = new HashSet<Projectile>();
  public static HashSet<Tower> towers = new HashSet<Tower>();
  public static HashMap<String, AudioClip> soundEffects = new HashMap<>();
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

  // private HashMap<Integer, Projectile> projectiles;
  private HashMap<String, Media> audio;

  private static BorderPane window;
  public static final int width = 800;
  public static final int height = 880;
  private static HashMap<String, Image> imageMap;

  // For testing
  // public ControllerMain() {
  // initializeAssets();
  // thePlayer = new Player();
  // isPlaying = false;
  // theMap = new DemoMap();
  // }

  /*
   * initializeAssets Initializes all images and sound, allowing for a flyweight
   * design pattern Parameters: None Returns: None
   */
  private void initializeAssets() {
    imageMap = new HashMap<String, Image>();
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

  }

  /* Launch the JavaFX application */
  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage stage) throws Exception {
    initializeAssets();
    thePlayer = new Player();

    // Tower theTower = new DemoTower();
    theScoreView = new ScoreView();

    // Initialize window
    window = new BorderPane();
    this.stage = stage;

    // Initialize menu buttons
    startButton = new Button("Start");
    instrButton = new Button("Instructions");
    backButtonMap = new Button("Back");
    backButtonInstr = new Button("Back");

    // Initialize Menu View
    theMenuView = new MenuView(startButton, instrButton);
    this.setViewTo(theMenuView);
    soundEffects.get("terran_soundtrack").play();

    menuButtonListener menuHandler = new menuButtonListener();
    startButton.setOnAction(menuHandler);
    instrButton.setOnAction(menuHandler);
    backButtonMap.setOnAction(menuHandler);
    backButtonInstr.setOnAction(menuHandler);

    // Initialize Instruction View
    theInstrView = new InstructionView(backButtonInstr);

    // Initialize Map View
    theMapView = new MapView(backButtonMap);
    isPlaying = false;

    Scene scene = new Scene(window, width, height);
    stage.setScene(scene);
    stage.show();
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
        // No difficulty selected
        if (theMenuView.getModeSelection() == null)
          return;

        // No map selected
        String mapSelection = theMenuView.getMapSelection();
        if (mapSelection == null)
          return;

        isPlaying = true;
        theMap = null;

        // Get difficulty
        String difficultyStr = theMenuView.getModeSelection();
        int difficulty = 0;
        if (difficultyStr.equals("Easy")) {
          difficulty = 1;
        } else if (difficultyStr.equals("Medium")) {
          difficulty = 2;
        } else if (difficultyStr.equals("Hard")) {
          difficulty = 3;
        } else {
          difficulty = 2;
        }
        Map.setWaveIntensity(3);

        // Set background for MapView based on Map Selection
        if (mapSelection.equals("Terran"))
          theMap = new TerranMap(difficulty);
        else if (mapSelection.equals("Protoss"))
          theMap = new ProtossMap(difficulty);
        else
          theMap = new ZergMap(difficulty);
        theMapView.setMapSelection(theMap.imageFilePath);

        // Set Wave Difficulty
        theMapView.setWaveNum(theMenuView.getModeSelection());

        // Set Kills
        theMapView.setKillsNum(0);

        // Set Cash
        theMapView.setCashNum(100);

        // Pass Player to MapView
        theMapView.setPlayer(thePlayer);
        
        // Pass Game Speed from MenuView to MapView
        theMapView.setGameSpeed(theMenuView.getGameSpeed());

        setViewTo(theMapView);

        // gotta start with a fresh new game :)
        thePlayer.resetStats();
        towers.clear();
        mobs.clear();
        projectiles.clear();

        // thread to show a playing game

        try {
          Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY / 2);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }

        playingNow = new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              while (isPlaying) {

                Thread.sleep((long) ControllerMain.UPDATE_FREQUENCY);
                theMapView.drawMap();
              }

              System.out.println("Gameplay Thread: Ended");
            } catch (InterruptedException e) {
            }
          }
        });

        playingNow.start();
      }

      // user wants to access instructions
      else if (buttonText.equals("Instructions")) {
        setViewTo(theInstrView);
      }

      // button to go back from the gameplay (might be a optional button)
      else if (buttonText.equals("Back")) {
        isPlaying = false;
        setViewTo(theMenuView);
      }
    }
  }

  /*
   * getGraphic Gets a asset image for a given string Parameters: imgfp: filepath
   * of the image Returns: None
   */
  public static Image getGraphic(String imgfp) {
    if (!imageMap.containsKey(imgfp)) {
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

  // For when you just need Random
  public static Random getRandom() {
    return random;
  }

  /*
   * Handles loss conditions for the player, terminating active mob threads and
   * resetting the game state.
   */
  public static void dealWithDeadPlayer() {
    if(soundEffects.get("terran_soundtrack").isPlaying()) {
      soundEffects.get("terran_soundtrack").stop();
    }
    soundEffects.get("defeat").play();
    System.out.println("Player lost");
    // ControllerMain.playingNow.interrupt();%here

    // display loss screen

    ControllerMain.isPlaying = false;

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
      });

      popupStage.show();
    });

  }

}
