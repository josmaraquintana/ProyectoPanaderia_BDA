/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.EmpleadoDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class EmpleadoDAO implements IEmpleadoDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(EmpleadoDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public EmpleadoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public boolean empleadoExiste(int id_usuario) throws PersistenciaExcepcion {
        String comandoSQL = "SELECT id_usuario FROM Empleados WHERE id_usuario  =?";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

            ps.setInt(1, id_usuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al verificar Empleado", ex);
        }
    }

    @Override
    public void registrarUsuario(EmpleadoDTO empleado) throws PersistenciaExcepcion {
        String comandoSQL = "{CALL registrar_empleado(?,?,?,?,?)}";

        try (Connection conn = this.conexionBD.crearConexion(); CallableStatement cs = conn.prepareCall(comandoSQL)) {
            cs.setString(1, empleado.getNombre_usuario());
            cs.setString(2, empleado.getContrasena());
            cs.setString(3, empleado.getNombres());
            cs.setString(4, empleado.getApellido_paterno());
            cs.setString(5, empleado.getApellido_materno());
            
            cs.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new PersistenciaExcepcion("Error al registrar empleado: " + ex.getMessage(), ex);
        }

    }

}
