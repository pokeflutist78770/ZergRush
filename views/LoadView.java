package views;

import controller.ControllerMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;





public class LoadView extends StackPane{
	HBox loadSave1, loadSave2, loadSave3;
	
	
	public LoadView() {
		// Black Background Canvas
		Canvas canvas = new Canvas(800,880);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.getChildren().add(canvas); 
				
		// Set Image Background
		Image background = new Image("file:assets/images/sc.jpg", false);
		ImageView imv = new ImageView();
		imv.setImage(background);
		this.getChildren().add(imv);
		StackPane.setAlignment(imv, Pos.TOP_CENTER); 
		
		
		BorderPane pane=new BorderPane();
		
		VBox mainBox=new VBox();
		mainBox.setPickOnBounds(false);
		
		Image loadImage=new Image("file:assets/images/loadGame.png", false);
		ImageView loadView=new ImageView(loadImage);
		BorderPane.setAlignment(loadView, Pos.CENTER);
		BorderPane.setMargin(loadView, new Insets(50,0,0,0));
		
		VBox loadBox=new VBox();
		loadBox.setMaxHeight(500);
		loadBox.setMaxWidth(600);
		loadBox.setPickOnBounds(false);
		BorderPane.setAlignment(loadBox, Pos.TOP_CENTER);
		loadBox.setStyle("-fx-border-color: grey;\r\n" + 
				"    -fx-border-insets: 5;\r\n" + 
				"    -fx-border-width: 3;\r\n" + 
				"    -fx-border-style: dashed;");
		
		loadSave1=new HBox();
		loadSave2=new HBox();
		loadSave3=new HBox();

		setupLoadBox(loadSave1);
		setupLoadBox(loadSave2);
		setupLoadBox(loadSave3);
		
		
		Insets loadMargins=new Insets(2,0,2,0);
		VBox.setMargin(loadSave1, loadMargins);
		VBox.setMargin(loadSave2, loadMargins);
		VBox.setMargin(loadSave3, loadMargins);
		
		loadBox.getChildren().addAll(loadSave1, loadSave2, loadSave3);
		pane.setTop(loadView);
		pane.setCenter(loadBox);
		this.getChildren().add(pane);
		
		Button backButton = new Button("Back");
		backButton.setMinWidth(80);
		backButton.setMinHeight(10);
		backButton.setStyle("-fx-font: 15 serif; -fx-base: #000000;");
		EventHandler<ActionEvent> backHandler=new backHandler();
		backButton.setOnAction(backHandler);
		System.out.println("Back event handler implemented");
		
		// Add Load Button
		VBox backBox = new VBox();
		backBox.getChildren().add(backButton);
		backBox.setPickOnBounds(false);
		backBox.setSpacing(2);
		backBox.setPadding(new Insets(10,0,0,10));
		this.getChildren().add(backBox);
		
		setupLoads();
	}
	
	
	private void setupLoads() {
		
	}
	
	
	private void setupLoadBox(HBox load) {
		load.getChildren().add(new Label("HI"));
		load.setPickOnBounds(false);
		load.setMaxHeight(166);
		load.setMaxWidth(600);
		load.setMinSize(600, 166);
		load.setStyle("-fx-border-color: grey;\r\n" + 
				"    -fx-border-insets: 5;\r\n" + 
				"    -fx-border-width: 3;\r\n" + 
				"    -fx-border-style: dashed;" +
				"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, rgba(116,116,116,0.53) 0%, rgba(116,116,116,0.94) 50%, rgba(116,116,116,0.54) 100%);");
	}
	
	
	private class backHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			System.out.println("Back");
			ControllerMain.resetMainMenu();
			
		}
	}
}







