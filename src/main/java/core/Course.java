package core;

import java.util.Arrays;
import java.util.List;

public class Course {

  List<Hole> holes;

  public Course(List<Hole> holes) {
    this.holes = (holes);
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

  public int getHoleIndex(Hole hole) {
    return holes.indexOf(hole);
  }
}
