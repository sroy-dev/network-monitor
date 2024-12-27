/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMonitor.utils;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Santu
 */
public class ViewSwitcher {
    
    public static void switchView(Stage stage, String fxmlPath) {
        try {
            // Load the new FXML view
            Parent newView = FXMLLoader.load(ViewSwitcher.class.getResource(fxmlPath));

            // Create a FadeTransition for the new view
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Set the new scene and play the transition
            Scene scene = new Scene(newView);
            stage.setScene(scene);
            fadeIn.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
