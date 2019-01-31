package views;

import controller.ControllerMain;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.Slider;
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
import javafx.util.Duration;

// You should have a starting screen prior to the maps.

// The players should have the choice of choosing between maps.

/**
 * This class represents the Menu Page of Tower Defense.
 * The Menu Page allows the Player to select a Map and Difficulty.
 * Player may also customize Game Speed and decide if they would like to Save or Load a game.
 * Start button will start the game, Instruction button will bring up instruction page.
 * 
 * @author David Weinflash
 *
 */

public class MenuView extends StackPane {

	private Image background;
	private Image title;
	private Button startButton;
	private Button instrButton;
	private HBox hBox;
	private Label difficulty;
	private static String mode;
	private String map;
	private Button loadButton;
	private Boolean load;
	private Slider speedSlider;
	private ToggleGroup radioGroup;
	
	/**
	* Constructor for MenuView. Adds the Start and Instruction buttons to bottom of page.
	* Adds Difficulty Radio Buttons below Start/Instruction Buttons. Label will update
	* based on Difficulty setting. Map Selection set at center of page below Title.
	* Game Speed as slider at top right; Load/Save buttons set at top left.
	* 
	* @param Button back, Button instr
	*          - Back Button returns to Main Menu
	*          - Instr Button sets view to Instruction View
	*/
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
		
		FadeTransition ft = new FadeTransition();
	    ft.setNode(imv2);
	    ft.setDuration(new Duration(4500));
	    ft.setFromValue(0.0);
	    ft.setToValue(1.0);
	    ft.setCycleCount(1);
	    ft.play();
		
		// Set Label
		difficulty = new Label("Select difficulty:");
		difficulty.setStyle("-fx-font: 22 serif; -fx-text-fill: #808080;");
		this.getChildren().add(difficulty);
		difficulty.setPadding(new Insets(670,0,0,16));
		
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
	    myComboBox.getItems().addAll("Terran","Protoss","Zerg");
	    myComboBox.setEditable(false);  
	    myComboBox.setPromptText("Map Selection");
	    myComboBox.setStyle("-fx-font: 16 serif; -fx-base: #000000;");
	    myComboBox.setOnAction((ActionEvent ev) -> {
	        map =  myComboBox.getSelectionModel().getSelectedItem().toString();
	    });
	    
	    // Game Speed Slider
	    VBox slideBox = new VBox();
	    speedSlider = new Slider(0, 1, 0.0);
	    speedSlider.setMaxWidth(150);
	    speedSlider.setStyle("-fx-font: 16 serif; -fx-base: #000000;");
	    Label sliderLabel = new Label("Game Speed");
	    sliderLabel.setStyle("-fx-text-fill: #ffffff");
	    
	    slideBox.getChildren().add(speedSlider);
	    slideBox.getChildren().add(sliderLabel);
	    sliderLabel.setPadding(new Insets(0,0,0,45));
	    slideBox.setPadding(new Insets(10,0,0,640));
	    slideBox.setPickOnBounds(false);
	    this.getChildren().add(slideBox);
	    
	    // Add Map Drop-Down
	    VBox comboVBox = new VBox();
	    comboVBox.getChildren().add(myComboBox);
	    comboVBox.setPadding(new Insets(515,0,0,322));
		comboVBox.setPickOnBounds(false);
	    this.getChildren().add(comboVBox);
		
		// Load Button
	    load = false;
		loadButton = new Button("Load");
		loadButton.setMinWidth(80);
		loadButton.setMinHeight(10);
		loadButton.setStyle("-fx-font: 15 serif; -fx-base: #000000;");
		EventHandler<ActionEvent> loadHandler=new loadHandler();
		loadButton.setOnAction(loadHandler);

	    
		// Add Load Button
		VBox loadSaveBox = new VBox();
		loadSaveBox.getChildren().add(loadButton);
		loadSaveBox.setPickOnBounds(false);
		loadSaveBox.setSpacing(2);
		loadSaveBox.setPadding(new Insets(10,0,0,10));
		this.getChildren().add(loadSaveBox);
		
		hBox.setPickOnBounds(false);
		this.getChildren().add(hBox);
		StackPane.setAlignment(hBox, Pos.CENTER);
	}

	/**
	* Class to handle the Difficulty selected by User via RadioButtons.
	* Update the Difficulty Label based on RadioButton selection
	*/
	private class RadioButtonHandler implements EventHandler<ActionEvent> {
		  
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
	
	
	  /*----------    Getters/Setters     -------------*/
	
	  public Boolean getLoadStatus()
	  {
		  return load;
	  }
	
	  public double getSpeedSelection()
	  {
		  // Returns 0.0 (min) to 1.0 (max) representing 
		  // position of cursor on slider  
		  return speedSlider.getValue();
	  }
	  
	  public static String getModeSelection()
	  {
		  if (mode.equals(""))
			  return null;
		  else
			  return mode;
	  }
	
	  public double getGameSpeed()
	  {
		  return speedSlider.getValue();
	  }
	  
	  public String getMapSelection()
	  {
		  if (map.equals(""))
			  return null;
		  else
			  return map;
	  }

    public void setLoadStatus(Boolean load) {
      this.load = load;
    }
    
    public static void setModeForTest(String newMode) {
    	mode=newMode;
    }
    
    
    private class loadHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			LoadView loadView=new LoadView();
			ControllerMain.changeCurrentView(loadView);
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
		}
    }
}
