package views;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

// You should have a starting screen prior to the maps.

// The players should have the choice of choosing between maps.

public class MenuView extends StackPane {

	private Button startButton;
	private Button instrButton;
	private GridPane grid;
	
	public MenuView(Button start, Button instr)
	{
		grid = new GridPane();
		startButton = start;
		instrButton = instr;
		
		grid.add(startButton, 0, 0);
		grid.add(instrButton, 0, 1);
		this.getChildren().add(grid);
		
		//StackPane.setAlignment(grid, Pos.CENTER);
		//this.setCenter(grid);
	}

	
	
}
