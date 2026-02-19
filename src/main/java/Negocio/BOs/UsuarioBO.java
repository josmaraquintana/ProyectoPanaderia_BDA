/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.*;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.dominio.Cliente;
import PersistenciaException.PersistenciaExcepcion;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class UsuarioBO implements IUsuarioBO{
    private final IUsuarioDAO usuarioDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

    public UsuarioBO(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO; //inyeccion de dependenciasO;
    }
    
    public UsuarioDTO login(String nombre_usuario, String contrasena) throws NegocioExcepcion{
        try{
        UsuarioDTO usuarioDTO = usuarioDAO.buscarUsuarioLogin(nombre_usuario, contrasena);
            if (usuarioDTO == null) {
                LOG.warning("El usuario o contraseña incorrectos, verifica");
            }

            return usuarioDTO;
        }catch(PersistenciaExcepcion ex){
            throw new NegocioExcepcion("No se encontro el usuario del login", ex);
        }
    }
}
