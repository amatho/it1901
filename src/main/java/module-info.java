module app {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.controls;

    exports app;

    opens app to javafx.fxml;
}
