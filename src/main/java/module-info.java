open module app {
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires com.google.gson;

  exports gui;
}
