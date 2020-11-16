package golfapp.gui.cell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ListCell;

public class LocalDateCell extends ListCell<LocalDate> {

  @Override
  protected void updateItem(LocalDate localDate, boolean empty) {
    super.updateItem(localDate, empty);

    setText(localDate == null || empty ? "" : localDate.format(DateTimeFormatter.ISO_DATE));
  }
}
