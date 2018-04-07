package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

// You are required to have an instruction page. Treat this as a user-guide
// to how to play your game. This guide should be found in the starting menu.

// The form of this can come in a variety of ways but should be within the game.
// No java-pop-ups.

// The minimum expectation is that a new player of your game will have all the
// knowledge of the game mechanics to successfully play once through.

// The player can read the User-Guide of the game anytime before game-play.

public class InstructionView extends StackPane {

	private Button backButton;
	private Label header;
	private BorderPane pane;
	
	public InstructionView(Button back)
	{
		backButton = back;
		header = new Label("Instructions Page");
		pane = new BorderPane();
		
		pane.setTop(header);
		pane.setBottom(backButton);
		this.getChildren().add(pane);
	}
	
}
