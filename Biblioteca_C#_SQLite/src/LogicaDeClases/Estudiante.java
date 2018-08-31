/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author ph
 */
public class Estudiante {
  private String Nombre;
  private String Apellido;
  private String Carnet;
  private String FechaNacimiento;
  private String Telefono;
  private String Direccion;
  private String Sexo;
  private ImageIcon imagenEstudiante;
  private boolean estado;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
  

    public ImageIcon getImagenEstudiante() {
        return imagenEstudiante;
    }

    public void setImagenEstudiante(ImageIcon imagenEstudiante) {
        this.imagenEstudiante = imagenEstudiante;
    }

    public Estudiante() {
    }

    public Estudiante(String Nombre, String Apellido, String Carnet, String FechaNacimiento, String Telefono, String Direccion, String Sexo) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Carnet = Carnet;
        this.FechaNacimiento = FechaNacimiento;
        this.Telefono = Telefono;
        this.Direccion = Direccion;
        this.Sexo = Sexo;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCarnet() {
        return Carnet;
    }

    public void setCarnet(String Carnet) {
        this.Carnet = Carnet;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
  
  
  
          
          }

