module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens application to javafx.fxml;
    exports application;
}
