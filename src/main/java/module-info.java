module com.llan.mahjongfunsies {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;


    opens com.llan.mahjongfunsies to javafx.fxml;
    exports com.llan.mahjongfunsies;
    exports com.llan.mahjongfunsies.controllers;
    opens com.llan.mahjongfunsies.controllers to javafx.fxml;
}