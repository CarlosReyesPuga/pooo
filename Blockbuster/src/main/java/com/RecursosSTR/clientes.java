/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RecursosSTR;

import java.util.Objects;

/**
 *
 * @author carlo
 */
public class clientes {
    private String clave;
    private String nombre;
    private String direccion;
    private int nivel_de_membresia;
    private int edad;

    public clientes(String clave, String nombre, String direccion, int nivel_de_membresia, int edad) {
        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nivel_de_membresia = nivel_de_membresia;
        this.edad = edad;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNivel_de_membresia() {
        return nivel_de_membresia;
    }

    public void setNivel_de_membresia(int nivel_de_membresia) {
        this.nivel_de_membresia = nivel_de_membresia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final clientes other = (clientes) obj;
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        return true;
    }
    
    
}
