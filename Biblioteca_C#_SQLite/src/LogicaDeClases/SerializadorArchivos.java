/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeClases;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ph
 */
public class SerializadorArchivos {

    private String errorEncontrado;

    public String getErrorEncontrado() {
        return errorEncontrado;
    }

    public void setErrorEncontrado(String errorEncontrado) {
        this.errorEncontrado = errorEncontrado;
    }

    public SerializadorArchivos() {
    }

    public byte[] serializarArchivo(Object archivo) {
        byte[] arregloDeDatosArchivo;
        ByteArrayOutputStream salidaDeBytes = new ByteArrayOutputStream();
        ObjectOutputStream salidaArchivo;

        try {
            salidaArchivo = new ObjectOutputStream(salidaDeBytes);
            salidaArchivo.writeObject(archivo);
            salidaArchivo.flush();
            arregloDeDatosArchivo = salidaDeBytes.toByteArray();
            salidaArchivo.close();
            salidaDeBytes.close();
            return arregloDeDatosArchivo;

        } catch (Exception ex) {
            setErrorEncontrado(ex.getMessage());
            return null;
        }


    }

    public Object descerealizarArchivo(byte[] arregloDeBytesArchivo) {
        Object objetoArchivo;
        ByteArrayInputStream entradaDeBytes;
        ObjectInputStream lectorObjeto;

        try {
            entradaDeBytes = new ByteArrayInputStream(arregloDeBytesArchivo);
            lectorObjeto = new ObjectInputStream(entradaDeBytes);
            objetoArchivo = lectorObjeto.readObject();
            return objetoArchivo;


        } catch (Exception ex) {

            setErrorEncontrado(ex.getMessage());
            return null;

        }
    }
}
