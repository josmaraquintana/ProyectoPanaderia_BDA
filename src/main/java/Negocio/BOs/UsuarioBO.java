/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoCuenta;
import Negocio.DTOs.*;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.dominio.Cliente;
import Persistencia.dominio.Empleado;
import Persistencia.dominio.Usuario;
import PersistenciaException.PersistenciaExcepcion;
import java.util.logging.Logger;

/**
 * Business Object (BO) encargado de la autenticación y gestión de sesiones de
 * usuario.
 * <p>
 * Esta clase centraliza el control de acceso al sistema, validando credenciales
 * y transformando las entidades de dominio (Cliente/Empleado) en objetos de
 * transferencia de datos (DTO) específicos para la capa de presentación.</p>
 *
 * * @author josma
 * @version 1.0
 */
public class UsuarioBO implements IUsuarioBO {

    private final IUsuarioDAO usuarioDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

    public UsuarioBO(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO; //inyeccion de dependencias;
    }

    /**
     * Realiza el proceso de autenticación (Login) en el sistema.
     * <p>
     * El método sigue el siguiente flujo de negocio:
     * <ol>
     * <li>Consulta las credenciales en la base de datos.</li>
     * <li>Si el usuario es un Cliente, verifica que su cuenta no esté
     * {@link EstadoCuenta#INACTIVO}.</li>
     * <li>Dependiendo del tipo de objeto (Cliente o Empleado), mapea los datos
     * al DTO correspondiente.</li>
     * </ol></p>
     *
     * * @param loginDTO Objeto con las credenciales (usuario y contraseña).
     * @return UsuarioDTO (ya sea instancia de ClienteDTO o EmpleadoDTO).
     * @throws NegocioExcepcion Si las credenciales son incorrectas, la cuenta
     * está inactiva o hay un error de conexión.
     */
    @Override
    public UsuarioDTO login(LoginDTO loginDTO) throws NegocioExcepcion {
        try {
            Usuario usuario = usuarioDAO.buscarUsuarioLogin(
                    loginDTO.getNombre_usuario(),
                    loginDTO.getContrasena()
            );

            if (usuario == null) {
                throw new NegocioExcepcion("Usuario o contraseña incorrectos");
            }

            if (usuario instanceof Cliente cliente) {
                //Agregamos la cereza del pastel, si el estado de la cuenta esta inactiva, no deja entrar
                if (cliente.getEstado_cuenta() == EstadoCuenta.INACTIVO) {
                    throw new NegocioExcepcion("Su cuenta esta INACTIVA.");
                }
                ClienteDTO cliente_dto = new ClienteDTO();
                cliente_dto.setId_cliente(cliente.getId_cliente());
                cliente_dto.setId_usuario(cliente.getId_usuario());
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
                empleado_dto.setId_usuario(empleado.getId_usuario());
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
