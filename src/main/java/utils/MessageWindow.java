package utils;

import javafx.scene.control.Alert;

public class MessageWindow {

    public static void ShowMessage(String title, String errorMessage, Alert.AlertType altertType){
        Alert alert = new Alert(altertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
