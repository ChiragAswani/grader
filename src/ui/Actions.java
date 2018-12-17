package ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utils.SessionManagementUtils;

public class Actions {
    public void goToDashBoard(Stage stage){
        SessionManagementUtils.buildBackButton(stage);
    }
    public void triggerAlert(String alertTitle, String alertHeader, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
