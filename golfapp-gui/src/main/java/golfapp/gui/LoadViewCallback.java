package golfapp.gui;

import javafx.util.Callback;

public interface LoadViewCallback {

  void loadView(String f, Callback<Class<?>, Object> c);

  default void loadView(String f) {
    loadView(f, null);
  }
}
