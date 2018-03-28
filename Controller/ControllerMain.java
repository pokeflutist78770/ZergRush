import javafx.application.*;
import javafx.stage.Stage;

public class ControllerMain extends Application {
	
	public static void main(String[] args) {
	  launch(args);
  }
	
	public void start(Stage stage) throws Exception {
		
	}

	public Map theMap;
	public Mob	theMob;
	public Tower theTower;
	
	public ControllerMain()
	{
		theMap = new Map();
		theMob = new Mob();
		theTower = new Tower();
	}
	
}
