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
public class Editar_usuariosController  extends LoginController implements Initializable {
    
    @FXML
    private Button btn_editar;
    @FXML
    private TextField txt_user;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_nivel;
    @FXML
    private TextField txt_jerarquia;
    
    private usuarios User;
    private ObservableList<usuarios> Usuarios;
    /**
     * Initializes the controller class.
     */
    
    public void initAttributes(ObservableList<usuarios> Usuarios, usuarios u){
        this.Usuarios = Usuarios;
        this.User = u;
        this.txt_user.setText(u.getUser());
        this.txt_password.setText(u.getPassword());
        this.txt_nivel.setText(String.valueOf(u.getNivel()));
        this.txt_jerarquia.setText(u.getNivel_jerarquico());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void editar_users(ActionEvent event){
        String user = txt_user.getText();
        String password = txt_password.getText();
        String jerarquia = txt_jerarquia.getText();
        
        try{
           int nivel = Integer.valueOf(txt_nivel.getText());
           
           Conexion.update_usuario(user, password, nivel, jerarquia);
           
           this.User.setUser(user);
           this.User.setPassword(password);
           this.User.setNivel(nivel);
           this.User.setNivel_jerarquico(jerarquia);
           
           Stage stage = (Stage) this.btn_editar.getScene().getWindow();
           stage.close();
           
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
