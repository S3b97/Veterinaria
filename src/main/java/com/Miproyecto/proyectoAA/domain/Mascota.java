package com.Miproyecto.proyectoAA.domain;

public class Mascota {

    private int id;
    private String nombre;
    private int edad ;
    private int peso;
    private String sexo;
    private String raza;
    private String tipo;

    public Mascota( String nombre, int edad, int peso, String sexo, String raza, String tipo) {

        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.sexo = sexo;
        this.raza = raza;
        this.tipo = tipo;
    }

    public Mascota(int id, String nombre, int edad, int peso, String sexo, String raza, String tipo) {

        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.sexo = sexo;
        this.raza = raza;
        this.tipo = tipo;
    }

    public Mascota() {

    }


    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void add(Mascota mascota) {
    }

@Override
    public String toString() {

        return nombre + " " + tipo;
}

}

