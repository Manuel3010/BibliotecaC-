/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecaprogra3;

import javax.swing.JFrame;

/**
 *
 * @author ph
 */
public class BibliotecaProgra3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        VentanaPrincipal vt=new VentanaPrincipal();
        vt.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vt.setVisible(true);
        
    }
}
