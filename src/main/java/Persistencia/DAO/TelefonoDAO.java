/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.TelefonoDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO encargado de la gestión de números telefónicos asociados a los clientes.
 * <p>
 * Maneja la persistencia de la entidad TelefonosClientes, permitiendo el
 * almacenamiento de múltiples números por usuario y su recuperación
 * categorizada.</p>
 *
 * * @author josma
 * @version 1.0
 */
public class TelefonoDAO implements ITelefonoDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(PedidoDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public TelefonoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Registra un nuevo número telefónico vinculado a un cliente específico.
     *
     * * @param numero Cadena con los dígitos del teléfono.
     * @param tipo Categoría del teléfono (ej. 'Móvil', 'Casa').
     * @param id_cliente Identificador del cliente propietario.
     * @return true si el registro fue exitoso, false en caso contrario.
     * @throws SQLException Si ocurre un error de sintaxis o restricción en la
     * BD.
     */
    public boolean agregarTelefono(String numero, String tipo, int id_cliente) throws SQLException {

        String comandoInsert = "INSERT INTO TelefonosClientes (id_cliente, telefono, tipo) VALUES (?,?,?)";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoInsert)) {

            ps.setInt(1, id_cliente);
            ps.setString(2, numero);
            ps.setString(3, tipo);

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Recupera la colección completa de teléfonos registrados para un cliente.
     *
     * * @param id_cliente Identificador único del cliente.
     * @return Lista de {@link TelefonoDTO} con los datos de contacto.
     * @throws SQLException Error al ejecutar la consulta.
     */
    //Metodo para poder listar todos los telefonos de un cliente
    public List<TelefonoDTO> obtenerTelefonos(int id_cliente) throws SQLException {
        List<TelefonoDTO> lista_telefonos = new ArrayList<>();

        String comandoSQL = "SELECT id_telefono, id_cliente, telefono, tipo FROM TelefonosClientes WHERE id_cliente = ?";;
        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            ps.setInt(1, id_cliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TelefonoDTO telefonoDTO = new TelefonoDTO();
                telefonoDTO.setId(rs.getInt("id_telefono"));
                telefonoDTO.setId_cliente(rs.getInt("id_cliente"));
                telefonoDTO.setTelefono(rs.getString("telefono"));
                telefonoDTO.setTipo(rs.getString("tipo"));
                lista_telefonos.add(telefonoDTO);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR DE LA SQL: " + ex.getMessage());
            throw ex;
        }
        return lista_telefonos;

    }

}
