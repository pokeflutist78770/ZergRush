package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ElementalAttribute;

public class ElementalAttributeTest {

  @Test
  public void testGetElementalMultiplier() {
    assertTrue(ElementalAttribute.NONE.getElementalMultiplier() == 1);
    assertTrue(ElementalAttribute.FIRE.getElementalMultiplier() == 1.25);
    assertTrue(ElementalAttribute.ICE.getElementalMultiplier() == 1.25);
    assertTrue(ElementalAttribute.POISON.getElementalMultiplier() == 1.25);
    assertTrue(ElementalAttribute.AIR.getElementalMultiplier() == 1.25);
  }

}
