package ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Actions {
    public void goToDashBoard(Stage stage){
        stage.close();
        Dashboard dashboard = new Dashboard();
        Stage a = new Stage();
        try{
            dashboard.start(a);
        } catch (Exception err){
            System.out.println(err);
        }
    }
    public void triggerAlert(String alertTitle, String alertHeader, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
