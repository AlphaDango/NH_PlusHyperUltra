package utils;

import javafx.scene.control.Alert;

/**
 * @author Oliver Neumann
 * @version 1.0
 * Helper class to create a Alert Window.
 */
public class MessageWindow {

    /**
     * Creates custom Altert Window.
     * @param title Title of the Altert.
     * @param errorMessage Message of the Altert.
     * @param altertType Type of the Alter.
     */
    public static void ShowMessage(String title, String errorMessage, Alert.AlertType altertType){
        Alert alert = new Alert(altertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
