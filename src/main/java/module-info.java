module app {
    requires javafx.fxml;
    requires transitive javafx.graphics;

    exports app;

    opens app to javafx.fxml;
}
