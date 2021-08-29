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
public class Modificar_productosController extends LoginController implements Initializable {

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

    public void initAttributes(ObservableList<Productos> productos, Productos p){
        this.productos = productos;
        this.producto = p;
        this.txt_clave.setText(p.getClave());
        this.txt_nombre.setText(p.getNombre());
        this.txt_tipo.setText(p.getTipo());
        this.txt_precio.setText(p.getPrecio());
        this.txt_existencia.setText(String.valueOf(p.getExistencia()));
    }
    
    public void modificar_datos(ActionEvent event){
        String clave="";
        String nombre="";
        String tipo="";
        String precio="";
        int existencia=0;
        
        clave = this.txt_clave.getText();
        nombre = this.txt_nombre.getText();
        tipo = this.txt_tipo.getText();
        precio = this.txt_precio.getText();
        
        try{
            existencia = Integer.parseInt(this.txt_existencia.getText());
        
            Conexion.update_productos(clave, nombre, tipo, precio, existencia);
        
            this.producto.setClave(clave);
            this.producto.setNombre(nombre);
            this.producto.setTipo(tipo);
            this.producto.setPrecio(precio);
            this.producto.setExistencia(existencia);
        
            Stage stage = (Stage) this.agregar.getScene().getWindow();
            stage.close();
        }catch(RuntimeException e){
            Alert alertd = new Alert(Alert.AlertType.ERROR);
            alertd.setHeaderText(null);
            alertd.setTitle("Error");
            alertd.setContentText("Ocurrio un error con algun dato");
            alertd.showAndWait();
        }
        
    }

    public Productos getProducto() {
        return producto;
    }
    
    
    
}
