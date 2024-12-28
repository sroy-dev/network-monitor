/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package NetworkMonitor;

import NetworkMonitor.controllers.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Santu
 */
public class NetworkMonitor extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/NetworkMonitor/views/home.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        primaryStage.setScene(scene);
//        primaryStage.show();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NetworkMonitor/views/home.fxml"));
        Scene scene = new Scene(loader.load());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Network Monitor");

        HomeController controller = loader.getController();
        controller.init();

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
