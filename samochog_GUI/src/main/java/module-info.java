module po.samochog_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens po.samochog_gui to javafx.fxml;
    exports po.samochog_gui;
   requires lab5;
    requires java.desktop;
}
