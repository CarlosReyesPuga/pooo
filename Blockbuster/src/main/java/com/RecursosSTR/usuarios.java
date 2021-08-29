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
public class usuarios {
    private String user;
    private String password;
    private int nivel;
    private String nivel_jerarquico;

    public usuarios(String user, String password, int nivel, String nivel_jerarquico) {
        this.user = user;
        this.password = password;
        this.nivel = nivel;
        this.nivel_jerarquico = nivel_jerarquico;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNivel_jerarquico() {
        return nivel_jerarquico;
    }

    public void setNivel_jerarquico(String nivel_jerarquico) {
        this.nivel_jerarquico = nivel_jerarquico;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final usuarios other = (usuarios) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
   
    
}
