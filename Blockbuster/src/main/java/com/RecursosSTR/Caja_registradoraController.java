package com.RecursosSTR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class Caja_registradoraController extends LoginController implements Initializable {
    
    @FXML
    private TextField txt_clave;
    @FXML
    private Button btn_pago;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_entrega;
    @FXML
    private TableView<compra> tabla_productos;
    @FXML
    private TableColumn colum_clave;
    @FXML
    private TableColumn column_producto;
    @FXML
    private TableColumn column_tipo;
    @FXML
    private TableColumn column_precio;
    
    private ObservableList<compra> compra_productos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compra_productos = FXCollections.observableArrayList();
        this.tabla_productos.setItems(compra_productos);
        
        this.colum_clave.setCellValueFactory(new PropertyValueFactory("clave"));
        this.column_producto.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.column_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.column_precio.setCellValueFactory(new PropertyValueFactory("precio")); 
    }
    
    
    
    public void cobrar_producto(ActionEvent event){
        
        
        if(Conexion.habilitar(this.txt_clave.getText())){
            
            String recivo="";
        for(int i = 0; i< this.tabla_productos.getItems().size();i++){
            compra p = this.tabla_productos.getItems().get(i);
            recivo=recivo+ p.getClave()+" - "+p.getNombre()+" - "+p.getTipo()+" - "+p.getPrecio()+"\n";
            if(p.getTipo()=="Entrega"){
                String nueva_clave=p.getClave();
            }
            String tipos=p.getTipo();
            if(tipos.toLowerCase()=="renta"){
                int estado=0;
                String codigo=Conexion.codigo();
                String clave=this.txt_clave.getText();
                String costo = p.getPrecio();
                Conexion.agregar_lista_renta(codigo, clave, costo, estado);
            }
            if(tipos.toLowerCase()=="venta"){
                int estado=1;
                String codigo=Conexion.codigo();
                String clave=this.txt_clave.getText();
                String costo = p.getPrecio();
                Conexion.agregar_lista_venta(codigo, clave, costo, estado);
            }
        }
        
        
        Date fecha = new Date();
            SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
            String fechas = formatofecha.format(fecha);
            int longitud_fecha= fechas.length();
            String nueva_fecha="";
            char verificacion;
            for(int i=0;i<longitud_fecha;i++){
                if(i==0){
                    nueva_fecha = String.valueOf(fechas.charAt(i));
                } else {
                    verificacion = fechas.charAt(i);
                    switch(verificacion){
                        case '/':
                            nueva_fecha=nueva_fecha+"-";
                            break;
                        case ':':
                            nueva_fecha=nueva_fecha+"-";
                            break;
                        default:
                            nueva_fecha= nueva_fecha+String.valueOf(verificacion);
                            break;
                    }
                }
            }
            
            recivo= formatofecha.format(fecha)+"\n"+recivo;
        
        try{
            String nombre_usuario=this.txt_clave.getText();
            String nombre_archivo=nombre_usuario+" - Recibo "+nueva_fecha+".txt";
            String ruta="C:\\Users\\carlo\\Documents\\recibos\\"+nombre_archivo;
            File file = new File(ruta);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(recivo);
            bw.close();
        }catch(IOException e){
              e.printStackTrace();
        }
        
        for(int i = 0; i<this.tabla_productos.getItems().size();i++){
            this.tabla_productos.getItems().clear();
        }
        this.tabla_productos.refresh();
            
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No se encuentra el usuario");
                alert.showAndWait();
        }
        
    }
    
    public void Agregar_Entrega(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Entrega.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            EntregaController controller = loader.getController();
            controller.initAttributes(compra_productos);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            compra p = controller.getProducto();
            
            this.compra_productos.add(p);
            this.tabla_productos.refresh();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void eliminar_producto(ActionEvent event){
        compra c = this.tabla_productos.getSelectionModel().getSelectedItem();
        
        if( c == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No selecciono ningun producto de la lista de compras");
                alert.showAndWait();
        } else {
            String clave = c.getClave();
            Conexion.update_productos_reintegro(clave);
            this.compra_productos.remove(c);
            this.tabla_productos.refresh();
        }
    }
    
    public void agregar_producto(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Caja_registradora_productos.fxml"));
            System.out.println("Ruta: "+loader.getLocation());
            Scene scene = loader.load();
            Caja_registradora_productosController controller = loader.getController();
            controller.initAttributes(compra_productos);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            
            compra p = controller.getProducto();
            
            this.compra_productos.add(p);
            this.tabla_productos.refresh();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
