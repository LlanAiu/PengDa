package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Main;
import com.llan.mahjongfunsies.controllers.GameController;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainingScreen {
    private VBox box;
    private Button train;
    private TextField inputText;
    private Label weights;
    private Button back;
    private Button save;
    private Button load;
    private ProgressBar bar;

    private static TrainingScreen instance;

    public static TrainingScreen getInstance(){
        if(instance == null){
            instance = new TrainingScreen();
        }
        return instance;
    }

    private TrainingScreen(){
        box = new VBox();
        train = new Button("Train");
        weights = new Label("Weights");
        weights.setWrapText(true);
        weights.setPrefWidth(400);
        back = new Button("Back");
        save = new Button("Save Weights");
        load = new Button("Load Weights");
        inputText = new TextField();
        inputText.setBackground(Background.fill(Color.DARKGRAY));
        inputText.setEditable(true);
        bar = new ProgressBar();
        train.setOnAction(actionEvent -> onTrain());
        save.setOnAction(actionEvent -> GameController.getInstance().saveTrainingWeights());
        load.setOnAction(actionEvent -> GameController.getInstance().loadWeights());
        back.setOnAction(this::backHome);
        box.getChildren().addAll(weights, inputText, bar, train, save, load, back);
        box.setPrefSize(400, 400);
        bar.setPrefWidth(box.getPrefWidth());
        bar.setProgress(0.0);
    }

    public void onTrain(){
        bar.setProgress(0.0);
        int iterations = Integer.parseInt(inputText.getText());
        Task<Integer> trainingTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                for(int i = 0; i < iterations; i++){
                    GameController.getInstance().initializeTraining();
                    this.updateProgress(i, iterations);
                }
                return iterations;
            }
        };
        bar.progressProperty().bind(trainingTask.progressProperty());
        Thread thread = new Thread(trainingTask);
        thread.setDaemon(true);
        thread.start();
        trainingTask.setOnFailed(workerStateEvent -> System.out.println("Training Task Failed"));
        trainingTask.setOnCancelled(workerStateEvent -> System.out.println("Training Task Cancelled"));
        trainingTask.setOnSucceeded(workerStateEvent -> {
            bar.progressProperty().unbind();
            bar.setProgress(1.0);
        });
    }

    public void backHome(ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public Parent getRoot(){
        return box;
    }
}
