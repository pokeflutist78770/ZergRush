package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Player;

public class PlayerTest {
  
  Player player = new Player();

  @Test
  public void testPlayer() {
    double cash = player.getCash();
    player.decrementCash(100);
    assertFalse(player.getCash() == cash);
    player.addCash(100);
    assertTrue(player.getCash() == cash);
  }

  @Test
  public void testGetHP() {
    double hp = player.getHP();
    player.takeDamage(10);
    assertFalse(player.getHP() == hp);
    player.takeDamage(player.getHP());
    assertTrue(player.isDead());
    player.resetStats();
    assertTrue(player.getHP() == hp);
    assertFalse(player.isDead());
  }

}
