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
 *
 * @author josma
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

    public boolean agregarTelefono(String numero, String tipo,int id_cliente) throws SQLException {

        String comandoInsert =  "INSERT INTO TelefonosClientes (id_cliente, telefono, tipo) VALUES (?,?,?)";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoInsert)) {

            ps.setInt(1, id_cliente);
            ps.setString(2, numero);
            ps.setString(3, tipo);

            return ps.executeUpdate() > 0;
        }
    }

    //Metodo para poder listar todos los telefonos de un cliente
    public List<TelefonoDTO> obtenerTelefnos(int id_cliente) throws SQLException {
        List<TelefonoDTO> lista_telefonos = new ArrayList<>();

        String comandoSQL = "SELECT t.id_telefono, t.telefono, t.tipo FROM TelefonosClientes t JOIN Clientes c ON t.id_cliente = c.id_cliente WHERE c.id_usuario = ?";
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
        }
        return lista_telefonos;

    }

}
