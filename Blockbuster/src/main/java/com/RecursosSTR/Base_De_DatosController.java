package com.RecursosSTR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javafx.scene.Parent;
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
public class Base_De_DatosController extends LoginController implements Initializable {
    
    @FXML
    private Button agregar;
    @FXML
    private Button modificar;
    @FXML
    private Button eliminar;
    @FXML
    private Button salir;
    @FXML
    private TableView<Productos> Tabla_Administrador;
    @FXML
    private TableColumn column_clave;
    @FXML
    private TableColumn column_nombre;
    @FXML
    private TableColumn column_tipo;
    @FXML
    private TableColumn column_precio;
    @FXML
    private TableColumn column_existencia;
    
    private ObservableList<Productos> productos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productos = FXCollections.observableArrayList();
        this.Tabla_Administrador.setItems(productos);
        
        this.column_clave.setCellValueFactory(new PropertyValueFactory("clave"));
        this.column_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.column_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.column_precio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.column_existencia.setCellValueFactory(new PropertyValueFactory("existencia"));
        
        Connection con = Conexion.connect();
        ResultSet result = null;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT clave, nombre, tipo, precio, existencia FROM Tabla_Productos";
            ps = con.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                String clave = result.getString("clave");
                String nombre = result.getString("nombre");
                String tipo = result.getString("tipo");
                String precio = result.getString("precio");
                int existencia = result.getInt("existencia");
                
                Productos p = new Productos(clave,nombre,tipo,precio,existencia);
                if(!this.productos.contains(p)){
                    this.productos.add(p);
                    this.Tabla_Administrador.setItems(productos);
                }
            }
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void Ingresar_tabla_clientes(ActionEvent event){
        try{   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Tabla_de_clientes.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Tabla_de_clientesController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex){
                    ex.printStackTrace();
        }
    }
    
    public void Ingresar_tabla_usuarios(ActionEvent event){
        try{   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Tabla_de_usuarios.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Tabla_de_usuariosController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void Agregar_Datos(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Agregar_productos.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Agregar_productosController controller = loader.getController();
            controller.initAttributes(productos);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            Productos p = controller.getProducto();
            if(p!=null){
                this.productos.add(p);
                this.Tabla_Administrador.refresh();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void Modificar_Datos(ActionEvent event){
        Productos c = this.Tabla_Administrador.getSelectionModel().getSelectedItem();
        if(c != null){
            try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Modificar_productos.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Modificar_productosController controller = loader.getController();
            controller.initAttributes(productos,c);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            Productos p = controller.getProducto();
            if(p!=null){
                this.Tabla_Administrador.refresh();
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
    
    public void Salir_Ventana(ActionEvent event){
            Stage stage = (Stage) this.salir.getScene().getWindow();
            stage.close();
    }
    
    public void eliminar_Datos(ActionEvent event){
        Productos c = this.Tabla_Administrador.getSelectionModel().getSelectedItem();
        
        if( c == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No selecciono ningun producto de la lista de productos");
                alert.showAndWait();
        } else {
            String clave = c.getClave();
            Conexion.eliminar_dato(clave);
            this.productos.remove(c);
            this.Tabla_Administrador.refresh();
        }
    }
    
}
