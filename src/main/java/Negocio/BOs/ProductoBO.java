/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoProducto;
import Negocio.DTOs.ProductoDTO;
import Negocio.DTOs.ProductoEstadoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.dominio.Producto;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business Object (BO) encargado de la gestión del catálogo de productos.
 * <p>
 * Esta clase centraliza las reglas de negocio para el inventario, asegurando
 * que la información de los productos sea consistente y válida antes de su
 * exposición en el punto de venta o en pedidos programados.</p>
 *
 * * @author josma
 * @version 1.0
 */
public class ProductoBO implements IProductoBO {

    private final IProductoDAO productoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    /**
     * Constructor que inicializa el objeto de negocio mediante inyección de
     * dependencias.
     *
     * @param producto Instancia de la interfaz DAO para la comunicación con la
     * base de datos.
     */
    public ProductoBO(IProductoDAO producto) {
        this.productoDAO = producto; //inyeccion de dependencias
    }

    /**
     * Recupera el catálogo completo de productos disponibles.
     *
     * @return Lista de {@link ProductoDTO} con la información de los productos.
     * @throws NegocioExcepcion Si la lista resulta nula o hay fallos en la
     * conexión.
     */
    @Override
    public List<ProductoDTO> obtenerListaProductos() throws NegocioExcepcion {
        try {
            List<ProductoDTO> lista_producto = productoDAO.obtenerListaProductos();
            if (lista_producto == null) {
                LOG.warning("La lista de los productos regreso vacia");
                throw new NegocioExcepcion("La lista de los productos esta vacia");
            }

            return lista_producto;
        } catch (PersistenciaExcepcion ex) {
            LOG.warning("Paso un error al querer regresar la lista de productos");
            throw new NegocioExcepcion("Hubo un error al querer obtener la lista.");
        }
    }

    /**
     * Registra un nuevo producto aplicando validaciones de integridad y
     * formato.
     * <p>
     * Reglas aplicadas:
     * <ul>
     * <li>Nombre obligatorio y sin espacios en blanco.</li>
     * <li>Precio estrictamente mayor a cero.</li>
     * <li>Tipo de producto (categoría) obligatorio.</li>
     * <li>Descripción limitada a 200 caracteres para evitar desbordamiento en
     * UI.</li>
     * </ul></p>
     *
     * @param producto DTO con la información capturada.
     * @throws NegocioExcepcion Si alguna regla de validación es violada.
     */
    @Override
    public void registrarProducto(ProductoDTO producto) throws NegocioExcepcion {

        //Primero validamos que el nombre no este vacio
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new NegocioExcepcion("El nombre del producto no puede estar vacío.");
        }

        //El precio del producto no debe ser menor a 0
        if (producto.getPrecio() <= 0) {
            throw new NegocioExcepcion("El precio debe ser un valor positivo mayor a cero.");
        }

        // SI O SI debe de tener un tipo
        if (producto.getTipo() == null) {
            throw new NegocioExcepcion("Debe seleccionar un tipo de producto válido.");
        }

        //Minimo debe tener una descripcion aunque sea corta
        if (producto.getDescripcion() != null && producto.getDescripcion().length() > 200) {
            throw new NegocioExcepcion("La descripción es demasiado larga (máximo 200 caracteres).");
        }

        try {
            //Se hace el llamado si pasa todas las validaciones
            productoDAO.registrarProducto(producto);
        } catch (PersistenciaExcepcion e) {
            // Si falla
            throw new NegocioExcepcion("Error de persistencia: " + e.getMessage());
        }
    }

    /**
     * Realiza una búsqueda filtrada de productos por coincidencia de nombre.
     *
     * @param filtro Cadena de texto a buscar.
     * @return Lista de {@link ProductoEstadoDTO} que coinciden con el criterio.
     * @throws Exception Si el filtro es nulo o vacío.
     */

    @Override
    public List<ProductoEstadoDTO> buscarProductosPorNombre(String filtro) throws Exception {

        if (filtro == null || filtro.trim().isEmpty()) {
            throw new Exception("Debe ingresar un nombre para realizar la búsqueda.");
        }

        return productoDAO.buscarProductosPorNombre(filtro.trim());
    }

    /**
     * Gestiona el ciclo de vida de un producto cambiando su estado (Disponible,
     * Agotado, etc.).
     *
     * @param id Identificador único del producto.
     * @param nuevoEstado Valor del Enum {@link EstadoProducto}.
     * @throws Exception Si el ID es inválido o el estado es nulo.
     */
    @Override
    public void actualizarEstadoProducto(int id, EstadoProducto nuevoEstado) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del producto no es válido.");
        }

        if (nuevoEstado == null) {
            throw new Exception("El nuevo estado no puede ser nulo.");
        }
        productoDAO.actualizarEstadoProducto(id, nuevoEstado);
    }
}
