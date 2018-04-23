package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import controller.ControllerMain;
import javafx.scene.image.Image;
import model.Towers.DemoTower;
import model.Towers.Tower;

public class DemoTowerTest {

  @Test
  public void testDemoTower() {
    
    new ControllerMain();
    
    DemoTower dTow = new DemoTower(new Point(400,400));
    assertTrue(dTow.getX() == 400);
    assertTrue(dTow.getY() == 400);
    assertTrue(dTow.getCost() == 0);
    assertTrue(dTow.getImageFilePath().equals("file:assets/images/Slime.png"));
    dTow.getImage();
    
    dTow.setImageFilePath("file:assets/images/ghost.png");
  }

}
