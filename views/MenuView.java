package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// You should have a starting screen prior to the maps.

// The players should have the choice of choosing between maps.

public class MenuView extends StackPane {

	private Image background;
	private Image title;
	private Button startButton;
	private Button instrButton;
	private HBox hBox;
	private Label difficulty;
	private String mode;
	private String map;
	private Button loadButton;
	private Button saveButton;
	private Boolean save;
	private Boolean load;
	private String speed;
	
	public MenuView(Button start, Button instr)
	{
		// Start Button
		hBox = new HBox();
		startButton = start;
		startButton.setMinWidth(130);
		startButton.setMinHeight(40);
		startButton.setStyle("-fx-font: 22 serif; -fx-base: #000000;");
		
		// Instructions Button
		instrButton = instr;
		instrButton.setMinWidth(100);
		instrButton.setMinHeight(40);
		instrButton.setStyle("-fx-font: 22 serif; -fx-base: #000000;");
		
		// Black Background Canvas
		Canvas canvas = new Canvas(800,880);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.getChildren().add(canvas);
		
		// Set Image Background
		background = new Image("file:assets/images/sc.jpg", false);
		ImageView imv = new ImageView();
		imv.setImage(background);
		this.getChildren().add(imv);
		StackPane.setAlignment(imv, Pos.TOP_CENTER);
		
		// Set Title Text
		title = new Image("file:assets/images/TitleText.png", false);
		ImageView imv2 = new ImageView();
		imv2.setImage(title);
		this.getChildren().add(imv2);
		
		// Set Label
		difficulty = new Label("Select difficulty:");
		difficulty.setStyle("-fx-font: 22 serif; -fx-text-fill: #808080;");
		this.getChildren().add(difficulty);
		difficulty.setPadding(new Insets(685,0,0,0));
		
		// Bottom Buttons
		hBox.getChildren().add(instrButton);
		hBox.getChildren().add(startButton);
		hBox.setSpacing(517);
		hBox.setPadding(new Insets(755,0,0,10));
		
		// Radio Buttons
		mode = "";
	    RadioButton easy = new RadioButton("e");
	    easy.setStyle("-fx-text-fill: #000000;");
	    RadioButton medium = new RadioButton("m");
	    medium.setStyle("-fx-text-fill: #000000;");
	    RadioButton hard = new RadioButton("h");
	    hard.setStyle("-fx-text-fill: #000000;");
	    RadioButton fun = new RadioButton("f");
	    fun.setStyle("-fx-text-fill: #000000;");
	
	    ToggleGroup radioGroup = new ToggleGroup();
	
	    // Radio Group
	    easy.setToggleGroup(radioGroup);
	    medium.setToggleGroup(radioGroup);
	    hard.setToggleGroup(radioGroup);
	    fun.setToggleGroup(radioGroup);
	    
	    // Radio Handler
	    RadioButtonHandler radioHandler = new RadioButtonHandler();
	    easy.setOnAction(radioHandler);
	    medium.setOnAction(radioHandler);
	    hard.setOnAction(radioHandler);
	    fun.setOnAction(radioHandler);
	    
	    // Bottom Radio Grid
	    GridPane grid = new GridPane();
	    grid.add(easy, 0, 0);
	    grid.add(medium, 1, 0);
	    grid.add(hard, 2, 0);
	    grid.add(fun, 3, 0);
		grid.setHgap(150);
		
		grid.setAlignment(Pos.BOTTOM_CENTER);
		grid.setPadding(new Insets(0,0,35,0));
		this.getChildren().add(grid);
		
		// Map Drop-Down
		map = "";
		ComboBox<String> myComboBox = new ComboBox<String>();
		myComboBox.setMinWidth(168);
	    myComboBox.getItems().addAll("Map 1","Map 2","Map 3");
	    myComboBox.setEditable(false);  
	    myComboBox.setPromptText("Map Selection");
	    myComboBox.setStyle("-fx-font: 16 serif; -fx-base: #000000;");
	    myComboBox.setOnAction((ActionEvent ev) -> {
	        map =  myComboBox.getSelectionModel().getSelectedItem().toString();
	    });
	    
	    // Speed Drop-Down
		speed = "";
		ComboBox<String> speedComboBox = new ComboBox<String>();
		speedComboBox.setMinWidth(168);
	    speedComboBox.getItems().addAll("Slow","Medium","Fast");
	    speedComboBox.setEditable(false);  
	    speedComboBox.setPromptText("Game Speed");
	    speedComboBox.setStyle("-fx-font: 16 serif; -fx-base: #000000;");
	    speedComboBox.setOnAction((ActionEvent ev) -> {
	        speed =  speedComboBox.getSelectionModel().getSelectedItem().toString();
	        System.out.println(speed);
	    });
	    
	    // Add Map Drop-Down
	    VBox comboVBox = new VBox();
	    comboVBox.getChildren().add(myComboBox);
	    comboVBox.getChildren().add(speedComboBox);
	    comboVBox.setPadding(new Insets(525,0,0,318));
	    comboVBox.setSpacing(3);
		comboVBox.setPickOnBounds(false);
	    this.getChildren().add(comboVBox);
	    
		// Save Button
	    save = false;
		saveButton = new Button("Save");
		saveButton.setMinWidth(80);
		saveButton.setMinHeight(10);
		saveButton.setStyle("-fx-font: 15 serif; -fx-base: #000000;");
	    saveButton.setOnAction((ActionEvent ev) -> {
	    	if (save == false)
	    	{
	    		saveButton.setStyle("-fx-text-fill: #ff0000; -fx-font: 15 serif; -fx-base: #000000;");
	    		save = true;
	    	}
	    	else
	    	{
	    		saveButton.setStyle("-fx-text-fill: #ffffff; -fx-font: 15 serif; -fx-base: #000000;");
	    		save = false;
	    	}
	    });
		
		// Load Button
	    load = false;
		loadButton = new Button("Load");
		loadButton.setMinWidth(80);
		loadButton.setMinHeight(10);
		loadButton.setStyle("-fx-font: 15 serif; -fx-base: #000000;");
	    loadButton.setOnAction((ActionEvent ev) -> {
	    	if (load == false)
	    	{
	    		loadButton.setStyle("-fx-text-fill: #ff0000; -fx-font: 15 serif; -fx-base: #000000;");
	    		load = true;
	    	}
	    	else
	    	{
	    		loadButton.setStyle("-fx-text-fill: #ffffff; -fx-font: 15 serif; -fx-base: #000000;");
	    		load = false;
	    	}
	    });
	    
		// Add Save & Load Buttons
		VBox loadSaveBox = new VBox();
		loadSaveBox.getChildren().add(saveButton);
		loadSaveBox.getChildren().add(loadButton);
		loadSaveBox.setPickOnBounds(false);
		loadSaveBox.setSpacing(2);
		loadSaveBox.setPadding(new Insets(10,0,0,10));
		this.getChildren().add(loadSaveBox);
		
		hBox.setPickOnBounds(false);
		this.getChildren().add(hBox);
		StackPane.setAlignment(hBox, Pos.CENTER);
	}

