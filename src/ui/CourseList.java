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

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CourseList extends Application {

    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        final Label label = new Label("Course List");
        label.setFont(new Font("Arial", 20));

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, hb);

        Home h = new Home();
        List<String[]> courses = h.seeCourses();

        for (int i = 0; i < courses.size(); i++){
            String courseID = courses.get(i)[0];
            String course = courses.get(i)[1];
            Button courseButton = new Button(course);
            courseButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    stage.close();
                    MainUI ui = new MainUI(h.loadCourse(courseID));
                    Stage a = new Stage();
                    ui.start(a);
                }
            });
            hb.getChildren().addAll(courseButton);
        }

        final TextField newCourse = new TextField();
        newCourse.setPromptText("Course Name");
        Button addCourseButton = new Button("Create New Course");
        addCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
                MainUI ui = new MainUI(h.createNewCourse(newCourse.getText()));
                Stage a = new Stage();
                ui.start(a);
            }
        });


        vbox.getChildren().addAll(newCourse, addCourseButton);

        //hb.getChildren().addAll(password, login);
        hb.setSpacing(3);


        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();


    }
}