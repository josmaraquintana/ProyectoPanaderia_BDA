/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoPedido;
import Componentes.ItemCarrito;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO encargado de la persistencia y gestión del ciclo de vida de los Pedidos.
 * <p>
 * Esta clase maneja la complejidad de los tres tipos de registros de venta:
 * Pedidos generales, Pedidos Express (con encriptación de seguridad) y el
 * detalle pormenorizado de productos (Maestro-Detalle).</p>
 *
 * * @author josma
 * @version 1.5
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

        // Eliminamos 'p.numero_pedido' del SELECT ya que no existe en la BD
        String comandoSQL = "SELECT p.id_pedido, p.fecha, p.num_productos, p.subtotal, p.total, p.estado, p.id_usuario, "
                + "CASE "
                + "    WHEN pp.id_pedido IS NOT NULL THEN 'Programado' "
                + "    WHEN pe.id_pedido IS NOT NULL THEN 'Express' "
                + "END AS tipo "
                + "FROM Pedidos p "
                + "LEFT JOIN PedidosProgramados pp ON p.id_pedido = pp.id_pedido "
                + "LEFT JOIN PedidosExpress pe ON p.id_pedido = pe.id_pedido "
                + "WHERE p.fecha BETWEEN ? AND ? "
                + "AND p.estado = ? "
                + "AND p.id_usuario = (SELECT c.id_usuario FROM Clientes c WHERE c.id_cliente = ?) "
                + "AND ("
                + "    (? = 'Programado' AND pp.id_pedido IS NOT NULL) "
                + "    OR "
                + "    (? = 'Express' AND pe.id_pedido IS NOT NULL)"
                + ")";

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

            ps.setDate(1, fecha_inicio);
            ps.setDate(2, fecha_fin);
            ps.setString(3, estado.name());
            ps.setInt(4, id_cliente);
            ps.setString(5, tipo);
            ps.setString(6, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PedidoDTO pedidoDTO = new PedidoDTO();
                    pedidoDTO.setIdPedido(rs.getInt("id_pedido"));
                    pedidoDTO.setFecha(rs.getDate("fecha"));

                    // Mapeo de valores numéricos (opcional, según tu DTO)
                    // pedidoDTO.setTotal(rs.getDouble("total"));
                    // pedidoDTO.setNumProductos(rs.getInt("num_productos"));
                    // Manejo robusto del Enum para el estado
                    String estadoStr = rs.getString("estado");
                    if (estadoStr != null) {
                        try {
                            pedidoDTO.setEstado(EstadoPedido.valueOf(estadoStr.trim().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            // Log o manejo en caso de que el valor en BD no coincida con el Enum
                        }
                    }

                    pedidoDTO.setTipo(rs.getString("tipo"));
                    lista_historial.add(pedidoDTO);
                }
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
                            WHERE p.estado != 'Entregado'
                            GROUP BY p.id_pedido, p.estado
                            ORDER BY p.estado ASC;
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

    /**
     * Registra un pedido express completo bajo una transacción atómica.
     * <p>
     * El proceso incluye: 1. Inserción en 'Pedidos' y recuperación del ID
     * generado. 2. Inserción en 'PedidosExpress' encriptando Folio y PIN con
     * AES_ENCRYPT. 3. Inserción iterativa en 'DetallePedidos' para cada
     * producto del carrito.</p>
     *
     * * @param carrito Lista de productos y cantidades.
     * @param folio Identificador público del pedido.
     * @param pin Clave de seguridad para la entrega.
     * @param fecha Fecha y hora del registro.
     * @param lista_id_productos IDs de productos para agilizar la inserción.
     * @param subtotal Monto total de la operación.
     * @throws PersistenciaExcepcion Si falla cualquier paso de la transacción.
     */
    @Override
    public void realizarPedidoExpress(List<ItemCarrito> carrito, String folio, String pin, LocalDateTime fecha, List<Integer> lista_id_productos, double subtotal) throws PersistenciaExcepcion {

        String llaveSecreta = "chorizo123";

        // Especificamos las columnas para que ignore id_usuario e id_pedido (autoincrementable)
        String comando_pedido = """
                            INSERT INTO Pedidos (fecha, num_productos, subtotal, total, estado) 
                            VALUES (?, ?, ?, ?, ?);
                            """;

        String comando_express = """
                             INSERT INTO PedidosExpress (id_pedido, folio, pin) 
                             VALUES (?, AES_ENCRYPT(?, ?), AES_ENCRYPT(?, ?));
                             """;

        // Especificamos columnas para DetallePedidos
        String comando_detalle = """
                             INSERT INTO DetallePedidos (id_pedido, id_producto, cantidad_producto, precio)
                             VALUES (?, ?, ?, ?);
                             """;

        try (Connection conn = this.conexionBD.crearConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps_uno = conn.prepareStatement(comando_pedido, Statement.RETURN_GENERATED_KEYS); PreparedStatement ps_dos = conn.prepareStatement(comando_express); PreparedStatement ps_cuatro = conn.prepareStatement(comando_detalle)) {

                // 1. Registro en Pedidos
                ps_uno.setTimestamp(1, Timestamp.valueOf(fecha));
                ps_uno.setInt(2, carrito.size());
                ps_uno.setDouble(3, subtotal);
                ps_uno.setDouble(4, subtotal);
                ps_uno.setString(5, "Pendiente");
                ps_uno.executeUpdate();

                try (ResultSet resul = ps_uno.getGeneratedKeys()) {
                    if (resul.next()) {
                        int id_generado = resul.getInt(1);

                        // 2. Registro en PedidosExpress (ENCRIPTADO)
                        ps_dos.setInt(1, id_generado);
                        ps_dos.setString(2, folio);
                        ps_dos.setString(3, llaveSecreta);
                        ps_dos.setString(4, pin);
                        ps_dos.setString(5, llaveSecreta);
                        ps_dos.executeUpdate();

                        // 3. Registro de Detalles
                        for (int i = 0; i < lista_id_productos.size(); i++) {
                            ps_cuatro.setInt(1, id_generado);
                            ps_cuatro.setInt(2, lista_id_productos.get(i));
                            ps_cuatro.setInt(3, carrito.get(i).getCantidad());
                            // USAMOS PRECIO UNITARIO, NO SUBTOTAL
                            ps_cuatro.setDouble(4, carrito.get(i).getProducto().getPrecio());
                            ps_cuatro.executeUpdate();
                        }
                    }
                }
                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                // IMPORTANTE: Imprime el error exacto para saber qué falló
                ex.printStackTrace();
                throw ex;
            }
        } catch (SQLException ex) {
            LOG.warning("No se logro hacer el registro del pedido express: " + ex.getMessage());
            throw new PersistenciaExcepcion("Error en base de datos", ex);
        }
    }

    /**
     * Valida credenciales encriptadas para marcar un pedido como entregado.
     * <p>
     * Utiliza AES_DECRYPT para comparar el folio y pin proporcionados con los
     * datos almacenados de forma segura en la base de datos.</p>
     *
     * * @param folio Folio proporcionado por el cliente.
     * @param pin PIN de seguridad.
     * @return true si las credenciales son válidas y el estado se actualizó.
     * @throws PersistenciaExcepcion Error de conexión o desencriptación.
     */
    @Override
    public boolean validarYEntregarPedidoExpress(String folio, String pin) throws PersistenciaExcepcion {
        String llave = "chorizo123";

        // 1. Buscamos el id_pedido donde el folio y pin desencriptados coincidan
        String sqlBuscar = """
        SELECT id_pedido FROM PedidosExpress 
        WHERE CAST(AES_DECRYPT(folio, ?) AS CHAR) = ? 
        AND CAST(AES_DECRYPT(pin, ?) AS CHAR) = ?
        """;

        String sqlActualizar = "UPDATE Pedidos SET estado = 'Entregado' WHERE id_pedido = ?";

        try (Connection conn = this.conexionBD.crearConexion()) {
            conn.setAutoCommit(false); // Iniciamos transacción

            try (PreparedStatement ps1 = conn.prepareStatement(sqlBuscar)) {
                ps1.setString(1, llave);
                ps1.setString(2, folio);
                ps1.setString(3, llave);
                ps1.setString(4, pin);

                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        int idPedido = rs.getInt("id_pedido");

                        // 2. Si lo encontró, actualizamos la tabla Pedidos
                        try (PreparedStatement ps2 = conn.prepareStatement(sqlActualizar)) {
                            ps2.setInt(1, idPedido);
                            ps2.executeUpdate();
                        }

                        conn.commit();
                        return true;
                    }
                }
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al validar credenciales", ex);
        }
        return false; // No encontró coincidencia
    }

}
