/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author ph
 */
public class AdministradorDatos {

   private Conexion con=new Conexion();
   private SerializadorArchivos serial=new SerializadorArchivos();
    
    public AdministradorDatos() {
    }
   
    
    public void agregarEstudiante(Estudiante es,String rutaArchivo,String nombreArchivo){
    boolean estadoEstudiante;
    boolean estadoInsercionImagen ;
    boolean estadoInsercionContacto;
    boolean estado;
    con.conectar();
    con.iniciarTransaccion();
    Hashtable tabla=new Hashtable();
    tabla.put("carnet",es.getCarnet());
    tabla.put("nombre",es.getNombre());
    tabla.put("apellido",es.getApellido());
    tabla.put("sexo",es.getSexo());
    tabla.put("direccion",es.getDireccion());
    tabla.put("fecha_nacimiento",es.getFechaNacimiento());
    estadoEstudiante=con.InsertarRegistro(tabla, "estudiante");
    //impiamos la tabla
    tabla.clear();
    //comenzamos agregando los datos de contacto
    tabla.put("numero_telefono",es.getTelefono());
    tabla.put("carnet_estudiante", es.getCarnet());
    estadoInsercionContacto=con.InsertarRegistro(tabla, "contacto_estudiante");
    
    //limpiamos la tabla
    tabla.clear();
    //comenzamos agregando los datos del estado del estudiante por defecto cuando se agrega uno nuevo ps el estado es true es decir activo y 
    //libre de cargos por no cumplir las normas de la biblioteca.-
    tabla.put("carnet_estudiante", es.getCarnet());
    tabla.put("estado", true);
    estado=con.InsertarRegistro(tabla, "estado_estudiante");
    
    
    if(!rutaArchivo.equals("")){
    tabla.clear();
    tabla.put("nombreImagen", nombreArchivo);
    tabla.put("idEstudiante", es.getCarnet());
    tabla.put("id","ROWID");
    byte [] arregloDeBytesImagen;
    ImageIcon imageDeEstudiante=new ImageIcon(rutaArchivo);
    arregloDeBytesImagen=serial.serializarArchivo(imageDeEstudiante);
    tabla.put("foto", arregloDeBytesImagen);
    estadoInsercionImagen=con.InsertarRegistro(tabla, "imagenEstudiante");
    
    
    
    }else{
    tabla.clear();
    tabla.put("nombreImagen", "");
    tabla.put("idEstudiante", es.getCarnet());
    tabla.put("id","ROWID");
    byte [] arregloDeBytesImagen;
    arregloDeBytesImagen = new byte[0];
    tabla.put("foto", arregloDeBytesImagen);
    estadoInsercionImagen=con.InsertarRegistro(tabla, "imagenEstudiante");  
    }
    if(estadoEstudiante && estadoInsercionImagen && estado && estadoInsercionContacto){
    
    con.commit();
    
    }else{
    
    con.rollback();
    }
    con.habilitarModoConfirmacionAutomatica();
    con.desconectar();
    }

    public ResultSet buscarLibros(String BuscarTexto) {
        ResultSet resultadoBusqueda=null;
        con.conectar();
        Hashtable tabla=new Hashtable();
        tabla.put("ISBN", BuscarTexto);
        tabla.put("titulo",BuscarTexto);
        tabla.put("editorial", BuscarTexto);
        tabla.put("edicion", BuscarTexto);
        tabla.put("clasificacion", BuscarTexto);
        resultadoBusqueda = con.buscarRegistrosAutoIncrementable(tabla, tabla, " or ", "libros");
        con.desconectar();
        return resultadoBusqueda;
        
    }

    public ResultSet buscarLibro(String bnLibro) {
         con.conectar();
        ResultSet resultadoBusqueda=null;
        Hashtable tablaCamposASeleccionar=new Hashtable();
        tablaCamposASeleccionar.put("titulo","");
        tablaCamposASeleccionar.put("editorial","");
        tablaCamposASeleccionar.put("edicion","");
        tablaCamposASeleccionar.put("clasificacion","");
        tablaCamposASeleccionar.put("estante","");
        Hashtable tablacondiciones=new Hashtable();
        tablacondiciones.put("ISBN",bnLibro );
        
        resultadoBusqueda=con.buscarRegistros(tablaCamposASeleccionar, tablacondiciones,"or", " = ", " libro " );
        con.desconectar();
        return resultadoBusqueda;
    }

    public ResultSet buscarEstudiante(String id) {
        ResultSet resultadoBusqueda=null;
        con.conectar();
        Hashtable tabla=new Hashtable();
        tabla.put("nombre", "");
        tabla.put("apellido","");
        tabla.put("sexo","");
        tabla.put("direccion", "");
        tabla.put("fecha_nacimiento","");
        
        Hashtable tablacondicion=new Hashtable();
        tablacondicion.put("carnet", id);
        
        resultadoBusqueda=con.buscarRegistros(tabla, tablacondicion, "and", " = ", " estudiante ");
        
        return resultadoBusqueda;
    }
    
