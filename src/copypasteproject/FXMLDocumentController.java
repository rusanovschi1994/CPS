/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copypasteproject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author IT-0
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TableView<Person> pTable;
    @FXML
    private TableView<Person> fTable;
    @FXML
    private TableColumn<Person, String> pCol;
    @FXML
    private TableColumn<Person, String> fCol;
    @FXML
    private TextField filterField;
    @FXML
    private Button btCopy;
    @FXML
    private Button btPaste;
    @FXML
    private Button btClear;
    @FXML
    private Button btFindLastRow;

    private final ObservableList<Person> pObservableList = FXCollections.observableArrayList();

    private final ObservableList<Person> fObservableList = FXCollections.observableArrayList();

    private int rowCount = 0;

    private int numberOfRows = 10000;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //setStaticRow
        setEmptyRow();

        pCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        fCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        //setItems "pTable"
        pTable.setPlaceholder(new Text("No content in table"));
        pTable.setItems(pObservableList);

        //setItems "fTable"
        fTable.setPlaceholder(new Text("No content in table"));
        fTable.setItems(fObservableList);

        // enable multi-selection on the table "fTable"
        fTable.getSelectionModel().setCellSelectionEnabled(true);
        fTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // enable paste on "pTable"
        TableUtils.installPasteHandler(pTable);
        fTable.setItems(pObservableList);

        // enable copy on "fTable"
        TableUtils.installCopyHandler(fTable);

        //----------------------------------------------------------------------
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Person> filteredData = new FilteredList<>(pObservableList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Person> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(fTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        fTable.setItems(sortedData);
    }

    public void setEmptyRow() {
        for (int i = 0; i < numberOfRows; i++) {
            pObservableList.add(i, new Person(""));
        }

        for (int j = 0; j < numberOfRows; j++) {
            fObservableList.add(j, new Person(""));
        }
    }

    @FXML
    private void handleClearAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Data");
        alert.setHeaderText("ATENTION!");
        alert.setContentText("Are you sure, you want to delete all data?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yes) {
            filterField.setText("");
            pTable.getItems().clear();
            setEmptyRow();
        } else {
            alert.close();
        }
    }

    @FXML
    private void handleCopyAction(ActionEvent event) {
        TableUtils.copySelectionToClipboard(fTable);
    }

    @FXML
    private void handlePasteAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Paste Data");
        alert.setHeaderText("ATENTION!");
        alert.setContentText("Are you sure, you want to paste data?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yes) {
            TableUtils.pasteFromClipboard(pTable);
        } else {
            alert.close();
        }
    }

    @FXML
    private void handleLastRowAction(ActionEvent event) {

        pTable.scrollTo(checkMaxRow());
        rowCount = 0;

    }

    private Integer checkMaxRow() {

        String s;
        int maxRow = 0;

        for (int i = 0; i < numberOfRows; i++) {
            rowCount++;
            s = pCol.getCellData(i);
            if (s.isEmpty() || s.length() == 1 || s.length() == 0) {
                continue;
            }
            System.out.println(rowCount + " : " + s);
            maxRow = rowCount;
        }
        System.out.println("Max number of rows is: " + maxRow);
        return maxRow - 1;
    }
}
