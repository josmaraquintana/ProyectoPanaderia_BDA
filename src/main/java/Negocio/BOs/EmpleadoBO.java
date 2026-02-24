/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.EmpleadoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.IEmpleadoDAO;
import PersistenciaException.PersistenciaExcepcion;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author DANIEL
 */
public class EmpleadoBO implements IEmpleadoBO {

    private final IEmpleadoDAO empleadoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    public EmpleadoBO(IEmpleadoDAO empleado) {
        this.empleadoDAO = empleado; //inyeccion de dependencias
    }

    @Override
    public void registrarEmpleado(EmpleadoDTO empleado) throws NegocioExcepcion {

        if (empleado.getNombre_usuario() == null || empleado.getNombre_usuario().isBlank()) {
            throw new NegocioExcepcion("Este campo es obligatorio");
        }

        if (empleado.getContrasena() == null || empleado.getContrasena().isBlank()) {
            throw new NegocioExcepcion("Ingrese una contrasena");
        }
        // Encriptado
        String passwordHash = BCrypt.hashpw(empleado.getContrasena(), BCrypt.gensalt());
        empleado.setContrasena(passwordHash);

        try {
            empleadoDAO.registrarUsuario(empleado);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioExcepcion("Error al registrar al empleado " + ex.getMessage());
        }

    }
}
