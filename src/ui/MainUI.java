package ui;

import DAO.GradeDAO;
import Student.Student;
import com.jfoenix.controls.JFXButton;
import core.Course;
import core.Home;
import database.GradeDB;
import grades.Category;
import grades.Gradable;
import grades.Grade;
import grades.Tag;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import utils.SessionManagementUtils;
import utils.Utils;

import java.math.BigDecimal;
import java.util.*;


public class MainUI extends Application {

    private ObservableList<Person> data =
            FXCollections.observableArrayList();

    private TableView table;
    private ObservableList<ObservableList> data2;
    private List<Person> students = new ArrayList<>();
    final HBox hb = new HBox();
    private Course course;
    private Stage currentStage;


    Callback<TableColumn, TableCell> cellFactory =
            new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    TableCell<Assignment, String> cell = new TableCell<Assignment, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty) ;
                            setText(empty ? null : item);
                        }
                    };
                    cell.setOnMouseClicked(e -> {
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setTitle("Add Grading Details");
                        Button n = (Button) p.getGraphic();

                        dialog.setHeaderText("Add Grading Details to Assignment: " + n.getText());

                        ButtonType addScore = new ButtonType("Add Score", ButtonBar.ButtonData.APPLY.OK_DONE);

                        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        dialog.getDialogPane().getButtonTypes().setAll(addScore, cancelButton);


                        GridPane grid = Utils.buildGridPane();

                        final Text totalPoints = new Text();

                        Gradable assignment =  course.findAssignmentByName(n.getText(), p.getParentColumn().getText());
                        BigDecimal totalScore = assignment.getMaxScore();
                        String tP = Utils.prettyString(totalScore);
                        totalPoints.setText(tP);


                        int row = cell.getIndex();
                        data2.get(row);
                        String studentId = data2.get(row).get(2).toString();
                        int gradableId = assignment.getgID();
                        GradeDAO gdb=new GradeDB();
                        Grade grade=null;
                        try{
                            grade=gdb.findOneGrade(studentId,gradableId);
                        }catch (Exception err){
                            err.printStackTrace();
                        }

                        final TextField pointsMissed = new TextField();
                        if (grade!=null && grade.getScore().intValue()>-1){
                            BigDecimal showScore=grade.getTotalScore(course.getCourseID());
                            showScore=showScore.subtract(grade.getScore());
                            pointsMissed.setText(Utils.prettyString(showScore));
                        }
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
                            boolean hasTag=false;
                            for (Tag t: grade.gettList()){
                                if (tagObj.gettID()==t.gettID()){
                                    hasTag=true;
                                }
                            }
                            if (hasTag){
                                cb1.setSelected(true);
                                selectedTags.add(tagObj);
                            }else{
                                cb1.setSelected(false);
                            }
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

                        Optional<ButtonType> result = dialog.showAndWait();
                        if (result.isPresent()){
                            if( result.get()==cancelButton){
                                return;
                            }
                            if(pointsMissed.getText().equals("")){
                                return;
                            }

                            double computedValue =  Double.parseDouble(tP) - Double.parseDouble(pointsMissed.getText());

                            course.editGrade(String.valueOf(studentId),n.getText(), BigDecimal.valueOf(computedValue), selectedTags);
                            System.out.println("Editing row: "+data2.get(row));
                            e.consume();
                            SessionManagementUtils.renderTable(currentStage, course);

                        }
                        else{
                            System.out.println("Canceled");
                        }

                    });
                    return cell ;
                }
            };

    Callback<TableColumn, TableCell> finalGradeCellFactory =
            new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    TableCell<Assignment, String> cell = new TableCell<Assignment, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty) ;
                            setText(empty ? null : item);
                        }
                    };
                    cell.setOnMouseClicked(e -> {
                        int row = cell.getIndex();
                        data2.get(row);
                        String studentID = data2.get(row).get(2).toString();
                        displayFinalGradeInfo(studentID);
                    });
                    return cell ;
                }
            };

    public MainUI(Course c){
         this.course = c;
    }

    public static void main(String[] args) {
        launch(args);
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
            List<String> item = (ObservableList) table.getItems().get(selectedIndices[i]);
            String BUID = item.get(2);
            course.deleteStudent(BUID);
            SessionManagementUtils.renderTable(currentStage, course);
        }
    }

    public TableColumn addNewGradingCategoryToTable( String gradingCategory){

        TableColumn gC = new TableColumn(gradingCategory);
        gC.setMinWidth(100);
        gC.setCellValueFactory(new PropertyValueFactory<GradingCategory, String>(gradingCategory));
        Button addAssignmentButton = buildAddGradableColumn(gC);

        gC.setGraphic(addAssignmentButton);
        table.getColumns().addAll(gC);

        SessionManagementUtils.renderTable(currentStage, course);

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
                    }
                }
        );

        course.addGradable(assignmentName, maxScore, ugradWeight, gradWeight, 0, gC.getText());
        SessionManagementUtils.renderTable(currentStage, course);


    }
        //Update Table
        public void buildData() {
            data2 = FXCollections.observableArrayList();
            course.refreshEverything();
            List<Gradable> gradableList = course.getGradableList();
            List<grades.Category> categoryList = course.getCategoryList();
            int columnCounter = 0;
            try {

                /**
                 * ********************************
                 * TABLE COLUMN ADDED DYNAMICALLY *
                 *********************************
                 */
                TableColumn column1 = buildNewColumn(columnCounter, "First Name");
                table.getColumns().addAll(column1);
                System.out.println("Column [" + columnCounter + "] ");
                columnCounter++;


                TableColumn column2 = buildNewColumn(columnCounter, "Last Name");
                table.getColumns().addAll(column2);
                System.out.println("Column [" + columnCounter + "] ");
                columnCounter++;

                TableColumn column3 = buildNewColumn(columnCounter, "BUID");
                table.getColumns().addAll(column3);
                System.out.println("Column [" + columnCounter + "] ");
                columnCounter++;

                for (int i=0; i<categoryList.size(); i++){
                    String categoryName = categoryList.get(i).getCategoryName();
                    TableColumn categoryColumn = new TableColumn(categoryName);
                    categoryColumn.setMinWidth(300);
                    categoryColumn.setCellValueFactory(new PropertyValueFactory<GradingCategory, String>(categoryName));
                    Button addAssignmentButton = buildAddGradableColumn(categoryColumn);
                    categoryColumn.setGraphic(addAssignmentButton);


                    List<Gradable> assignmentsInCategory = course.getAssignmentsForCategory(categoryName);
                    System.out.println("Assignments in category: "+assignmentsInCategory);

                    for (Gradable gradable : assignmentsInCategory) {
                        String assignmentName = gradable.getAssignmentName();
                        TableColumn assignmentColumn = new TableColumn();
                        assignmentColumn.setMinWidth(100);
                        Button editAssignment = buildEditAssignmentButton(assignmentName, gradable.getMaxScore(), gradable.getWeight_ungrad(), gradable.getWeight_grad(), categoryColumn, assignmentColumn);
                        assignmentColumn.setGraphic(editAssignment);
                        assignmentColumn.setCellFactory(cellFactory);
                        int finalColumnCounter = columnCounter;
                        assignmentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(finalColumnCounter).toString());
                            }
                        });
                        columnCounter++;
                        categoryColumn.getColumns().addAll(assignmentColumn);

                        System.out.println("Adding Gradable: "+assignmentName);
                    }

                    System.out.println("Adding Category Column: "+i);

                    table.getColumns().addAll(categoryColumn);

                }


                /**
                 * ******************************
                 * Data added to ObservableList *
                 *******************************
                 */
                List<Student> studentList = course.getStudentList();
                for (Student student : studentList) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    row.add(student.getFirstName());
                    row.add(student.getLastName());
                    row.add(student.getStudentID());
                    List<Grade> gradeList = student.getGradeList();
                    System.out.println(gradeList);
                    for (int i=0; i<gradeList.size(); i++){
                        Grade grade = gradeList.get(i);
                        System.out.println(grade.getScore());
                        int intScore = grade.getScore().intValue();
                        BigDecimal score = grade.getScore().setScale(2);
                        BigDecimal total = grade.getTotalScore(course.getCourseID());
                        if (intScore == -1){
                            row.add("");
                        }
                        else{
                            row.add(Utils.prettyString(score) +"/"+Utils.prettyString(total));
                        }
                    }
                    for (int i=0; i<gradableList.size()-gradeList.size(); i++){
                        row.add("0");
                    }

                    System.out.println("Row [1] added " + row);
                    data2.add(row);
                }

                /**
                 * ******************************
                 * Add final scores *
                 *******************************
                 */
                TableColumn finalScoreColumn = buildNewColumn(columnCounter, "Final Grade");
                finalScoreColumn.setCellFactory(finalGradeCellFactory);
                table.getColumns().addAll(finalScoreColumn);
                System.out.println("Column [" + columnCounter + "] ");
                columnCounter++;
                List<String> finalScores = course.calculateFinalGrades();
                for (int i=0;i<studentList.size(); i++){
                    data2.get(i).add(finalScores.get(i));
                }

                //FINALLY ADDED TO TableView
                table.setItems(data2);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
        }

    @Override
    public void start(Stage stage) {
        //TableView
        table = new TableView();
        table.setMinWidth(1400);
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);


        buildData();

        Button newStudentButton = buildNewStudentButton();
        Button newCategoryButton = buildNewColumnButton();

        //Main Scene
        Scene scene = new Scene(new Group());
        scene.getStylesheets().add("ui/table-view.css");
        currentStage = stage;
        currentStage.setTitle(course.getCourseName());
        currentStage.setWidth(1500);
        currentStage.setHeight(900);


        Button deleteStudentButton = new Button("Delete Selected Rows");
        deleteStudentButton.getStyleClass().add("delButton");
        deleteStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { deleteStudent(); }
        });

        hb.getChildren().addAll(newStudentButton, deleteStudentButton);
        hb.setSpacing(3);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Button goBackButton = new Button("Go Back To Course Selection");
        goBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SessionManagementUtils.buildBackButton(currentStage);
            }
        });
        goBackButton.getStyleClass().add("backButton");

        final Label label = new Label(this.course.getCourseName());
        label.setFont(new Font("Arial", 20));

        vbox.getChildren().addAll(label,newCategoryButton, table, hb, goBackButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        currentStage.setScene(scene);

        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        currentStage.show();


    }



    public Button buildNewColumnButton(){
        final Button addGradingCategoryButton = new Button("Add a New Grading Category");
        addGradingCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Add A New Grading Category");
                dialog.setHeaderText("Add a New Grading Category (i.e. Homeworks)");

                ButtonType addGradingCategory = new ButtonType("Add Grading Category", ButtonBar.ButtonData.APPLY.OK_DONE);

                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(addGradingCategory, cancelButton);

                GridPane grid = Utils.buildGridPane();

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

                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent()){
                    if( result.get()==cancelButton){
                        return;
                    }
                    String gradingCategory = addGradingCategoryInput.getText();
                    String uWeight = underGraduateWeight.getText();
                    String gWeight = graduateWeight.getText();
                    course.addCategory(gradingCategory, BigDecimal.valueOf(Double.parseDouble(uWeight)), BigDecimal.valueOf(Double.parseDouble(gWeight)));

//                    SessionManagementUtils.renderTable(currentStage);
                    addNewGradingCategoryToTable(gradingCategory);
                }
            }
        });
        return addGradingCategoryButton;
    }

    public Button buildNewStudentButton(){
        final Button addStudent = new Button("Add A New Student");
        addStudent.getStyleClass().add("addButton");
        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Dialog dialog = new Dialog();
                dialog.setTitle("Add a New Student");
                dialog.setHeaderText("Add a student to " + course.getCourseName());

                ButtonType addStudentButton = new ButtonType("Add Student", ButtonBar.ButtonData.APPLY.OK_DONE);

                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(addStudentButton, cancelButton);

                GridPane grid = Utils.buildGridPane();

                final TextField addFirstName = new TextField();
                addFirstName.setPromptText("First Name");

                final TextField addLastName = new TextField();
                addLastName.setPromptText("Last Name");

                final TextField addBUID = new TextField();
                addBUID.setPromptText("BUID");

                ObservableList<String> options =
                        FXCollections.observableArrayList("ugrad", "grad");
                final ComboBox<String> comboBox = new ComboBox<>(options);
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
                    if( result.get()==cancelButton){
                        return;
                    }
                    if (addFirstName.getText().length() == 0){
                        System.out.println("First Name cannot be empty");
                    }
                    else if (addLastName.getText().length() == 0){
                        System.out.println("Last Name cannot be empty");
                    }
                    else if (addBUID.getText().length() == 0){
                        System.out.println("BUID cannot be empty");
                    } else {
                        course.addStudent(addBUID.getText(), addFirstName.getText(), addLastName.getText(),  0, comboBox.getSelectionModel().getSelectedItem().toString());
                        Person newPerson = new Person(
                                addFirstName.getText(),
                                addLastName.getText(),
                                addBUID.getText(),
                                new ArrayList<>(),
                                comboBox.getSelectionModel().getSelectedItem().toString());
//                        students.add(newPerson);
                        if (!students.isEmpty()){
                            Person masterStudent = students.get(0);
                            newPerson.setCategoryList(masterStudent.copyCategories());
                        }
//                        saveState();

                        ObservableList<String> row = FXCollections.observableArrayList();
                        row.add(newPerson.firstName);
                        row.add(newPerson.lastName);
                        row.add(newPerson.BUID);
                        System.out.println("New Row added " + row);
                        data2.add(row);


                        students.add(newPerson);

                        data.add(newPerson);
                        addFirstName.clear();
                        addLastName.clear();
                        addBUID.clear();
                        SessionManagementUtils.renderTable(currentStage, course);

                    }
                }
            }
        });
        return addStudent;
    }

    public Button buildAddGradableColumn(TableColumn gC){
        Button addAssignment = new Button("+");
        addAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                Dialog dialog = new Dialog<>();
                dialog.setTitle("Add a Gradeable");
                dialog.setHeaderText("Add a new assignment to: " + gC.getText());

                ButtonType addAssignmentButton = new ButtonType("Add Assignment", ButtonBar.ButtonData.APPLY.OK_DONE);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                dialog.getDialogPane().getButtonTypes().addAll(addAssignmentButton, cancelButton);

                GridPane grid = Utils.buildGridPane();

                final TextField addAssignmentName = new TextField();
                addAssignmentName.setPromptText("i.e HW2");

                final TextField addMaxScore = new TextField();
                addMaxScore.setPromptText("i.e 100");



                grid.add(new Label("Add Assignment Name"), 0, 0);
                grid.add(addAssignmentName, 1, 0);
                grid.add(new Label("How many points in this assignment out of?"), 0, 1);
                grid.add(addMaxScore, 1, 1);


                dialog.getDialogPane().setContent(grid);

                Optional result = dialog.showAndWait();

                if (result.isPresent()){
                    if( result.get()==cancelButton){
                        return;
                    }
                    if (addAssignmentName.getText().length() == 0){
                        System.out.println("Assignment Name cannot be empty");
                    }
                    else if (addMaxScore.getText().length() == 0){
                        System.out.println("Maximum Score cannot be empty");
                    }
                    else {
                        BigDecimal maxScore = BigDecimal.valueOf(Integer.parseInt(addMaxScore.getText()));

                        String assignmentName = addAssignmentName.getText();
//                        course.addGradable(assignmentName, maxScore , ugradWeight, gradWeight, 0, gC.getText());

                        addNewGradable(assignmentName, maxScore , new BigDecimal(0), new BigDecimal(0), gC);

                    }
                }

            }
        });
        return addAssignment;
    }

    public Button buildEditAssignmentButton(String assignmentName, BigDecimal maxScore , BigDecimal ugradWeight, BigDecimal gradWeight, TableColumn gC, TableColumn section){
        Button editAssignment = new JFXButton(assignmentName);
        editAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Edit a Assignment");
                dialog.setHeaderText("Editing Assignment: " + assignmentName);

                ButtonType addAssignmentButton = new ButtonType("Edit Assignment", ButtonBar.ButtonData.APPLY.OK_DONE);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                dialog.getDialogPane().getButtonTypes().addAll(addAssignmentButton, cancelButton);

                GridPane grid = Utils.buildGridPane();

                final TextField addAssignmentName = new TextField();
                addAssignmentName.setPromptText("i.e HW2");
                addAssignmentName.setText(assignmentName);

                final TextField addMaxScore = new TextField();
                addMaxScore.setPromptText("i.e 100");
                Float f = maxScore.floatValue();
                addMaxScore.setText(f.toString());

                grid.add(new Label("Edit Assignment Name"), 0, 0);
                grid.add(addAssignmentName, 1, 0);
                grid.add(new Label("Edit How many points in this assignment out of?"), 0, 1);
                grid.add(addMaxScore, 1, 1);

                Gradable gradable = course.findAssignmentByName(assignmentName, gC.getText());


                dialog.getDialogPane().setContent(grid);

                Optional result = dialog.showAndWait();

                if (result.isPresent()){
                    if( result.get()==cancelButton){
                        return;
                    }
                    if (addAssignmentName.getText().length() == 0){
                        System.out.println("Assignment Name cannot be empty");
                    }
                    else if (addMaxScore.getText().length() == 0){
                        System.out.println("Maximum Score cannot be empty");
                    } else {

                        BigDecimal maxScore = new BigDecimal(Double.parseDouble(addMaxScore.getText()));


                        gradable.setAssignmentName(addAssignmentName.getText());
                        gradable.setMaxScore(maxScore);
                        gradable.saveGradable();

                        Button newAssignment = buildEditAssignmentButton(addAssignmentName.getText(), maxScore , ugradWeight, gradWeight, gC, section);
                        section.setGraphic(newAssignment);

                        SessionManagementUtils.renderTable(currentStage, course);
                    }
                }
            }
        });
        return editAssignment;
    }

    public TableColumn buildNewColumn(int columnCounter, String columnName){
        TableColumn column = new TableColumn(columnName);
        column.setSortable(false);
        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(columnCounter).toString());
            }
        });
        return column;
    }


    public void displayFinalGradeInfo(String studentID){
        Dialog dialog = new Dialog<String>();

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButton);

        dialog.setHeaderText("Additional Information");
        GridPane grid = Utils.buildGridPane();

        Student currentStudent = course.getStudentList().get(course.findStudentIndex(studentID));

        Text firstName = new Text();
        firstName.setText(currentStudent.getFirstName());
        Text lastName = new Text();
        lastName.setText(currentStudent.getLastName());
        Text BUID = new Text();
        BUID.setText(currentStudent.getStudentID());

        int rowIndex = 0;
        grid.add(new Label("First Name:"), 0, rowIndex);
        grid.add(firstName, 1, rowIndex);
        rowIndex++;

        grid.add(new Label("Last Name:"), 0, rowIndex);
        grid.add(lastName, 1, rowIndex);
        rowIndex++;

        grid.add(new Label("BUID:"), 0, rowIndex);
        grid.add(BUID, 1, rowIndex);
        rowIndex++;

        Home h = new Home();
        List<Tag> allTagList=h.getAllTag();
        Map<String,Object> tagMap=new HashMap<>();
        for (Tag t:allTagList){
            tagMap.put(t.getTname(),0);
        }
        for (int courseCounter = 0; courseCounter < course.getCategoryList().size(); courseCounter++){
            Category category = course.getCategoryList().get(courseCounter);
            Text categoryName = new Text(category.getCategoryName());
            categoryName.setFont(new Font(20));
            categoryName.setUnderline(true);
            grid.add(categoryName, 0, rowIndex);
            rowIndex++;

            List<Gradable> assignmentsInCategory = course.getAssignmentsForCategory(category.getCategoryName());
            List<Grade> gradeInAssignment = currentStudent.getGradeList();

            for (int assignmentCounter = 0; assignmentCounter < assignmentsInCategory.size(); assignmentCounter++){
                String assignmentName = assignmentsInCategory.get(assignmentCounter).getAssignmentName();

                BigDecimal assignmentGrade = gradeInAssignment.get(assignmentCounter).getScore();
                String totalScore = Utils.prettyString(assignmentGrade) + "/" + Utils.prettyString(assignmentsInCategory.get(assignmentCounter).getMaxScore());

                grid.add(new Label(assignmentName + ":"), 0, rowIndex);
                grid.add(new Text(totalScore), 1, rowIndex);
                rowIndex++;
            }
        }

        Text tagsHeader = new Text("Student Tag Results");
        tagsHeader.setFont(new Font(20));
        tagsHeader.setUnderline(true);
        grid.add(tagsHeader, 0, rowIndex);
        rowIndex++;

        List<Grade> gradeInAssignment = currentStudent.getGradeList();

        for (Grade g:gradeInAssignment){
            if (g.gettList()!=null && g.gettList().size()!=0){
                for (Tag t:g.gettList()){
                    int newValue=(int)tagMap.get(t.getTname());
                    newValue++;
                    tagMap.replace(t.getTname(),newValue);
                }
            }
        }

        for (Tag t:allTagList){
            if ((int)tagMap.get(t.getTname()) == 0) {
                continue;
            } else{
                String tagName = t.getTname();
                Integer tagCount = (int)tagMap.get(t.getTname());
                grid.add(new Text(tagName), 0, rowIndex);
                grid.add(new Text(String.valueOf(tagCount)), 1, rowIndex);
                rowIndex++;
            }
        }

        dialog.getDialogPane().setContent(grid);
        Optional result = dialog.showAndWait();
    }


}