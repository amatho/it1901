package golfapp.data;

import golfapp.core.User;

public class FileUserDao extends AbstractFileDao<User> {

  public FileUserDao() {
    super();
  }

  @Override
  String getFilename() {
    return "users.json";
  }
}
