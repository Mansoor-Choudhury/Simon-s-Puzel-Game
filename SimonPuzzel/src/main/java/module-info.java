module com.example.simonspuzzel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simonspuzzel to javafx.fxml;
    exports com.example.simonspuzzel;
}