/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.TelefonoDTO;
import Negocio.DTOs.UsuarioDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.ITelefonoDAO;
import Persistencia.DAO.TelefonoDAO;
import Validadores.Validaciones;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business Object (BO) encargado de la gestión de números telefónicos de
 * contacto.
 * <p>
 * Esta clase permite asociar múltiples teléfonos a un mismo cliente (Casa,
 * Celular, Trabajo) y asegura que los datos cumplan con los formatos numéricos
 * requeridos antes de ser persistidos.</p>
 *
 * * @author josma
 * @version 1.0
 */
public class TelefonoBO implements ITelefonoBO {

    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    private ITelefonoDAO telefonoDAO;

    /**
     * Constructor que inicializa el objeto de negocio mediante inyección de
     * dependencias.
     *
     * @param telefonoDAO Instancia de la interfaz DAO para la comunicación con
     * la base de datos.
     */
    public TelefonoBO(ITelefonoDAO telefonoDAO) {
        this.telefonoDAO = telefonoDAO;
    }

    /**
     * Valida y registra un nuevo número de teléfono asociado a un cliente.
     * <p>
     * El método verifica:
     * <ul>
     * <li>Que el objeto DTO no sea nulo.</li>
     * <li>Que el formato de la cadena contenga únicamente números.</li>
     * <li>Que el tipo de teléfono (etiqueta) no esté vacío.</li>
     * <li>Que exista un ID de cliente válido para establecer la relación.</li>
     * </ul></p>
     *
     * @param telefono DTO con la información del teléfono a agregar.
     * @return true si el registro fue exitoso en la base de datos.
     * @throws NegocioExcepcion Si fallan las validaciones de formato o hay
     * errores SQL.
     */
    public boolean agregarTelefono(TelefonoDTO telefono) throws NegocioExcepcion {
        try {
            if (telefono == null) {
                LOG.warning("El telefono no puede estar vacio");
                throw new NegocioExcepcion("El telefono no puede estar vacio");
            }
            if (!Validaciones.validarTelefono(telefono.getTelefono())) {
                LOG.warning("El telefono deben ser solo numeros");
                throw new NegocioExcepcion("Solo se permiten numeros");
            }

            if (telefono.getTipo() == null || telefono.getTipo().trim().isEmpty()) {
                LOG.warning("Es necesaria una etiqueta para el telefono");
                throw new NegocioExcepcion("Debe colocar un tipo de etiqueta al telefono");
            }
            System.out.println("PRUEBA DE QUE EL ID SI ESTE LLEGANDO: " + telefono.getId_cliente());
            if (telefono.getId_cliente() <= 0) {
                LOG.warning("Algo fallo con el cliente");
                throw new NegocioExcepcion("Algo fallo con el cliente");
            }

            return telefonoDAO.agregarTelefono(telefono.getTelefono().trim(), telefono.getTipo().trim(), telefono.getId_cliente());
        } catch (SQLException ex) {
            throw new NegocioExcepcion("Algo fallo a la hora de agregar el telefono");
        }
    }

    /**
     * Recupera la lista de teléfonos asociados a un cliente específico.
     *
     * @param cliente DTO del cliente del cual se desean obtener los contactos.
     * @return Lista de {@link TelefonoDTO} asociados al ID del cliente.
     * @throws NegocioExcepcion Si el cliente es nulo o ocurre un error en la
     * consulta.
     */
    public List<TelefonoDTO> listarTelefonos(ClienteDTO cliente) throws NegocioExcepcion {
        try {
            if (cliente == null) {
                LOG.warning("Algo esta fallando con el usuario");
                throw new NegocioExcepcion("Usuario invalida, intenta otra ves");
            }

            return telefonoDAO.obtenerTelefonos(cliente.getId_cliente());

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new NegocioExcepcion("No se pudo obtener la lista de telefonos");
        }
    }
}
