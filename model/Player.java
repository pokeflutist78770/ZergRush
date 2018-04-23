package model;

import controller.ControllerMain;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import views.MapView;

// Players can buy towers through some form of currency (money, points, mana 
// power, life-force, etc.).

// There is a graphical way for the player to place the tower at the desired
// location (e.g. change the cursor to look liek the tower and use a mouse click,
// select a grid square, etc.)

// Players should start with an appropriate amount of currency and can obtain
// this currency during game-play by deteating enemies.

// Player should be able to interact with his tower always.

// Players can see their current amount amount of currency throughout the game.

// Players should largely use the mouse as device to play the game.

public class Player {
	//just a default for now until mob attacks and balances are sorted out
	//Maybe even when difficulties are added, decrease health as such, 
	private final double BASE_HP=10000;
	private double CURRENT_HP=BASE_HP;
	
	
	/* takeDamage
	 * subtracts a certain amount from the players HP
	 * Parameters: damage: base damage to be taken 
	 * Returns: None
	*/
	public void takeDamage(double damage) {
		if(damage<=CURRENT_HP) {
			CURRENT_HP-=damage;
			System.out.println("Player HP: "+CURRENT_HP);
		}
		
		if(CURRENT_HP<=0) {
			System.out.println("Player lost");
			ControllerMain.playingNow.interrupt();
			
			//display loss screen
			
			ControllerMain.isPlaying=false;
			
			Platform.runLater(() -> {
				//This code will be moved to when a player reaches a set amount of waves, 
				//but for the demo this will suffice
				ControllerMain.currentView.setEffect(new GaussianBlur());
				
				VBox pauseRoot = new VBox(5);
	            pauseRoot.getChildren().add(new Label("You lost!"));
	            pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
	            pauseRoot.setAlignment(Pos.CENTER);
	            pauseRoot.setPadding(new Insets(20));
	
	            Button resume = new Button("Main Menu");
	            pauseRoot.getChildren().add(resume);
	            
				Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	            popupStage.initOwner(ControllerMain.getStage());
	            popupStage.initModality(Modality.APPLICATION_MODAL);
	            popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
	            
	            resume.setOnAction(event -> {
	                ControllerMain.currentView.setEffect(null);
	                ControllerMain.resetMainMenu();
	                popupStage.hide();
	            });
	            
	            popupStage.show();
			});
		}
		
	}
	
	public double getHP()
	{
		return CURRENT_HP;
	}
	
	//resets HP for a new game
	public void resetStats() {
		CURRENT_HP=BASE_HP;
	}
	
	
	//tells if the player is dead yet
	public boolean isDead() {
		return CURRENT_HP<=0;
	}
}
