package controller;


import java.util.HashMap;

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

public class ControllerMain extends Application {
	public final static int GUI_SIZE= 1000;
	public final static int MOBS_PER_SCREEN = 50; // How many 1x1 sprites should fit on an axis of the gui.
	public final static int TILE_SIZE= GUI_SIZE/MOBS_PER_SCREEN;
	private Map theMap;
	private Player thePlayer;
	private Tower theTower;
	private Mob theMob;
	private MapView theMapView;
	private MenuView theMenuView;
	private ScoreView theScoreView;
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
		theTower = new BasicTower01();
		theMob = new BasicMob01();
		theMapView = new MapView();
		theMenuView = new MenuView();
		theScoreView = new ScoreView();
	}

}
