package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BookingTest {

  @Test
  void equals_ignoresUserId() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(UUID.randomUUID(), dateTime);
    var booking2 = new Booking(UUID.randomUUID(), dateTime);

    assertEquals(booking, booking2);
  }

  @Test
  void hashCode_equalObjectsSameHash() {
    var dateTime = LocalDate.now().atTime(10, 15);
    var booking = new Booking(UUID.randomUUID(), dateTime);
    var booking2 = new Booking(UUID.randomUUID(), dateTime);

    assertEquals(booking.hashCode(), booking2.hashCode());
  }
}
