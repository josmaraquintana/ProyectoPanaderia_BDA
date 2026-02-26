/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoDatosDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO especializado en la gestión de Pedidos Programados.
 * <p>
 * Esta clase extiende la funcionalidad de los pedidos estándar permitiendo la
 * programación de fechas, la aplicación de múltiples cupones y la gestión de
 * notas específicas por detalle de producto.</p>
 *
 * * @author josma
 * @version 1.2
 */
public class PedidoProgramadoDAO implements IPedidoProgramadoDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(PedidoProgramadoDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public PedidoProgramadoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Realiza un registro integral de un pedido programado en la base de datos.
     * <p>
     * El método ejecuta una secuencia de inserciones:
     * <ol>
     * <li>Inserta la cabecera en la tabla {@code Pedidos}.</li>
     * <li>Registra la especialización en {@code PedidosProgramados}.</li>
     * <li>Relaciona los cupones aplicados en {@code Cupones_Pedidos}.</li>
     * <li>Desglosa los productos en {@code DetallePedidos}.</li>
     * </ol></p>
     *
     * * @param carrito Lista de items en el carrito actual.
     * @param cliente DTO del cliente que realiza la compra.
     * @param lista_cupones_id Lista de cupones aplicados al pedido.
     * @param pedidoDTO Datos generales del pedido (totales, fechas).
     * @param notas Comentarios adicionales para la preparación.
     * @param lista_id_productos IDs de los productos para referenciación
     * rápida.
     * @throws PersistenciaExcepcion Si ocurre un fallo en cualquier etapa del
     * proceso SQL.
     */
    @Override
    public void realizarRegistroPedidoProgramado(List<ItemCarrito> carrito, ClienteDTO cliente,
            List<CuponDTO> lista_cupones_id, PedidoDatosDTO pedidoDTO, String notas, List<Integer> lista_id_productos) throws PersistenciaExcepcion {
        //Creamos los dos comandos para poder hacer los inserts en la base de datos
        String comando_pedido = """
                            INSERT INTO Pedidos (fecha, num_productos, subtotal, total, estado, id_usuario) 
                            VALUES (?, ?, ?, ?, ?, ?);
                            """;
        String comando_programado = """
                                    INSERT INTO PedidosProgramados (id_pedido) 
                                    VALUES (?);
                                    """;
        String comando_cupones_pedidos = """
                                         INSERT INTO Cupones_Pedidos (id_cupon, id_pedido)
                                         VALUES(?, ?)
                                         """;
        String comando_detalle = """
                                 INSERT INTO DetallePedidos (id_pedido, id_producto, cantidad_producto, precio, nota)
                                 VALUES (?, ?, ?, ?, ?);
                                 """;

        //Creamos la conexion y los dos comandos listos para ejecutarse 
        try (Connection conn = this.conexionBD.crearConexion(); //Este es para ejecutar el comando para hacer el registro de pedido
                 PreparedStatement ps_uno = conn.prepareStatement(comando_pedido, Statement.RETURN_GENERATED_KEYS); //Este es para ejecutar el comando para hacer el registro en pedido programado
                 PreparedStatement ps_dos = conn.prepareStatement(comando_programado); //Este es para ejecutar el comando para hacer el registro de pedido con cupones
                 PreparedStatement ps_tres = conn.prepareStatement(comando_cupones_pedidos); //Este es para ejecutar el comando para hacer el registro de detalles del pedido
                 PreparedStatement ps_cuatro = conn.prepareStatement(comando_detalle)) {
            //Remplazamos los signos de interrogacion
            ps_uno.setTimestamp(1, Timestamp.valueOf(pedidoDTO.getFecha()));
            ps_uno.setInt(2, pedidoDTO.getNum_productos());
            ps_uno.setDouble(3, pedidoDTO.getSubtotal());
            ps_uno.setDouble(4, pedidoDTO.getTotal());
            ps_uno.setString(5, "Pendiente");
            ps_uno.setInt(6, cliente.getId_usuario());

            //Ejecutamos los comandos
            ps_uno.executeUpdate();
            try (ResultSet resul = ps_uno.getGeneratedKeys()) {
                if (resul.next()) {
                    //Guardamos el id del inserta la tabla de pedidos
                    int id_generado = resul.getInt(1);

                    ps_dos.setInt(1, id_generado);
                    //Ejecutamos el segundo ID
                    ps_dos.executeUpdate();

                    for (CuponDTO cuponDTO : lista_cupones_id) {
                        //Iteramos sobre cada uno de los cupones hasta que todos esten registrados
                        ps_tres.setInt(1, cuponDTO.getId());
                        ps_tres.setInt(2, id_generado);
                        //Ejecutamos la relacion entre cupones y pedido
                        ps_tres.executeUpdate();
                    }

                    int cont = 0;
                    for (Integer id_producto : lista_id_productos) {
                        ps_cuatro.setInt(1, id_generado);
                        ps_cuatro.setInt(2, id_producto);
                        ps_cuatro.setInt(3, carrito.get(cont).getCantidad());
                        ps_cuatro.setDouble(4, carrito.get(cont).getSubtotal());
                        ps_cuatro.setString(5, notas);
                        ps_cuatro.executeUpdate();
                        cont++;
                    }

                }
            }

        } catch (SQLException ex) {
            LOG.warning("No se logro hacer el registro del pedido.");
            throw new PersistenciaExcepcion("Hubo un error al querer insertar el registro de pedido", ex);
        }

    }

    @Override
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws PersistenciaExcepcion {
        //Hacemos el comando para la base de datos
        String comando = """
                          SELECT p.id_pedido, p.estado, MAX(dp.nota) AS nota_del_pedido
                          FROM Pedidos p
                          LEFT JOIN DetallePedidos dp ON p.id_pedido = dp.id_pedido
                          WHERE p.estado = 'Pendiente'
                          GROUP BY p.id_pedido, p.estado
                          ORDER BY p.id_pedido ASC;
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
    public boolean cambiarEstadoPedido(int id) throws PersistenciaExcepcion {

        String comando = """
                         UPDATE Pedidos SET estado = 'Cancelado' WHERE id_pedido = ?;
                         """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando)) {

            ps.setInt(1, id);

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
