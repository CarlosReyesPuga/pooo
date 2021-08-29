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
public class Tabla_de_usuariosController extends LoginController implements Initializable {
    
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_salir;
    @FXML
    private TableView<usuarios> Tabla_usuarios;
    @FXML
    private TableColumn column_user;
    @FXML
    private TableColumn column_password;
    @FXML
    private TableColumn column_nivel;
    @FXML
    private TableColumn column_jerarquia;
    /**
     * Initializes the controller class.
     */
    private ObservableList<usuarios> Usuarios;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuarios  = FXCollections.observableArrayList();
        this.Tabla_usuarios.setItems(Usuarios);
        
        this.column_user.setCellValueFactory(new PropertyValueFactory("user"));
        this.column_password.setCellValueFactory(new PropertyValueFactory("password"));
        this.column_nivel.setCellValueFactory(new PropertyValueFactory("nivel"));
        this.column_jerarquia.setCellValueFactory(new PropertyValueFactory("nivel_jerarquico"));
        
        Connection con = Conexion.connect();
        ResultSet result = null;
        PreparedStatement ps = null;
        
        try{
            String sql="SELECT user,password,nivel,nivel_jerarquico FROM Tabla_Usuario";
            ps=con.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                String user = result.getString("user");
                String password = result.getString("password");
                int nivel = result.getInt("nivel");
                String nivel_jerarquico = result.getString("nivel_jerarquico");
                
                usuarios u = new usuarios(user,password,nivel,nivel_jerarquico);
                if(!this.Usuarios.contains(u)){
                    this.Usuarios.add(u);
                    this.Tabla_usuarios.setItems(Usuarios);
                }
            }
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void agregar_users(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Agregar_usuarios.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Agregar_usuariosController controller = loader.getController();
            controller.initAttributes(Usuarios);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            usuarios u = controller.getUser();
            if(u!=null){
                this.Usuarios.add(u);
                this.Tabla_usuarios.refresh();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void modificar_users(ActionEvent event){
        usuarios u = this.Tabla_usuarios.getSelectionModel().getSelectedItem();
        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Editar_usuarios.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Editar_usuariosController controller = loader.getController();
            controller.initAttributes(Usuarios,u);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            usuarios c = controller.getUser();
            if(c!=null){
                this.Tabla_usuarios.refresh();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void eliminar_users(ActionEvent event){
        usuarios u = this.Tabla_usuarios.getSelectionModel().getSelectedItem();
        
        if( u == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No selecciono ningun cliente de la lista de productos");
                alert.showAndWait();
        } else {
            String user = u.getUser();
            Conexion.eliminar_usuario(user);
            this.Usuarios.remove(u);
            this.Tabla_usuarios.refresh();
        }
    }
    
    public void salir(ActionEvent event){
        Stage stage = (Stage) this.btn_salir.getScene().getWindow();
        stage.close();
    }
    
}
