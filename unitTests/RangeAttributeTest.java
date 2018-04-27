package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.RangeAttribute;

public class RangeAttributeTest {

  @Test
  public void testToDouble() {
    assertTrue(RangeAttribute.SMALL_RANGE.toDouble() == 100);
    assertTrue(RangeAttribute.MEDIUM_RANGE.toDouble() == 150);
    assertTrue(RangeAttribute.LARGE_RANGE.toDouble() == 200);
  }

}
