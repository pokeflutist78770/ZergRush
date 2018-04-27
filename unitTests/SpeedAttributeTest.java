package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.SpeedAttribute;

public class SpeedAttributeTest {

  @Test
  public void testGetSpeed() {
    assertTrue(SpeedAttribute.VERY_FAST.getSpeed() > 0);
    assertTrue(SpeedAttribute.SLOW.getSpeed() > 0);
  }

}
