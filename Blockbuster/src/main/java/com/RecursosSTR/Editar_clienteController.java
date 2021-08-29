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
public class Editar_clienteController extends LoginController implements Initializable {
    
    @FXML
    private Button editar;
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
    
    public void initAttributes(ObservableList<clientes> Clientes, clientes c){
        this.Clientes = Clientes;
        this.Cliente = c;
        this.txt_clave.setText(c.getClave());
        this.txt_nombre.setText(c.getNombre());
        this.txt_direccion.setText(c.getDireccion());
        this.txt_membresia.setText(String.valueOf(c.getNivel_de_membresia()));
        this.txt_edad.setText(String.valueOf(c.getEdad()));
    }
    
    public void editar_cliente(ActionEvent event){
        String clave = this.txt_clave.getText();
        String nombre = this.txt_nombre.getText();
        String direccion = this.txt_direccion.getText();
        try{
            int membresia = Integer.parseInt(this.txt_membresia.getText());
            int edad = Integer.parseInt(this.txt_edad.getText());
            
            Conexion.update_cliente(clave, nombre, direccion, membresia, edad);
            
            this.Cliente.setClave(clave);
            this.Cliente.setNombre(nombre);
            this.Cliente.setDireccion(direccion);
            this.Cliente.setNivel_de_membresia(membresia);
            this.Cliente.setEdad(edad);
            
            Stage stage = (Stage) this.editar.getScene().getWindow();
            stage.close();
        }catch(RuntimeException e){
            Alert alertd = new Alert(Alert.AlertType.ERROR);
            alertd.setHeaderText(null);
            alertd.setTitle("Error");
            alertd.setContentText("Ocurrio un error con algun dato");
            alertd.showAndWait();
        }
    }

    public clientes getCliente() {
        return Cliente;
    }
    
    
}

