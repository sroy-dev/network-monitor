/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMonitor.controllers;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author Santu
 */
public class HomeController implements Initializable {

    
    @FXML
    private Label activeAdapterLabel;
    @FXML
    private ListView<String> adaptersListView;
    
    
    public void init() throws SocketException {
        loadAdapters();
//        startSpeedMonitor();
    }
    
    private void loadAdapters() throws SocketException {
        System.out.println(adaptersListView);
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            adaptersListView.getItems().clear();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isUp()) {
//                    adaptersListView.getItems().add(networkInterface.getName());
                    System.out.println(networkInterface.getName());
                    if (networkInterface.isLoopback() || networkInterface.isVirtual()) continue;
                    activeAdapterLabel.setText(networkInterface.getDisplayName());
                }
            }
        } catch (SocketException e) {
            activeAdapterLabel.setText("Error: Unable to load adapters");
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
