package core;

import java.util.ArrayList;
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

    public void checkValidHole(int index) {
        if (index >= this.getCourseLength() || index < 0) {
            throw new IllegalArgumentException("Hole does not exist");
        }
    }

    public Hole getHole(int index) {
        this.checkValidHole(index);
        return holes.get(index);
    }
}
