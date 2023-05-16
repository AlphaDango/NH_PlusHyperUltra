package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Caregiver;
import java.sql.SQLException;
import java.util.List;

/**
 * The <code>AllCaregiverController</code> contains the entire logic of the patient view. It
 * determines which data is displayed and how to react to events.
 */
public class AllCaregiverController {

    @FXML
    private TableView<Caregiver> tableView;

    @FXML
    private TableColumn<Caregiver, Integer> colID;

    @FXML
    private TableColumn<Caregiver, String> colFirstName;

    @FXML
    private TableColumn<Caregiver, String> colSurname;

    @FXML
    private TableColumn<Caregiver, String> colTelephone;

    @FXML
    Button btnDelete;

    @FXML
    Button btnAdd;

    @FXML
    TextField txfSurname;

    @FXML
    TextField txfFirstname;

    @FXML
    TextField txfTelephone;

    private ObservableList<Caregiver> tableViewContent = FXCollections.observableArrayList();

    private CaregiverDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to
     * be displayed.
     */

    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("cid"));

        //CellValueFactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstName"));

        //CellValueFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colTelephone.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phoneNumber"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.tableViewContent);
    }

    /**
     * handles new firstname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new phoneNumber value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setPhoneNumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a caregiver by calling the update-Method in the {@link CaregiverDAO}
     *
     * @param t row to be updated by the user (includes the caregiver)
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t){
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link CaregiverDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableViewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        List<Caregiver> allCaregivers;
        try {
            allCaregivers = dao.readAll();
            for (   Caregiver c : allCaregivers) {
                this.tableViewContent.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the
     * {@link CaregiverDAO}
     */

    @FXML
    public void handleDeleteRow() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            dao.deleteById(selectedItem.getCid());
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an add-click-event. Creates A patient and calls the create method in the
     * {@link CaregiverDAO}
     */
    @FXML
    public void handleAdd() {
        String firstname = this.txfFirstname.getText();
        String surname = this.txfSurname.getText();
        String phoneNumber = this.txfTelephone.getText();
        try {
            Caregiver c = new Caregiver(firstname, surname, phoneNumber);
            dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextFields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextFields(){
        this.txfFirstname.clear();
        this.txfSurname.clear();
        this.txfTelephone.clear();
    }
}
