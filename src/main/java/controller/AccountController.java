package controller;

import datastorage.CaregiverDAO;
import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Caregiver;
import utils.StringUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.MessageWindow.ShowMessage;
import static utils.StringUtil.StringIsNumber;
import static utils.StringUtil.StringToSHA256;

/**
 * @author Oliver Neumann
 * @version 1.0
 * Controller class for the AccountView in which a User can manage their credentials.
 */
public class AccountController {

    private Caregiver caregiver;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPhone;

    @FXML
    private PasswordField pfOldPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfNewPasswordControl;

    /**
     * Initialization Method to fill in the credentials in the corresponding textfields.
     */
    public void initialize(){
        readAccountInformation();
    }

    /**
     * Reads the Caregiver and User Table in the Database and fills in the credentials.
     */
    private void readAccountInformation(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            caregiver = dao.read(LoginViewController.CID);
            tfName.setText(caregiver.getFirstName() + ' ' + caregiver.getSurname());
            tfPhone.setText(caregiver.getPhoneNumber());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Connection connection = ConnectionBuilder.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT USERNAME from USER WHERE CID = ?");
            statement.setString(1, String.valueOf(LoginViewController.CID));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                tfUsername.setText(resultSet.getString("USERNAME"));
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
            return;
        }
    }

    @FXML
    /**
     * ActionListener which handles the update of the user credentials.
     */
    private void handleChangeAccountInformations(ActionEvent e){

        if(!StringIsNumber(tfPhone.getText())){
            ShowMessage("Ungültige Nummer","Die angegebene Telefonnummer ist keine Nummer!", Alert.AlertType.ERROR);
            return;
        }

        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        caregiver.setPhoneNumber(tfPhone.getText());

        try {
            dao.update(caregiver);
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement statement = connection.prepareStatement(String.format("UPDATE USER SET USERNAME = '%s' WHERE CID = %s",tfUsername.getText(),LoginViewController.CID));
            statement.executeUpdate();
            ShowMessage("Benutzerdaten geändert","Die neuen Benutzerdaten wurden erfolgreich übernommen!", Alert.AlertType.CONFIRMATION);

        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @FXML
    /**
     * ActionListener to change the passwort of the user.
     */
    private void handleChangePassword(ActionEvent e){
        if(!pfNewPassword.getText().equals(pfNewPasswordControl.getText())){
            ShowMessage("Passwort ändern fehlgelschagen","Passwörter stimmen nicht überein!", Alert.AlertType.ERROR);
            return;
        }

        String oldPwHash = StringToSHA256(pfOldPassword.getText());
        String newPwHash = StringToSHA256(pfNewPassword.getText());

        Connection connection = ConnectionBuilder.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT PASSWORT from USER WHERE USERNAME = ?");
            statement.setString(1,tfUsername.getText());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(oldPwHash.equals(resultSet.getString("PASSWORT"))){
                    statement = connection.prepareStatement(String.format("UPDATE USER SET PASSWORT = '%s' WHERE CID = %s",newPwHash,LoginViewController.CID));
                    statement.executeUpdate();
                    ShowMessage("Passwort geändert","Das Passwort wurde erfolgreich geändert!", Alert.AlertType.CONFIRMATION);
                    pfOldPassword.clear();
                    pfNewPassword.clear();
                    pfNewPasswordControl.clear();
                } else ShowMessage("Flasches Passwort","Altes Passwort stimmt nicht überein", Alert.AlertType.ERROR);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
