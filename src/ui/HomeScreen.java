package ui;

import core.Course;
import core.Home;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView.TableViewSelectionModel;
import ui.CourseList;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class HomeScreen extends Application {

    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        final Label label = new Label("Enter Password");
        label.setFont(new Font("Arial", 20));

        final TextField password = new TextField();
        password.setPromptText("Password");

        Button login = new Button("Login");
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Home h = new Home();
                Boolean isPasswordValid = h.login(password.getText());
                if(!isPasswordValid){
                    stage.close();
                    CourseList courseList = new CourseList();
                    Stage a = new Stage();
                    courseList.start(a);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("Authentication Error");
                    alert.setContentText("Invalid Password");
                    alert.showAndWait();
                }
            }
        });


        //hb.getChildren().addAll(password, login);
        hb.setSpacing(3);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, hb);
        vbox.getChildren().addAll(password, login);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();


    }
}