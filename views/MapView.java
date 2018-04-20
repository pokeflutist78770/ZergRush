package views;

import java.awt.Paint;
import java.awt.Point;
import java.util.Iterator;

import controller.ControllerMain;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
	
	
	public MapView(Button back)
	{
		vBox = new VBox();
		backButton = back;
		pane = new StackPane();
		canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		background = new Image("file:assets/images/map/demoMap.png", false);
		
		// Draw background
		gc.drawImage(background, 0.0, 0.0);
		
		Button tower =new Button("Tower");
		// Add button
		backButton.setMaxWidth(100);
		vBox.getChildren().addAll(tower, backButton);
		vBox.setPadding(new Insets(750,0,0,350));
		
		pane.getChildren().add(canvas);
		pane.getChildren().add(vBox);
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
	    			 nextMob.getSpriteSizeX(),  nextMob.getSpriteSizeY(), 
	    		     nextMob.getX(), nextMob.getY(),
	    		     nextMob.getSpriteSizeX(), nextMob.getSpriteSizeY());
	  }

	  //drasws any current rojectiles
	  Iterator<Projectile> projitr = ControllerMain.projectiles.iterator();
	  while (projitr.hasNext()) {
	    Projectile nextProj = projitr.next();
	    gc.drawImage(nextProj.getImage(), nextProj.getX(), nextProj.getY());
	  }	
	}
}
