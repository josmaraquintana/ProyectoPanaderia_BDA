/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Persistencia.DAO.*;
import java.util.logging.Logger;

/**
 *
 * @author josmara, ramses, daniel
 */
public class PedidoProgramadoBO implements IPedidoProgramadoBO{
    private final IPedidoProgramadoDAO pedidoProgramadoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(PedidoProgramadoBO.class.getName());

    public PedidoProgramadoBO(IPedidoProgramadoDAO pedido_programado) {
        this.pedidoProgramadoDAO = pedido_programado; //inyeccion de dependencias
    }
}
