package utils;

import core.Course;
import javafx.stage.Stage;
import ui.Dashboard;
import ui.MainUI;

public class SessionManagementUtils {
    public static void buildBackButton(Stage currentStage) {
        currentStage.close();
        Dashboard dashboard = new Dashboard();
        Stage a = new Stage();
        try{
            dashboard.start(a);
        } catch (Exception err){
            System.out.println(err);
        }
    }

    public static void renderTable(Stage stage, Course course){
        MainUI newUI = new MainUI(course);
        Stage a = new Stage();
        try{
            newUI.start(a);
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
