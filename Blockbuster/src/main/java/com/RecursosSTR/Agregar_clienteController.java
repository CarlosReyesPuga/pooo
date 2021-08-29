/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.net.URL;
import java.text.ParseException;
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
public class Agregar_clienteController extends LoginController implements Initializable {
    
    @FXML
    private Button agregar;
    @FXML
    private TextField txt_clave;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_membresia;
    @FXML
    private TextField txt_edad;
    
    private clientes Cliente;
    private ObservableList<clientes> Clientes;
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initAttributes(ObservableList<clientes> Clientes){
        this.Clientes = Clientes;
    }
    
    public void agregar_cliente(ActionEvent event){
        String clave = this.txt_clave.getText();
        String nombre = this.txt_nombre.getText();
        String direccion = this.txt_direccion.getText();
        
        try{
            int membresia = Integer.parseInt(this.txt_membresia.getText());
            int edad = Integer.parseInt(this.txt_edad.getText());
             
            clientes c = new clientes(clave,nombre,direccion,membresia,edad);
            
            if(!Clientes.contains(c)){
                Conexion.Agregar_cliente(clave, nombre, direccion, membresia, edad);
                this.Cliente =  c;
                Stage stage = (Stage) this.agregar.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Ya se encuentra este cliente");
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
    
    public clientes getCliente() {
        return Cliente;
    }
    
}
