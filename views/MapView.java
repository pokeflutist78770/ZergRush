package views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

//A player can view information about an enemy by clicking one that has been 
//placed. Information should include the characteristics of that enemy.

// Information should be viewed in an inclusive manner within the game. Stay 
// away from Java Pop-ups!

// When the game begins in the map, it should be animated. That includes all
// sprites moving.

public class MapView extends StackPane {


	private Button backButton;
	private Label header;
	private BorderPane pane;
	
	public MapView(Button back)
	{
		backButton = back;
		pane = new BorderPane();
		header = new Label("Map View");

		pane.setTop(header);
		pane.setBottom(backButton);
		this.getChildren().add(pane);
	}
	
}
