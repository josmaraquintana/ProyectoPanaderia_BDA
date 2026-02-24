/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoPedido;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
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

    @Override
    public List<PedidoDTO> traerHistorial(Date fecha_inicio, Date fecha_fin, int id_cliente, String tipo, EstadoPedido estado) throws PersistenciaExcepcion {
        List<PedidoDTO> lista_historial = new ArrayList<>();
        String comandoSQL = "SELECT p.id_pedido, p.numero_pedido, p.fecha, p.num_productos, "
                + "p.subtotal, p.total, p.estado, p.id_usuario, "
                + "CASE "
                + "    WHEN pp.id_pedido IS NOT NULL THEN 'Programado' "
                + "    WHEN pe.id_pedido IS NOT NULL THEN 'Express' "
                + "END AS tipo "
                + "FROM Pedidos p "
                + "LEFT JOIN PedidosProgramados pp ON p.id_pedido = pp.id_pedido "
                + "LEFT JOIN PedidosExpress pe ON p.id_pedido = pe.id_pedido "
                + "WHERE p.fecha BETWEEN ? AND ? "
                + "AND p.estado = ? "
                + "AND p.id_usuario = (SELECT c.id_usuario FROM Clientes c WHERE c.id_cliente = ?)"
                + "AND ("
                + "    (? = 'Programado' AND pp.id_pedido IS NOT NULL) "
                + "    OR "
                + "    (? = 'Express' AND pe.id_pedido IS NOT NULL)"
                + ")";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

            ps.setDate(1, fecha_inicio);
            ps.setDate(2, fecha_fin);
            ps.setString(3, estado.name()); // OJITO JOS: aqui estamos mandando el name del enum porque antes no nos agarraba
            ps.setInt(4, id_cliente);
            ps.setString(5, tipo);
            ps.setString(6, tipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PedidoDTO pedidoDTO = new PedidoDTO();
                pedidoDTO.setIdPedido(rs.getInt("id_pedido"));
                pedidoDTO.setFecha(rs.getDate("fecha"));

                //Maxima seguridad, hacemos lo mas compatible que podamos los valores incluso
                //parseando o tuperqueando el estado desde la bd
                String estadoStr = rs.getString("estado");
                if (estadoStr != null) {
                    pedidoDTO.setEstado(EstadoPedido.valueOf(estadoStr.trim().toUpperCase()));
                }


                pedidoDTO.setTipo(rs.getString("tipo"));
                lista_historial.add(pedidoDTO);
            }
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al consultar historial", e);
        }
        return lista_historial;
    }

    @Override
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws PersistenciaExcepcion {
        //Hacemos el comando para la base de datos
        String comando = """
                         SELECT p.id_pedido, p.estado, MAX(dp.nota) AS nota_del_pedido
                         FROM Pedidos p
                         LEFT JOIN DetallePedidos dp ON p.id_pedido = dp.id_pedido
                         GROUP BY p.id_pedido, p.estado;
                         """;
        //Creamos la conexion
        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando)) {
            //Creamos la lista para guardar los pedidos
            List<PedidoEstadoDTO> lista = new ArrayList<>();
            //Obtenemos el resultado de la consulta
            try (ResultSet rs = ps.executeQuery()) {
                //Creamos un objeto tipo PedidoEstadoDTO para guardar en la lista 
                while (rs.next()) {
                    PedidoEstadoDTO pedido = new PedidoEstadoDTO();
                    pedido.setId(rs.getInt("p.id_pedido"));
                    pedido.setEstado(rs.getString("p.estado"));
                    pedido.setResumen(rs.getString("nota_del_pedido"));

                    lista.add(pedido);

                }

            }
            return lista;

        } catch (SQLException ex) {
            LOG.warning("Hubo un error al querer consultar los pedidos con su resumen.");
            throw new PersistenciaExcepcion("No se pudo obtener la lista.", ex);
        }

    }

    @Override
    public boolean cambiarEstadoPedido(int id, String estado) throws PersistenciaExcepcion {

        String comando = """
                         UPDATE Pedidos SET estado = ? WHERE id_pedido = ?;
                         """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando)) {

            ps.setString(1, estado);
            ps.setInt(2, id);

            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            LOG.warning("Hubo un error al querer actualizar.");
            throw new PersistenciaExcepcion("No se completo la actualizacion de estado.", ex);
        }

    }
}
