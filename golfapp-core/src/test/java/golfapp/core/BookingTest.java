package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class BookingTest {

  @Test
  void equals_ignoresUser() {
    var user = new User("Foo");
    var user2 = new User("Foo Bar");
    var course = new Course("Bar", List.of());
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(user, course, dateTime);
    var booking2 = new Booking(user2, course, dateTime);

    assertEquals(booking, booking2);
  }

  @Test
  void hashCode_equalObjectsSameHash() {
    var user = new User("Foo");
    var user2 = new User("Foo Bar");
    var course = new Course("Bar", List.of());
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(user, course, dateTime);
    var booking2 = new Booking(user2, course, dateTime);

    assertEquals(booking.hashCode(), booking2.hashCode());
  }
}
