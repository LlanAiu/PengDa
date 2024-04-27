module com.llan.mahjongfunsies {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.llan.mahjongfunsies to javafx.fxml;
    exports com.llan.mahjongfunsies;
}