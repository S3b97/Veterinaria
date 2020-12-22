package com.Miproyecto.proyectoAA.domain;

public class Usuario {

private int id;
private String nombre;
private String contraseña;


public Usuario(String nombre, String contraseña){

    this.nombre = nombre;
    this.contraseña = contraseña;

}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }



}
