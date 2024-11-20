module org.example.client_serverapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens chapter33 to javafx.graphics;
    exports chapter33 to javafx.graphics;
    opens org.example.client_serverapplication to javafx.fxml;
    exports org.example.client_serverapplication;
}