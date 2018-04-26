package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.ControllerMain;
import model.Player;

public class PlayerTest {
  
  private Player player1 = new Player();
  private ControllerMain cm = new ControllerMain();

  @Test
  public void testGetHP() {
    player1.takeDamage(500);
    assertTrue(player1.getHP() == 9500);
  }

  @Test
  public void testResetStats() {
    player1.takeDamage(500);
    assertTrue(player1.getHP() != 10000);
    player1.resetStats();
    assertTrue(player1.getHP() == 10000);
  }

  @Test
  public void testIsDead() {
    assertFalse(player1.isDead());
    /**
     * Cannot test the following because of the lambda in dealWithDeadPlayer in ControllerMain 
    player1.takeDamage(2*player1.getHP());
    assertTrue(player1.isDead());
    */
  }

}
