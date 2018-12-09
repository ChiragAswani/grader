package ui;

import core.Home;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class HomeScreen extends Application {

    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        final TextField passwordInput = new TextField();
        passwordInput.setPromptText("Enter Password Here");

        Dialog dialog = new Dialog();
        dialog.setTitle("Authentication");
        dialog.setHeaderText("Welcome to GradeSafe");

        ButtonType addGradingCategory = new ButtonType("Enter Grading Portal", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addGradingCategory, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        grid.add(new Label("Password: "), 0, 0);
        grid.add(passwordInput, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            Home h = new Home();
            Boolean isPasswordValid = h.login(passwordInput.getText());
            if(isPasswordValid){
                stage.close();
                Dashboard dashboard = new Dashboard();
                Stage a = new Stage();
                dashboard.start(a);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText("Authentication Error");
                alert.setContentText("Invalid Password");
                alert.showAndWait();
            }
        } else {
            System.exit(0);
        }

    }
}