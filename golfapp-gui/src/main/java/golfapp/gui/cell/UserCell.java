package golfapp.gui.cell;

import golfapp.core.User;
import javafx.scene.control.ListCell;

public class UserCell extends ListCell<User> {

  @Override
  protected void updateItem(User user, boolean empty) {
    super.updateItem(user, empty);

    setText(user == null || empty ? "" : user.getDisplayName());
  }
}