    public ResultSet buscarContacto(String id){
    ResultSet resultado=null;
    Hashtable tabla=new Hashtable();
    tabla.put("numero_telefono", "");
    
    Hashtable condicion=new Hashtable();
    condicion.put("carnet_estudiante", id);
    
    resultado=con.buscarRegistros(tabla, condicion, "and", "=", "contacto_estudiante");
    
    
    
    return resultado;
    
    }

    public void modificarEstudiante(Estudiante es,String rutaArchivo,String nombreArchivo) {
   boolean estadoActualizacionEstudiante;
    boolean estadoActualizacionImagen;
    boolean estadoActualizacionContacto;
    con.conectar();
    con.iniciarTransaccion();
    Hashtable tabla=new Hashtable();
    tabla.put("carnet",es.getCarnet());
    tabla.put("nombre",es.getNombre());
    tabla.put("apellido",es.getApellido());
    tabla.put("sexo",es.getSexo());
    tabla.put("direccion",es.getDireccion());
    tabla.put("fecha_nacimiento",es.getFechaNacimiento());
    
    Hashtable condicion=new Hashtable();
    condicion.put("carnet", es.getCarnet());
    
    estadoActualizacionEstudiante=con.ModificarRegistro(tabla, "estudiante", condicion,"and", "=");
    //impiamos la tabla
    tabla.clear();
    //comenzamos agregando los datos de contacto
    tabla.put("numero_telefono",es.getTelefono());
    tabla.put("carnet_estudiante", es.getCarnet());
    //
    condicion.clear();
    condicion.put("carnet_estudiante", es.getCarnet());
    estadoActualizacionContacto=con.ModificarRegistro(tabla, "contacto_estudiante", condicion, "and", "=");
  
    
    
    if(!rutaArchivo.equals("")){
    tabla.clear();
    tabla.put("nombreImagen", nombreArchivo);
    tabla.put("idEstudiante", es.getCarnet());
    tabla.put("id","ROWID");
    condicion.clear();
    condicion.put("idEstudiante", es.getCarnet());
    byte [] arregloDeBytesImagen;
    ImageIcon imageDeEstudiante=new ImageIcon(rutaArchivo);
    arregloDeBytesImagen=serial.serializarArchivo(imageDeEstudiante);
    tabla.put("foto", arregloDeBytesImagen);
    estadoActualizacionImagen=con.ModificarRegistro(tabla, "imagenEstudiante",condicion, "and", "=");
    
    if(estadoActualizacionEstudiante && estadoActualizacionImagen  && estadoActualizacionContacto){
    
    con.commit();
    
    }else{
    
    con.rollback();
    }
    
    }else if (estadoActualizacionEstudiante   && estadoActualizacionContacto){
    
    con.commit();
    }else{
    
    con.rollback();
    }
    con.habilitarModoConfirmacionAutomatica();
    con.desconectar();
    }
    
    
    public ImageIcon imagenEstudiante(String id){
          ImageIcon iconoEstudinate=null;
        try {
          
           // byte [] arregloDeImagen;
            Hashtable tabla=new Hashtable();
            tabla.put("foto", tabla);
            
            Hashtable codicion=new Hashtable();
            codicion.put("idEstudiante", id);
            
           ResultSet resultado= con.buscarRegistros(tabla, codicion, "and", "=", "imagenEstudiante");
            while(resultado.next()){
            iconoEstudinate=(ImageIcon) serial.descerealizarArchivo(resultado.getBytes("foto"));
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return iconoEstudinate;
    }


    public ResultSet buscarEstadoEstudiante(String text) {
        ResultSet resultado=null;
        Hashtable tabla=new Hashtable();
        tabla.put("estado","");
        Hashtable tablaCondicion=new Hashtable();
        tablaCondicion.put("carnet_estudiante", text);
        resultado=con.buscarRegistros(tabla, tablaCondicion, "and", "=", "estado_estudiante");
        
        return resultado;
    }

    public void modificarEstadoEstudiante(String id,String nuevoEstado,String Descripcion) {
        Hashtable tabla=new Hashtable<>();
        Hashtable condicion=new Hashtable();
        condicion.put("carnet_estudiante", id);
        if("baja".equalsIgnoreCase(nuevoEstado)){
        tabla.put("estado",false);
        
        }else{
        tabla.put("estado",true);
        
        }
        tabla.put("descripcion",Descripcion);
        
        
        con.ModificarRegistro(tabla, "estado_estudiante",condicion,"and","=");
    }
    
    
    
}
