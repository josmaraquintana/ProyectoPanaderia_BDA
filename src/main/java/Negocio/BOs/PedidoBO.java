/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Persistencia.DAO.*;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class PedidoBO implements IPedidoBO {
    private final IPedidoDAO pedidoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(PedidoBO.class.getName());

    public PedidoBO(IPedidoDAO pedido) {
        this.pedidoDAO = pedido; //inyeccion de dependencias
    }
}
