package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class BookingTest {

  @Test
  void equals_ignoresUser() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(new User("foo@foo.com", "Foo"), dateTime);
    var booking2 = new Booking(new User("bar@bar.com", "Bar"), dateTime);

    assertEquals(booking, booking2);
  }

  @Test
  void hashCode_equalObjectsSameHash() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(new User("foo@example.com", "Foo"), dateTime);
    var booking2 = new Booking(new User("bar@example.com", "Bar"), dateTime);

    assertEquals(booking.hashCode(), booking2.hashCode());
  }
}
