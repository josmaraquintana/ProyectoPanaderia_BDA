/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.*;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.dominio.Cliente;
import Persistencia.dominio.Empleado;
import Persistencia.dominio.Usuario;
import PersistenciaException.PersistenciaExcepcion;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class UsuarioBO implements IUsuarioBO {

    private final IUsuarioDAO usuarioDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

    public UsuarioBO(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO; //inyeccion de dependencias;
    }

    
    @Override
    public UsuarioDTO login(LoginDTO loginDTO) throws NegocioExcepcion {
        try {
            Usuario usuario = usuarioDAO.buscarUsuarioLogin(loginDTO.getNombre_usuario(), loginDTO.getContrasena());
            if (usuario == null) {
                LOG.warning("El usuario o contraseña incorrectos, verifica");
            }

            if (usuario instanceof Cliente cliente) {
                ClienteDTO cliente_dto = new ClienteDTO();
                cliente_dto.setId(cliente.getId_usuario());
                cliente_dto.setNombre_usuario(cliente.getNombre_usuario());
                cliente_dto.setNombres(cliente.getNombres());
                cliente_dto.setApellido_paterno(cliente.getApellidoPaterno());
                cliente_dto.setApellido_materno(cliente.getApellidoMaterno());
                cliente_dto.setEdad(cliente.getEdad());
                cliente_dto.setFecha_nacimiento(cliente.getFecha_nacimiento());
                return cliente_dto;
            }
            if (usuario instanceof Empleado empleado) {
                EmpleadoDTO empleado_dto = new EmpleadoDTO();
                empleado_dto.setId(empleado.getId_usuario());
                empleado_dto.setNombre_usuario(empleado.getNombre_usuario());
                empleado_dto.setNombres(empleado.getNombres());
                empleado_dto.setApellido_paterno(empleado.getApellidoPaterno());
                empleado_dto.setApellido_materno(empleado.getApellidoMaterno());
                return empleado_dto;

            }
            return null;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioExcepcion("No se encontro el usuario del login", ex);
        }
    }


}
