package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DefenseAttribute;

public class DefenseAttributeTest {

  @Test
  public void testGetDefense() {
    assertTrue(DefenseAttribute.SMALL.getDefense() == 50);
    assertTrue(DefenseAttribute.MEDIUM.getDefense() == 100);
    assertTrue(DefenseAttribute.LARGE.getDefense() == 350);
  }

}
