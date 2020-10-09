package golfapp.data;

import golfapp.core.User;

class FileUserDao extends AbstractFileDao<User> {

  public FileUserDao() {
    super();
  }

  @Override
  String getFilename() {
    return "users.json";
  }
}
