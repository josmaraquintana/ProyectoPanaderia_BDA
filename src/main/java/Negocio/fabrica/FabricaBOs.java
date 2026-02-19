/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.fabrica;

import Negocio.BOs.*;
import Persistencia.fabrica.FabricaDAO;

/**
 *
 * @author josmara, ramses, daniel
 */
public class FabricaBOs {

    public static IUsuarioBO obtenerUsuarioBO() {
        //Creamos el usuario BO e inyectamos la dependencia
        IUsuarioBO usuario = new UsuarioBO(FabricaDAO.obtenerUsuarioDAO());
        //Regresamos el usuario listo
        return usuario;
    }

    public static IClienteBO obtenerClienteBO() {
        //Creamos el cliente BO e inyectamos la dependencia
        IClienteBO cliente = new ClienteBO(FabricaDAO.obtenerClienteDAO());
        //Regresamos el usuario listo
        return cliente;
    }

    public static IPedidoBO obtenerPedidoBO() {
        //Creamos el pedido BO e inyectamos la dependencia
        IPedidoBO pedido = new PedidoBO(FabricaDAO.obtenerPedidoDAO());
        //Regresamos el usuario listo
        return pedido;
    }

    public static IPedidoProgramadoBO obtenerPedidoProgramadoBO() {
        //Creamos el pedido BO e inyectamos la dependencia
        IPedidoProgramadoBO pedido_programado = new PedidoProgramadoBO(FabricaDAO.obtenerPedidoProgramadoDAO());
        //Regresamos el usuario listo
        return pedido_programado;
    }
}
