/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMonitor.controllers;

import NetworkMonitor.components.AdapterItemController;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;
import oshi.hardware.NetworkIF.IfOperStatus;

/**
 *
 * @author Santu
 */
public class HomeController implements Initializable {

    
    private NetworkIF activeAdapter;
    
    @FXML
    private Label activeAdapterLabel;
    @FXML
    private VBox adaptersList;
    
    @FXML
    private Label uploadSpeedLabel;
    @FXML
    private Label downloadSpeedLabel;
    
    
    
    
    public void init() throws SocketException {
        loadAdapters();
        startSpeedMonitor();
    }
    
    private void loadAdapters() throws SocketException {
        String osName = new SystemInfo().getOperatingSystem().getFamily();

        Set<String> seen = new HashSet<>();
        List<NetworkIF> networkIFs = new SystemInfo().getHardware().getNetworkIFs().stream()
                .filter(net -> seen.add(net.getMacaddr()))
                .collect(Collectors.toList());
        
        for(NetworkIF networkIF : networkIFs) {
            if("macOS".equals(osName) && networkIF.getSpeed() > 0 && networkIF.getBytesRecv() > 0){
                activeAdapter = networkIF;
            }else if(networkIF.getIfOperStatus() == IfOperStatus.UP){
                activeAdapter = networkIF;
            }
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/NetworkMonitor/components/AdapterItem.fxml"));
                HBox item = loader.load();
                
                AdapterItemController itemController = loader.getController();
                itemController.setNetworkInterface(networkIF);
                
                adaptersList.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    
    public void startSpeedMonitor() {
        new Thread(() -> {
            if (activeAdapter == null) {
                System.out.println("No active network interface found.");
                return;
            }

            try {
                activeAdapter.updateAttributes();

                long prevBytesSent = activeAdapter.getBytesSent();
                long prevBytesReceived = activeAdapter.getBytesRecv();

                while (true) {
                    activeAdapter.updateAttributes();

                    long bytesSent = activeAdapter.getBytesSent();
                    long bytesReceived = activeAdapter.getBytesRecv();

                    long uploadSpeed = bytesSent - prevBytesSent;
                    long downloadSpeed = bytesReceived - prevBytesReceived;

                    prevBytesSent = bytesSent;
                    prevBytesReceived = bytesReceived;

                    Platform.runLater(() -> {
                        uploadSpeedLabel.setText(formatSpeed(uploadSpeed));
                        downloadSpeedLabel.setText(formatSpeed(downloadSpeed));
                    });

                    // Pause for 1 second before the next update
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private String formatSpeed(long bytesPerSecond) {
        if (bytesPerSecond < 1024) {
            return bytesPerSecond + " B/s";
        } else if (bytesPerSecond < 1024 * 1024) {
            return String.format("%.2f KB/s", bytesPerSecond / 1024.0);
        } else {
            return String.format("%.2f MB/s", bytesPerSecond / (1024.0 * 1024.0));
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
