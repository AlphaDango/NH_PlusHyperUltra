package controller;

import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Patient;
import utils.DateConverter;
import datastorage.DAOFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It
 * determines which data is displayed and how to react to events.
 */
public class AllPatientController {
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient, Integer> colID;
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colSurname;
    @FXML
    private TableColumn<Patient, String> colDateOfBirth;
    @FXML
    private TableColumn<Patient, String> colCareLevel;
    @FXML
    private TableColumn<Patient, String> colRoom;

    // this archiveTable is for role Admin implementation if required
    // @FXML
    // private TableColumn<Patient, String> colArchivedDatum;


    @FXML
    Button btnArchive;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtBirthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;

    private ObservableList<Patient> tableviewContent = FXCollections.observableArrayList();
    private PatientDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to
     * be displayed.
     */
    public void initialize() {
        //readAllAndShowInTableView();
        readAssignedAndShowInTable();

        // this deletes the patient archive
        deleteAllPatientArchiveOlderThanTen();



        this.colID.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("pid"));

        // CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName
                .setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
        // CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colDateOfBirth
                .setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfBirth"));
        this.colDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colCareLevel
                .setCellValueFactory(new PropertyValueFactory<Patient, String>("careLevel"));
        this.colCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colRoom.setCellValueFactory(new PropertyValueFactory<Patient, String>("roomnumber"));
        this.colRoom.setCellFactory(TextFieldTableCell.forTableColumn());

        // for Admin role this data can be shown if the feature is required
        // this.colArchivedDatum
        // .setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfArchived"));
        // this.colArchivedDatum.setCellFactory(TextFieldTableCell.forTableColumn());
        //

        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     * 
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * 
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }


    /**
     * handles new birthdate value
     * 
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setDateOfBirth(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new carelevel value
     * 
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setCareLevel(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new roomnumber value
     * 
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRoomnumber(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setRoomnumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a patient by calling the update-Method in the {@link PatientDAO}
     * 
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = dao.getAllNoneArchivedPatients();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void readAssignedAndShowInTable(){
        this.dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        this.tableviewContent.clear();
        try {
            allPatients = dao.readByID(LoginViewController.CID);
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a archive-click-event. Calls the update methods in the {@link PatientDAO} and Saves
     * {@link TreatmentDAO}
     */
    @FXML
    public void handleArchiveRowSperren() {
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            selectedItem.setDateOfArchive(LocalDate.now());
            dao.update(selectedItem);
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an add-click-event. Creates a patient and calls the create method in the
     * {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String birthday = this.txtBirthday.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        try {
            Patient p = new Patient(firstname, surname, date, carelevel, room);
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtBirthday.clear();
        this.txtCarelevel.clear();
        this.txtRoom.clear();
    }

    /**
     * for better design it is recommended to seperate business logic from controller however we do
     * not have service layer and therefore it is written here the function deletes the archive if
     * it is older than 10 years
     */
    private void deleteAllPatientArchiveOlderThanTen() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        dao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            List<Patient> allPatient = dao.getAllArchivedPatients().stream()
                    .filter(patient -> isMoreThanTenYearsAgo(patient.getDateOfArchive())).toList();
            allPatient.stream().forEach(pToDel -> {
                try {
                    long id = pToDel.getPid();
                    tDao.deleteByPid(id);
                    dao.deleteById(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * the method calculates difference between the Dates from now and 10 years ago and return true
     * if the date of archived patient is older than 10 years
     * 
     * @param dateArchive is the date of archived patient
     * 
     * @return true if the date of archived is older than 10 years
     */
    private boolean isMoreThanTenYearsAgo(String dateArchive) {
        LocalDate date = DateConverter.convertStringToLocalDate(dateArchive);
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        return date.isBefore(tenYearsAgo);
    }
}
