package com.example.menuapp.ui.comerciolocal;

import androidx.appcompat.app.AppCompatActivity;

public class Comercio extends AppCompatActivity {

    private int idComercio;
    private String Nombre;
    private String CorreoElectronico;
    private int Telefono;
    private String Descripcion;
    private String Tipo;
    private String foto;

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = Nombre;
    }

    public String getCorreo() {
        return CorreoElectronico;
    }

    public void setCorreo(String correo) {
        this.CorreoElectronico = CorreoElectronico;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        this.Telefono = Telefono;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = Tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Comercio() {}

    public Comercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public Comercio(String nombre, String CorreoElectronico, int Telefono, String Descripcion, String Tipo, String foto) {
        this.Nombre = nombre;
        this.CorreoElectronico = CorreoElectronico;
        this.Telefono = Telefono;
        this.Descripcion = Descripcion;
        this.Tipo = Tipo;
        this.foto = foto;
    }

    public Comercio(int idComercio, String Nombre, String CorreoElectronico, int Telefono, String Descripcion, String Tipo, String foto) {
        this.idComercio = idComercio;
        this.Nombre = Nombre;
        this.CorreoElectronico = CorreoElectronico;
        this.Telefono = Telefono;
        this.Descripcion = Descripcion;
        this.Tipo = Tipo;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Comercio{" +
                "idComercio=" + idComercio +
                ", nombre='" + Nombre + '\'' +
                ", correo='" + CorreoElectronico + '\'' +
                ", telefono=" + Telefono +
                ", descripcion='" + Descripcion + '\'' +
                ", tipo='" + Tipo + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
