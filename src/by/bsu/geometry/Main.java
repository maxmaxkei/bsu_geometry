package by.bsu.geometry;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import by.bsu.geometry.navigation.NavigationBar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        NavigationBar bar = new NavigationBar(root);
        root.setLeft(bar.createNavigationBar());
        root.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(1000);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
