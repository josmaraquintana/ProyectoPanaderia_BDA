/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Persistencia.DAO.*;
import Persistencia.DAO.IPedidoProgramadoDAO;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class ClienteBO implements IClienteBO{
    private final IClienteDAO clienteDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    public ClienteBO(IClienteDAO cliente) {
        this.clienteDAO = cliente; //inyeccion de dependencias
    }
}
