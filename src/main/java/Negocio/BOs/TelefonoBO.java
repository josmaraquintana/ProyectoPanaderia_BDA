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
 *
 * @author josma
 */
public class TelefonoBO implements ITelefonoBO {

    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    private ITelefonoDAO telefonoDAO;

    public TelefonoBO(ITelefonoDAO telefonoDAO) {
        this.telefonoDAO = telefonoDAO;
    }

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

            if (telefono.getId_cliente() <= 0) {
                LOG.warning("Algo fallo con el usuario");
                throw new NegocioExcepcion("Algo fallo con el usuario");
            }

            return telefonoDAO.agregarTelefono(telefono.getTelefono().trim(), telefono.getTipo().trim(), telefono.getId());
        } catch (SQLException ex) {
            throw new NegocioExcepcion("Algo fallo a la hora de agregar el telefono");
        }
    }

    public List<TelefonoDTO> listarTelefonos(ClienteDTO cliente) throws NegocioExcepcion {
        try {
            if (cliente == null) {
                LOG.warning("Algo esta fallando con el usuario");
                throw new NegocioExcepcion("Usuario invalida, intenta otra ves");
            }

            return telefonoDAO.obtenerTelefnos(cliente.getId());

        } catch (SQLException ex) {
            throw new NegocioExcepcion("No se pudo obtener la lista de telefonos");
        }
    }
}
