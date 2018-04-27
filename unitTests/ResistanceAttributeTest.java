package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ResistanceAttribute;

public class ResistanceAttributeTest {

  @Test
  public void testGetResistance() {
    assertTrue(ResistanceAttribute.NONE.getResistance() == 0);
    assertTrue(ResistanceAttribute.FIRE.getResistance() == .33);
    assertTrue(ResistanceAttribute.ICE.getResistance() == .33);
    assertTrue(ResistanceAttribute.POISON.getResistance() == .5);
    assertTrue(ResistanceAttribute.ELECTRIC.getResistance() == .33);
  }

}
