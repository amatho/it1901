package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class BookingTest {

  @Test
  void equals_ignoresUserId() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking("foo@foo.com", dateTime);
    var booking2 = new Booking("bar@bar.com", dateTime);

    assertEquals(booking, booking2);
  }

  @Test
  void hashCode_equalObjectsSameHash() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking("foo@example.com", dateTime);
    var booking2 = new Booking("bar@example.com", dateTime);

    assertEquals(booking.hashCode(), booking2.hashCode());
  }
}
