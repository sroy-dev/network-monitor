/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMonitor.controllers;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import oshi.SystemInfo;

/**
 *
 * @author Santu
 */
public class HomeController implements Initializable {

    
    @FXML
    private Label activeAdapterLabel;
    @FXML
    private ListView<String> adaptersListView;
    
    @FXML
    private Label uploadSpeedLabel;
    @FXML
    private Label downloadSpeedLabel;
    
    
    private NetworkInterface activeAdapter;
    
    
    public void init() throws SocketException {
        loadAdapters();
        
        SystemInfo systemInfo = new SystemInfo();
        
        System.out.println(systemInfo);
        
        
//        startSpeedMonitor();
    }
    
    private void loadAdapters() throws SocketException {
//        System.out.println(adaptersListView);
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            adaptersListView.getItems().clear();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isUp()) {
//                    adaptersListView.getItems().add(networkInterface.getName());
//                    System.out.println(networkInterface.getName());
                    if (networkInterface.isLoopback() || networkInterface.isVirtual()) continue;
                    
                    activeAdapter = networkInterface;
                    activeAdapterLabel.setText(networkInterface.getDisplayName());
                }
            }
        } catch (SocketException e) {
            activeAdapterLabel.setText("Error: Unable to load adapters");
            e.printStackTrace();
        }
    }
    
    
    private void startSpeedMonitor() {
        new Thread(() -> {
            try{
                if (activeAdapter == null) {
                    System.out.println("No active network interface found.");
                    return;
                }
                long prevBytesSent = 0;
                long prevBytesReceived = 0;
                
                
                while (true) {
                    long bytesSent = getBytesSent(activeAdapter);
                    long bytesReceived = getBytesReceived(activeAdapter);

                    long uploadSpeed = bytesSent - prevBytesSent;
                    long downloadSpeed = bytesReceived - prevBytesReceived;

                    prevBytesSent = bytesSent;
                    prevBytesReceived = bytesReceived;
                    
//                    System.out.print(uploadSpeed);
//                    System.out.println(downloadSpeed);

                    // Update the labels on the JavaFX UI thread
                    Platform.runLater(() -> {
                        uploadSpeedLabel.setText(formatSpeed(uploadSpeed));   // Upload speed
                        downloadSpeedLabel.setText(formatSpeed(downloadSpeed)); // Download speed
                    });

                    Thread.sleep(1000); // Update every second
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
                
            
//            while (true) {
//                Platform.runLater(() -> {
//                    uploadSpeedLabel.setText(String.valueOf(Math.random() * 100)); // Simulated upload speed
//                    downloadSpeedLabel.setText(String.valueOf(Math.random() * 100)); // Simulated download speed
//                });
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }).start();
    }
    
    
    
    private static long getBytesSent(NetworkInterface networkInterface) throws Exception {
        System.out.println(networkInterface);
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage() > 0 ? 5000L : 2000L; // Simulated data
    }

    private static long getBytesReceived(NetworkInterface networkInterface) throws Exception {
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage() > 0 ? 10000L : 4000L; // Simulated data
    }
    
    private static String formatSpeed(long bytes) {
        double kbps = bytes * 8.0 / 1024;
        if (kbps < 1024) {
            return String.format("%.2f Kbps", kbps);
        } else {
            return String.format("%.2f Mbps", kbps / 1024);
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
