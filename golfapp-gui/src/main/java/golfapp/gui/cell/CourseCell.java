package golfapp.gui.cell;

import golfapp.core.Course;
import javafx.scene.control.ListCell;

public class CourseCell extends ListCell<Course> {

  @Override
  protected void updateItem(Course course, boolean empty) {
    super.updateItem(course, empty);

    setText(course == null || empty ? ""
        : course.getName() + " (" + course.getCourseLength() + "-holes)");
  }
}
