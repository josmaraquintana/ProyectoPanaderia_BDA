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
 * Business Object (BO) encargado de la lógica de negocio para la gestión de empleados.
 * <p>Esta clase centraliza las reglas de validación de perfiles administrativos, 
 * el control de acceso y la seguridad de las credenciales de los trabajadores.</p>
 * * @author DANIEL
 * @version 1.0
 */
public class EmpleadoBO implements IEmpleadoBO {

    private final IEmpleadoDAO empleadoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());
    /**
     * Constructor que inicializa el objeto de negocio mediante inyección de dependencias.
     * * @param empleado Instancia de la interfaz DAO para la comunicación con la base de datos.
     */
    public EmpleadoBO(IEmpleadoDAO empleado) {
        this.empleadoDAO = empleado; //inyeccion de dependencias
    }
    /**
     * Registra un nuevo empleado en el sistema aplicando protocolos de seguridad.
     * <p>El método realiza las siguientes acciones:
     * <ul>
     * <li>Valida que el nombre de usuario y la contraseña no sean nulos o vacíos.</li>
     * <li>Genera un "Salt" aleatorio y encripta la contraseña usando el algoritmo BCrypt.</li>
     * <li>Delega la persistencia del objeto DTO a la capa de datos.</li>
     * </ul></p>
     * * @param empleado El DTO que contiene la información del empleado a registrar.
     * @throws NegocioExcepcion Si los campos obligatorios están ausentes o si ocurre 
     * un error en la capa de persistencia.
     */
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
