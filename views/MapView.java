package views;

import java.awt.Point; 
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import controller.ControllerMain;
import controller.PersistenceMaster;
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
import javafx.scene.paint.Color;
import model.Archon;
import model.BattleCruiser;
import model.DarkTemplar;
import model.DepotTower;
import model.Hydralisk;
import model.Marine;
import model.MarineTower;
import model.Metric;
import model.Mob;
import model.Player;
import model.Projectile;
import model.RangeAttribute;
import model.SpeedAttribute;
import model.TankTower;
import model.Tower;
import model.TowerGame;
import model.Ultralisk;
import model.Wraith;
import model.Zealot;
import model.Zergling;

//A player can view information about an enemy by clicking one that has been 
//placed. Information should include the characteristics of that enemy.

// Information should be viewed in an inclusive manner within the game. Stay 
// away from Java Pop-ups!

// When the game begins in the map, it should be animated. That includes all
// sprites moving.

/**
 * The MapView class gives the view of the map. It is displayed when the user is playing a game.
 * @author J. David Taylor
 *
 */
public class MapView extends StackPane implements Observer {
  public static final double ghostTowerSize=60;
  private GraphicsContext gcCommand;
  private Button backButton;
  private StackPane pane;
  private Image background;
  private Canvas canvas;
  private GraphicsContext gc;
  private HBox towerBox;

 
  private HBox pauseBox;
  private TowerButton tower1;
  private TowerButton tower2;
  private TowerButton tower3;

  private Button upgradeButton;
  private Button upgrade;
  private Button pause;
  private Button save;
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
  private String statusText;
  private String attr1Text;
  private String attr2Text;
  private String attr3Text;
  private String attr4Text;
  private String attr5Text;
  private String attr6Text;
  private int updateCount = 0;
  private double gameSpeed;
  
  private boolean saveSelected;
  private boolean towerSelected;
  private boolean mobSelected;
  private boolean firstAttack = true;
  private double selectedX;
  private double selectedY;
  private Tower currentTower;
  private Mob currentMob;
  
  private boolean towerPlacement;
  private Point mousePos;
  private boolean isValidPos;
  private RangeAttribute currRange;
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
	currRange=RangeAttribute.DEMO_RANGE;
	currName="";
	
	// variables for tower/mob selection
	towerSelected = false;
	mobSelected=false;
	selectedX = 0;
	selectedY = 0;
	
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
    EventHandler<MouseEvent> towerButtonMouseHandler=new towerButtonMouseHandler();
    EventHandler<ActionEvent> upgradeButtonHandler=new upgradeHandler();
    EventHandler<ActionEvent> pauseButtonHandler = new pauseButtonHandler();
    EventHandler<ActionEvent> saveButtonHandler = new saveButtonHandler();
    
    // Back Button
    backButton.setStyle("-fx-font: 14 serif; -fx-base: #000000;");
    backButton.setMinWidth(50);
    backButton.setMinHeight(10);
    
    // Pause Button
    pause = new Button("Pause");
    pause.setStyle("-fx-font: 14 serif; -fx-base: #000000;");
    pause.setMinWidth(50);
    pause.setMinHeight(10);
    pause.setOnAction(pauseButtonHandler);
    
    // Save Button
    save = new Button("Save");
    save.setStyle("-fx-font: 14 serif; -fx-base: #000000;");
    save.setMinWidth(50);
    save.setMinHeight(10);
    save.setOnAction(saveButtonHandler);
    
    pauseBox = new HBox();
    pauseBox.getChildren().addAll(pause, save, backButton);
    pauseBox.setPadding(new Insets(770,0,0,5));
    pauseBox.setSpacing(5);
    pauseBox.setPickOnBounds(false);
    
    String pic="";
    
    if (MenuView.getModeSelection().equals("Fun")) {
    	pic="file:assets/images/tower/cage.png";
    }
    else {
    	pic="file:assets/images/tower/marine.png";
    }
    
