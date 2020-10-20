package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingSystemsListConverter extends
    StdConverter<Map<Course, BookingSystem>, List<BookingSystemsEntry>> {

  @Override
  public List<BookingSystemsEntry> convert(Map<Course, BookingSystem> courseBookingSystemMap) {
    return courseBookingSystemMap.entrySet().stream().map(e -> {
      var entry = new BookingSystemsEntry();
      entry.course = e.getKey();
      entry.bookingSystem = e.getValue();
      return entry;
    }).collect(Collectors.toList());
  }
}
