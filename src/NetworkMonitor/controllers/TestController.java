/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package NetworkMonitor.controllers;

import NetworkMonitor.utils.ViewSwitcher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Santu
 */
public class TestController implements Initializable {
    
    @FXML
    private Button button;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

        // Switch the scene
        Stage primaryStage = (Stage) button.getScene().getWindow();
        
        ViewSwitcher.switchView(primaryStage, "/NetworkMonitor/views/login.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
