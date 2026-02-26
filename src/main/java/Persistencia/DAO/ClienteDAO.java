/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoCuenta;
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
        String comandoSQL = "{CALL registrar_cliente(?,?,?,?,?,?,?,?,?,?,?,?)}";

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
            cs.setString(12, EstadoCuenta.ACTIVO.name());

            cs.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new PersistenciaExcepcion("Error al registrar cliente: " + ex.getMessage(), ex);
        }

    }

    @Override
    public ClienteDTO obtenerClientePorUsuario(int id_cliente) throws PersistenciaExcepcion {
        String ComandoSQL = "SELECT id_cliente, edad, fecha_nacimiento, calle, numero_casa, colonia, codigo_postal, id_usuario, estado_cuenta FROM Clientes WHERE id_cliente = ?";
        try (Connection con = this.conexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(ComandoSQL)) {
            ps.setInt(1, id_cliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    //COMO AGREGUE EL ESTADO DE LA CUENTA HICE MODIFICACIONES - JOS
                    EstadoCuenta estado = EstadoCuenta.valueOf(rs.getString("estado_cuenta"));
                    
                    return new ClienteDTO(
                            rs.getInt("id_cliente"),
                            rs.getInt("edad"),
                            rs.getDate("fecha_nacimiento"),
                            rs.getString("calle"),
                            rs.getString("colonia"),
                            rs.getInt("codigo_postal"),
                            estado, //<-- New parametro EL ESTADO SIEMPRE SIEMPRE ESTA DEBAJO DE CODIGO POSTAL
                            rs.getInt("numero_casa")
                    );

                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al obtener el clinete" + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) throws PersistenciaExcepcion {

        String sqlUsuario = """
        UPDATE Usuarios
        SET nombres = ?,
            apellido_paterno = ?,
            apellido_materno = ?
        WHERE id_usuario = ?
    """;

        String sqlCliente = """
        UPDATE Clientes
        SET edad = ?,
            fecha_nacimiento = ?,
            calle = ?,
            numero_casa = ?,
            colonia = ?,
            codigo_postal = ?
        WHERE id_cliente = ?
    """;

        try (Connection conn = conexionBD.crearConexion()) {

            conn.setAutoCommit(false);

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario); PreparedStatement psCliente = conn.prepareStatement(sqlCliente)) {

                // UPDATE USUARIO
                psUsuario.setString(1, cliente.getNombres());
                psUsuario.setString(2, cliente.getApellido_paterno());
                psUsuario.setString(3, cliente.getApellido_materno());
                psUsuario.setInt(4, cliente.getId_usuario());
                psUsuario.executeUpdate();

                // UPDATE CLIENTE
                psCliente.setInt(1, cliente.getEdad());
                psCliente.setDate(2, cliente.getFecha_nacimiento());
                psCliente.setString(3, cliente.getCalle());
                psCliente.setInt(4, cliente.getNumero_casa());
                psCliente.setString(5, cliente.getColonia());
                psCliente.setInt(6, cliente.getCodigo_postal());
                psCliente.setInt(7, cliente.getId_cliente());
                psCliente.executeUpdate();

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al actualizar cliente", ex);
        }
    }
    @Override
    public boolean inactivarCliente(int idCliente) throws PersistenciaExcepcion{
    String comandoSQL = "UPDATE Clientes SET estado_cuenta = 'INACTIVO' WHERE id_cliente = ?";
    
    try (Connection con = this.conexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comandoSQL)) {      
        ps.setInt(1, idCliente);        
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0; // Regresa true si se se actualizo el estaod de cuenta
        
    } catch (SQLException ex) {
         throw new PersistenciaExcepcion("Error al intentar desactivar la cuenta", ex);
    }
}
    
    
}
