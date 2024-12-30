/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package NetworkMonitor.components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import oshi.hardware.NetworkIF;

/**
 * FXML Controller class
 *
 * @author sroy
 */
public class AdapterItemController {
    
    
    @FXML
    private Text interfaceName;
    @FXML
    private Text macAddress;

    public void setNetworkInterface(NetworkIF networkIF) {
        interfaceName.setText(networkIF.getDisplayName());
        macAddress.setText(networkIF.getMacaddr());
        
        System.out.println(networkIF.queryNetworkInterface().getDisplayName());
    }
    
    
    
}
