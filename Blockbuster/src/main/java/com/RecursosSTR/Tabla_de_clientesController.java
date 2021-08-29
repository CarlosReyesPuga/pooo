/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class Tabla_de_clientesController extends LoginController implements Initializable {
    
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_salir;
    @FXML
    private TableView<clientes> Tabla_clientes;
    @FXML
    private TableColumn column_clave;
    @FXML
    private TableColumn column_nombre;
    @FXML
    private TableColumn column_direccion;
    @FXML
    private TableColumn column_membresia;
    @FXML
    private TableColumn column_edad;
    
    private ObservableList<clientes> Clientes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Clientes  = FXCollections.observableArrayList();
        this.Tabla_clientes.setItems(Clientes);
        
        this.column_clave.setCellValueFactory(new PropertyValueFactory("clave"));
        this.column_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.column_direccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        this.column_membresia.setCellValueFactory(new PropertyValueFactory("nivel_de_membresia"));
        this.column_edad.setCellValueFactory(new PropertyValueFactory("edad"));
        
        Connection con = Conexion.connect();
        ResultSet result = null;
        PreparedStatement ps = null;
        
        try{
            String sql="SELECT clave,nombre,direccion,nivel_de_membresia,edad FROM Tabla_Clientes";
            ps=con.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                String clave = result.getString("clave");
                String nombre = result.getString("nombre");
                String direccion = result.getString("direccion");
                int membresia = result.getInt("nivel_de_membresia");
                int edad = result.getInt("edad");
                
                clientes c = new clientes(clave,nombre,direccion,membresia,edad);
                if(!this.Clientes.contains(c)){
                    this.Clientes.add(c);
                    this.Tabla_clientes.setItems(Clientes);
                }
            }
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }    
    
    public void agregar_cliente(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Agregar_cliente.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Agregar_clienteController controller = loader.getController();
            controller.initAttributes(Clientes);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            clientes c = controller.getCliente();
            if(c!=null){
                this.Clientes.add(c);
                this.Tabla_clientes.refresh();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void eliminar_cliente(ActionEvent event){
        clientes c = this.Tabla_clientes.getSelectionModel().getSelectedItem();
        
        if( c == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No selecciono ningun cliente de la lista de productos");
                alert.showAndWait();
        } else {
            String clave = c.getClave();
            Conexion.eliminar_cliente(clave);
            this.Clientes.remove(c);
            this.Tabla_clientes.refresh();
        }
    }
    
    public void editar_cliente(ActionEvent event){
        clientes c = this.Tabla_clientes.getSelectionModel().getSelectedItem();
        if(c != null){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("/Editar_cliente.fxml"));
                System.out.println("Ruta: "+loader.getLocation());
                Scene scene = loader.load();
                Editar_clienteController controller = loader.getController();
                controller.initAttributes(Clientes,c);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.showAndWait();
            
                clientes p = controller.getCliente();
                
                if(p!=null){
                    this.Tabla_clientes.refresh();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }   
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No selecciono ningun producto de la lista de productos");
                alert.showAndWait();
        }
    }
    
    public void salir(ActionEvent event){
        Stage stage = (Stage) this.btn_salir.getScene().getWindow();
        stage.close();
    }
}
