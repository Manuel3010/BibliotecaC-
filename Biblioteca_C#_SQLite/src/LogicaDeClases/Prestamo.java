/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

import javax.print.attribute.standard.DateTimeAtCompleted;

/**
 *
 * @author ph
 */
public class Prestamo {
  private int codigoPrestamo;
  private String carnetEstudiante;
  private int Isbn;
  private String tipoPrestamo;
  private DateTimeAtCompleted fechaPrestamo;
  private DateTimeAtCompleted fechaDevolucion;

    public Prestamo() {
    }

    public Prestamo(int codigoPrestamo, String carnetEstudiante, int Isbn, String tipoPrestamo, DateTimeAtCompleted fechaPrestamo, DateTimeAtCompleted fechaDevolucion) {
        this.codigoPrestamo = codigoPrestamo;
        this.carnetEstudiante = carnetEstudiante;
        this.Isbn = Isbn;
        this.tipoPrestamo = tipoPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIsbn() {
        return Isbn;
    }

    public void setIsbn(int Isbn) {
        this.Isbn = Isbn;
    }

    public String getCarnetEstudiante() {
        return carnetEstudiante;
    }

    public void setCarnetEstudiante(String carnetEstudiante) {
        this.carnetEstudiante = carnetEstudiante;
    }

    public int getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public void setCodigoPrestamo(int codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    public DateTimeAtCompleted getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(DateTimeAtCompleted fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public DateTimeAtCompleted getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(DateTimeAtCompleted fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }
  
  
  
  
}
