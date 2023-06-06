package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Oliver Neumann
 * Controller class for the LoginView.fxml
 */
public class LoginViewController {

    public static int CID;

    @FXML
    private TextField FUsername;
    @FXML
    private PasswordField FPasswort;
    @FXML
    private Button btnLogin;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            stage.setWidth(300);
            stage.setHeight(200);
            stage.centerOnScreen();
            stage.setTitle("NHPlus Login");
        });
    }


    /**
     * Shows a dialog Window with an errormessage, that the login failed due to an false username or password.
     */
    private void ShowLoginError(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Anmeldung Fehlgeschlagen");
        alert.setHeaderText(null);
        alert.setContentText("Benutzername oder Passwort ist falsch.");
        Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        errorButton.setId("loginFailOk");
        alert.showAndWait();
    }


    /**
     * Login handler which checkts if the username exists in the database and if the SHA256 hash of the password equals to the hash of the user in the database.
     * @throws IOException Might get throw if the MainWindowView.fxml is not found.
     * @throws SQLException Might get thrown if no connection to the database could be opened.
     */
    public void handleButtonAction() throws SQLException, IOException {
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            assert false: "MessageDigest couldn't initialise";
        }

        byte[] hashData = md.digest(FPasswort.getText().getBytes(UTF_8));
        StringBuilder buffer = new StringBuilder();

        for (byte b : hashData)
            buffer.append(String.format("%02x",b));

        Connection connection = ConnectionBuilder.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT CID,PASSWORT from USER WHERE USERNAME = ?");
        statement.setString(1,FUsername.getText());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            if (buffer.toString().equals(resultSet.getString("PASSWORT"))) {
                CID = resultSet.getInt("CID");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainWindowView.fxml")));
                Scene scene = btnLogin.getScene();
                scene.setRoot(root);
            } else ShowLoginError();
        } else ShowLoginError();
    }
}
