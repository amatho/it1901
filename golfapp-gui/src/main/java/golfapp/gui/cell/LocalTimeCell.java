package golfapp.gui.cell;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ListCell;

public class LocalTimeCell extends ListCell<LocalTime> {

  @Override
  protected void updateItem(LocalTime localTime, boolean empty) {
    super.updateItem(localTime, empty);

    setText(
        localTime == null || empty ? "" : localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
  }
}
