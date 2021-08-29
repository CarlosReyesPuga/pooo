/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class Agregar_usuariosController extends LoginController implements Initializable {
    
    @FXML
    private Button btn_agregar;
    @FXML
    private TextField txt_user;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_nivel;
    @FXML
    private TextField txt_jerarquia;
    /**
     * Initializes the controller class.
     */
    private usuarios User;
    private ObservableList<usuarios> Usuarios;
    
    public void initAttributes(ObservableList<usuarios> Usuarios){
        this.Usuarios = Usuarios;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void agregar_users(ActionEvent event){
        String user = txt_user.getText();
        String password = txt_password.getText();
        String jerarquia = txt_jerarquia.getText();
        
        try{
            int nivel = Integer.valueOf(txt_nivel.getText());
            
            usuarios u = new usuarios(user,password,nivel,jerarquia);
            
            if(!Usuarios.contains(u)){
                Conexion.Agregar_usuario(user, password, nivel, jerarquia);
                this.User = u;
                Stage stage = (Stage) this.btn_agregar.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Ya se encuentra este usuario");
                alert.showAndWait();
            }
        }catch(RuntimeException e){
            Alert alertd = new Alert(Alert.AlertType.ERROR);
            alertd.setHeaderText(null);
            alertd.setTitle("Error");
            alertd.setContentText("No se ingresaron todos los datos necesarios o un dato escrito no es el requerido");
            alertd.showAndWait();
        }
    }
    
    public usuarios getUser() {
        return User;
    }
    
}