    // Tower1 Button  
    Image tower1Image = new Image(pic, false);
    ImageView iv1 = new ImageView(tower1Image);
    iv1.setFitHeight(37);
    iv1.setFitWidth(37);
    tower1 = new TowerButton("", iv1, "Marine", 
    		                 MarineTower.BASE_RANGE, 
    		                 MarineTower.COST);
    tower1.setOnAction(towerButtonHandler);
    tower1.setOnMouseEntered(towerButtonMouseHandler);
    tower1.setOnMouseExited(towerButtonMouseHandler);
    tower1.setStyle("-fx-base: #808080;");

    
    if (MenuView.getModeSelection().equals("Fun")) {
    	pic="file:assets/images/tower/doge.png";
    }
    else {
    	pic="file:assets/images/tower/depot.png";
    }
    
    // Tower2 Button
    Image tower2Image = new Image(pic, false);
    ImageView iv2 = new ImageView(tower2Image);
    iv2.setFitHeight(37);
    iv2.setFitWidth(37);
    tower2 = new TowerButton("", iv2, "Depot", 
    		                 DepotTower.BASE_RANGE, 
    		                 DepotTower.COST);
    tower2.setOnAction(towerButtonHandler);
    tower2.setOnMouseEntered(towerButtonMouseHandler);
    tower2.setOnMouseExited(towerButtonMouseHandler);
    tower2.setStyle("-fx-base: #808080;");
    
    
    if (MenuView.getModeSelection().equals("Fun"))
    	pic = "file:assets/images/tower/shrek.png";
    else
    	pic = "file:assets/images/tower/tank.png";
    
    // Tower3 Button
    Image tower3Image = new Image(pic, false);
    ImageView iv3 = new ImageView(tower3Image);
    iv3.setFitHeight(37);
    iv3.setFitWidth(37);
    tower3 = new TowerButton("", iv3, "Tank", 
    		                 TankTower.BASE_RANGE, 
    		                 TankTower.COST);
    tower3.setOnAction(towerButtonHandler);
    tower3.setOnMouseEntered(towerButtonMouseHandler);
    tower3.setOnMouseExited(towerButtonMouseHandler);
    tower3.setStyle("-fx-base: #808080;");

    if (MenuView.getModeSelection().equals("Fun"))
    	pic = "guy.jpeg";
    else
    	pic = "upgrade.png";
    
    // Upgrade Button
    Image upgradeImage = new Image("file:assets/images/tower/"+pic, false);
    ImageView iv4 = new ImageView(upgradeImage);
    iv4.setFitHeight(35);
    iv4.setFitWidth(35);
    
    upgradeButton = new Button("", iv4);
    upgradeButton.setOnAction(upgradeButtonHandler);
    upgradeButton.setVisible(false);
    upgradeButton.setMinWidth(30);
    upgradeButton.setMinHeight(10);
    upgradeButton.setStyle("-fx-font: 14 serif; -fx-base: #808080;");

    // Canvas Border
    gc.setLineDashes(5);
    gc.setStroke(Color.GRAY);
    gc.setLineWidth(3);

    // Draw background
    gc.drawImage(background, 0.0, 0.0);

    // Add Tower Buttons
    towerBox.getChildren().add(tower1);
    towerBox.getChildren().add(tower2);
    towerBox.getChildren().add(tower3);
    towerBox.getChildren().add(upgradeButton);
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

    healthNum = new Label(""+theGame.getPlayer().getHP());
    healthNum.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ff0000;");
    healthNum.setPadding(new Insets(0, 0, 0, 3));
    gameGrid.add(healthNum, 3, 0);

    // Cash Label
    cash = new Label("Cash:");
    cash.setStyle("-fx-font: 15.5 serif; -fx-text-fill: #ffffff;");
    gameGrid.add(cash, 0, 1);

    cashNum = new Label("$"+theGame.getPlayer().getCash());
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
    statusText = "";
    status.setStyle("-fx-font: 15 serif; -fx-text-fill: #32cd32;");

