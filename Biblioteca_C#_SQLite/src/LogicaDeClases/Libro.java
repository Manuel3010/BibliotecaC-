/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

/**
 *
 * @author ph
 */
public class Libro {
    private int Isbn;
    private String Titulo;
    private String Editorial;
    private String Edicion;
    private String Clasificacion;
    private int Ejemplares;

    public Libro() {
    }

    public Libro(int Isbn, String Titulo, String Editorial, String Edicion, String Clasificacion, int Ejemplares) {
        this.Isbn = Isbn;
        this.Titulo = Titulo;
        this.Editorial = Editorial;
        this.Edicion = Edicion;
        this.Clasificacion = Clasificacion;
        this.Ejemplares = Ejemplares;
    }

    public String getClasificacion() {
        return Clasificacion;
    }

    public void setClasificacion(String Clasificacion) {
        this.Clasificacion = Clasificacion;
    }

    public String getEdicion() {
        return Edicion;
    }

    public void setEdicion(String Edicion) {
        this.Edicion = Edicion;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public int getEjemplares() {
        return Ejemplares;
    }

    public void setEjemplares(int Ejemplares) {
        this.Ejemplares = Ejemplares;
    }

    public int getIsbn() {
        return Isbn;
    }

    public void setIsbn(int Isbn) {
        this.Isbn = Isbn;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
    
    
}
