/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.ClienteDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.DAO.IPedidoProgramadoDAO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.SQLException;
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
    
    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioExcepcion{
        
        if(cliente.getNombre_usuario() == null || cliente.getNombre_usuario().isBlank()){
            throw new NegocioExcepcion("Este campo es obligatorio");
        }
        
        if(cliente.getContrasena() == null || cliente.getContrasena().isBlank()){
            throw new NegocioExcepcion("Ingrese una contrasena");
        }
        
        if(cliente.getEdad() <= 0){
            throw new NegocioExcepcion("Ingrese una edad valida");
        }
        
        if(cliente.getFecha_nacimiento() == null){
            throw new NegocioExcepcion("Ingrese una fecha valida");
        }
        try{
            clienteDAO.registrarUsuario(cliente);
        }catch(PersistenciaExcepcion ex){
            throw new NegocioExcepcion("Error al registrar al cliente " + ex.getMessage());
        }
        
    }
}