    // Status Box
    statusBox = new HBox();
    statusBox.getChildren().add(status);
    statusBox.setPadding(new Insets(860, 0, 0, 550));
    statusBox.setPickOnBounds(false);

    pane.getChildren().add(commandCanvas);
    pane.getChildren().add(canvas);
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
    cashNum.setText("$" + String.valueOf(theGame.getPlayer().getCash()));
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
    if(MenuView.getModeSelection().equals("Fun")) {
    	gc.drawImage(mob.getImage(), x, y, sw, sh);
    	return;
    }
    
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
      
      if (!theGame.isPaused() && this.updateCount % 5 == 0) {
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
    Platform.runLater(new Runnable() {
      @Override
        public void run() {
          gc.drawImage(background, 0, 0);
          
          if(towerSelected) {
        	  drawTowerSelected();
          }
          if(mobSelected && !currentMob.isDone()) {
        	  drawMobSelected();
          }
          
          drawTowers();
          drawMobs();
          drawProjectiles();
          checkTowers();
          
          if(towerPlacement) {
            drawGhostTower();
          }
        }
      });
  }


  /**
   * Iterates through the set of projectiles and draws each one.
   */
  private void drawProjectiles() {
    for (Iterator<Projectile> itr = theGame.getProjectiles().iterator(); itr.hasNext(); ) {
      Projectile p  = itr.next();
      gc.drawImage(p.getImage(), 0,0, 400, 400, p.getX() - 15, p.getY() -15, 
    		       p.getProjSize(), p.getProjSize());
    }
  }


  /**
   * Iterates through the set of mobs and draws each one.
   */
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

  /** 
   * Iterates through the set of towers and draws each one.
   */
  private void drawTowers() {
    for (Iterator<Tower> itr = theGame.getTowers().iterator(); itr.hasNext(); ) {
      Tower t = itr.next();
      gc.drawImage(t.getImage(), t.getX(), t.getY(),
          ghostTowerSize, ghostTowerSize); // TODO: Ben, will you center this graphic?
    }
  }
  
  
  /**
   * checkTowers
   * Compares Player cash with the cost to buy specific towers and updates the
   * button GUI in order to directly show a user if a tower is buyable, greyed out if
   * player does not have enough cash
   * 
   * @param None
   * @return None
  */
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


  /**
   * Draws the green oval around a tower to be placed.
   */
  public void drawTowerSelected()
  {
	  gc.setFill(Color.color(0, .5, 0, .5));
	  gc.fillOval(selectedX-currentTower.getRange()+.5*ghostTowerSize, 
			      selectedY-currentTower.getRange()+.5*ghostTowerSize, 
			      currentTower.getRange()*2, currentTower.getRange()*2);
  }


  /**
   * drawMobSelected
   * draws a shape around the selected mob
   */
  public void drawMobSelected() {
	  gc.setFill(Color.color(.5,.5,0,.5));
	  gc.fillOval(currentMob.getX()-.5*currentMob.getSW()-10, 
			      currentMob.getY()-.5*currentMob.getSH()-10,
			      currentMob.getSW()+20, currentMob.getSH()+20);
  }


  /*
   * updteLabels
   * updates labels to display the current text 
  */
  private void updateLabels() {
	  attr1.setText(attr1Text);
	  attr2.setText(attr2Text);
	  attr3.setText(attr3Text);
	  attr4.setText(attr4Text);
	  attr5.setText(attr5Text);
	  attr6.setText(attr6Text);
  }


