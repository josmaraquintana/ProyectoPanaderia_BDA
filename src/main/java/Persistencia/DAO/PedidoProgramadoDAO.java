/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoDatosDTO;
import Persistencia.conexion.IConexionBD;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author josma
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
        try(Connection conn = this.conexionBD.crearConexion(); 
                //Este es para ejecutar el comando para hacer el registro de pedido
                PreparedStatement ps_uno = conn.prepareStatement(comando_pedido, Statement.RETURN_GENERATED_KEYS);
                //Este es para ejecutar el comando para hacer el registro en pedido programado
                PreparedStatement ps_dos = conn.prepareStatement(comando_programado);
                //Este es para ejecutar el comando para hacer el registro de pedido con cupones
                PreparedStatement ps_tres = conn.prepareStatement(comando_cupones_pedidos);
                //Este es para ejecutar el comando para hacer el registro de detalles del pedido
                PreparedStatement ps_cuatro = conn.prepareStatement(comando_detalle)
                ){
            //Remplazamos los signos de interrogacion
            ps_uno.setObject(1, pedidoDTO.getFecha());
            ps_uno.setInt(2, pedidoDTO.getNum_productos());
            ps_uno.setDouble(3, pedidoDTO.getSubtotal());
            ps_uno.setDouble(4, pedidoDTO.getTotal());
            ps_uno.setString(5, "Pendiente");
            ps_uno.setInt(6, cliente.getId_usuario());
            //Ejecutamos los comandos
            ps_uno.executeUpdate();
            try(ResultSet resul = ps_uno.getGeneratedKeys()){
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
            
            
            
        }catch(SQLException ex){
            LOG.warning("No se logro hacer el registro del pedido.");
            throw new PersistenciaExcepcion("Hubo un error al querer insertar el registro de pedido", ex);
        }
        
    }

    
    
    
    
}
