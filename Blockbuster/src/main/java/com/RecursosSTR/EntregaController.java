/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class EntregaController extends LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btn_seleccionar;
    @FXML
    private TableView<lista> Lista;
    @FXML
    private TableColumn column_codigo;
    @FXML
    private TableColumn column_emision;
    @FXML
    private TableColumn column_clave;
    @FXML
    private TableColumn column_entrega;
    private ObservableList<lista> lista_ventas;
    
    private compra Producto;
    private ObservableList<compra> compra_productos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista_ventas = FXCollections.observableArrayList();
        this.Lista.setItems(lista_ventas);
        
        this.column_codigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.column_emision.setCellValueFactory(new PropertyValueFactory("emision"));
        this.column_clave.setCellValueFactory(new PropertyValueFactory("clave"));
        this.column_entrega.setCellValueFactory(new PropertyValueFactory("entrega"));
        
        Connection con = Conexion.connect();
        ResultSet result = null;
        PreparedStatement ps = null;
        
        try{
            String sql ="SELECT Codigo,Fecha_Emision,Clave_Usuario,Fecha_Entrega FROM Tabla_Registro WHERE Estado = 0";
            ps = con.prepareStatement(sql);
            result = ps.executeQuery();
            while(result.next()){
                String codigo = result.getString("Codigo");
                String emision = result.getString("Fecha_Emision");
                String clave = result.getString("clave");
                String entrega = result.getString("Fecha_Entrega");
                    lista l = new lista(codigo,emision,clave,entrega);
                    this.lista_ventas.add(l);
                    this.Lista.setItems(lista_ventas);
            }
            ps.close();
            con.close();
        }catch(SQLException e){
          e.printStackTrace();
        }
    }
    
    public void initAttributes(ObservableList<compra> compra_productos){
        this.compra_productos = compra_productos;
    }
    
    public void seleccionar(ActionEvent event){
        lista l =this.Lista.getSelectionModel().getSelectedItem();
        if(l != null){
            Date fecha = new Date();
            SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY");
            String fechas = formatofecha.format(fecha);
            String fecha_emision = l.getEntrega();
            Calendar c = Calendar.getInstance();
            try{
                String fecha_comprobacion ="";
                c.setTime(formatofecha.parse(fecha_emision));
                for(int i=0; i<60; i++){
                    if(i > 0){
                        c.add(Calendar.DATE, 1);
                    }
                    fecha_comprobacion = formatofecha.format(c.getTime());
                    switch(i){
                        case 0:
                            if(fecha_comprobacion == fechas){
                                compra d = new compra(l.getClave(),"Entrega de producto","Entrega","$0.00");
                                i=60;
                                this.Producto = d;
                                Stage stage = (Stage) this.Lista.getScene().getWindow();
                                stage.close();
                            }
                            break;
                        default:
                            if(fecha_comprobacion == fechas){
                                int costo = 30*i;
                                String costoso = "$"+costo+".00";
                                compra d = new compra(l.getClave(),"Entrega de producto","Entrega",costoso);
                                i=60;
                                this.Producto = d;
                                Stage stage = (Stage) this.Lista.getScene().getWindow();
                                stage.close();
                            }
                            break;
                    }
                }
                if(fecha_comprobacion!=fechas){
                    String costoso = JOptionPane.showInputDialog("Digite el precio a pagar: ");
                    compra d = new compra(l.getClave(),"Entrega de producto","Entrega",costoso);
                    this.Producto = d;
                    Stage stage = (Stage) this.btn_seleccionar.getScene().getWindow();
                    stage.close();
                }
            }catch(ParseException e){
                e.printStackTrace();
            }
            
        }
    }
    
    public compra getProducto() {
        return Producto;
    }
    
}
