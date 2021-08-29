package com.RecursosSTR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_password;
    @FXML
    private Button ingresar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void Ingresar_Datos(ActionEvent event){
        String Usuario;
        String Contrasena;
        
        Usuario = txt_usuario.getText();
        Contrasena = txt_password.getText();
        
        int ventana_mostrar = Conexion.Usuarios_System(Usuario, Contrasena);
        switch(ventana_mostrar){
            case 1:  
                try{   
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("/Caja_registradora.fxml"));
                System.out.println("Ruta: "+loader.getLocation());
                Scene scene = loader.load();
                Caja_registradoraController controller = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            case 2:
                try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("/Base_De_Datos.fxml"));
                System.out.println("Ruta: "+loader.getLocation());
                Scene scene = loader.load();
                Base_De_DatosController controller = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error, usuario no encontrado o la password no concide con el nombre de usuario");
                alert.showAndWait();
                break;
        }
    }
    
}
