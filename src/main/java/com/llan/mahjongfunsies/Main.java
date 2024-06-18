package com.llan.mahjongfunsies;

import com.llan.mahjongfunsies.ai.data.DataSaver;
import com.llan.mahjongfunsies.util.NumericMatrix;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Funsies!");
        stage.setScene(scene);
        stage.show();

//        NumericMatrix test = new NumericMatrix(2, 2);
//        test.setValue(5, 0, 0);
//        test.setValue(6, 0, 1);
//        test.setValue(7, 1, 0);
//        test.setValue(8, 1, 1);
//        System.out.println(test.getDataString());
//        DataSaver.save(test, "test.txt");
        NumericMatrix test = DataSaver.retrieve("test.txt");
        System.out.println(test);
    }

    public static void main(String[] args) {
        launch();
    }
}