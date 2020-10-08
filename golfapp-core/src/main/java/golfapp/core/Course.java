package golfapp.core;

import java.util.List;

public class Course {

  private final String name;
  private final List<Hole> holes;
  private final BookingSystem bookingSystem;

  /**
   * Create a new course.
   *
   * @param name          the course name
   * @param holes         the holes of this course
   * @param bookingSystem the booking system for this course
   */
  public Course(String name, List<Hole> holes, BookingSystem bookingSystem) {
    this.name = name;
    this.holes = holes;
    this.bookingSystem = bookingSystem;
  }

  /**
   * Create a new course, using a new booking system.
   *
   * @param name  the course name
   * @param holes the holes of this course
   */
  public Course(String name, List<Hole> holes) {
    this.name = name;
    this.holes = holes;
    bookingSystem = new BookingSystem();
  }

  public String getName() {
    return name;
  }

  public List<Hole> getHoles() {
    return holes;
  }

  public Hole getHole(int index) {
    return holes.get(index);
  }

  public int getHoleIndex(Hole hole) {
    return holes.indexOf(hole);
  }

  public int getCourseLength() {
    return holes.size();
  }

  public BookingSystem getBookingSystem() {
    return bookingSystem;
  }
}
