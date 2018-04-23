package views;

import java.util.List; 
import java.awt.Paint;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import controller.ControllerMain;
import javafx.animation.PathTransition;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Player;
import model.Projectile;
import model.Mobs.Archon;
import model.Mobs.Mob;
import model.Towers.Range;
import model.Towers.Tower;

//A player can view information about an enemy by clicking one that has been 
//placed. Information should include the characteristics of that enemy.

// Information should be viewed in an inclusive manner within the game. Stay 
// away from Java Pop-ups!

// When the game begins in the map, it should be animated. That includes all
// sprites moving.

public class MapView extends StackPane {
  
  private GraphicsContext gcCommand;
  private Button backButton;
  private StackPane pane;
  private Image background;
  private Canvas canvas;
  private GraphicsContext gc;
  private VBox vBox;
  private HBox towerBox;
  private Button tower1;
  private Button tower2;
  private Button tower3;
  private Button upgradeButton;
  private Button upgrade;
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
  private int updateCount = 0;
  
  private boolean towerPlacement;
  private Point mousePos;
  private boolean isValidPos;
  private Range currRange;
  private Image currTower;
  
  private Player thePlayer;
  private DecimalFormat formatter;
  private static int deadMobs;
  private static int cashEarned;
  
  public MapView(Button back) {

	mousePos=new Point(0,0);	
	towerPlacement=false;
	currRange=Range.DEMO_RANGE;
	
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
    deadMobs = 0;
    cashEarned = 0;

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
    
    // Tower1 Button
    Image tower1Image = new Image("file:assets/images/marine.png", false);
    ImageView iv1 = new ImageView(tower1Image);
    iv1.setFitHeight(37);
    iv1.setFitWidth(37);
    tower1 = new TowerButton("", iv1, Range.DEMO_RANGE);
    tower1.setOnAction(towerButtonHandler);
    tower1.setStyle("-fx-base: #808080;");

    // Tower2 Button
    Image tower2Image = new Image("file:assets/images/depot.png", false);
    ImageView iv2 = new ImageView(tower2Image);
    iv2.setFitHeight(37);
    iv2.setFitWidth(37);
    tower2 = new TowerButton("", iv2, Range.DEMO_RANGE);
    tower2.setOnAction(towerButtonHandler);
    tower2.setStyle("-fx-base: #808080;");
    
    // Tower3 Button
    Image tower3Image = new Image("file:assets/images/tank.png", false);
    ImageView iv3 = new ImageView(tower3Image);
    iv3.setFitHeight(37);
    iv3.setFitWidth(37);
    tower3 = new TowerButton("", iv3, Range.DEMO_RANGE);
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

    // Wave Number Label
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
     * attr1 attr2 attr3 attr4 attr5 attr6
     * 
     */

    // attr1 Update Grid
    attr1 = new Label();
    attr1.setStyle("-fx-font: 15 serif; -fx-text-fill: #ff0000;");
    updateGrid.add(attr1, 0, 0);

    // attr2 Update Grid
    attr2 = new Label();
    attr2.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    attr2.setPadding(new Insets(0, 0, 0, 40));
    updateGrid.add(attr2, 1, 0);

    // attr3 Update Grid
    attr3 = new Label();
    attr3.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    updateGrid.add(attr3, 0, 1);

    // attr4 Update Grid
    attr4 = new Label();
    attr4.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    attr4.setPadding(new Insets(0, 0, 0, 40));
    updateGrid.add(attr4, 1, 1);

    // attr5 Update Grid
    attr5 = new Label();
    attr5.setStyle("-fx-font: 15 serif; -fx-text-fill: #ffffff;");
    updateGrid.add(attr5, 0, 2);

    // attr6 Update Grid
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
    
    this.getChildren().add(pane);
    this.setOnMouseClicked(mouseHandler);
    this.setOnMouseMoved(mouseHandler);
    //this.setOnMouse
  }
  
  public void setMapSelection(String filepath)
  {
    background = new Image(filepath, ControllerMain.GUI_SIZE, ControllerMain.GUI_SIZE, true, true);
    gc.drawImage(background, 0.0, 0.0);
  }

