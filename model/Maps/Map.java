package model.Maps;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.ControllerMain;
import javafx.scene.image.Image;

// You should have at least 3 maps.

// Each map has a background image and contains a pre-defined path for enemies
// such that enemies originate at their start location and move along this path
// toward the player's destination and toward the end-zone.

// Different maps have different backgrounds and paths that enemies travel.

// One of the maps contains more than one path for enemies to travel on ( so it
// presents more difficult gameplay, not a linear path! The path should diverge
// at some point).

// A player can select and play on at least three maps. The path that enemies
// travel must be different -- you cannot just change a background image and
// call it a new map. These 3 maps should be built-in and selectable at 
// game startup.

// Maps should have space on the sides of the path for players to build towers.

// Enemies continuously Spawn from the map at some location. We call this the 
// Spawn-Zone.




public abstract class Map {
  
  protected String imageFilePath;
  protected HashMap<Integer, List<Point>> paths; // Each map class should have its own hardcoded path setup.
  
  public Map (String imgFp) {
    paths = new HashMap<Integer, List<Point>>();
    constructMobRoute();
    imageFilePath = imgFp;
  }
  
  
  /* scalePoint
   * scales a given x and y position to the screen resolution
   * Parameters: p: a gien Point object
   * Returns: None
  */
  public static void scalePoint(Point p) {
	int x = (int) p.getX();
	int y = (int) p.getY();
	p.setLocation((int) x*ControllerMain.GUI_SIZE/1000, (int)y*ControllerMain.GUI_SIZE/1000);
  }
  
  //gets the map image
  public Image getImage() {
    return ControllerMain.getGraphic(imageFilePath);
  }

  abstract void constructMobRoute();
	
}
