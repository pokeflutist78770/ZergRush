package views;

import java.util.List;
import java.awt.Paint;
import java.awt.Point;
import java.util.Set;
import java.util.Iterator;

import controller.ControllerMain;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Projectile;
import model.Mobs.Mob;
import model.Towers.Tower;

//A player can view information about an enemy by clicking one that has been 
//placed. Information should include the characteristics of that enemy.

// Information should be viewed in an inclusive manner within the game. Stay 
// away from Java Pop-ups!

// When the game begins in the map, it should be animated. That includes all
// sprites moving.

public class MapView extends StackPane {

	private Button backButton;
	private StackPane pane;
	private Image background;
	private Canvas canvas;
	private GraphicsContext gc;
	private VBox vBox;
	private HBox towerBox;
	private Set mob;
	private Set projectiles;
	private List towers;
	private Button tower1;
	private Button tower2;
	private Button tower3;
	private Button upgradeButton;
	private Button upgrade;
	
	public MapView(Button back, Set<Mob> m, Set<Projectile> p, List<Tower> t)
	{
		mob = m;
		projectiles = p;
		towers = t;
		vBox = new VBox();
		towerBox = new HBox();
		backButton = back;
		pane = new StackPane();
		canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		StackPane.setAlignment(canvas, Pos.TOP_CENTER);
		background = new Image("file:assets/images/map/demoMap.png", false);
		
		// Command Panel - Black Background
		Canvas commandCanvas = new Canvas(800,880);
		GraphicsContext gcCommand = commandCanvas.getGraphicsContext2D();
		gcCommand.setFill(Color.BLACK);
		gcCommand.setStroke(Color.BLACK);
		gcCommand.fillRect(0, 0, commandCanvas.getWidth(), commandCanvas.getHeight());
		
		// Tower1 Button
	    Image tower1Image = new Image("file:assets/images/marine.png", false);
	    ImageView iv1 = new ImageView(tower1Image);
	    iv1.setFitHeight(37);
	    iv1.setFitWidth(37);
	    tower1 = new Button("", iv1);
	    tower1.setStyle("-fx-base: #808080;");
	    
		// Tower2 Button
	    Image tower2Image = new Image("file:assets/images/ghost.png", false);
	    ImageView iv2 = new ImageView(tower2Image);
	    iv2.setFitHeight(37);
	    iv2.setFitWidth(37);
	    tower2 = new Button("", iv2);
	    tower2.setStyle("-fx-base: #808080;");
	    
		// Tower3 Button
	    Image tower3Image = new Image("file:assets/images/thick.png", false);
	    ImageView iv3 = new ImageView(tower3Image);
	    iv3.setFitHeight(37);
	    iv3.setFitWidth(37);
	    tower3 = new Button("", iv3);
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
		vBox.setPadding(new Insets(828,0,0,470));
		
		// Add Tower Buttons
		towerBox.getChildren().add(tower1);
		towerBox.getChildren().add(tower2);
		towerBox.getChildren().add(tower3);
		towerBox.setPadding(new Insets(818,0,0,275));
		towerBox.setSpacing(12);
		towerBox.setPickOnBounds(false);
		
		pane.getChildren().add(commandCanvas);
		pane.getChildren().add(canvas);
		pane.getChildren().add(vBox);
		pane.getChildren().add(towerBox);
		this.getChildren().add(pane);
	}

	
	/* drawMap
	 * Draws the overall map and mobs/towers every iteration
	 * Parameters: None
	 * Returns: None
	*/
	public void drawMap()
	{
	  gc.drawImage(background, 0, 0);
	  gc.strokeLine(0, 800, 800, 800);
	  
	  /*
	   * NOTE: These only work for Zergling and Hydralisk, will probably need a way to 
	   * allow the mob to know its own dimensions or something, whatever it may be
	  */
	  double sx=2;
      double sy=2;
      double sw=38;
      double sh=38;
      double dw=38;
      double dh=38;

      /*
	   * Our beautiful animation stuff will go here
      */

	  //draws all current towers
      Iterator<Tower> towitr = ControllerMain.towers.iterator();
      while (towitr.hasNext()) {
        Tower nextTower = towitr.next();
        gc.drawImage(nextTower.getImage(), nextTower.getX(), nextTower.getY());
      }

      //draws every mob
	  Iterator<Mob> mobitr = ControllerMain.mobs.iterator();
	  while (mobitr.hasNext()) {
	    Mob nextMob = mobitr.next();
	    
	    //This is done here instead to make prevent any errors when removing while iterating
	    if(nextMob.isDead()) {
	    	mobitr.remove();
	    	continue;
	    }
	    
	    gc.drawImage(nextMob.getImage(), sx, sy,
	    			 nextMob.getSpriteSize(),  nextMob.getSpriteSize(), 
	    		     nextMob.getX(), nextMob.getY(),
	    		     nextMob.getSpriteSize(), nextMob.getSpriteSize());
	  }


	  //drasws any current rojectiles
	  Iterator<Projectile> projitr = ControllerMain.projectiles.iterator();
	  while (projitr.hasNext()) {
	    Projectile nextProj = projitr.next();
	    gc.drawImage(nextProj.getImage(), nextProj.getX(), nextProj.getY());
	  }	
	}
}
