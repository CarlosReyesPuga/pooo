/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author carlo
 */
public class App extends Application {
    
    
    public Stage stage;
    
    
     public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
         
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Login.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            LoginController controller = loader.getController();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            this.stage = primaryStage;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    
}