  /**
   * setTowerSelected
   * Sets a currently selected tower for the user, displaying stats and range of the tower
   * 
   * @param t: Tower representing tower to be selected
   * @param x: double representing x position of the tower
   * @param y: double representing y position of the tower
   * 
   * @return None
  */
  public void setTowerSelected(Tower t, double x, double y)
  {
	  int upgradeCost;
	  String range;
	  towerSelected = true;
	  selectedX = x;
	  selectedY = y;
	  currentTower=t;
	  
	  // Update Command Panel
	  attr1Text = "Tower";
	  
	  if (t instanceof MarineTower)
	  {
		  attr2Text = "Marine";
		  upgradeCost = 100;
	  }
	  else if (t instanceof DepotTower)
	  {
		  attr2Text = "Depot";
		  upgradeCost = 150;
	  }
	  else
	  {
		  attr2Text = "Tank";
		  upgradeCost = 200;
	  }
	  
	  if(!currentTower.isFullyUpgraded()) {
		  upgradeButton.setVisible(true);
	  }
	  else {
		  upgradeButton.setVisible(false);
	  }
	  
	  attr3Text = "Upgrade Cost:";
	  
	  //in case of tower being upgraded all the way, dont display upgrade cost
	  if(currentTower.isFullyUpgraded()) {
		  attr4Text = "Fully Upgraded";
	  }
	  else {
		  attr4Text = "$"+String.valueOf(currentTower.getUpgradeCost());
	  }
	  
	  attr5Text = "Range:";
	  attr6Text = String.valueOf(formatter.format(t.getRange())); 
	  drawMap();
  }


  /**
   * setMobSelected
   * Sets the current selected mob to highlight and show stats
   * @param mob: Mob object selected by the user
   * @param x: x position of the mob
   * @param y: y position of the mob
   */
  public void setMobSelected(Mob mob, double x, double y) {
	  double hp=mob.getHP();
	  String range;
	  mobSelected = true;
	  selectedX = x;
	  selectedY = y;
	  currentMob=mob;
	  
	  // Update Command Panel
	  attr1Text = "Mob";
	  
	  if (mob instanceof Marine) {
		  attr2Text = "Marine";
	  }
	  
	  else if (mob instanceof Archon) {
		  attr2Text = "Archon";
	  }
	  
	  else if(mob instanceof BattleCruiser){
		  attr2Text = "BattleCruiser";
	  }
	  
	  else if(mob instanceof DarkTemplar) {
		  attr2Text="DarkTemplar";
	  }
	  
	  else if(mob instanceof Hydralisk){
		  attr2Text = "Hydralisk";
	  }
	  
	  else if(mob instanceof Ultralisk){
		  attr2Text = "Ultralisk";
	  }
	  
	  else if(mob instanceof Wraith){
		  attr2Text = "Wraith";
	  }
	  
	  else if(mob instanceof Zealot){
		  attr2Text = "Zealot";
	  }
	  
	  else if(mob instanceof Zergling){
		  attr2Text = "Zergling";
	  }
	  
	  attr3Text = "Health Points:";
	  attr4Text = ""+hp;
	  attr5Text = "Attack:";
	  attr6Text = String.valueOf(formatter.format(mob.getAttack()));
  }


  /**
   * upgradeHandler
   * handles all upgrade procedures for the given tower
   * @author aagua
   *
  */
  private class upgradeHandler implements EventHandler<ActionEvent>{
	@Override
	public void handle(ActionEvent e) {
		Button button=(Button) e.getSource();
		
		System.out.println("------UPGRADE-----");
		System.out.println("\t Player: "+thePlayer.getCash());
		System.out.println("\t Tower Cost: "+currentTower.getUpgradeCost());
		System.out.println("\t TowerRank: "+currentTower.getRank());
		//user wants to upgrade a currently selected tower
		//user can actually upgrade
		if(towerSelected  && thePlayer.getCash()>=currentTower.getUpgradeCost()) {
			thePlayer.decrementCash(currentTower.getUpgradeCost());
			currentTower.upgrade();
			
			if(MenuView.getModeSelection().equals("Fun")) {
				ControllerMain.soundEffects.get("dankUpgrade").play();
			}
			else {
				ControllerMain.soundEffects.get("upgrade").play();
			}
				
			if(currentTower.isFullyUpgraded()) {
				attr4Text = "MaxRank";
				upgradeButton.setVisible(false);  
			}
			else {
				attr4Text = "$"+String.valueOf(currentTower.getUpgradeCost());
			}
			updateLabels();
		}
		
		//player doesnt have enough money
		else if(thePlayer.getCash()<currentTower.getUpgradeCost()) {
			ControllerMain.soundEffects.get("mins").play();
		}
		drawMap();
	}
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
		
