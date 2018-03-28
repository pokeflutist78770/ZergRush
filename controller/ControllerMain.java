package controller;


import javafx.application.*;
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
	
	private Map theMap;
	private Player thePlayer;
	private Tower theTower;
	private Mob theMob;
	private MapView theMapView;
	private MenuView theMenuView;
	private ScoreView theScoreView;
	
	public static void main(String[] args) {
	  launch(args);
  }
	
	public void start(Stage stage) throws Exception {
		theMap = new Map01();
		thePlayer = new Player();
		theTower = new BasicTower01();
		theMob = new BasicMob01();
		theMapView = new MapView();
		theMenuView = new MenuView();
		theScoreView = new ScoreView();
	}

}
