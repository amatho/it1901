package golfapp.core;

public class GuestUser extends User {

  /**
   * Create a new user.
   *
   * @param email       the user's email
   * @param displayName the name to display for this user
   */
  public GuestUser(String email, String displayName) {
    super(email, displayName);
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }
}
