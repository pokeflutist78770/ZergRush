package views;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import controller.ControllerMain;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Player;
import model.Projectile;
import model.TowerGame;
import model.Mobs.Archon;
import model.Mobs.Mob;
import model.Mobs.SpeedAttribute;
import model.Towers.Depot;
import model.Towers.Marine;
import model.Towers.Range;
import model.Towers.Tank;
import model.Towers.Tower;

//A player can view information about an enemy by clicking one that has been 
//placed. Information should include the characteristics of that enemy.

// Information should be viewed in an inclusive manner within the game. Stay 
// away from Java Pop-ups!

// When the game begins in the map, it should be animated. That includes all
// sprites moving.

public class MapView extends StackPane implements Observer {
  public static final double ghostTowerSize=60;
  private GraphicsContext gcCommand;
  private Button backButton;
  private StackPane pane;
  private Image background;
  private Canvas canvas;
  private GraphicsContext gc;
  private VBox vBox;
  private HBox towerBox;

 
  private HBox pauseBox;
  private TowerButton tower1;
  private TowerButton tower2;
  private TowerButton tower3;

  private Button upgradeButton;
  private Button upgrade;
  private Button pause;
  private GridPane gameGrid;
  private Label wave;
  private Label health;
  private Label cash;
  private Label kills;
  private Label waveNum;
  private Label healthNum;
  private Label cashNum;
  private Label killsNum;
  private GridPane updateGrid;
  private HBox statusBox;
  private Label status;
  private Label attr1;
  private Label attr2;
  private Label attr3;
  private Label attr4;
  private Label attr5;
  private Label attr6;
  private String attr1Text;
  private String attr2Text;
  private String attr3Text;
  private String attr4Text;
  private String attr5Text;
  private String attr6Text;
  private int updateCount = 0;
  private double gameSpeed;
  
  private boolean towerSelected;
  private double towerX;
  private double towerY;
  private Tower currentTower;
  
  private boolean towerPlacement;
  private Point mousePos;
  private boolean isValidPos;
  private Range currRange;
  private Image currTower;
  private String currName;
  
