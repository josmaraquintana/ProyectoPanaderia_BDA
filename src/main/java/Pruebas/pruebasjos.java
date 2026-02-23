/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import GUIs.*;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Persistencia.DAO.IPedidoDAO;
import Persistencia.DAO.ITelefonoDAO;
import Persistencia.DAO.IUsuarioDAO;
import Persistencia.DAO.PedidoDAO;
import Persistencia.DAO.TelefonoDAO;
import Persistencia.DAO.UsuarioDAO;
import Persistencia.conexion.ConexionBD;
import Persistencia.conexion.IConexionBD;

/**
 *
 * @author josma
 */
public class pruebasjos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        IConexionBD conexion = new ConexionBD();

        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
        UsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);
        
        IPedidoDAO pedidoDAO = new PedidoDAO(conexion);
        PedidoBO pedidoBO = new PedidoBO(pedidoDAO);
        
        ITelefonoDAO telefonoDAO = new TelefonoDAO(conexion);
        TelefonoBO telefonoBO = new TelefonoBO(telefonoDAO);

        new VInicioSesion(pedidoBO,usuarioBO,telefonoBO).setVisible(true);

    }

}
