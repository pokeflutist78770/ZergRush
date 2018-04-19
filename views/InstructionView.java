package views;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

// You are required to have an instruction page. Treat this as a user-guide
// to how to play your game. This guide should be found in the starting menu.

// The form of this can come in a variety of ways but should be within the game.
// No java-pop-ups.

// The minimum expectation is that a new player of your game will have all the
// knowledge of the game mechanics to successfully play once through.

// The player can read the User-Guide of the game anytime before game-play.

public class InstructionView extends StackPane {

	private Image background;
	private Button backButton;
	private BorderPane pane;
	private VBox vBox;
	
	public InstructionView(Button back)
	{
		pane = new BorderPane();
		background = new Image("file:assets/images/sc.jpg", false);
		
		//Back Button
		backButton = back;
		backButton.setMinWidth(80);
		backButton.setMinHeight(20);
		backButton.setStyle("-fx-font: 18 serif; -fx-base: #000000;");
		
		vBox = new VBox();
		vBox.getChildren().add(backButton);
		vBox.setPadding(new Insets(0,0,25,370));
		
		// Black Background Canvas
		Canvas canvas = new Canvas(800,880);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.getChildren().add(canvas);
		
		// Set background
		ImageView imv = new ImageView();
		imv.setImage(background);
		this.getChildren().add(imv);
		StackPane.setAlignment(imv, Pos.TOP_CENTER);
		
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
		instructions.setStyle("-fx-font: 13 serif;");
		instructions.setFill(Color.WHITE);
		
		pane.setCenter(instructions);
		pane.setBottom(vBox);
		BorderPane.setMargin(vBox, new Insets(0,0,10,0));
		this.getChildren().add(pane);
	}
	
}
