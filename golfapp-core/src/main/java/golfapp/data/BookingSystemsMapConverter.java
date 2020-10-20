package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSystemsMapConverter extends
    StdConverter<List<BookingSystemsEntry>, Map<Course, BookingSystem>> {

  @Override
  public Map<Course, BookingSystem> convert(List<BookingSystemsEntry> bookingSystemsEntries) {
    var map = new HashMap<Course, BookingSystem>();
    for (var e : bookingSystemsEntries) {
      map.put(e.course, e.bookingSystem);
    }
    return map;
  }
}
