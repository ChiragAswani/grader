package ui;

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
}
