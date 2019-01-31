package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.AttackAttribute;

public class AttackAttributeTest {

  @Test
  public void testGetAttack() {
    assertTrue(AttackAttribute.WEAK_ATTACK.getAttack() == 50);
    assertTrue(AttackAttribute.MEDIUM_ATTACK.getAttack() == 100);
    assertTrue(AttackAttribute.STRONG_ATTACK.getAttack() == 200);
  }

}
