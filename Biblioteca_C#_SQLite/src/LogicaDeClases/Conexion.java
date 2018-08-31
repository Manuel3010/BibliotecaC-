/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JOptionPane;

/**
 *
 * @author ph
 */
public class Conexion {

    private Connection con;
    private static final String driver = "org.sqlite.JDBC";
    private static final String ruta = System.getProperty("user.dir") + "\\baseDeDatos\\Biblioteca.s3db";
    private static final String url = "jdbc:sqlite:" + ruta;
    public String error = "";
    
    
//son los metodos ke serviran para devolver el error y mostrarlo al usuario

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Conexion() {
    }

    /*
     *metodo que sirve para conectarse con la base de datos.- 
     * 
     */
    
    public void conectar() {
        try {
            //JOptionPane.showMessageDialog(null, System.getProperty("user.dir") + "\\baseDeDatos\\Biblioteca.s3db");
            Class.forName(driver);
            con = DriverManager.getConnection(url);
            //por el momento mostramos el mensaje de conexion extablecida exitosamente.-
            //JOptionPane.showMessageDialog(null, "conexion establecida exitosamente");


        } catch (Exception ex) {
            this.setError(ex.getMessage());
            //por el momento mostramos el mensaje de error al intentar conectar la base de datos
            JOptionPane.showMessageDialog(null, "error al intentar conectar con la base de datos: " + ex.getMessage());
        }

    }
    
    
    
