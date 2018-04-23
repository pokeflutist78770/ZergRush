/*
 * Only one turnin needed to D2L Assignment '6-April) Animation'
 * 
 * Write one, two or three names here. You need not be on the same final project team
 * 
 *  1. _
 *  
 *  2. _
 *  
 *  3. _
 */
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * This view contains an animation of a running girl thought the desert.
 * It can be added to any Pane elsewhere and started with the instance method
 * 
 *   public void animate()
 * 
 * @author Rick Mercer
 * 
 * Angel Aguayo, Benjamin Walters, David Weinflash
 */
public class RunningGirlView extends Canvas {

  private Image spritesheet, dirt;
  private GraphicsContext g2;
  private Timeline timeline;

  public RunningGirlView() {
    this.setWidth(333);
    this.setHeight(200);

    //  1: Create both images and draw both when the controller instructs
    spritesheet = new Image("file:images/runner.png", false);
    // spritsheet contains 6 sub images
    dirt = new Image("file:images/desertdirt.jpg", false);
    g2 = this.getGraphicsContext2D();

    // Create a TimeLine that call AnimateStarter.handle every 100ms
    // class AnimateStarter has two method stubs you have to complete.
    timeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStarter()));
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  // Call this from the Application to begin the spritesheet animation
  public void animate() {
    timeline.play();
  }

  //  2: Complete this class with its handle method to show 15 different 
  // views of the runner by cycling through the spritesheet three times. 
  // Begin at the upper left corner. Moving 10 pixels to the right each time.
  // The image two draw is 90 x 90 pixels.
  private class AnimateStarter implements EventHandler<ActionEvent> {
    private int tic = 0;
    double sx, sy, sw, sh, dx, dy, dw, dh;

    public AnimateStarter() {
      //  ICA: And the only one: Complete this animation to show 21 drawImages.
      // You need to add code the constructor to set the 8 instance variables
      // and draw the first image.  The handle messsage show shoe different
      // subImages moving left to right across the background.
      /*
      The images to draw are know as spritesheet (6 images) and dirt (the background)
      Use method drawImage with 9 arguments: 
        drawImage(theImage, sx, sy, sw, sh, dx, dy, dw, dh)
      sx the source rectangle's X coordinate position.
      sy the source rectangle's Y coordinate position.
      sw the source rectangle's width.
      sh the source rectangle's height.
      dx the destination rectangle's X coordinate position.
      dy the destination rectangle's Y coordinate position.
      dw the destination rectangle's width.
      dh the destination rectangle's height.
      */
    	sx=5;
    	sy=5;
    	sw=90;
    	sh=90;
    	dx=5;
    	dy=5;
    	dw=90;
    	dh=90;
    	
    	g2.drawImage(dirt, 0, 0);
    	g2.drawImage(spritesheet, sx, sy, sw,sh, dx, dy, dw, dh);
    }

    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(ActionEvent event) {
    	tic++;
    	
    	if(tic<42) {
    		g2.drawImage(dirt, 0,0);
    		g2.drawImage(spritesheet, sx, sy, sw,sh, dx, dy, dw, dh);
    		dx+=5;
    		if(tic%5==0) {
    			sx=0;
    		}
    		else
    			sx+=90;
    		
    	}
    	else
    		timeline.stop();
    }
  }
}





