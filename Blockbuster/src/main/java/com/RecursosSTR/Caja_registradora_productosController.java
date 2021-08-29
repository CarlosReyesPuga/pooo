package com.RecursosSTR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Caja_registradora_productosController extends LoginController implements Initializable {

    
    @FXML
    private Button btn_agregar;
    @FXML
    private TextField txt_clave;
    
    private compra Producto;
    private ObservableList<compra> compra_productos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initAttributes(ObservableList<compra> compra_productos){
        this.compra_productos = compra_productos;
    }
    
    public void agregar_producto_lista(ActionEvent event){
        String clave = txt_clave.getText();
        Connection con = Conexion.connect();
        ResultSet result = null;
        PreparedStatement ps = null;
        PreparedStatement pd = null;
        
        if(clave==""){
            Alert alertd = new Alert(Alert.AlertType.ERROR);
                    alertd.setHeaderText(null);
                    alertd.setTitle("Error");
                    alertd.setContentText("Error, no se escribio nada en la caja de texto");
                    alertd.showAndWait();
        } else {
                try{
                String sql = "SELECT clave,nombre,tipo,precio,existencia FROM Tabla_Productos WHERE clave = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, clave);
                result = ps.executeQuery();
                String nombre = result.getString("nombre");
                String tipo = result.getString("tipo");
                String precio = result.getString("precio");
                int existencia = result.getInt("existencia");
                ps.close();
                
                if(existencia == 0 ){
                    Alert alertf = new Alert(Alert.AlertType.ERROR);
                    alertf.setHeaderText(null);
                    alertf.setTitle("Error");
                    alertf.setContentText("Error, ya no contamos con ese producto");
                    alertf.showAndWait();
                    con.close();
                } else {
                    String SQL = "UPDATE Tabla_Productos set existencia = ? WHERE clave = ? ";
                    pd = con.prepareStatement(SQL);
                    int contador_existencia = existencia-1;
                    pd.setInt(1, contador_existencia);
                    pd.setString(2, clave);
                    pd.execute();
                    pd.close();
                    con.close();
                    compra p = new compra(clave,nombre,tipo,precio);
                    this.Producto = p;
                    Stage stage = (Stage) this.btn_agregar.getScene().getWindow();
                    stage.close();
                }
                con.close();
            }catch(SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error, no se encontro el producto");
                    alert.showAndWait();
            }
        }
        
    }

    public compra getProducto() {
        return Producto;
    }
    
    
}
