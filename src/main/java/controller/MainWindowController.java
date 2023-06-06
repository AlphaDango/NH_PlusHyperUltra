package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button btnCaregiver;

    public void initialize() {

        Platform.runLater(() -> {
            // Zugriff auf die Stage
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            // Ändern der Fenstergröße und Position
            stage.setWidth(1000);
            stage.setHeight(1000);
            stage.setTitle("NHPlus");
            stage.centerOnScreen();

            // Plegermenue nur anzeigen wenn User ist Supervisor
            try {
                Connection connection = ConnectionBuilder.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT SUPER from USER WHERE CID = ?");
                statement.setInt(1, LoginViewController.CID);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next())
                    if(resultSet.getInt("SUPER") > 0)
                        return;

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            btnCaregiver.setVisible(false);
            btnCaregiver.setDisable(true);
        });
    }

    @FXML
    private void handleShowAllPatient(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllPatientController controller = loader.getController();
    }

    @FXML
    private void handleShowAllTreatments(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllTreatmentController controller = loader.getController();
    }

    @FXML
    private void handleShowAllCaregiver(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllCaregiverController controller = loader.getController();
    }
}