	private class RadioButtonHandler implements EventHandler<ActionEvent> {

			/**
			* Determine the shape type based on the radio button 
			* selected by user.
			*/
		  
			@Override
			public void handle(ActionEvent e) 
			{
				String radioType = ((RadioButton) e.getSource()).getText();
				difficulty.setStyle("-fx-font: 22 serif; -fx-text-fill: #ff0000;");
				
				switch(radioType)
				{
					case "e":
						difficulty.setText("Easy");
						mode = "Easy";
						break;
					case "m":
						difficulty.setText("Medium");
						mode = "Medium";
						break;
					case "h":
						difficulty.setText("Hard");
						mode = "Hard";
						break;
					case "f":
						difficulty.setText("Dank Memes");
						mode = "Fun";
						break;
				}
				
			}
	  }
	
	  public Boolean getLoadStatus()
	  {
		  return load;
	  }
	
	  public Boolean getSaveStatus()
	  {
		  return save;
	  }
	
	  public String getSpeedSelection()
	  {
		  if (speed.equals(""))
			  return null;
		  else
			  return speed;
	  }
	  
	  public String getModeSelection()
	  {
		  if (mode.equals(""))
			  return null;
		  else
			  return mode;
	  }
	
	  public String getMapSelection()
	  {
		  if (map.equals(""))
			  return null;
		  else
			  return map;
	  }
}
