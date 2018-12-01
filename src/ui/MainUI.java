package ui;

import Student.Student;
import com.sun.tools.javac.Main;
import core.Course;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.util.Pair;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainUI extends Application {

    private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();
    private Course course;
    public MainUI(Course c){
         this.course = c;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void deletePerson()
    {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();

        // Check, if any rows are selected
        if (tsm.isEmpty())
        {
            System.out.println("Please select a row to delete.");
            return;
        }

        // Get all selected row indices in an array
        ObservableList<Integer> list = tsm.getSelectedIndices();

        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        // Sort the array
        Arrays.sort(selectedIndices);

        // Delete rows (last to first)
        for(int i = selectedIndices.length - 1; i >= 0; i--)
        {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(1000);
        stage.setHeight(1000);

        final Label label = new Label(this.course.getCourseName());
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setPrefWidth(800);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );


        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    }
                }
        );

        TableColumn BUIDCol = new TableColumn("BUID");
        BUIDCol.setMinWidth(100);
        BUIDCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("BUID"));
        BUIDCol.setCellFactory(cellFactory);
        BUIDCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setBUID(t.getNewValue());
                    }
                }
        );

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                deletePerson();
            }
        });

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, BUIDCol);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");

        final TextField addBUID = new TextField();
        addBUID.setMaxWidth(BUIDCol.getPrefWidth());
        addBUID.setPromptText("BUID");


        final TextField addGradingCategory = new TextField();
        addGradingCategory.setPromptText("Add Grading Category");


        final Button addStudent = new Button("Add A New Student");
        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Create the custom dialog.
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Login Dialog");
                dialog.setHeaderText("Add a student to " + course.getCourseName());

                ButtonType loginButtonType = new ButtonType("Add Student", ButtonBar.ButtonData.APPLY.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                final TextField addFirstName = new TextField();
                addFirstName.setPromptText("First Name");

                final TextField addLastName = new TextField();
                addLastName.setPromptText("Last Name");

                final TextField addBUID = new TextField();
                addBUID.setPromptText("BUID");

                ObservableList<String> options =
                        FXCollections.observableArrayList("ugrad", "grad");
                final ComboBox comboBox = new ComboBox(options);
                comboBox.getSelectionModel().selectFirst();

                grid.add(new Label("FirstName:"), 0, 0);
                grid.add(addFirstName, 1, 0);
                grid.add(new Label("LastName:"), 0, 1);
                grid.add(addLastName, 1, 1);
                grid.add(new Label("BUID:"), 0, 2);
                grid.add(addBUID, 1, 2);
                grid.add(new Label("Student Type:"), 0, 3);
                grid.add(comboBox, 1, 3);


                dialog.getDialogPane().setContent(grid);

                Platform.runLater(() -> addFirstName.requestFocus());

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(addFirstName.getText(), addLastName.getText());
                    }
                    return null;
                });
                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    if (addFirstName.getText().length() == 0){
                        System.out.println("First Name cannot be empty");
                    }
                    else if (addLastName.getText().length() == 0){
                        System.out.println("Last Name cannot be empty");
                    }
                    else if (addBUID.getText().length() == 0){
                        System.out.println("BUID cannot be empty");
                    } else {
                        course.addStudent(addFirstName.getText(), addLastName.getText(), addBUID.getText(), comboBox.getSelectionModel().getSelectedIndex());
                        Person newPerson = new Person(
                                addFirstName.getText(),
                                addLastName.getText(),
                                addBUID.getText());
                        data.add(newPerson);
                        addFirstName.clear();
                        addLastName.clear();
                        addBUID.clear();
                    }
                });

            }
        });

        final Button addGradingCategoryButton = new Button("Add Grading Category");
        addGradingCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (addGradingCategory.getText().length() == 0){
                    System.out.println("Grading Category cannot be empty");
                } else {
                    String gradingCategory = addGradingCategory.getText();
                    addGradingCategory.clear();
                    TableColumn gC = new TableColumn(gradingCategory);
                    gC.setMinWidth(100);
                    gC.setCellValueFactory(new PropertyValueFactory<Person, String>(gradingCategory));
                    gC.setCellFactory(cellFactory);

                    Button addAssignment = new Button("+");
                    addAssignment.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            TextInputDialog dialog = new TextInputDialog();
                            dialog.setTitle("Assignment Input");
                            dialog.setHeaderText("Enter Assignment (i.e. HW2)");
                            dialog.setContentText("Assignment:");
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()){
                                TableColumn section = new TableColumn(result.get());
                                section.setMinWidth(100);
                                section.setCellValueFactory(new PropertyValueFactory<Person, String>(result.get()));
                                section.setCellFactory(cellFactory);

                                Button deleteAssignmentSection = new Button("-");
                                deleteAssignmentSection.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override public void handle(ActionEvent e) {
                                        System.out.println(result.get());
                                        section.getColumns().clear();
                                    }
                                });
                                section.setGraphic(deleteAssignmentSection);
                                gC.getColumns().addAll(section);
                            }
                        }
                    });
                    gC.setGraphic(addAssignment);
                    table.getColumns().addAll(gC);
                }
            }
        });

        hb.getChildren().addAll(addStudent, deleteButton);
        hb.setSpacing(3);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        vbox.getChildren().addAll(addGradingCategory, addGradingCategoryButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();


        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

    }
}