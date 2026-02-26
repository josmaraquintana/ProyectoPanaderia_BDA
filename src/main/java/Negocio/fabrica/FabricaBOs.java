/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.fabrica;

import Negocio.BOs.*;
import Persistencia.fabrica.FabricaDAO;

/**
 * Fábrica de Objetos de Negocio (Business Objects).
 * <p>Esta clase centraliza la creación e instanciación de todos los BOs del sistema,
 * aplicando el patrón Factory y encargándose de la inyección de dependencias
 * desde la capa de persistencia mediante {@link FabricaDAO}.</p>
 * * <p>Al utilizar esta fábrica, la capa de presentación no necesita conocer cómo 
 * se construyen los objetos ni sus dependencias internas.</p>
 * * @author josmara, ramses, daniel
 * @version 1.0
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

    public static ICuponBO obtenerCuponBO() {
        //Creamos el cupon BO e inyectamos la dependencia
        ICuponBO cupon = new CuponBO(FabricaDAO.obtenerCuponDAO());
        //Regresamos el cupon listo
        return cupon;
    }

    public static IProductoBO obtenerProductoBO() {
        //Creamos el producto BO e inyectamos la dependencia
        IProductoBO producto = new ProductoBO(FabricaDAO.obtenerProductoDAO());
        //Regresamos el cupon listo
        return producto;
    }
}