  private Player thePlayer;
  private DecimalFormat formatter;
  private TowerGame theGame;
  
  
  /**
  * Constructor for MapVew. Provides a HUD and view of the Map to illustrate current game
  * state. HUD includes a Command Panel for game stats (Player HP, Cash, Kills, Difficulty),
  * tower purchases, tower upgrades, and status updates (click on an item in game, warning
  * messages) Takes a Button for implementation of a Back Button to Main Menu.
   * @param tg 
  * 
  * @param Button back
  *          - Back Button returns to Main Menu
  */
  public MapView(Button back, TowerGame tg) {
    
    theGame = tg;
    
    //variables for towewr placement
	mousePos=new Point(0,0);	
	towerPlacement=false;
	isValidPos=true;
	currRange=Range.DEMO_RANGE;
	currName="";
	
	// variables for tower selection
	towerSelected = false;
	towerX = 0;
	towerY = 0;
	
    vBox = new VBox();
    towerBox = new HBox();
    backButton = back;
    pane = new StackPane();
    canvas = new Canvas(800, 800);
    EventHandler<MouseEvent> mouseHandler=new mouseHandler();
    canvas.setOnMouseMoved(mouseHandler);
    canvas.setOnMouseClicked(mouseHandler);
    gc = canvas.getGraphicsContext2D();
    StackPane.setAlignment(canvas, Pos.TOP_CENTER);
    background = new Image("file:assets/images/map/demoMap.png", false);
    formatter = new DecimalFormat("#,###");

    // Command Panel - Black Background
    Canvas commandCanvas = new Canvas(800, 880);
    EventHandler<MouseEvent> mouseHandler1=new mouseHandler();
    commandCanvas.setOnMouseMoved(mouseHandler1);
    commandCanvas.setOnMouseClicked(mouseHandler1);
    gcCommand = commandCanvas.getGraphicsContext2D();
    gcCommand.setFill(Color.BLACK);
    gcCommand.setStroke(Color.BLACK);
    gcCommand.fillRect(0, 0, commandCanvas.getWidth(), commandCanvas.getHeight());

    EventHandler<ActionEvent> towerButtonHandler=new towerButtonHandler();
    EventHandler<ActionEvent> pauseButtonHandler = new pauseButtonHandler();
    
    // Pause Button
    pause = new Button("Pause");
    pause.setStyle("-fx-font: 14 serif; -fx-base: #000000;");
    pause.setMinWidth(50);
    pause.setMinHeight(10);
    pause.setOnAction(pauseButtonHandler);
    
    pauseBox = new HBox();
    pauseBox.getChildren().add(pause);
    pauseBox.setPadding(new Insets(767,0,0,5));
    pauseBox.setPickOnBounds(false);
    
    // Tower1 Button
    Image tower1Image = new Image("file:assets/images/tower/marine.png", false);
    ImageView iv1 = new ImageView(tower1Image);
    iv1.setFitHeight(37);
    iv1.setFitWidth(37);
    tower1 = new TowerButton("", iv1, "Marine", Range.MEDIUM_RANGE, Marine.COST);
    tower1.setOnAction(towerButtonHandler);
    tower1.setStyle("-fx-base: #808080;");

    // Tower2 Button
    Image tower2Image = new Image("file:assets/images/tower/depot.png", false);
    ImageView iv2 = new ImageView(tower2Image);
    iv2.setFitHeight(37);
    iv2.setFitWidth(37);
    tower2 = new TowerButton("", iv2, "Depot", Range.LARGE_RANGE, Depot.COST);
    tower2.setOnAction(towerButtonHandler);
    tower2.setStyle("-fx-base: #808080;");
    
    // Tower3 Button
    Image tower3Image = new Image("file:assets/images/tower/tank.png", false);
    ImageView iv3 = new ImageView(tower3Image);
    iv3.setFitHeight(37);
    iv3.setFitWidth(37);
    tower3 = new TowerButton("", iv3, "Tank", Range.DEMO_RANGE, Tank.COST);
    tower3.setOnAction(towerButtonHandler);
    tower3.setStyle("-fx-base: #808080;");

    // Purchase Button
    upgradeButton = new Button("Upgrade");
    upgradeButton.setMinWidth(30);
    upgradeButton.setMinHeight(10);
    upgradeButton.setStyle("-fx-font: 14 serif; -fx-base: #808080;");

    // Canvas Border
    gc.setLineDashes(5);
    gc.setStroke(Color.GRAY);
    gc.setLineWidth(3);

    // Draw background
    gc.drawImage(background, 0.0, 0.0);

    // Add upgrade button
    backButton.setMaxWidth(100);
    vBox.getChildren().add(upgradeButton);
    vBox.setPadding(new Insets(828, 0, 0, 460));

    // Add Tower Buttons
    towerBox.getChildren().add(tower1);
    towerBox.getChildren().add(tower2);
    towerBox.getChildren().add(tower3);
    towerBox.setPadding(new Insets(818, 0, 0, 265));
    towerBox.setSpacing(12);
    towerBox.setPickOnBounds(false);

    // GameGrid
    gameGrid = new GridPane();
    gameGrid.setPickOnBounds(false);
    gameGrid.setPadding(new Insets(818, 0, 0, 10));

    // Wave Difficulty Label
    wave = new Label("Wave:");
    wave.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ff0000;");
    gameGrid.add(wave, 0, 0);

    waveNum = new Label("1");
    waveNum.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ff0000;");
    waveNum.setPadding(new Insets(0, 0, 0, 10));
    gameGrid.add(waveNum, 1, 0);

    // Health Remaining Label
    health = new Label("Player Health:");
    health.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ff0000;");
    health.setPadding(new Insets(0, 0, 0, 20));
    gameGrid.add(health, 2, 0);

    healthNum = new Label("000");
    healthNum.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ff0000;");
    healthNum.setPadding(new Insets(0, 0, 0, 3));
    gameGrid.add(healthNum, 3, 0);

    // Cash Label
    cash = new Label("Cash:");
    cash.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ffffff;");
    gameGrid.add(cash, 0, 1);

    cashNum = new Label("$100");
    cashNum.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ffffff;");
    cashNum.setPadding(new Insets(0, 0, 0, 10));
    gameGrid.add(cashNum, 1, 1);

    // Kills Label
    kills = new Label("Total Kills:");
    kills.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    kills.setPadding(new Insets(0, 0, 0, 20));
    gameGrid.add(kills, 2, 1);

    killsNum = new Label("000");
    killsNum.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    killsNum.setPadding(new Insets(0, 0, 0, 3));
    gameGrid.add(killsNum, 3, 1);

    // Update Grid
    updateGrid = new GridPane();
    updateGrid.setPickOnBounds(false);
    updateGrid.setPadding(new Insets(805, 0, 0, 605));

    /**
     * Update Grid Layout
     * 
     * attr1 attr2 
     * attr3 attr4 
     * attr5 attr6
     * 
     */

    // attr1 Update Grid
    attr1Text = "";
    attr1 = new Label();
    attr1.setStyle("-fx-font: 15 serif; -fx-text-fill: #ff0000;");
    updateGrid.add(attr1, 0, 0);

    // attr2 Update Grid
    attr2Text = "";
    attr2 = new Label();
    attr2.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    attr2.setPadding(new Insets(0, 0, 0, 40));
    updateGrid.add(attr2, 1, 0);

    // attr3 Update Grid
    attr3Text = "";
    attr3 = new Label();
    attr3.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    updateGrid.add(attr3, 0, 1);

    // attr4 Update Grid
    attr4Text = "";
    attr4 = new Label();
    attr4.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    attr4.setPadding(new Insets(0, 0, 0, 40));
    updateGrid.add(attr4, 1, 1);

    // attr5 Update Grid
    attr5Text = "";
    attr5 = new Label();
    attr5.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    updateGrid.add(attr5, 0, 2);

    // attr6 Update Grid
    attr6Text = "";
    attr6 = new Label();
    attr6.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    attr6.setPadding(new Insets(0, 0, 0, 40));
    updateGrid.add(attr6, 1, 2);

    // Status Label
    status = new Label();
    status.setStyle("-fx-font: 15 serif; -fx-text-fill: #32cd32;");

    // Status Box
    statusBox = new HBox();
    statusBox.getChildren().add(status);
    statusBox.setPadding(new Insets(860, 0, 0, 550));
    statusBox.setPickOnBounds(false);

    pane.getChildren().add(commandCanvas);
    pane.getChildren().add(canvas);
    pane.getChildren().add(vBox);
    pane.getChildren().add(towerBox);
    pane.getChildren().add(gameGrid);
    pane.getChildren().add(updateGrid);
    pane.getChildren().add(statusBox);
    pane.getChildren().add(pauseBox);
    
    this.getChildren().add(pane);
    this.setOnMouseClicked(mouseHandler);
    this.setOnMouseMoved(mouseHandler);
  }
  
  
   /**
   * Set the map background on canvas.
   * 
   * @param filepath
   *          - String representing the image file of map
   * @return None
   */
  public void setMapSelection(String filepath)
  {
    background = new Image(filepath, ControllerMain.GUI_SIZE, ControllerMain.GUI_SIZE, true, true);
    gc.drawImage(background, 0.0, 0.0);
  }
  

