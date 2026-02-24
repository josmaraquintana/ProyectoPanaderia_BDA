/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.TipoProducto;
import Negocio.DTOs.ProductoDTO;
import Negocio.DTOs.ProductoIdDTO;
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
public class ProductoDAO implements IProductoDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public ProductoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<ProductoDTO> obtenerListaProductos() throws PersistenciaExcepcion {
        //Lista para guardar los productos 
        List<ProductoDTO> lista_productos = new ArrayList<>();
        //comando para sql
        String comandoSQL = """
                            SELECT nombre_producto, descripcion, estado, precio FROM productos WHERE estado = 'Disponible';
                            """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            //Creamos la conexion y ejecutamos el comando
            try (ResultSet resul = ps.executeQuery()) {
                while (resul.next()) {
                    //creamos el dto producto para que podamos guardarlos en la lista 
                    ProductoDTO producto = new ProductoDTO();
                    //guardamos en el objeto los atributos que necesitamos 
                    producto.setNombre(resul.getString("nombre_producto"));
                    producto.setDescripcion(resul.getString("descripcion"));
                    producto.setEstado(resul.getString("estado"));
                    producto.setPrecio(resul.getDouble("precio"));
                    //agregamos en la lista el objeto
                    lista_productos.add(producto);

                }
            }

        } catch (SQLException ex) {
            LOG.warning("No se encontraron productos.");
            throw new PersistenciaExcepcion("Hubo un error al lista los productos", ex);
        }
        return lista_productos;

    }

    @Override
    public List<ProductoIdDTO> obtenerListaProductosId() throws PersistenciaExcepcion {
        //Lista para guardar los productos 
        List<ProductoIdDTO> lista_productos = new ArrayList<>();
        //comando para sql
        String comandoSQL = """
                            SELECT id_producto, nombre_producto FROM productos;
                            """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            //Creamos la conexion y ejecutamos el comando
            try (ResultSet resul = ps.executeQuery()) {
                while (resul.next()) {
                    //creamos el dto producto para que podamos guardarlos en la lista 
                    ProductoIdDTO producto = new ProductoIdDTO();
                    //guardamos en el objeto los atributos que necesitamos 
                    producto.setId(resul.getInt("id_producto"));
                    producto.setNombre(resul.getString("nombre_producto"));
                    //agregamos en la lista el objeto
                    lista_productos.add(producto);

                }
            }

        } catch (SQLException ex) {
            LOG.warning("No se encontraron productos.");
            throw new PersistenciaExcepcion("Hubo un error al lista los productos", ex);
        }
        return lista_productos;

    }

    @Override
    public void registrarProducto(ProductoDTO producto) throws PersistenciaExcepcion {
        String ComandoSQL = "INSERT INTO Productos (nombre_producto, descripcion, tipo, precio, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(ComandoSQL)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());

            // --- HOLA CHICOS AQUI MANEJAMOS EL ENUM PARA QUE COINCIDA---
            // Convertimos DULCE -> Dulce --> COINCIDE CON EL MYSQL
            String tipo_enum_string = formatearEnum(producto.getTipo());
            ps.setString(3, tipo_enum_string);

            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getEstado());

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Error al insertar producto: " + ex.getMessage());
            throw new PersistenciaExcepcion("Algo fallo a la hora de agregar el producto: ", ex.getCause());
        }
    }

    /**
     * Convierte el nombre del Enum (DULCE) a formato de mysql (Dulce) para
     * que coincida con el script SQL (Dulce, Integral, Salado)
     */
    private String formatearEnum(TipoProducto tipo) {
        if (tipo == null) {
            return null;
        }
        String nombre = tipo.name().toLowerCase(); // aqui es dulce
        return nombre.substring(0, 1).toUpperCase() + nombre.substring(1); // aqui pasa a Dulce
    }
}
