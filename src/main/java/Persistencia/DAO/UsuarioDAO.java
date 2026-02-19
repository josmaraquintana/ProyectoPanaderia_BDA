/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.*;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
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
public class UsuarioDAO implements IUsuarioDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(UsuarioDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public UsuarioDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override

    public UsuarioDTO buscarUsuarioLogin(String usuario_nombre, String contrasena) throws PersistenciaExcepcion {
        UsuarioDTO usuario = null;
        String comandoSQL = "SELECT id_usuario, usuario, contrasena, nombres, apellido_paterno, apellido_materno FROM Usuarios WHERE usuario  = ? and contrasena = ?";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            ps.setString(1, usuario_nombre);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioDTO();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre_usuario(rs.getString("usuario"));
                    usuario.setContrasena(rs.getString("contrasena"));
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al buscar usuario", ex);
        }
        return usuario;
    }
}