  /**
  * Set the amount of Cash for Player.
  * cashEarned is int instance variable.
  * cashNum is Label on Command Panel.
  * 
  * @param num
  * 	- Integer representing the Player Cash.
  * 
  * @return None
  */
  public void setCashNum(int num) {
	// Sets the Player Cash Label
    cashNum.setText("$" + String.valueOf(theGame.getCash()));
  }

  
  /**
  * Set the amount of Health Points for Player.
  * Adjusts the healthNum Label on Command Panel.
  * 
  * @param hp
  * 	- Double value representing the HP for Player.
  * 
  * @return None
  */
  public void setHealthNum(double hp) {
    healthNum.setText(String.valueOf(hp));
  }

  
  /**
  * Set the Wave Difficulty on Command Panel.
  * Adjusts the waveNum Label on Command Panel.
  * 
  * @param Difficulty
  * 	- String representing the chosen difficulty by Player.
  * 
  * @return None
  */
  public void setWaveNum(String difficulty) {
    waveNum.setText(difficulty);
  }
  
  
  /**
  * Get the current game speed selected from
  * Menu View slider.
  * 
  * @param s
  * 	- A double [0-1] representing game speed
  * 
  * @return None
  */
  public void setGameSpeed(Double s)
  {
	  gameSpeed = s;
  }
  
