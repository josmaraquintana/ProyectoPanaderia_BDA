/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.*;
import Persistencia.conexion.IConexionBD;
import Persistencia.dominio.Cliente;
import Persistencia.dominio.Empleado;
import Persistencia.dominio.Usuario;
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

    public Usuario buscarUsuarioLogin(String usuario_nombre, String contrasena) throws PersistenciaExcepcion {
        Usuario usuario = null;
        String comandoSQL = "SELECT u.id_usuario, u.usuario, u.contrasena, u.nombres, u.apellido_paterno, u.apellido_materno, c.id_usuario AS cliente_checar, c.edad, c.fecha_nac, e.id_usuario AS empleado_checar FROM Usuarios u LEFT JOIN Clientes c ON u.id_usuario = c.id_usuario LEFT JOIN Empleados e ON u.id_usuario = e.id_usuario WHERE u.usuario = ? AND u.contrasena = ?";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            ps.setString(1, usuario_nombre);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //En caso de que sea un cliente
                    if (rs.getObject("cliente_checar") != null) {

                        Cliente cliente = new Cliente();
                        cliente.setId_usuario(rs.getInt("id_usuario"));
                        cliente.setNombre_usuario(rs.getString("usuario"));
                        cliente.setEdad(rs.getInt("edad"));
                        cliente.setFecha_nacimiento(rs.getDate("fecha_nac"));

                        usuario = cliente;
                    } //En caso de que sea empleado
                    else if (rs.getObject("empleado_checar") != null) {

                        Empleado empleado = new Empleado();
                        empleado.setId_usuario(rs.getInt("id_usuario"));
                        empleado.setNombre_usuario(rs.getString("usuario"));
                        empleado.setNombres(rs.getString("nombres"));
                        empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
                        empleado.setApellidoMaterno(rs.getString("apellido_materno"));

                        usuario = empleado;
                    }

                }

            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al buscar usuario", ex);
        }
        return usuario;
    }
}
