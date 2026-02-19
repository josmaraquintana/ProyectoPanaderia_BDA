/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import GUIs.VIniciarSesion;
import GUIs.VPedido;
import GUIs.VPedidoCliente;
import Negocio.BOs.*;
import Persistencia.DAO.*;
import Persistencia.conexion.ConexionBD;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author josma
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        VIniciarSesion iniciar_sesion = new VIniciarSesion(frame,true);
//        iniciar_sesion.setVisible(true);

//    new VPedidoCliente();
    
    SwingUtilities.invokeLater(() -> {

            try {

                // Crear conexión
                ConexionBD conexionBD = new ConexionBD();

                // Crear DAOs
                IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
                IClienteDAO clienteDAO = new ClienteDAO(conexionBD);

                // Crear BO
                IUsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);

                // Crear ventana login
                VIniciarSesion login = new VIniciarSesion(
                        null,
                        true,
                        usuarioBO,
                        clienteDAO
                );

                login.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        
        
    }
    
}