  /**
  * Get the current Player as established in ControllerMain.
  * Set the passed in player to thePlayer.
  * 
  * @param p
  * 	- Player instantiated in Controller Main
  * 
  * @return None
  */
  public void setPlayer(Player p)
  {
	  thePlayer = p;
  }
  

  /**
  * Draws a mob given in its correct orientation and animation position.
  * 
  * @param mob Mob to be drawn
  */
  private void drawMob(Mob mob) {
    this.updateCount++;
    double sx = mob.getSX();
    double sy = mob.getSY();
    double sw = mob.getSW();
    double sh = mob.getSH();
    double delX = mob.getDelX();
    double delY = mob.getDelY();
    int animSteps = mob.getAnimationSteps();
    int stepCount = mob.getStepCount();
    double x = mob.getX() -(int)sw/2 ;
    double y = mob.getY() -(int)sh/2;
    double angle = mob.getDirectionAngle();

    double currentStep = stepCount % animSteps + 1;
    double currSY = sy + currentStep * delY;
    
    // Draw the Archon enemy on map
    if(mob instanceof Archon) {
      double nrgX = 385;
      double nrgY = 393;
      double nrgSteps = 4;
      double nrgCurrStep = stepCount % nrgSteps + 1;
      double nrgSX = nrgX + nrgCurrStep * 85;
      gc.drawImage(mob.getImage(), nrgSX, nrgY, sw, sh, x, y, sw, sh);
      
      gc.drawImage(mob.getImage(), sx, currSY, sw, sh, x, y, sw, sh);
      
      
      double swirlX = 343;
      double swirlY = 1047;
      double swirlSteps = 15;
      double swirlCurrStep = stepCount % swirlSteps + 1;
      double swirlSX = swirlX + swirlCurrStep * 85;
      gc.drawImage(mob.getImage(), swirlSX, swirlY, sw, sh, x, y, sw, sh);
      
      if (this.updateCount % 5 == 0) {
        mob.step();
      }
    } else {
      gc.drawImage(mob.getImage(), sx, currSY, sw, sh, x, y, sw, sh);
      
      if (this.updateCount % 5 == 0) {
        mob.step();
      }
    }

    
  }

  
  /**
  * drawMap Draws the overall map and mobs/towers every iteration 
  * 
  * @Param: None 
  * 
  * @return: None
  */
  
  public void drawMap() {
    gc.drawImage(background, 0, 0);
    Platform.runLater(new Runnable() {//TODO: fix this
      @Override
        public void run() {
          gc.drawImage(background, 0, 0);
          drawTowers();
          drawMobs();
          drawProjectiles();
  
          if(towerPlacement) {
            drawGhostTower();
          }
        }
      });

  }

  private void drawProjectiles() {
    for (Iterator<Projectile> itr = theGame.getProjectiles().iterator(); itr.hasNext(); ) {
      Projectile p  = itr.next();
      gc.drawImage(p.getImage(), 0,0, 1001, 1001, p.getX() - 15, p.getY() -15, 30, 30);
    }
  }


