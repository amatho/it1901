module app {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.controls;

    exports gui;

    opens gui to javafx.fxml;
}