    public void desconectar(){
        try {
            con.close();
           // JOptionPane.showMessageDialog(null, "conexion finalizada exitosamente");
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "error al intentar cerrar la conexion"+ex.getMessage());
        }
    
    }

    /**
     * metodo que inserta un nuevo registro en la base de datos
     * @param tabla cotiene el nombre de los campos y el valor de cada uno
     * @param tablaDeBD es el nombre de la tabla en la que se almacenara el nuevo registro.-
     * @return devuelve verdadero o falso segun sea conveniente
     */
    
    
    public boolean InsertarRegistro(Hashtable tabla, String tablaDeBD) {
        
        if(tabla.size()<1){
        this.setError("Debe de ingresar por lo menos un campo para guardar el registro.-");
        return false;
        }
        
        Enumeration enumTabla = tabla.keys();
        String sentencia = "INSERT INTO " + tablaDeBD + "(";
        String segundaParteSentencia = " VALUES (";
        String llave;
        int x = 0;


        ArrayList<Object> listaDeDatos = new ArrayList<>();
        while (enumTabla.hasMoreElements()) {
            llave = (String) enumTabla.nextElement();
            if (x == 0) {
                sentencia = sentencia + llave;
                segundaParteSentencia = segundaParteSentencia + "?";
            } else if (x > 0 && x < tabla.size()) {
                sentencia = sentencia + "," + llave;
                segundaParteSentencia = segundaParteSentencia + "," + "?";
            }
            listaDeDatos.add(tabla.get(llave));
            //JOptionPane.showMessageDialog(null,"esto es :"+listaDeDatos.get(x).getClass().getSimpleName()); 
            //JOptionPane.showMessageDialog(null, listaDeDatos.get(x));
            x++;
        }

        
            sentencia = sentencia + ")";
            segundaParteSentencia = segundaParteSentencia + ")";
            sentencia = sentencia + segundaParteSentencia;
            //JOptionPane.showMessageDialog(null, sentencia);
            try {
            //preparamos el statement
            PreparedStatement pst = con.prepareStatement(sentencia);
            int posicion=1;
           this.completarSentencia(posicion,listaDeDatos, pst);

            int nr = pst.executeUpdate();
            if (nr > 0) {
                JOptionPane.showMessageDialog(null, "datosGuardados satisfactoriamente");
                return true;
            }else{
             JOptionPane.showMessageDialog(null, "los datos no pueden ser actualizados.-");
             return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error al intentar guardar los datos: "+ex.getMessage());
            return false;
        }
    }

    
    
    
    /**
     * metodo que modifica un registro existente en la base de datos.-
     * @param tabla almacena los ccampos a actualizar y los nuevos valores.-
     * @param tablaDeBD nombre de la tabla en la que se encuentra el regisro a modificar
     * @param condicion condiciones que se deben de cumplir para modificar el registro
     * @param condicionEntreCampos son las condiciones entre campos, cuando exista mas de una condicion
     * @param CondicionEntreValorYCampo son los diferentes operadores que se pueden utilizar en la clausula where
     * @return 
     */
    
    public boolean ModificarRegistro(Hashtable tabla, String tablaDeBD, Hashtable condicion,String condicionEntreCampos,String CondicionEntreValorYCampo) {
        
        if(tabla.size()<1){
        JOptionPane.showMessageDialog(null, "La sentencia update debe de actualizar por lo menos un dato.---");
        return false;
        }
        
        Enumeration enumTabla = tabla.keys();
        String sentencia = "UPDATE " + tablaDeBD + " SET ";
        //String segundaParteSentencia=" WHERE ";
        String llave;
        int x = 0;
        ArrayList<Object> listaDeDatosActualizar=new ArrayList<>();
        while (enumTabla.hasMoreElements()) {
            llave = (String) enumTabla.nextElement();
            if (x == 0) {
                sentencia = sentencia + llave + " = ?";
                //segundaParteSentencia=segundaParteSentencia+tabla.get(llave);
            } else if (x > 0 && x < tabla.size()) {
                sentencia = sentencia + "," + llave + " = ?";
                //segundaParteSentencia=segundaParteSentencia+","+tabla.get(llave);
            }
            listaDeDatosActualizar.add(tabla.get(llave));
            x++;
        }
        
        //segundaParteSentencia=segundaParteSentencia+")";
        
        //si la tabla condicion tiene por lo menos un dato ps se le agrega el where
        //y se hace el procedimiento de agregar los datos y posteriormente sus valores.-
       if(condicion.size()>0){
       sentencia = sentencia + " WHERE ";
       }

Enumeration enumTablaCondicion=condicion.keys();
String llaveCondicion;
int iteracion=0;
ArrayList<Object> listaDeDatosComparacion=new ArrayList<>();
        while(enumTablaCondicion.hasMoreElements()){
        llaveCondicion=(String) enumTablaCondicion.nextElement();
        if(iteracion==0){
        sentencia=sentencia+" ( "+llaveCondicion+CondicionEntreValorYCampo+" ? )";
        
        }else if(iteracion>0&&iteracion<condicion.size()){
        sentencia=sentencia+condicionEntreCampos+" ("+llaveCondicion+CondicionEntreValorYCampo+" ? )";
        
        }
        listaDeDatosComparacion.add(condicion.get(llaveCondicion));
        iteracion++;
        }
        
        //preparamos el statement pero como es compuesto primero mandamos los datos de lo
        //campos a actualizar y despues los campos de la condcion.-
        try{
        PreparedStatement pst=con.prepareStatement(sentencia);
        int posicion=1;
        this.completarSentencia(posicion, listaDeDatosActualizar, pst);
        this.completarSentencia(tabla.size()+1, listaDeDatosComparacion, pst);
        
        int resultado=0;
        resultado=pst.executeUpdate();
        if(resultado>0){
        JOptionPane.showMessageDialog(null, "Datos modificados exitosamente");
        return true;
        }
        }catch(Exception ex){
           // JOptionPane.showMessageDialog(null, ex.getMessage());
        this.setError(ex.getMessage());
        return false;
        }
        
        //JOptionPane.showMessageDialog(null, sentencia);
        return true;
    }

    
    /**
     * 
     * @param tabla contiene los campos que se deben de cumplir para ke el registro sea eliminado
     * @param tablaDeBD nombre de la tabla que contiene el registro
     * @param condicionEntreCampos condicion entre distintos campos ej. and, or, etc.
     * @param condicionEntreCampoYvalor condicion entre nombre de campo y valor ej: =,!=, etc.
     * @return devuelve verdadero o falso segun sea el caso.-
     */
    
    public boolean EliminarRegistro(Hashtable tabla, String tablaDeBD, String condicionEntreCampos,String condicionEntreCampoYvalor) {
        Enumeration enumTabla = tabla.keys();
        String sentencia = "DELETE FROM " + tablaDeBD;
        
        if(tabla.size()>0){
        sentencia=sentencia+" WHERE ";
        
        }
        String llave;
        int x = 0;
        ArrayList<Object> listaDeDatos=new ArrayList<>();
        while (enumTabla.hasMoreElements()) {
            llave = (String) enumTabla.nextElement();
            if (x == 0) {
                sentencia = sentencia + "(" + llave+condicionEntreCampoYvalor + " ? " + ")";

            } else if (x > 0 && x < tabla.size()) {

         sentencia = sentencia + " " + condicionEntreCampos + " (" + llave + condicionEntreCampoYvalor+" ? " + ")";
            }
            listaDeDatos.add(tabla.get(llave));

            x++;
        }

        //JOptionPane.showMessageDialog(null, sentencia);
        try{
            PreparedStatement pst=con.prepareStatement(sentencia);
            int posicion=1;
            this.completarSentencia(posicion, listaDeDatos, pst);
            int resultado=0;
            resultado=pst.executeUpdate();
            if(resultado>0){
            return true;
            }else{
            
            return false;
            }
        }catch(Exception ex){
        
        
        return false;
        }

    }
    
    
    /**
     * metodo que devuelve el resultado de una busqueda.-
     * @param tablaSeleccionarCampos contiene los campos ke se van a seleccionar del registro
     * @param tablaCondicionCampos condiciones que debe de cumplir para poder ser devuelta en el resulset
     * @param condicionEntreCampos condicion entre campos distintos
     * @param CondicionEntreCampoYValor condicion entre campo y valor
     * @param tabla nombre de la tabla que contiene el registro.-
     * @return el resulset de la busqueda
     */
    
    public ResultSet buscarRegistros(Hashtable tablaSeleccionarCampos,Hashtable tablaCondicionCampos,String condicionEntreCampos,String CondicionEntreCampoYValor,String tabla){
    ResultSet resultadoConsulta=null;
    String sentencia="";
        if(tablaSeleccionarCampos.size()<1){
        sentencia= " SELECT * ";
        
        }
    Enumeration enumSeleccionarDatos=tablaSeleccionarCampos.keys();
    Enumeration enumCondicionCampos=tablaCondicionCampos.keys();
    
    int x=0;
    String llave;
    while(enumSeleccionarDatos.hasMoreElements()){
    llave=  (String) enumSeleccionarDatos.nextElement();
    if (x==0){
    sentencia="SELECT "+llave;
    
    }else if(x>0&&x<tablaSeleccionarCampos.size()){
    sentencia=sentencia+" , "+llave;
    
    }
    x++;
    }
    
    if(tablaCondicionCampos.size()<1){
    sentencia=sentencia+" FROM "+tabla;
    
    }else{
    
    sentencia=sentencia+" FROM "+tabla+ " WHERE ";
    
    }
    //se le vuelve a asignar el valor de 0 a x y de "" a llave
    //para recorrer el nuevo bucle 
    x=0;
    llave="";
    ArrayList<Object> listaDeCondicionCampos=new ArrayList<>();
    while(enumCondicionCampos.hasMoreElements()){
        llave=(String) enumCondicionCampos.nextElement();
    if (x==0){
    sentencia=sentencia+llave+CondicionEntreCampoYValor+" ? ";
    }else if(x>0&&x<tablaCondicionCampos.size()){
    sentencia=sentencia+" "+condicionEntreCampos+ " "+llave+CondicionEntreCampoYValor+" ? ";
    
    }
    listaDeCondicionCampos.add(tablaCondicionCampos.get(llave));
    x++;
    }
    //JOptionPane.showMessageDialog(null, sentencia);
    
    try{
    PreparedStatement pst =con.prepareStatement(sentencia);
    int posicion=1;
    this.completarSentencia(posicion, listaDeCondicionCampos, pst);
    resultadoConsulta=pst.executeQuery();
    }catch(Exception ex){
    
    
    }
    return resultadoConsulta;
    }
    
    /**
     * 
     * metodo que devuelve el resultado de una busqueda autoincrementable.-
     * @param tablaSeleccionarCampos contiene los campos ke se van a seleccionar del registro
     * @param tablaCondicionCampos condiciones que debe de cumplir para poder ser devuelta en el resulset
     * @param condicionEntreCampos condicion entre campos distintos
     * @param tabla nombre de la tabla que almacena los registros a buscar
     * @return devuelve el resulset con los registros que cumplen las condiciones
     */
    
    
    public ResultSet buscarRegistrosAutoIncrementable(Hashtable tablaSeleccionarCampos,Hashtable tablaCondicionCampos,String condicionEntreCampos,String tabla){
    ResultSet resultadoBusqueda=null;
    String sentencia="";
        if(tablaSeleccionarCampos.size()<1){
        sentencia= " SELECT * ";
        
        }
    Enumeration enumSeleccionarDatos=tablaSeleccionarCampos.keys();
    Enumeration enumCondicionCampos=tablaCondicionCampos.keys();
    
    int x=0;
    String llave;
    while(enumSeleccionarDatos.hasMoreElements()){
    llave=  (String) enumSeleccionarDatos.nextElement();
    if (x==0){
    sentencia="SELECT "+llave;
    
    }else if(x>0&&x<tablaSeleccionarCampos.size()){
    sentencia=sentencia+" , "+llave;
    
    }
    x++;
    }
    
    sentencia=sentencia+" FROM "+tabla+" WHERE ";
    //se le vuelve a asignar el valor de 0 a x y de "" a llave
    //para recorrer el nuevo bucle 
    x=0;
    llave="";
    ArrayList<Object> listaDeCondicionCampos=new ArrayList<>();
    while(enumCondicionCampos.hasMoreElements()){
        llave=(String) enumCondicionCampos.nextElement();
    if (x==0){
    sentencia=sentencia+llave+" LIKE ? ";
    }else if(x>0&&x<tablaCondicionCampos.size()){
    sentencia=sentencia+" "+condicionEntreCampos+ " "+llave+" LIKE ? ";
    
    }
    //le agregamos el comodin para ke busque los registros con este contenido
    listaDeCondicionCampos.add(tablaCondicionCampos.get(llave)+"%");
    x++;
    }
    //JOptionPane.showMessageDialog(null, sentencia);
    
    try{
    PreparedStatement pst =con.prepareStatement(sentencia);
    //aumentamos la x en uno ps los parametros inician en uno
    int posicion=1;
    this.completarSentencia(posicion, listaDeCondicionCampos, pst);
    resultadoBusqueda=pst.executeQuery();
    }catch(Exception ex){
    JOptionPane.showMessageDialog(null, "error : "+ex.getMessage());
    
    }
    
    
    return resultadoBusqueda;
    }
    
    /**
     * este metodo es el que permite que se controle una transaccion
     * pues desactiva el autocommit
     * @return verdadero o falso segun el caso.-
     */
    
    public boolean iniciarTransaccion(){
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
          return false; 
        }
    return true;
    }
    
    /**
     * metodo que habilita el autocommit
     */
    
    public void habilitarModoConfirmacionAutomatica(){
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex) {
          JOptionPane.showConfirmDialog(null, "error al intentar poner en modo autocommit : "+ex.getMessage());
        }
    
    }

    /**
     * metodo que deshace los cambios de una transaccion
     */
    
    
    public void rollback(){
        try {
            con.rollback();
        } catch (SQLException ex) {
         
        }
    
    }
    
    /**
     * metodo que guarda de manera irreversible los registros modificados,
     * agregados, o eliminados
     */
    
    public void commit(){
        try {
            con.commit();
        } catch (SQLException ex) {
         
        }
    }
    /***
     * metodo que permite colocar los datos en la sentencia donde aparecen los simbolos ?
     * @param listaDeDatos almacena los datos que seran reemplazados en la sentencia que se a pasado
     * al prepare statement.-
     * @param pst almacena la sentencia y tambien coloca los valores reemplazados en las posiciones especificas
     * @return devuelve verdadero si se completo correctamente el procedimiento, falso de los contrario.-
     */
    
    private boolean completarSentencia(int posicion,ArrayList<Object>listaDeDatos,PreparedStatement pst){
        //int posicion=0;
        try{
    for (int i = 0; i < listaDeDatos.size(); i++) {
                if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("String")) {
                    String dato;
                    dato = (String) listaDeDatos.get(i);
                    pst.setString(posicion, dato);
                } else if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Double")) {
                    double dato;
                    dato = (double) listaDeDatos.get(i);
                    pst.setDouble(posicion, dato);
                } else if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Integer")) {
                    int dato;
                    dato = (int) listaDeDatos.get(i);
                    pst.setInt(posicion, dato);
                }else if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Float")){
                float dato;
                dato=(float) listaDeDatos.get(i);
                pst.setFloat(posicion, dato);
                }else if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Long")){
                long dato;
                dato=(long) listaDeDatos.get(i);
                pst.setFloat(posicion, dato);
                
                }else if ((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Byte[]")){
                byte[] dato;
                dato=(byte []) listaDeDatos.get(i);
                pst.setBytes(posicion, dato);
                
                }else if((listaDeDatos.get(i).getClass().getSimpleName()).equalsIgnoreCase("Boolean")){
                boolean dato;
                dato=(boolean) listaDeDatos.get(i);
                pst.setBoolean(posicion, dato);
                }
                posicion++;
            }
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, "error al intentar completar sentencia: "+ex.getMessage());
        return false;
        }
        return true;
    }
    //sentencia=sentencia+")";
    //sentencia=sentencia;
}
