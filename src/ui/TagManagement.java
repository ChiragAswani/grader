package ui;
import core.Home;
import grades.Tag;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class TagManagement extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Dialog dialog = new Dialog();
        Home h = new Home();
        List<Tag> tagList =  h.getAllTag();

        GridPane grid = Utils.buildGridPane();

        int i = 0;

        final Text tagsTitle = new Text();
        tagsTitle.setText("Tags");
        tagsTitle.setFont(new Font(20));
        tagsTitle.setUnderline(true);
        grid.add(tagsTitle, 0, 0);
        while(i < tagList.size()){
            Tag tagObj = tagList.get(i);
            String tagName = tagList.get(i).getTname();

            Text tagText = new Text();
            tagText.setText(tagName);
            i++;
            grid.add(tagText, 0, i);

            Button viewCourse = new Button("Delete");
            viewCourse.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    h.deleteTag(tagObj);
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
                h.createTag(createANewTagTextField.getText());
                dialog.close();
            }
        });
        grid.add(addTagButton, 2, i+2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("Back", ButtonBar.ButtonData.APPLY.OK_DONE));
        dialog.showAndWait();
        dialog.close();

    }

}