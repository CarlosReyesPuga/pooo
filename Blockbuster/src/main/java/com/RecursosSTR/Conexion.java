package com.RecursosSTR;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carlo
 */
public class Conexion {
    
    public static Connection connect(){
        Connection con =  null;
        
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:TablaBlockbuster.db");
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return con;
    }
    
    public static void insert_productos(String clave,String nombre,String tipo,String precio,int existencia){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        try{
            String sql = "INSERT INTO Tabla_Productos(clave, nombre, tipo, precio, existencia) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, clave);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, precio);
            ps.setInt(5, existencia);
            ps.execute();
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void update_productos(String clave,String nombre,String tipo,String precio,int existencia){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE Tabla_Productos set nombre = ?, tipo = ?, precio = ?, existencia = ? WHERE clave = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, tipo);
            ps.setString(3, precio);
            ps.setInt(4, existencia);
            ps.setString(5, clave);
            ps.execute();
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void update_productos_reintegro(String clave){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        PreparedStatement pd = null;
        ResultSet result = null;
        int existencia=0;
        try{
            String sql = "SELECT existencia FROM Tabla_Productos WHERE clave = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, clave);
            result = ps.executeQuery();
            existencia= Integer.parseInt(result.getString("existencia"));
            ps.close();
            existencia++;
            String sqld = "UPDATE Tabla_Productos set existencia = ? WHERE clave = ?";
            pd = con.prepareStatement(sqld);
            pd.setInt(1, existencia);
            pd.setString(2, clave);
            pd.execute();
            pd.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static int Usuarios_System(String usuario,String contrasena){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        PreparedStatement pd = null;
        ResultSet result = null;
        ResultSet resultado = null;
        try{
            String sql = "SELECT nivel FROM Tabla_Usuario WHERE user = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,usuario);
            result = ps.executeQuery();
            int nivel = Integer.parseInt(result.getString("nivel"));
            String sqld = "SELECT nivel FROM Tabla_Usuario WHERE password = ?";
            pd = con.prepareStatement(sqld);
            pd.setString(1, contrasena);
            resultado = pd.executeQuery();
            int nivelt = Integer.parseInt(resultado.getString("nivel"));
            ps.close();
            pd.close();
            con.close();
            if(nivel == nivelt){
                return nivel;
            } else {
                return 0;
            }
        }catch(SQLException e){
            return 0;
        }
    }
    
    public static void eliminar_dato(String clave){
        Connection con = Conexion.connect();
        PreparedStatement ps= null;
        
        try{
            String sql = "delete from Tabla_Productos WHERE clave = ?";
            ps =  con.prepareStatement(sql);
            ps.setString(1, clave);
            ps.execute();
            con.close();
        }catch(SQLException e){
             e.printStackTrace();
        } finally{
            
        }
    }
    
    public static boolean habilitar(String clave_usuario){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        ResultSet result = null;
        int verificacion = 0;
        
        try{
            String sql = "SELECT nivel_de_membresia FROM Tabla_Clientes WHERE clave = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,clave_usuario);
            result = ps.executeQuery();
            verificacion = Integer.parseInt(result.getString("nivel_de_membresia"));
            ps.close();
            con.close();
            if(verificacion!=0){
                return true;
            } else { 
                return false;
            }
        }catch(SQLException e){
                return false;
        }
    }
    
   public static void agregar_lista_venta(String codigo,String clave,String costo,int estado){
       Connection con = Conexion.connect();
       PreparedStatement ps = null;
       
       Date fecha = new Date();
       SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY");
       String fechas = formatofecha.format(fecha);
       
       try{
           String sql="INSERT INTO Tabla_Registro(Codigo,Fecha_Emision,Clave_Usuario,Fecha_entrega,Costo,Estado) VALUES(?,?,?,?,?,?)";
           ps =con.prepareStatement(sql);
           ps.setString(1, codigo);
           ps.setString(2, fechas);
           ps.setString(3, clave);
           ps.setString(4, " ");
           ps.setString(5, costo);
           ps.setInt(6, estado);
           ps.execute();
           ps.close();
           con.close();
       }catch(SQLException e){
           e.printStackTrace();
       }
   }
   
   public static void Agregar_cliente(String clave, String nombre, String direccion, int membresia, int edad){
       Connection con = Conexion.connect();
       PreparedStatement ps = null;
       try{
           String sql="INSERT INTO Tabla_Clientes(clave,nombre,direccion,nivel_de_membresia,edad) VALUES(?,?,?,?,?)";
           ps =con.prepareStatement(sql);
           ps.setString(1, clave);
           ps.setString(2, nombre);
           ps.setString(3, direccion);
           ps.setInt(4, membresia);
           ps.setInt(5, edad);
           ps.execute();
           ps.close();
           con.close();
       }catch(SQLException e){
       }
   }
   
   public static void update_cliente(String clave,String nombre,String direccion, int membresia, int edad){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE Tabla_Clientes set nombre = ?, direccion = ?, nivel_de_membresia = ?, edad = ? WHERE clave = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setInt(3, membresia);
            ps.setInt(4, edad);
            ps.setString(5, clave);
            ps.execute();
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
   
   public static void eliminar_cliente(String clave){
        Connection con = Conexion.connect();
        PreparedStatement ps= null;
        try{
            String sql = "delete from Tabla_Clientes WHERE clave = ?";
            ps =  con.prepareStatement(sql);
            ps.setString(1, clave);
            ps.execute();
            con.close();
        }catch(SQLException e){
             e.printStackTrace();
        } finally{
            
        }
    }
   
   public static void Agregar_usuario(String user, String password, int nivel, String jerarquia){
       Connection con = Conexion.connect();
       PreparedStatement ps = null;
       try{
           String sql="INSERT INTO Tabla_Usuario(user,password,nivel,nivel_jerarquico) VALUES(?,?,?,?)";
           ps =con.prepareStatement(sql);
           ps.setString(1, user);
           ps.setString(2, password);
           ps.setInt(3, nivel);
           ps.setString(4, jerarquia);
           ps.execute();
           ps.close();
           con.close();
       }catch(SQLException e){
       }
   }
   
    public static void update_usuario(String user,String password,int nivel, String jerarquia){
        Connection con = Conexion.connect();
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE Tabla_Usuario set password = ?, nivel = ?, nivel_jerarquico = ? WHERE user = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, nivel);
            ps.setString(3, jerarquia);
            ps.setString(4, user);
            ps.execute();
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
   
   public static void eliminar_usuario(String user){
        Connection con = Conexion.connect();
        PreparedStatement ps= null;
        try{
            String sql = "delete from Tabla_Usuario WHERE user = ?";
            ps =  con.prepareStatement(sql);
            ps.setString(1, user);
            ps.execute();
            con.close();
        }catch(SQLException e){
             e.printStackTrace();
        } finally{
            
        }
    }
   
   public static void agregar_lista_renta(String codigo,String clave,String costo,int estado){
       Connection con = Conexion.connect();
       PreparedStatement ps = null;
       
       Date fecha = new Date();
       SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY");
       String fechas = formatofecha.format(fecha);
       Calendar c = Calendar.getInstance();
       try{
           c.setTime(formatofecha.parse(fechas));
           c.add(Calendar.DATE, 3);
           try{
               String fecha_entrega = formatofecha.format(c.getTime());
                String sql="INSERT INTO Tabla_Registro(Codigo,Fecha_Emision,Clave_Usuario,Fecha_entrega,Costo,Estado) VALUES(?,?,?,?,?,?)";
                ps =con.prepareStatement(sql);
                ps.setString(1, codigo);
                ps.setString(2, fechas);
                ps.setString(3, clave);
                ps.setString(4, fecha_entrega);
                ps.setString(5, costo);
                ps.setInt(6, estado);
                ps.execute();
                ps.close();
                con.close();
           }catch(SQLException e){
                e.printStackTrace();
            }
       }catch(ParseException ex){
           ex.printStackTrace();
       }
       
   }
   
   public static void update_entrega(String codigo){
     Connection con = Conexion.connect();
     PreparedStatement ps = null;
     
     try{
            String sql = "UPDATE Tabla_Registro set Estado=1 WHERE Codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.execute();
            ps.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
   }
   
   public static String codigo(){
       int aleatorio = 0;
       String codigo="";
       for(int i=0; i<10;i++){
           aleatorio = (int)(Math.random()*10);
           switch(aleatorio){
               case 1:
                   if(i==0){
                       codigo = "A";
                   } else {
                       codigo = codigo+"A";
                   }
                   break;
                case 2:
                   if(i==0){
                       codigo = "C";
                   } else {
                       codigo = codigo+"C";
                   }
                   break;
                case 3:
                   if(i==0){
                       codigo = "E";
                   } else {
                       codigo = codigo+"E";
                   }
                   break;
                case 4:
                   if(i==0){
                       codigo = "G";
                   } else {
                       codigo = codigo+"G";
                   }
                   break;
                case 5:
                    if(i==0){
                        codigo = "I";
                   } else {
                        codigo = codigo+"I";
                   }
                    break;
                case 6:
                    if(i==0){
                        codigo = "K";
                   } else {
                        codigo = codigo+"K";
                   }
                    break;
                case 7:
                    if(i==0){
                        codigo = "M";
                   } else {
                        codigo = codigo+"M";
                   }
                    break;
                case 8:
                    if(i==0){
                        codigo = "O";
                   } else {
                        codigo = codigo+"O";
                   }
                    break;
                case 9:
                    if(i==0){
                        codigo = "Q";
                   } else {
                        codigo = codigo+"Q";
                   }
                    break;
                default:
                    if(i==0){
                        codigo = "S";
                   } else {
                        codigo = codigo+"S";
                   }
                    break;
            }
       }
       
       return codigo;
   }
}
