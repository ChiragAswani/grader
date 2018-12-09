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

public class Dashboard extends Application {

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
        coursesTitle.setFont(new Font(20));
        coursesTitle.setUnderline(true);
        grid.add(coursesTitle, 0, 0);
        while(i < courses.size()){
            String courseID = courses.get(i)[0];
            String courseName = courses.get(i)[1];

            Text courseText = new Text();
            courseText.setText(courseName);
            i++;
            grid.add(courseText, 0, i);

              Button viewCourse = new Button("View");
            viewCourse.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        MainUI ui = new MainUI(h.loadCourse(courseID));
                        Stage a = new Stage();
                        ui.start(a);
                        dialog.close();

                    }
                });
            viewCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(viewCourse, 1, i);

            Button renameCourse = new Button("Rename");
            renameCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    Dialog d = new Dialog();
                    d.setTitle("Rename Course");
                    d.setHeaderText("Rename Course: "+ courseName);

                    ButtonType confirmPassword = new ButtonType("Rename Course", ButtonBar.ButtonData.APPLY.OK_DONE);
                    d.getDialogPane().getButtonTypes().addAll(confirmPassword, ButtonType.CANCEL);

                    GridPane g = new GridPane();
                    g.setHgap(10);
                    g.setVgap(10);
                    g.setPadding(new Insets(20, 150, 10, 10));

                    final TextField newPassword = new TextField();
                    newPassword.setPromptText("Course Name");

                    final Text courseNameText = new Text();
                    courseNameText.setText(courseName);

                    g.add(new Label("Old Course Name"), 0, 0);
                    g.add(courseNameText, 1, 0);

                    g.add(new Label("New Course Name"), 0, 1);
                    g.add(newPassword, 1, 1);

                    d.getDialogPane().setContent(g);

                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()){
//                    changeCourseName(newPassword.getText());
                    }
                }
            });
            renameCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(renameCourse, 2, i);

            Button archiveCourse = new Button("Archive");
            archiveCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                }
            });
            archiveCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(archiveCourse, 3, i);

            Button deleteCourse = new Button("Delete");
            deleteCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
//                    h.deleteCourse(courseID);
                }
            });
            deleteCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(deleteCourse, 4, i);

        }
        final Text actionsHeader = new Text();
        actionsHeader.setText("Actions");
        actionsHeader.setFont(new Font(20));
        actionsHeader.setUnderline(true);
        grid.add(actionsHeader, 0, i+2);

        final Text createANewCourseText = new Text();
        createANewCourseText.setText("Create A New Course");
        grid.add(createANewCourseText, 0, i+3);

        final TextField createANewCourseTextField = new TextField();
        createANewCourseTextField.setPromptText("Course Name");
        grid.add(createANewCourseTextField, 1, i+3);


        Button addCourseButton = new Button("Create Course");
        addCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
                MainUI ui = new MainUI(h.createNewCourse(createANewCourseTextField.getText()));
                Stage a = new Stage();
                ui.start(a);
            }
        });
        grid.add(addCourseButton, 2, i+3);

        Button tagManagement = new Button("Tag Management");
        tagManagement.setMaxWidth(Double.MAX_VALUE);
        tagManagement.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
                TagManagement tagManagement1 = new TagManagement();
                Stage a = new Stage();
                tagManagement1.start(a);
            }
        });
        grid.add(tagManagement, 0, i+4);



        Button viewArchivesButton = new Button("View Archives");
        viewArchivesButton.setMaxWidth(Double.MAX_VALUE);
        viewArchivesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            }
        });
        grid.add(viewArchivesButton, 0, i+5);


        resetPasswordButton(grid, 0, i+6);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("Quit", ButtonBar.ButtonData.APPLY.OK_DONE));
        Optional<String> result = dialog.showAndWait();
        dialog.close();

    }

    public void resetPasswordButton(GridPane grid, Integer columnIndex, Integer rowIndex){
        Button resetPasswordButton = new Button("Reset Password");
        resetPasswordButton.setMaxWidth(Double.MAX_VALUE);
        resetPasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Dialog dialog = new Dialog();
                dialog.setTitle("Authentication");
                dialog.setHeaderText("Reset Password");

                ButtonType confirmPassword = new ButtonType("Confirm Password", ButtonBar.ButtonData.APPLY.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(confirmPassword, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                final TextField newPassword = new TextField();
                newPassword.setPromptText("New Password");

                grid.add(new Label("Enter New Password"), 0, 0);
                grid.add(newPassword, 1, 0);

                dialog.getDialogPane().setContent(grid);

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
//                    resetPassword(newPassword.getText());
                }

            }
        });
        grid.add(resetPasswordButton, columnIndex, rowIndex);
    }

}