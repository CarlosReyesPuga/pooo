package com.RecursosSTR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carlo
 */
public class compra {
    
    private String clave;
    private String nombre;
    private String tipo;
    private String precio;

    public compra(String clave, String nombre, String tipo, String precio) {
        this.clave = clave;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
    
    
}