  public void setKillsNum(int num) {
    deadMobs = num;
  }
  
  public static void incrKills()
  {
	  deadMobs++;
	  cashEarned += 50;
  }

  public void setCashNum(int num) {
	  // Sets the Player Cash Label
	cashEarned = num;
    cashNum.setText("$" + String.valueOf(num));
  }

  public void setHealthNum(double hp) {
    healthNum.setText(String.valueOf(hp));
  }

  public void setWaveNum(String difficulty) {
    waveNum.setText(difficulty);
  }
  
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
    
    if(mob instanceof Archon) {
      double nrgX = 385;
      double nrgY = 393;
      double nrgSteps = 4;
      double nrgCurrStep = stepCount % nrgSteps + 1;
      double nrgSX = nrgX + nrgCurrStep * 92;
      gc.drawImage(mob.getImage(), nrgSX, nrgY, sw, sh, x, y, sw, sh);
      
      gc.drawImage(mob.getImage(), sx, currSY, sw, sh, x, y, sw, sh);
      
      
      double swirlX = 343;
      double swirlY = 1047;
      double swirlSteps = 15;
      double swirlCurrStep = stepCount % swirlSteps + 1;
      double swirlSX = swirlX + swirlCurrStep * 92;
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

  /*
   * drawMap Draws the overall map and mobs/towers every iteration Parameters:
   * None Returns: None
   */
  public void drawMap() {
	gc.drawImage(background, 0, 0);
    gc.strokeLine(0, 800, 800, 800);
    

    if(towerPlacement) {
    	drawGhostTower();
    }

    double health = thePlayer.getHP() / 100;
    String healthStr = formatter.format(health);
    
    String cashStr = formatter.format(cashEarned);
    
    // UI can't be updated on a non-application thread
    // Since drawMap() is called from a non-application thread,
    // add this thread to the event queue to be called later 
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            // if you change the UI, do it here !
        	healthNum.setText(healthStr);
        	killsNum.setText(String.valueOf(deadMobs));
        	cashNum.setText("$"+cashStr);
        }
    });
   

    
    /*
     * Our beautiful animation stuff will go here
     */

    // draws all current towers
    HashSet<Tower> towersCpy = new HashSet(ControllerMain.towers);
    for (Tower t: towersCpy) {
      gc.drawImage(t.getImage(), t.getX(), t.getY()); // TODO: Ben, center this graphic.
    }
    towersCpy.clear();

    // draws every mob
    HashSet<Mob> mobsCpy = new HashSet(ControllerMain.mobs);
    for (Mob m: mobsCpy) {
      drawMob(m);
    }
    mobsCpy.clear();
    


    // drasws any current rojectiles
    HashSet<Projectile> projectilesCpy = new HashSet(ControllerMain.projectiles);
    for (Projectile p: projectilesCpy) {
      gc.drawImage(p.getImage(), p.getX(), p.getY());// TODO: Ben, center this graphic
    }
    projectilesCpy.clear();
  }
  
  
  public void drawGhostTower() {
	  double radius=currRange.toDouble();
	  if(isValidPos) {
		  gc.setFill(Color.color(0, .5, 0, .5));
	  }
	  else {
		  gc.setFill(Color.color(.75, 0, 0, .5));
	  }
	  
	  gc.fillOval(mousePos.getX()-radius, mousePos.getY()-radius, 
			      radius*2, radius*2);
	  gc.drawImage(currTower, mousePos.getX(), mousePos.getY());
  }
  
  
  
  
  private class towerButtonHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent e) {
		towerPlacement=!towerPlacement;
		TowerButton button=(TowerButton) e.getSource();
		currRange=button.getRange();
		currTower=button.getImage().getImage();
		System.out.println("BUTTON CLICKED\n"+"TP: "+towerPlacement);
	} 
  }
  
  
  private class mouseHandler implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent e) {
	  if(e.getEventType()==MouseEvent.MOUSE_MOVED) {
		mousePos.setLocation(e.getX(), e.getY());
	  }
	  else if(e.getEventType()==MouseEvent.MOUSE_CLICKED) {
		System.out.println("Mouse Clicked");
	  }
    }
  }
}




