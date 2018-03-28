import javafx.application.*;
import javafx.stage.Stage;
import Model.Maps.Map;

public class ControllerMain extends Application {
	
	private Map theMap;
	private Player thePlayer;
	private Tower theTower;
	private Mob theMob;
	private mapView theMapView;
	private menuView theMenuView;
	private scoreView theScoreView;
	
	public static void main(String[] args) {
	  launch(args);
  }
	
	public void start(Stage stage) throws Exception {
		theMap = new Map();
		thePlayer = new Player();
		theTower = new Tower();
		theMob = new Mob();
		theMapView = new MapView();
		theMenuView = new MenuView();
		theScoreView = new ScoreView();
	}

}
