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
import javafx.application.Platform;

public class TagManagement extends Application {

    final HBox hb = new HBox();

    //make a dashboard

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        Dialog dialog = new Dialog();

        ArrayList<String> tagList = new ArrayList();
        tagList.add("took an extra 5 minutes");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int i = 0;

        final Text tagsTitle = new Text();
        tagsTitle.setText("Tags");
        tagsTitle.setFont(new Font(20));
        tagsTitle.setUnderline(true);
        grid.add(tagsTitle, 0, 0);
        while(i < tagList.size()){
            String tagName = tagList.get(i);

            Text tagText = new Text();
            tagText.setText(tagName);
            i++;
            grid.add(tagText, 0, i);

            Button viewCourse = new Button("Delete");
            viewCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {

                    dialog.close();
                }
            });
            viewCourse.setMaxWidth(Double.MAX_VALUE);
            grid.add(viewCourse, 1, i);
        }

        final Text createANewTag = new Text();
        createANewTag.setText("Create A New Tag");
        grid.add(createANewTag, 0, i+2);

        final TextField createANewTagTextField = new TextField();
        createANewTagTextField.setPromptText("Tag Name");
        grid.add(createANewTagTextField, 1, i+2);


        Button addTagButton = new Button("Create Tag");
        addTagButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                dialog.close();
            }
        });
        grid.add(addTagButton, 2, i+2);




        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("Quit", ButtonBar.ButtonData.APPLY.OK_DONE));
        Optional<String> result = dialog.showAndWait();
        dialog.close();

    }

}