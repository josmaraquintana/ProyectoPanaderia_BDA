/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import GUIs.*;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.CuponBO;
import Negocio.BOs.EmpleadoBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Persistencia.DAO.ClienteDAO;
import Persistencia.DAO.CuponDAO;
import Persistencia.DAO.EmpleadoDAO;
import Persistencia.DAO.IClienteDAO;
import Persistencia.DAO.ICuponDAO;
import Persistencia.DAO.IEmpleadoDAO;
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
        
        IEmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
        EmpleadoBO empleadoBO = new EmpleadoBO(empleadoDAO);
        
        IClienteDAO clienteDAO = new ClienteDAO(conexion);
        ClienteBO clienteBO = new ClienteBO(clienteDAO);
        
        ICuponDAO cuponDAO = new CuponDAO(conexion);
        CuponBO cuponBO = new CuponBO(cuponDAO);

        new VInicioSesion(pedidoBO,usuarioBO,telefonoBO,cuponBO,empleadoBO, clienteBO).setVisible(true);

    }

}
