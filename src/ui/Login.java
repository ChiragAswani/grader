package ui;

import core.Home;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        Home h = new Home();
        final PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter Password Here");

        Dialog dialog = new Dialog();
        dialog.setTitle("Authentication");
        dialog.setHeaderText("Welcome to GradeSafe");

        ButtonType enterGradingPortal = new ButtonType("Enter Grading Portal", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enterGradingPortal, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        grid.add(new Label("Password: "), 0, 0);
        grid.add(passwordInput, 1, 0);

        Hyperlink importantInfo = new Hyperlink();
        importantInfo.setText("First Time Logging in?");
        importantInfo.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Welcome");
                alert.setHeaderText("First Time Logging In");
                alert.setContentText("If this is your first time logging in, " +
                                     "your initial password will be kept");
                alert.showAndWait();
            }
        });
        grid.add(importantInfo, 0, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            Boolean isPasswordValid = h.login(passwordInput.getText());
            if(isPasswordValid){
                Actions action = new Actions();
                action.goToDashBoard(stage);
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