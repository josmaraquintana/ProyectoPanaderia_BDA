/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoPedido;
import Negocio.DTOs.PedidoDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.Date;
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
public class PedidoDAO implements IPedidoDAO {

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
    public PedidoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public List<PedidoDTO> traerHistorial(Date fecha_inicio, Date fecha_fin, String tipo, EstadoPedido estado) throws PersistenciaExcepcion {
        List<PedidoDTO> lista_historial = new ArrayList<>();
        String comandoSQL = "SELECT p.id_pedido, p.fecha, p.estado, "
                + "CASE "
                + "WHEN pp.id_pedido IS NOT NULL THEN 'Programado' "
                + "WHEN pe.id_pedido IS NOT NULL THEN 'Express' "
                + "END AS tipo "
                + "FROM Pedidos p "
                + "LEFT JOIN PedidosProgramados pp ON p.id_pedido = pp.id_pedido "
                + "LEFT JOIN PedidosExpress pe ON p.id_pedido = pe.id_pedido "
                + "WHERE p.fecha BETWEEN ? AND ? "
                + "AND p.estado = ? "
                + "AND ((? = 'Programado' AND pp.id_pedido IS NOT NULL) "
                + "OR (? = 'Express' AND pe.id_pedido IS NOT NULL))";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            ps.setDate(1, fecha_inicio);
            ps.setDate(2, fecha_fin);
            ps.setString(3, estado.name());
            ps.setString(4, tipo);
            ps.setString(5, tipo);
            //le pasamos doblemente el tipo porque hace dos where, uno entra y el tro no 
            //fue la manera menos compleja de hacerlo, otra era separar dos consultas sql distintas
            //pero no se estaba logrando 
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PedidoDTO dto = new PedidoDTO();
                dto.setIdPedido(rs.getInt("id_pedido"));
                dto.setFecha(rs.getDate("fecha"));
                dto.setEstado(
                        EstadoPedido.valueOf(rs.getString("estado"))
                );
                dto.setTipo(rs.getString("tipo"));

                lista_historial.add(dto);
            }
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al consultar historial", e);
        }

        return lista_historial;

    }
}
