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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView.TableViewSelectionModel;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CourseList extends Application {

    final HBox hb = new HBox();

    //make a dashboard

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Dialog dialog = new Dialog();
        dialog.setTitle("Dashboard");
        dialog.setHeaderText("Hello, Christine Papadakis\n\n" +
                             "Logged In At: " + dtf.format(now));
        dialog.setGraphic(new ImageView(this.getClass().getResource("login.jpg").toString()));

        Home h = new Home();
        List<String[]> courses = h.seeCourses();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int i = 0;

        final Text coursesTitle = new Text();
        coursesTitle.setText("Courses");
        grid.add(coursesTitle, i, 0);
        i++;
        while(i < courses.size()){
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
            i++;
            courseButton.setMaxWidth(Double.MAX_VALUE);
            grid.add(courseButton, i, 0);
        }

        final Text createANewCourseText = new Text();
        createANewCourseText.setText("Create A New Course");
        grid.add(createANewCourseText, 0, 1);

        final TextField createANewCourseTextField = new TextField();
        createANewCourseTextField.setPromptText("Course Name");
        grid.add(createANewCourseTextField, 2, 1);

        Button addCourseButton = new Button("Create Course");
        addCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
                MainUI ui = new MainUI(h.createNewCourse(createANewCourseTextField.getText()));
                Stage a = new Stage();
                ui.start(a);
            }
        });
        grid.add(addCourseButton, 3, 1);

        dialog.getDialogPane().setContent(grid);
        Optional<String> result = dialog.showAndWait();

    }
}