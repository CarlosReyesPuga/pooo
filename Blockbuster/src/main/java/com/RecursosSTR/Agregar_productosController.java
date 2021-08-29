/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.io.IOException;
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
public class Agregar_productosController extends LoginController implements Initializable {
    
    @FXML
    private Button agregar;
    @FXML
    private TextField txt_clave;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_tipo;
    @FXML
    private TextField txt_precio;
    @FXML
    private TextField txt_existencia;
    
    private Productos producto;
    private ObservableList<Productos> productos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initAttributes(ObservableList<Productos> productos){
        this.productos = productos;
    }
    
    public void agregar_datos(ActionEvent event){
        String clave="";
        String nombre="";
        String tipo="";
        String precio="";
        int existencia=0;
        String exin="";
        
        try{
            clave = this.txt_clave.getText();
            nombre = this.txt_nombre.getText();
            tipo = this.txt_tipo.getText();
            precio = this.txt_precio.getText();
            existencia = Integer.parseInt(this.txt_existencia.getText());
                Productos p = new Productos(clave,nombre,tipo,precio,existencia);
        
                if(!productos.contains(p)){
                    Conexion.insert_productos(clave, nombre, tipo, precio, existencia);
                    this.producto = p;
                    Stage stage = (Stage) this.agregar.getScene().getWindow();
                    stage.close();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Ya se encuentra este producto");
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

    public Productos getProducto() {
        return producto;
    }
    
}
