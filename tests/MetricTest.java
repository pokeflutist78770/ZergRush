package Tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Maps.Metric;

public class MetricTest {

  @Test
  public void testGetDirection() {
    assertTrue(Metric.getDirectionAngle(new Point(0,0), new Point(1,1)) == 315.0);
    assertTrue(Metric.getDirectionAngle(new Point(1,1), new Point(0,0)) == 135.0);
    assertTrue(Metric.getDirectionAngle(new Point(1,0), new Point(0,1)) == 225.0);
    assertTrue(Metric.getDirectionAngle(new Point(0,1), new Point(1,0)) == 45.0);
    
    assertTrue(Metric.getDirectionAngle(new Point(0,0), new Point(1,0)) == 0.0);
    assertTrue(Metric.getDirectionAngle(new Point(0,0), new Point(0,1)) == 270.0);
    assertTrue(Metric.getDirectionAngle(new Point(0,1), new Point(0,0)) == 90.0);
  }
  
  @Test
  public void testCloseEnough() {
    assertTrue(Metric.closeEnough(new Point(0,0), new Point(1,1), 2));
    assertFalse(Metric.closeEnough(new Point(0,0), new Point(1,1), 1));
  }

}
