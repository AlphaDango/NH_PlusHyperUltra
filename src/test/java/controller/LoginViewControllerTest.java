package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

public class LoginViewControllerTest extends ApplicationTest{

    @FXML private TextField FUsername;
    @FXML private PasswordField FPasswort;
    @FXML private Button FTreatments;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));//("/MainWindowView.fxml"));
        BorderPane pane = loader.load();
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
    }

    @Test
    public void handleLogin(){
        clickOn("#FUsername").write("Dango");
        clickOn("#FPasswort").write("test");
        clickOn("#btnLogin");
        FxAssert.verifyThat(window("NHPlus"), WindowMatchers.isFocused());
    }
}