  private void drawMobs() {

    
    // Update Mobs' speed attribute if
    // Game Speed slider adjusted from MenuView
    // Game Speed slider set as default to 0%
    
    // ***Don't use SLOW speed as mobs will freeze***
    
    // 0% - NO CHANGE
    // 0-33% - NORMAL
    // 33-66% - FAST
    // 66-100% - VERY_FAST
    
    SpeedAttribute mobSpeed = SpeedAttribute.NORMAL;
    if (gameSpeed > 0.33 && gameSpeed < 0.66)
    {
      mobSpeed = SpeedAttribute.FAST;
    }
    else if (gameSpeed > 0.66)
    {
      mobSpeed = SpeedAttribute.VERY_FAST;
    }
    
    for (Iterator<Mob> itr = theGame.getMobs().iterator(); itr.hasNext(); ) {
      Mob m = itr.next();
      m.setSpeed(mobSpeed);
      drawMob(m);

    }
  }

  private void drawTowers() {
    for (Iterator<Tower> itr = theGame.getTowers().iterator(); itr.hasNext(); ) {
      Tower t = itr.next();
      gc.drawImage(t.getImage(), t.getX(), t.getY(),
          ghostTowerSize, ghostTowerSize); // TODO: Ben, will you center this graphic?
    }
  }
  
  
  public void checkTowers() {
	  if(theGame.getCash()>=tower1.getCost()) {
	    	tower1.setOpacity(1);
	    }
	  else {
	    tower1.setOpacity(.5);
	  }
	  
	  if(theGame.getCash()>=tower2.getCost()) {
	    tower2.setOpacity(1);
	  }
	  else {
	    tower2.setOpacity(.5);
	  }
	 
	  if(theGame.getCash()>=tower3.getCost()) {
		  tower3.setOpacity(1);
	  }
	  else {
		  tower3.setOpacity(.5);
	  }
  }

  /**
  * Draw a representation of Tower with green circle surrounding
  * to allow Player to choose a placement of Tower on Map.
  * 
  * @param None
  * 
  * @return None
  */
  public void drawGhostTower() {
	  double radius=currRange.toDouble();
	  
	  //tower can be placed
	  if(isValidPos) {
		  gc.setFill(Color.color(0, .5, 0, .5));
	  }
	  else {
		  gc.setFill(Color.color(.75, 0, 0, .5));
	  }
	  
	  gc.fillOval(mousePos.getX()-radius, mousePos.getY()-radius, 
			      radius*2, radius*2);
	  
	  gc.drawImage(currTower, mousePos.getX()-.5*ghostTowerSize, 
			       mousePos.getY()-.5*ghostTowerSize,
			       ghostTowerSize, ghostTowerSize);
  }
  
  public void drawTowerSelected()
  {
	  gc.setFill(Color.color(0, .5, 0, .5));
	  gc.fillOval(towerX-5, towerY-5, 70, 70);
  }
  
  public void setTowerSelected(Tower t, double x, double y)
  {
	  int upgradeCost;
	  String range;
	  towerSelected = true;
	  towerX = x;
	  towerY = y;
	  
	  // Update Command Panel
	  attr1Text = "Tower";
	  
	  if (t instanceof Marine)
	  {
		  attr2Text = "Marine";
		  upgradeCost = 100;
	  }
	  else if (t instanceof Depot)
	  {
		  attr2Text = "Depot";
		  upgradeCost = 150;
	  }
	  else
	  {
		  attr2Text = "Tank";
		  upgradeCost = 200;
	  }
	  
	  attr3Text = "Upgrade Cost:";
	  attr4Text = "$"+String.valueOf(t.getCost()+upgradeCost);
	  attr5Text = "Range:";
	  attr6Text = String.valueOf(formatter.format(t.getRange()));
  }

