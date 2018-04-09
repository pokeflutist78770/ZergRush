package views;

import java.util.List;
import java.awt.Paint;
import java.awt.Point;
<<<<<<< HEAD
import java.util.Set;
=======
import java.util.Iterator;
>>>>>>> f0bc51179bb1c04bf6d7b212bc489a026058592d

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
	private Set mob;
	private Set projectiles;
	private List towers;
	
	public MapView(Button back, Set<Mob> m, Set<Projectile> p, List<Tower> t)
	{
		mob = m;
		projectiles = p;
		towers = t;
		vBox = new VBox();
		backButton = back;
		pane = new StackPane();
		canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		background = new Image("file:assets/images/sc.jpg", false);
		
		// Draw background
		gc.drawImage(background, 0.0, 0.0);
		
		// Add button
		backButton.setMaxWidth(100);
		vBox.getChildren().add(backButton);
		vBox.setPadding(new Insets(750,0,0,350));
		
		pane.getChildren().add(canvas);
		pane.getChildren().add(vBox);
		this.getChildren().add(pane);
	}

	public void drawMap()
	{
	  gc.setFill(Color.BLACK);
	  gc.fillRect(0, 0, 100, 100);
	  gc.drawImage(background, 0, 0);
    Iterator<Tower> towitr = ControllerMain.towers.iterator();
    while (towitr.hasNext()) {
      Tower nextTower = towitr.next();
      gc.drawImage(nextTower.getImage(), nextTower.getX(), nextTower.getY());
    }
	  Iterator<Mob> mobitr = ControllerMain.mobs.iterator();
	  while (mobitr.hasNext()) {
	    Mob nextMob = mobitr.next();
	    gc.drawImage(nextMob.getImage(), nextMob.getX(), nextMob.getY());
	  }
	  Iterator<Projectile> projitr = ControllerMain.projectiles.iterator();
	  while (projitr.hasNext()) {
	    Projectile nextProj = projitr.next();
	    gc.drawImage(nextProj.getImage(), nextProj.getX(), nextProj.getY());
	  }
	  
		
	}
	
}
