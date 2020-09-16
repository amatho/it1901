package core;

import java.util.Arrays;
import java.util.List;

public class Course {

  List<Hole> holes;

  public Course(Hole... holes) {
    this.holes = Arrays.asList(holes);
  }

  public int getCourseLength() {
    return holes.size();
  }

  public List<Hole> getHoles() {
    return holes;
  }

  public Hole getHole(int index) {
    return holes.get(index);
  }

  public int getHoleNumber(Hole hole) {
    return 0;
  }
}