  /**
  * Button handler to place either Tower1, Tower2, or Tower3 on Map.
  * Get Tower image according to Button clicked.
  * Update instance variables currRange, currTower and currName.
  * 
  * @param None
  * 
  * @return None
  */
  private class towerButtonHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent e) {
		TowerButton button=(TowerButton) e.getSource();
		
		towerSelected = false;
		
		//user clicks on the same button
		if(currName.equals(button.getName()) && towerPlacement) {
			towerPlacement=false;
			return;
		} else if (theGame.getCash() < 50){
		  return;
		}
		if(button.canBeBought(theGame.getCash())) {
			towerPlacement=true;
			currRange=button.getRange();
			currTower=button.getImage().getImage();
			currName=button.getName();
			System.out.println("BUTTON CLICKED\n"+"TP: "+towerPlacement);
		}
	} 
  } 
  
  /**
  * Button handler to pause the game.
  * Text in button will turn red when game is paused.
  * Text will change to white when game is not paused.
  * 
  * @param None
  * 
  * @return None
  */
  private class pauseButtonHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent e) {
		
		// Set button to red text
    	if (!theGame.isPaused())
    	{
    		pause.setStyle("-fx-text-fill: #ff0000; -fx-font: 14 serif; -fx-base: #000000;");
    		theGame.pause();
    	}
    	// Set button to white text
    	else
    	{
    		pause.setStyle("-fx-text-fill: #ffffff; -fx-font: 14 serif; -fx-base: #000000;");
    		theGame.unPause();
    	}
		
	} 
  } 
  
  
  /**
  * Mouse handler to place a Tower on click or hover with Tower for placement.
  * Get Tower image according to Button clicked.
  * Add Tower to list on ControllerMain when clicked.
  * 
  * @param None
  * 
  * @return None
  */
  private class mouseHandler implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent e) {
	  //need to update current mouse position
	  if(e.getEventType()==MouseEvent.MOUSE_MOVED) {
		mousePos.setLocation(e.getX(), e.getY());
	  }
	  
	  else if(e.getEventType()==MouseEvent.MOUSE_CLICKED) {

		  attr1Text = "";
		  attr2Text = "";
		  attr3Text = "";
		  attr4Text = "";
		  attr5Text = "";
		  attr6Text = "";
		  
		if(towerPlacement) {
			towerPlacement=false;
			Tower newTower=null;
			//currName="";
			double cost=0;
			
			//the different buttons
			if(currName.equals("Marine")) {
				cost=tower1.getCost();
				newTower=new Marine(new Point((int)(mousePos.getX()-.5*ghostTowerSize), 
					                		    (int)(mousePos.getY()-.5*ghostTowerSize)), theGame);
			}
			else if( currName.equals("Depot")){
				cost=tower2.getCost();
				newTower=new Depot(new Point((int)(mousePos.getX()-.5*ghostTowerSize), 
					                		    (int)(mousePos.getY()-.5*ghostTowerSize)), theGame);
			}
			else if(currName.equals("Tank")) {
				cost=tower3.getCost();
				newTower=new Tank(new Point((int)(mousePos.getX()-.5*ghostTowerSize), 
					                		    (int)(mousePos.getY()-.5*ghostTowerSize)), theGame);
			}
			theGame.add(newTower);
			theGame.decrementCash(cost);

		}
		else {
			// Determine if any Towers in ControllerMain match MouseClick coordinates
			
			double mousePosX = mousePos.getX()-.5*ghostTowerSize;
			double mousePosY = mousePos.getY()-.5*ghostTowerSize;
			
			for (Tower t : theGame.getTowers())
			{
				towerSelected = false;
				if (t.getX() >= mousePosX-20 && t.getX() <= mousePosX+20)
				{
					if (t.getY() >= mousePosY-20 && t.getY() <= mousePosY+20)
					{
						setTowerSelected(t, t.getX(), t.getY());
						break;
					}
				}	
			}
		}
		
		System.out.println("MOUSE X: "+mousePos.getX());
		System.out.println("MOUSE Y: "+mousePos.getY());
		System.out.println("Mouse Clicked");
	  }
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    drawMap();

    double health = thePlayer.getHP() / 100;
    String healthStr = formatter.format(health);
    
    String cashStr = formatter.format(theGame.getCash());

    
    // UI can't be updated on a non-application thread
    // Since drawMap() is called from a non-application thread,
    // add this thread to the event queue to be called later
    
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            // if you change the UI, do it here !
          healthNum.setText(healthStr);
          killsNum.setText(String.valueOf(theGame.getKillCount()));
          cashNum.setText("$"+cashStr);
        }
    });
    
    
  }
}




