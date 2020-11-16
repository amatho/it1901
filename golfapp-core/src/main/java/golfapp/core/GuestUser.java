package golfapp.core;

/**
 * A guest user. Guest users will have their email set to "Guest", and cannot change their email.
 */
public class GuestUser extends User {

  /**
   * Create a new guest user.
   *
   * @param displayName the name to display for this guest user
   */
  public GuestUser(String displayName) {
    super("", displayName);
  }

  /**
   * Sets the email of this guest user to "Guest", ignoring the argument.
   *
   * @param email ignored argument
   */
  @Override
  public void setEmail(String email) {
    this.email = "Guest";
  }
}
