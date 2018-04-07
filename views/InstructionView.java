package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
		
		Text instructions = new Text("OBJECTIVE\nThe objective of Tower Defense is to defend your command"
				+ " center. Waves of enemies will spawn periodically, each wave stronger than the last.\n"
				+ "In order to defend your command center, you will need to construct towers. Each tower costs"
				+ " money. You will earn money after killing enemies.\nTowers may be upgraded as you "
				+ "earn money. Survive all waves of enemies with your command center in tact, and you will have survived\n"
				+ "Tower Defense!\n\n"
				+ "START NEW GAME\n"
				+ "1) Choose Persistence (Save option)\n"
				+ "2) Choose Game Speed\n"
				+ "3) Choose Map\n"
				+ "4) Select Start\n\n"
				+ "LOAD GAME\n"
				+ "1) Select Load option\n"
				+ "2) Select Start\n\n"
				+ "END GAME\n"
				+ "1) Select the Back button to return to Main Menu");
		
		
		pane.setTop(header);
		pane.setCenter(instructions);
		pane.setBottom(backButton);
		this.getChildren().add(pane);
	}
	
}
