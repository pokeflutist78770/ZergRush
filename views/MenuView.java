package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// You should have a starting screen prior to the maps.

// The players should have the choice of choosing between maps.

public class MenuView extends StackPane {

	private Image background;
	private Button startButton;
	private Button instrButton;
	private VBox vBox;
	
	public MenuView(Button start, Button instr)
	{
		vBox = new VBox();
		startButton = start;
		startButton.setMaxWidth(100);
		instrButton = instr;
		instrButton.setMaxWidth(100);
		background = new Image("assets/images/sc.jpg", false);
		
		// Set background
		ImageView imv = new ImageView();
		imv.setImage(background);
		this.getChildren().add(imv);
		
		// Center Buttons
		vBox.getChildren().add(startButton);
		vBox.getChildren().add(instrButton);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(370,0,0,350));
		
		this.getChildren().add(vBox);
		StackPane.setAlignment(vBox, Pos.CENTER);
	}

	
	
}
