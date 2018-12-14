package ui;

import Student.Student;
import core.Course;
import core.Home;
import grades.Gradable;
import grades.Tag;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView.TableViewSelectionModel;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;


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

    Callback<TableColumn, TableCell> cellFactory =
            new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn assignmentNameCol) {
                    TableCell<Assignment, String> cell = new TableCell<Assignment, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty) ;
                            setText(empty ? null : item);
                        }
                    };
                    cell.setOnMouseClicked(e -> { addAssignment(assignmentNameCol, cell, e); });
                    return cell ;
                }
            };

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle(course.getCourseName());
        stage.setWidth(1000);
        stage.setHeight(1000);

        final Label label = new Label(this.course.getCourseName());
        label.setFont(new Font("Arial", 20));
        table.setEditable(true);
        table.setPrefWidth(900);

        TableColumn firstNameCol = setInitialTable("First Name", "firstName");
        TableColumn lastNameCol = setInitialTable("Last Name", "lastName");
        TableColumn BUIDCol = setInitialTable("BUID", "BUID");


        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { deleteStudent(); }
        });

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, BUIDCol);

        readInCourse();

        final Button addStudent = new Button("Add A New Student");
        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { addStudent(); }
        });

        final Button addGradingCategoryButton = new Button("Add a New Grading Category");
        addGradingCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { addGradingCategory(); }
        });

        hb.getChildren().addAll(addStudent, deleteButton);
        hb.setSpacing(3);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Button goBackButton = new Button("Go Back To Course Selection");
        goBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Actions action = new Actions();
                action.goToDashBoard(stage);
            }
        });

        vbox.getChildren().addAll(label, addGradingCategoryButton, table, hb, goBackButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void readInCourse(){
        HashMap<String, ArrayList<Gradable>> categories = new HashMap<>();
        for (Gradable gradable : course.getGradableList()) {
            String currentCategory = gradable.getType();
            if(!categories.containsKey(currentCategory)){
                ArrayList<Gradable> grads = new ArrayList<>();
                grads.add(gradable);
                categories.put(currentCategory, grads);
            }
            else{
                ArrayList<Gradable> grads = categories.get(currentCategory);
                grads.add(gradable);
                categories.put(currentCategory, grads);
            }
        }

        for (Student student : course.getStudentList()) {
            Person newPerson = new Person(
                    student.getFirstName(),
                    student.getLastName(),
                    student.getStudentID(), "0");
            data.add(newPerson);
        }
    }

    public void addAssignment(TableColumn assignmentNameCol, Cell cell, MouseEvent e){
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Grading Details");
        dialog.setHeaderText("Add Grading Details to Assignment: " + assignmentNameCol.getText());

        ButtonType addScore = new ButtonType("Add Score", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addScore, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final Text totalPoints = new Text();
        String tP = "40"; //backend-getTotalPointsByAssignmentName(assignmentName)
        totalPoints.setText(tP);

        final TextField pointsMissed = new TextField();
        pointsMissed.setPromptText("i.e 7");


        final Text percent = new Text();
        percent.setText("%");
        final Text percent2 = new Text();
        percent2.setText("%");

        grid.add(new Label("Assignment Total Points"), 0, 2);
        grid.add(totalPoints, 1, 2);
        grid.add(new Label("Points Missed"), 0, 3);
        grid.add(pointsMissed, 1, 3);

        Home h = new Home();
        List<Tag> selectedTags = new ArrayList<Tag>();
        List<Tag> tagList =  h.getAllTag();

        int i = 0;
        while (i < tagList.size()){
            Tag tagObj = tagList.get(i);
            CheckBox cb1 = new CheckBox();
            cb1.setText(tagList.get(i).getTname());
            cb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {
                    if(new_val){
                        selectedTags.add(tagObj);
                    } else{
                        selectedTags.remove(tagObj);
                    }
                }
            });
            grid.add(cb1, 0, 4+i);
            i++;
        }

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){

            Integer computedValue = Integer.parseInt(tP) - Integer.parseInt(pointsMissed.getText());
            cell.startEdit();
            cell.setText(computedValue.toString() + "/" + tP);
            cell.commitEdit(computedValue.toString() + "/" + tP);
            e.consume();
        }
    }

    public void deleteStudent() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }
        ObservableList<Integer> list = tsm.getSelectedIndices();

        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);
        Arrays.sort(selectedIndices);
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    public TableColumn setInitialTable(String columnName, String value){
        TableColumn firstNameCol = new TableColumn(columnName);
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>(value));
        return firstNameCol;
    }

    public void addStudentToTable(TextField addFirstName, TextField addLastName, TextField addBUID, ComboBox comboBox) {
        if (addFirstName.getText().length() == 0){
            System.out.println("First Name cannot be empty");
        }
        else if (addLastName.getText().length() == 0){
            System.out.println("Last Name cannot be empty");
        }
        else if (addBUID.getText().length() == 0){
            System.out.println("BUID cannot be empty");
        } else {
            course.addStudent(addFirstName.getText(), addLastName.getText(), addBUID.getText(), 0, comboBox.getSelectionModel().getSelectedItem().toString());
            Person newPerson = new Person(
                    addFirstName.getText(),
                    addLastName.getText(),
                    addBUID.getText(), "0");
            data.add(newPerson);
            addFirstName.clear();
            addLastName.clear();
            addBUID.clear();
        }
    }

    public void addStudent(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Add a New Student");
        dialog.setHeaderText("Add a student to " + course.getCourseName());

        ButtonType addStudentButton = new ButtonType("Add Student", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addStudentButton, ButtonType.CANCEL);

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

        Optional result = dialog.showAndWait();

        if (result.isPresent()){
            addStudentToTable(addFirstName, addLastName, addBUID, comboBox);
        }
    }

    public void addGradingCategory(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Add A New Grading Category");
        dialog.setHeaderText("Add a New Grading Category (i.e. Homeworks)");

        ButtonType addGradingCategory = new ButtonType("Add Grading Category", ButtonBar.ButtonData.APPLY.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addGradingCategory, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final TextField addGradingCategoryInput = new TextField();
        addGradingCategoryInput.setPromptText("Category Name");

        final TextField underGraduateWeight = new TextField();
        underGraduateWeight.setPromptText("i.e 10");

        final TextField graduateWeight = new TextField();
        graduateWeight.setPromptText("i.e 10");

        final Text graduateWeightPercent = new Text();
        graduateWeightPercent.setText("%");

        final Text undergraduateWeightPercent = new Text();
        undergraduateWeightPercent.setText("%");

        grid.add(new Label("Grading Category"), 0, 0);
        grid.add(addGradingCategoryInput, 1, 0);

        grid.add(new Label("Undergraduate Weight"), 0, 1);
        grid.add(underGraduateWeight, 1, 1);
        grid.add(undergraduateWeightPercent, 2, 1);

        grid.add(new Label("Graduate Weight"), 0, 2);
        grid.add(graduateWeight, 1, 2);
        grid.add(graduateWeightPercent, 2, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            addNewGradingCategoryToTable(addGradingCategoryInput.getText());
        }
    }

    public TableColumn addNewGradingCategoryToTable( String gradingCategory){

        TableColumn gC = new TableColumn(gradingCategory);
        gC.setMinWidth(100);
        gC.setCellValueFactory(new PropertyValueFactory<GradingCategory, String>(gradingCategory));

        Button addAssignment = new Button("+");
        addAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                Dialog dialog = new Dialog();
                dialog.setTitle("Add a Gradeable");
                dialog.setHeaderText("Add a new assignment to: " + gC.getText());

                ButtonType addAssignmentButton = new ButtonType("Add Assignment", ButtonBar.ButtonData.APPLY.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addAssignmentButton, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                final TextField addAssignmentName = new TextField();
                addAssignmentName.setPromptText("i.e HW2");

                final TextField addMaxScore = new TextField();
                addMaxScore.setPromptText("i.e 100");

                final TextField addUgradWeight = new TextField();
                addUgradWeight.setPromptText("i.e 35");

                final TextField addGradWeight = new TextField();
                addGradWeight.setPromptText("i.e 15");

                final Text percent = new Text();
                percent.setText("%");
                final Text percent2 = new Text();
                percent2.setText("%");


                grid.add(new Label("Add Assignment Name"), 0, 0);
                grid.add(addAssignmentName, 1, 0);
                grid.add(new Label("How many points in this assignment out of?"), 0, 1);
                grid.add(addMaxScore, 1, 1);
                grid.add(new Label("Undergraduate Weight"), 0, 2);
                grid.add(addUgradWeight, 1, 2);
                grid.add(percent2, 2, 2);
                grid.add(new Label("Graduate Weight"), 0, 3);
                grid.add(addGradWeight, 1, 3);
                grid.add(percent, 2, 3);

                dialog.getDialogPane().setContent(grid);

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()){
                    if (addAssignmentName.getText().length() == 0){
                        System.out.println("Assignment Name cannot be empty");
                    }
                    else if (addMaxScore.getText().length() == 0){
                        System.out.println("Maximum Score cannot be empty");
                    }
                    else if (addUgradWeight.getText().length() == 0 && addGradWeight.getText().length() == 0){
                        System.out.println("Both graduate and undergraduate weight are empty. Please fill in one");
                    } else {
                        BigDecimal maxScore = BigDecimal.valueOf(Integer.parseInt(addMaxScore.getText())).movePointLeft(2);
                        BigDecimal ugradWeight = BigDecimal.valueOf(Integer.parseInt(addUgradWeight.getText())).movePointLeft(2);
                        BigDecimal gradWeight = BigDecimal.valueOf(Integer.parseInt(addGradWeight.getText())).movePointLeft(2);

                        String assignmentName = addAssignmentName.getText();
                        course.addGradable(assignmentName, maxScore , ugradWeight, gradWeight, 0, gC.getText());

                        addNewGradable(assignmentName, maxScore , ugradWeight, gradWeight, gC);

                    }
                }

            }
        });
        gC.setGraphic(addAssignment);
        table.getColumns().addAll(gC);

        return gC;

    }

    public void addNewGradable(String assignmentName, BigDecimal maxScore , BigDecimal ugradWeight, BigDecimal gradWeight, TableColumn gC){
        TableColumn section = new TableColumn();
        section.setCellValueFactory(new PropertyValueFactory<Person, String>("test"));
        section.setMinWidth(100);
        section.setCellFactory(cellFactory);
        section.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        System.out.println(t.getNewValue());
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setGradable(t.getNewValue());
                    }
                }
        );
        Button editAssignment = new Button(assignmentName);
        editAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Dialog dialog = new Dialog();
                dialog.setTitle("Edit a Assignment");
                dialog.setHeaderText("Editing Assignment: " + assignmentName);
                ButtonType addAssignmentButton = new ButtonType("Edit Assignment", ButtonBar.ButtonData.APPLY.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addAssignmentButton, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                final TextField addAssignmentName = new TextField();
                addAssignmentName.setPromptText("i.e HW2");
                addAssignmentName.setText(assignmentName);

                final TextField addMaxScore = new TextField();
                addMaxScore.setPromptText("i.e 100");
                Float f = maxScore.floatValue()*100;
                addMaxScore.setText(f.toString());

                final TextField addUgradWeight = new TextField();
                addUgradWeight.setPromptText("i.e 35");
                addUgradWeight.setText(ugradWeight.toString());
                Float g = ugradWeight.floatValue()*100;
                addMaxScore.setText(g.toString());

                final TextField addGradWeight = new TextField();
                addGradWeight.setPromptText("i.e 15");
                addGradWeight.setText(gradWeight.toString());
                Float h = gradWeight.floatValue()*100;
                addMaxScore.setText(h.toString());

                final Text percent = new Text();
                percent.setText("%");
                final Text percent2 = new Text();
                percent2.setText("%");

                grid.add(new Label("Edit Assignment Name"), 0, 0);
                grid.add(addAssignmentName, 1, 0);
                grid.add(new Label("Edit How many points in this assignment out of?"), 0, 1);
                grid.add(addMaxScore, 1, 1);
                grid.add(new Label("Edit Undergraduate Weight"), 0, 2);
                grid.add(addUgradWeight, 1, 2);
                grid.add(percent2, 2, 2);
                grid.add(new Label("Edit Graduate Weight"), 0, 3);
                grid.add(addGradWeight, 1, 3);
                grid.add(percent, 2, 3);

                dialog.getDialogPane().setContent(grid);

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()){
                    if (addAssignmentName.getText().length() == 0){
                        System.out.println("Assignment Name cannot be empty");
                    }
                    else if (addMaxScore.getText().length() == 0){
                        System.out.println("Maximum Score cannot be empty");
                    }
                    else if (addUgradWeight.getText().length() == 0 && addGradWeight.getText().length() == 0){
                        System.out.println("Both graduate and undergraduate weight are empty. Please fill in one");
                    } else {
                        BigDecimal maxScore = BigDecimal.valueOf(Integer.parseInt(addMaxScore.getText())).movePointLeft(2);
                        BigDecimal ugradWeight = BigDecimal.valueOf(Integer.parseInt(addUgradWeight.getText())).movePointLeft(2);
                        BigDecimal gradWeight = BigDecimal.valueOf(Integer.parseInt(addGradWeight.getText())).movePointLeft(2);
                        Button newAssignment = new Button(addAssignmentName.getText());
                        section.setGraphic(newAssignment);

                    }
                }

            }
        });
        section.setGraphic(editAssignment);
        gC.getColumns().addAll(section);

    }
}