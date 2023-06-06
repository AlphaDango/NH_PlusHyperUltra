package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button btnCaregiver;

    /**
     * Initialization of the mainWindow. Sets the dimensions and centers the Window. Also sets the close event to a Logout or Exit Promt.
     */
    public void initialize() {
        Platform.runLater(() -> {
            // Zugriff auf die Stage
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume(); // Verhindert, dass das Fenster automatisch geschlossen wird

                    // Erzeugen einer Bestätigungsdialogbox
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Abfrage");
                    alert.setHeaderText("Abmelden oder Beenden?");
                    alert.setContentText("Möchten Sie sich abmelden oder das Programm beenden?");

                    // Hinzufügen von Abmelde- und Beenden-Buttons zur Dialogbox
                    ButtonType logoutButton = new ButtonType("Abmelden");
                    ButtonType exitButton = new ButtonType("Beenden");

                    alert.getButtonTypes().setAll(logoutButton, exitButton, ButtonType.CANCEL);

                    // Anzeigen der Dialogbox und Verarbeiten der Benutzeraktion
                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType == logoutButton) {
                            LoginViewController.CID = -1;
                            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));//("/MainWindowView.fxml"));
                            BorderPane pane = null;
                            try {
                                pane = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            Scene scene = new Scene(pane);
                            stage.setTitle("NHPlus");
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();

                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent e) {
                                    ConnectionBuilder.closeConnection();
                                    Platform.exit();
                                    System.exit(0);
                                }
                            });
                        } else if (buttonType == exitButton) {
                            ConnectionBuilder.closeConnection();
                            Platform.exit();
                            System.exit(0);
                        }
                    });
                }
            });


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
    /**
     * Opens the usercredentials menu.
     */
    private void handleShowAccount(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AccountView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AccountController controller = loader.getController();
    }

    @FXML
    /**
     * Opens the patient overview.
     */
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
    /**
     * Opens the treatments overview.
     */
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
    /**
     * Opens the Caregiver interface which is only accessible by supervisors.
     */
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
