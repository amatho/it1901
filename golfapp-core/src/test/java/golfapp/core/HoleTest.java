package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class HoleTest {

  @Test
  void hole_throwsExceptionWhenInvalidLength() {
    Hole hole = new Hole(10.0, 3, 10.0);
    assertThrows(IllegalArgumentException.class, () -> new Hole(-1.0, 3, 10));
    assertThrows(IllegalArgumentException.class, () -> new Hole(0, 3, 10));
  }

  @Test
  void hole_throwsExceptionWhenInvalidPar() {
    Hole hole = new Hole(10.0, 3, 10.0);
    assertThrows(IllegalArgumentException.class, () -> new Hole(50.0, -1, 10));
    assertThrows(IllegalArgumentException.class, () -> new Hole(50.0, 0, 10));
  }
}
