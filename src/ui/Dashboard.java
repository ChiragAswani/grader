package ui;

import core.Course;
import core.Home;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Match;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView.TableViewSelectionModel;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dashboard extends Application {
    Actions action = new Actions();

    public static void main(String[] args) {
        launch(args);
    }

    public DateTimeFormatter getTimeStamp(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf;
    }

    public void setCoursesTitle(GridPane grid){
        final Text coursesTitle = new Text();
        coursesTitle.setText("Courses");
        coursesTitle.setFont(new Font(20));
        coursesTitle.setUnderline(true);
        grid.add(coursesTitle, 0, 1);
    }

    public void setCourseRowProperties(GridPane gridPane, Integer i){

    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("/ui/login.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        Home h = new Home();
        List<String[]> courses = h.seeUnArchivedCourses();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        setCoursesTitle(grid);
        LocalDateTime now = LocalDateTime.now();
        Text header = new Text();
        header.setText("Hello, Christine Papadakis\n\n" +
                "Logged In At: " + getTimeStamp().format(now));
        grid.add(header, 0, 0);
        grid.add(imageView, 1, 0, 5, 1);

        int i = 0;
        while(i < courses.size()){
            String courseID = courses.get(i)[0];
            String courseName = courses.get(i)[1];

            Text courseText = new Text();
            courseText.setText(courseName);
            i++;
            grid.add(courseText, 0, i+1);

            Button viewCourse = new Button("View");
            viewCourse.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        stage.close();
                        MainUI ui = new MainUI(h.loadCourse(courseID));
                        Stage a = new Stage();
                        try {
                            ui.start(a);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }


                    }
                });
            viewCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(viewCourse, 1, i+1);

            Button renameCourse = new Button("Rename");
            renameCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    stage.close();
                    Dialog d = new Dialog();
                    d.setTitle("Rename Course");
                    d.setHeaderText("Rename Course: "+ courseName);

                    ButtonType confirmRenameCourse = new ButtonType("Rename Course", ButtonBar.ButtonData.APPLY.OK_DONE);
                    d.getDialogPane().getButtonTypes().addAll(confirmRenameCourse, ButtonType.CANCEL);

                    GridPane g = new GridPane();
                    g.setHgap(10);
                    g.setVgap(10);
                    g.setPadding(new Insets(20, 150, 10, 10));

                    final TextField newCourseName = new TextField();
                    newCourseName.setPromptText("Course Name");

                    final Text courseNameText = new Text();
                    courseNameText.setText(courseName);

                    g.add(new Label("Old Course Name"), 0, 0);
                    g.add(courseNameText, 1, 0);

                    g.add(new Label("New Course Name"), 0, 1);
                    g.add(newCourseName, 1, 1);

                    d.getDialogPane().setContent(g);

                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()){
                        if (newCourseName.getText().trim().length() == 0){
                            action.triggerAlert("Error Message",
                                    "Invalid Course Name",
                                    "Course name cannot be empty");
                            action.goToDashBoard(stage);
                        } else {
                            Course c = new Course();
                            c.setCourseID(Integer.parseInt(courseID));
                            c.setCourseName(newCourseName.getText());
                            c.setArchived(0);
                            h.updateCourse(c);
                            action.goToDashBoard(stage);
                        }
                    }
                }
            });
            renameCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(renameCourse, 2, i+1);

            Button archiveCourse = new Button("Archive");
            archiveCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    Course c = new Course();
                    c.setCourseID(Integer.parseInt(courseID));
                    c.setCourseName(courseName);
                    c.setArchived(1);
                    h.updateCourse(c);
                    Actions action = new Actions();
                    action.goToDashBoard(stage);
                }
            });
            archiveCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(archiveCourse, 3, i+1);

            Button deleteCourse = new Button("Delete");
            deleteCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    Course c = new Course();
                    c.setCourseID(Integer.parseInt(courseID));
                    h.deleteCourse(c);
                    Actions action = new Actions();
                    action.goToDashBoard(stage);
                }
            });
            deleteCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(deleteCourse, 4, i+1);

        }
        final Text actionsHeader = new Text();
        actionsHeader.setText("Actions");
        actionsHeader.setFont(new Font(20));
        actionsHeader.setUnderline(true);
        grid.add(actionsHeader, 0, i+3);

        final Text createANewCourseText = new Text();
        createANewCourseText.setText("Create A New Course");
        grid.add(createANewCourseText, 0, i+4);

        final TextField createANewCourseTextField = new TextField();
        createANewCourseTextField.setPromptText("Course Name");
        grid.add(createANewCourseTextField, 1, i+4);


        Button addCourseButton = new Button("Create");
        addCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                String courseName = createANewCourseTextField.getText();
                if (courseName.trim().length() == 0){
                    action.triggerAlert("Error Message",
                            "Invalid Course Name",
                            "Course name cannot be empty");
                } else {
                    stage.close();
                    MainUI ui = new MainUI(h.createNewCourse(courseName));
                    Stage a = new Stage();
                    try {
                        ui.start(a);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        grid.add(addCourseButton, 2, i+4);

        Button tagManagement = new Button("Tag Management");
        tagManagement.setMaxWidth(Double.MAX_VALUE);
        tagManagement.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                TagManagement tagManagement1 = new TagManagement();
                Stage a = new Stage();
                tagManagement1.start(a);
            }
        });
        grid.add(tagManagement, 0, i+5);



        Button viewArchivesButton = new Button("View Archives");
        viewArchivesButton.setMaxWidth(Double.MAX_VALUE);
        viewArchivesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
                ArchivesManagement archiveManagement = new ArchivesManagement();
                Stage a = new Stage();
                archiveManagement.start(a);
            }
        });
        grid.add(viewArchivesButton, 0, i+6);


        resetPasswordButton(grid, 0, i+7);

        StackPane root = new StackPane(grid);
        Scene scene = new Scene(root, 750, 620);
        stage.setScene(scene);
        stage.show();

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
                    String passwordText = newPassword.getText();
                    if (passwordText.contains(" ")){
                        action.triggerAlert("Error Message",
                                "Authentication Error",
                                "Password cannot have spaces");
                    } else {
                        Home h = new Home();
                        h.changeLogin(newPassword.getText());
                    }
                }

            }
        });
        grid.add(resetPasswordButton, columnIndex, rowIndex);
    }

}