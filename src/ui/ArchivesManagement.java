package ui;

import core.Course;
import core.Home;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class ArchivesManagement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Dialog dialog = new Dialog();
        ButtonType enterGradingPortal = new ButtonType("Go Back to Course Selection", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enterGradingPortal);

        Home h = new Home();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        final Text archivedCoursesTitle = new Text();
        archivedCoursesTitle.setText("Archived Courses");
        archivedCoursesTitle.setFont(new Font(20));
        archivedCoursesTitle.setUnderline(true);
        grid.add(archivedCoursesTitle, 0, 0);

        List<String[]> archivedCourses = h.seeArchivedCourses();

        int i = 0;
        while(i < archivedCourses.size()){

            String courseID = archivedCourses.get(i)[0];
            String courseName = archivedCourses.get(i)[1];
            i++;
            Text tagText = new Text();
            tagText.setText(courseName);
            grid.add(tagText, 0, i);

            Button unarchiveCourse = new Button("Unarchive Course");
            unarchiveCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    Course c = new Course();
                    c.setCourseID(Integer.parseInt(courseID));
                    c.setCourseName(courseName);
                    c.setArchived(0);
                    h.updateCourse(c);
                    Actions action = new Actions();
                    action.goToDashBoard(stage);
                }
            });
            unarchiveCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(unarchiveCourse, 1, i);
        }

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Actions action = new Actions();
            action.goToDashBoard(stage);
            dialog.close();
        }

    }

}