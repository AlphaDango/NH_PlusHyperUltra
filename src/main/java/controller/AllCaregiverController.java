package controller;

import datastorage.AccountDAO;
import datastorage.CaregiverDAO;
import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Account;
import model.Caregiver;
import utils.DateConverter;
import utils.LogChanges;
import utils.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static utils.LogChanges.LogChangesToFile;

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
    private TableColumn<Caregiver, String> colRole;

    @FXML
    Button btnArchive;

    @FXML
    Button btnAdd;

    @FXML
    TextField txfSurname;

    @FXML
    TextField txfFirstname;

    @FXML
    TextField txfTelephone;

    @FXML
    ChoiceBox cbxRole;

    private ObservableList<Caregiver> tableViewContent = FXCollections.observableArrayList();

    private CaregiverDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to
     * be displayed.
     */

    public void initialize() {
        readAllAndShowInTableView();

        // this deletes the patient archive
        deleteAllPatientArchiveOlderThanTen();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("cid"));

        //CellValueFactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstName"));

        //CellValueFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colTelephone.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phoneNumber"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colRole.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("role"));
        this.colRole.setCellFactory(TextFieldTableCell.forTableColumn());

        // for Admin role this data can be shown if the feature is required
        // this.colArchivedDatum
        // .setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfArchived"));
        // this.colArchivedDatum.setCellFactory(TextFieldTableCell.forTableColumn());
        //

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
     * handles new role value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRole(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setRole(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a caregiver by calling the update-Method in the {@link CaregiverDAO}
     * logs given caregiver by calling the LogChangesToFile-Method {@link LogChanges}
     *
     * @param t row to be updated by the user (includes the caregiver)
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t){
        try {
            dao.update(t.getRowValue());
            LogChangesToFile("Caregiver", t.getRowValue().toString());
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
            allCaregivers = dao.getAllNoneArchivedCaregivers();
            for (   Caregiver c : allCaregivers) {
                this.tableViewContent.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an archive-click-event. Calls the update methods in the
     * {@link CaregiverDAO}
     */

    @FXML
    public void handleArchiveRowSperren() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            selectedItem.setDateOfArchive(LocalDate.now());
            dao.update(selectedItem);
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an add-click-event.
     * Checks if no inputField is empty.
     * Creates A patient and calls the create method in the
     * {@link CaregiverDAO}
     */
    @FXML
    public void handleAdd() {
        removeHighlightsFromFields();
        if(validateTextField(this.txfFirstname) && validateTextField(this.txfSurname) && validateTextField(this.txfTelephone)) {
            String firstname = this.txfFirstname.getText();
            String surname = this.txfSurname.getText();
            String phoneNumber = this.txfTelephone.getText();
            String role = this.cbxRole.getValue().toString();
            boolean roleAsBool = false;
            try {
                Caregiver c = new Caregiver(firstname, surname, phoneNumber, role);
                roleAsBool = c.RoleAsBool();
                dao.create(c);
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            readAllAndShowInTableView();
            clearTextFields();


            String username = firstname.toLowerCase()+'.'+ surname.toLowerCase().charAt(0)+phoneNumber.substring(Math.max(phoneNumber.length() - 2, 0));
            String password = StringUtil.StringToSHA256(username);
            long CID;
            try {

                Connection connection = ConnectionBuilder.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT CID from CAREGIVER WHERE firstname = '"+firstname+"' AND surname = '"+surname+"' AND phoneNumber = '"+phoneNumber+"'");
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    CID = resultSet.getInt(1);
                    AccountDAO accountDAO = DAOFactory.getDAOFactory().createAccountDAO();
                    accountDAO.create(new Account(CID,username,password,roleAsBool));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            highlightEmptyField();
        }
    }

    /**
     * removes content from all textfields
     */
    private void clearTextFields(){
        this.txfFirstname.clear();
        this.txfSurname.clear();
        this.txfTelephone.clear();
    }

    /**
     * validates inputfields to not be empty
     * @param input inputField to check
     */
    private boolean validateTextField(TextField input) {
        if(input.getText().isEmpty()) {
           return false;
        }
        return true;
    }

    /**
     * add border to the empty fields, to show the missing inputs
     */
    private void highlightEmptyField(){
        if (!validateTextField(this.txfFirstname)) {
            this.txfFirstname.setStyle("-fx-border-color: red");
        }
        if (!validateTextField(this.txfSurname)) {
            this.txfSurname.setStyle("-fx-border-color: red");
        }
        if (!validateTextField(this.txfTelephone)) {
            this.txfTelephone.setStyle("-fx-border-color: red");
        }
    }

    /**
     * removes the highlighted border from the inputFields
     */
    private void removeHighlightsFromFields(){
        this.txfFirstname.setStyle("-fx-border-color: none");
        this.txfSurname.setStyle("-fx-border-color: none");
        this.txfTelephone.setStyle("-fx-border-color: none");
    }


    /**
     * for better design it is recommended to seperate business logic from controller however we do
     * not have service layer and therefore it is written here
     *
     * the function deletes the archive if it is older than 10 years
     *
     *
     */
    private void deleteAllPatientArchiveOlderThanTen() {
        dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            List<Caregiver> allCaregiver = dao.getAllArchivedCaregivers().stream()
                    .filter(caregiver -> isMoreThanTenYearsAgo(caregiver.getDateOfArchive())).toList();
            allCaregiver.stream().forEach(pToDel -> {
                try {
                    long id = pToDel.getCid();
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
     * Method that Checks if an archived database record is older than ten years.
     * @param dateArchive The Date of archival.
     * @return Returns true if the record is archived for ten years or longer.
     */
    private boolean isMoreThanTenYearsAgo(String dateArchive) {
        LocalDate date = DateConverter.convertStringToLocalDate(dateArchive);
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        return date.isBefore(tenYearsAgo);
    }
}
