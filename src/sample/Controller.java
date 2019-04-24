package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controller.AlertBox;
import sample.model.Agent;
import sample.model.AgentManagement;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Label lblMessageLogin;

    @FXML
    private Label lblClose;

    @FXML
    void onClick_lblClose(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void onClick_btnSignIn(ActionEvent event) {
        Parent root = null;
        String username = txtUser.getText();
        String p = txtPass.getText();
        Agent agent = new Agent();
        agent.setAgtUser(username);
        agent.setAgtPassword(p);
        try {
            if (AgentManagement.Authenticate(agent) != 0) {
                try {
                    root = FXMLLoader.load(getClass().getResource("mainProgram.fxml"));

                    // Close Sign In windows
                    Stage prevStage = (Stage) btnSignIn.getScene().getWindow();
                    prevStage.close();

                    // Open main program windows
                    Stage stage = new Stage();
                    stage.setTitle("Travel Expert Agency");
                    stage.setScene(new Scene(root, 1300, 700));
                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                //message
                AlertBox.display("Error","Incorrect username or password", "Try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtPass != null : "fx:id=\"txtPass\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnSignIn != null : "fx:id=\"btnSignIn\" was not injected: check your FXML file 'sample.fxml'.";
        assert lblMessageLogin != null : "fx:id=\"lblMessageLogin\" was not injected: check your FXML file 'sample.fxml'.";
        assert lblClose != null : "fx:id=\"lblClose\" was not injected: check your FXML file 'sample.fxml'.";

    }
}
