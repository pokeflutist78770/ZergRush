package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ArmorAttribute;

public class ArmorAttributeTest {

  @Test
  public void testGetArmor() {
    assertTrue(ArmorAttribute.NONE.getArmor() == 0);
    assertTrue(ArmorAttribute.LIGHT_ARMOR.getArmor() == .25);
    assertTrue(ArmorAttribute.MEDIUM_ARMOR.getArmor() == .33);
    assertTrue(ArmorAttribute.HEAVY_ARMOR.getArmor() == .5);
  }

}