		mobSelected=false; 
		towerSelected = false;
		
		//user clicks on the same button as previously clicked, so cancel
		if(currName.equals(button.getName()) && towerPlacement) {
			towerPlacement=false;
			return;
			
		} else if (theGame.getCash() < 50){
		  return;
		}
		
		//user can buy the tower
		if(button.canBeBought(theGame.getCash())) {
			towerPlacement=true;
			currRange=button.getRange();
			currTower=button.getImage().getImage();
			currName=button.getName();
			System.out.println("BUTTON CLICKED\n"+"TP: "+towerPlacement);
		} else {
		  ControllerMain.soundEffects.get("mins").play();
		}
		
		drawMap();
	} 
  } 
  
  
  /**
   * Display tower properties when button is hovered over with mouse.
   * Tower properties will show tower name, cost and attack type.
   * Update command panel with tower hover.
   * 
   * @return None
   */
  private class towerButtonMouseHandler implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent e) {
		
		if(e.getEventType()==MouseEvent.MOUSE_ENTERED) {
			TowerButton button=(TowerButton) e.getTarget();
		  	attr1Text = "Tower";
		  	attr2Text=button.getName();
			attr3Text = "Cost:";
			attr4Text= ""+button.getCost();
			attr5Text = "Type:";
			
			if (button.getName().equals("Marine"))
			{
			    attr6Text = "Poison";
			}
			else if (button.getName().equals("Depot"))
			{
				attr6Text = "Ice";
			}
			else
			{ 
				attr6Text = "Fire";
			}
			  
			updateLabels();
		}
		else if(e.getEventType()==MouseEvent.MOUSE_EXITED) {
			attr1Text = "";
		  	attr2Text="";
			attr3Text = "";
			attr4Text= "";
			attr5Text = "";
			attr6Text = "";
			
			updateLabels();
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
    		ControllerMain.soundEffects.get("paused").play();
    		theGame.pause();
    	}
    	// Set button to white text
    	else
    	{
    		pause.setStyle("-fx-text-fill: #ffffff; -fx-font: 14 serif; -fx-base: #000000;");
    		ControllerMain.soundEffects.get("resumed").play();
    		theGame.unPause();
    	}
		
	} 
  } 
  
  
  /**
  * Button handler to save the game.
  * Game status label will notify user that game has been saved.
  * Status label will clear after 3 seconds.
  * 
  * @param None
  * 
  * @return None
  */
  private class saveButtonHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent e) {
		
		// Set status text to Saved
		statusText = "Game saved.";
		PersistenceMaster.saveGame(theGame);
		
		// After 3 seconds, clear status text
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

		        @Override
		        public void run() {
		            Platform.runLater(new Runnable() {
		                @Override
		                public void run() {
		                    statusText = "";
		                }
		            });

		        }
		    }, 3000);
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
		towerSelected=false;
		mobSelected=false;
		upgradeButton.setVisible(false);
		  
		if(towerPlacement) {
			placeTower();
		}
		else {
			
			// Determine if any Towers in ControllerMain match MouseClick coordinates
			//towerSelected = false;
			double mousePosX = mousePos.getX()-.5*ghostTowerSize;
			double mousePosY = mousePos.getY()-.5*ghostTowerSize;
			
			Tower closestTower=null;
			double tempDist=0;
			double closestTowerDistance=Double.MAX_VALUE;
			
			Point mousePoint=new Point((int) mousePosX, (int) mousePosY);
			Point towerPoint=null;
			
			//find the closest tower to the users click
			for (Tower t : theGame.getTowers()) {
				
				towerPoint=new Point((int) t.getX(), (int) t.getY());
				tempDist=Metric.distanceSquared(mousePoint,  towerPoint);
				
				//Compares it to the square of what we want as the click radius
				if(isClosest(tempDist, closestTowerDistance)) {
					closestTower=t;
					closestTowerDistance=tempDist;
				}
			}
			
			Mob closestMob=null;
			double closestMobDistance=Double.MAX_VALUE;
			Point mobPoint=null;
			
			//find the closest mob to the user click
			for(Mob mob: theGame.getMobs()) {
				mobPoint=new Point((int) mob.getX(), (int) mob.getY());
				
				tempDist=Metric.distanceSquared(mousePoint, mobPoint);
				
				if(isClosest(tempDist, closestMobDistance)) {
					closestMob=mob;
					closestMobDistance=tempDist;
				}
			}
			
			//user clicked on a tower
			if(closestTowerDistance<=closestMobDistance && closestTower!=null) {
				setTowerSelected(closestTower, closestTower.getX(), closestTower.getY());
			}
			
			//user clicked on a mob
			else if(closestMobDistance<closestTowerDistance) {
				setMobSelected(closestMob, closestMob.getX(), closestMob.getY());
			}
		}
		
		updateLabels();
		System.out.println("MOUSE X: "+mousePos.getX());
		System.out.println("MOUSE Y: "+mousePos.getY());
		System.out.println("Mouse Clicked");
	  }
	  
	  if(theGame.isPaused()) {
		  drawMap();
	  }
    }
	
	
	/*
	 * placeTower
	 * goes and places a tower on the mouse x and y position
 	 * Parameters: None
 	 * Returns: None
	*/
	private void placeTower() {
		towerPlacement=false;
		Tower newTower=null;
		double cost=0;
		
		//the different buttons   
		if(currName.equals("Marine")) {
			cost=tower1.getCost();
			newTower=new MarineTower(new Point((int)(mousePos.getX()-.5*ghostTowerSize),
					                           (int)(mousePos.getY()-.5*ghostTowerSize)), 
									 theGame, MenuView.getModeSelection().equals("Fun"));
		}
		else if( currName.equals("Depot")){
			cost=tower2.getCost();
			newTower=new DepotTower(new Point((int)(mousePos.getX()-.5*ghostTowerSize), 
				                		    (int)(mousePos.getY()-.5*ghostTowerSize)), 
					                theGame, MenuView.getModeSelection().equals("Fun"));
		}
		else if(currName.equals("Tank")) {
			cost=tower3.getCost();
			newTower=new TankTower(new Point((int)(mousePos.getX()-.5*ghostTowerSize), 
				                		    (int)(mousePos.getY()-.5*ghostTowerSize)), 
					               theGame, MenuView.getModeSelection().equals("Fun"));
		}
		theGame.add(newTower);
		theGame.decrementCash(cost);
	}
	
	
	/**
	 * isClosest
	 * Checks if a given distance beats the closest distance while maintaining a boundary
	 * in distances
	 * @param currDistance: double representing distance to be checked
	 * @param closestDistance: the best (closest) distance
	 * @return boolean representing
	 */
	private boolean isClosest(double currDistance, double closestDistance) {
		return currDistance<=5000 && currDistance<closestDistance;
	}
  }

  
  /**
   * The update method is called by notify observers in the TowerGame when the main loop iterates.
   */
  @Override
  public void update(Observable o, Object arg) {
    drawMap();
    
    double health = thePlayer.getHP() / 100;
    if(firstAttack && health < 100) {
    	if(MenuView.getModeSelection().equals("Fun")) {
    		 ControllerMain.soundEffects.get("swamp").play();
    	}
    	else {
    		ControllerMain.soundEffects.get("underattack").play();
    	}
    	
        firstAttack = false;
    }
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
          status.setText(statusText);
        }
    });
    
    
  }
}




