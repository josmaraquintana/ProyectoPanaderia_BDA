/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.*;
import NegocioException.NegocioExcepcion;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class ClienteDAO implements IClienteDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(ClienteDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public boolean clienteExiste(int id_usuario) throws PersistenciaExcepcion {
        String comandoSQL = "SELECT id_usuario FROM Clientes WHERE id_usuario  =?";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

            ps.setInt(1, id_usuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al verificar cliente", ex);
        }
    }

    @Override
    public void registrarUsuario(ClienteDTO cliente) throws PersistenciaExcepcion {
        String comandoSQL = "{CALL registrar_cliente(?,?,?,?,?,?,?,?,?,?,?)}";

        try (Connection conn = this.conexionBD.crearConexion(); CallableStatement cs = conn.prepareCall(comandoSQL)) {
            cs.setString(1, cliente.getNombre_usuario());
            cs.setString(2, cliente.getContrasena());
            cs.setString(3, cliente.getNombres());
            cs.setString(4, cliente.getApellido_paterno());
            cs.setString(5, cliente.getApellido_materno());
            cs.setInt(6, cliente.getEdad());
            cs.setDate(7, cliente.getFecha_nacimiento());
            cs.setString(8, cliente.getCalle());
            cs.setInt(9, cliente.getNumero_casa());
            cs.setString(10, cliente.getColonia());
            cs.setInt(11, cliente.getCodigo_postal());

            cs.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new PersistenciaExcepcion("Error al registrar cliente: " + ex.getMessage(), ex);
        }

    }

    @Override
    public ClienteDTO obtenerClientePorUsuario(int id_cliente) throws PersistenciaExcepcion {
        String ComandoSQL = "SELECT id_cliente, edad, fecha_nac, calle, numero, colonia, codigo_postal id_usuario FROM Clientes WHERE id_usuario = ?";
        try (Connection con = this.conexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(ComandoSQL)) {
            ps.setInt(1, id_cliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClienteDTO(
                            rs.getInt("id_cliente"),
                            rs.getInt("edad"),
                            rs.getDate("fecha_nacimiento"),
                            rs.getString("calle"),
                            rs.getString("colonia"),
                            rs.getInt("codigo_postal"),
                            rs.getInt("numero_casa")
                    );

                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al obtener el clinete" + ex.getMessage(), ex);
        }
        return null;
    }

}
