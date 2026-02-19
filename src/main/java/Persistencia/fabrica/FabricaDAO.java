/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.fabrica;

import Persistencia.DAO.*;
import Persistencia.conexion.ConexionBD;
import Persistencia.conexion.IConexionBD;

/**
 *
 * @author josmara, ramses, daniel
 */
public class FabricaDAO {
    //1. Definir la conexion con la que se va  a trabajar 
    
    private static IConexionBD conexion = new ConexionBD();
    
    //2. metodo para obtener el tecnicoDAO
    
    public static IUsuarioDAO obtenerUsuarioDAO(){
        //Creamos el usuario e inyectamos la conexion
        IUsuarioDAO usuario = new UsuarioDAO(conexion);
        //Regresamos el usuario listo
        return usuario;
    }
    public static IClienteDAO obtenerClienteDAO(){
        //Creamos el cliente e inyectamos la conexion
        IClienteDAO cliente = new ClienteDAO(conexion);
        //Regresamos el cliente listo
        return cliente;
    }
    
    public static IPedidoDAO obtenerPedidoDAO(){
        //Creamos el pedido e inyectamos la conexion
        IPedidoDAO pedido = new PedidoDAO(conexion);
        //Regresamos el pedido listo
        return pedido;
    }
    
    public static IPedidoProgramadoDAO obtenerPedidoProgramadoDAO(){
        //Creamos el pedido_programado e inyectamos la conexion
        IPedidoProgramadoDAO pedido_programado = new PedidoProgramadoDAO(conexion);
        //Regresamos el pedido_programado listo
        return pedido_programado;
    }
    
    public static ICuponDAO obtenerCuponDAO(){
        //Creamos el cupon e inyectamos la conexion
        ICuponDAO cupon = new CuponDAO(conexion);
        //Regresamos el cupon listo
        return cupon;
    }
    
    public static IProductoDAO obtenerProductoDAO(){
        //Creamos el producto e inyectamos la conexion
        IProductoDAO producto = new ProductoDAO(conexion);
        //Regresamos el producto listo
        return producto;
    }
}
