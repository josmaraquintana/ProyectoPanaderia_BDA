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
 *
 * @author josma
 */
public class ProductoBO implements IProductoBO {

    private final IProductoDAO productoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    public ProductoBO(IProductoDAO producto) {
        this.productoDAO = producto; //inyeccion de dependencias
    }

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
     * Método para registrar un producto con validaciones de negocio.
     *
     * @param producto El DTO con la información del producto.
     * @throws Exception Si hay errores de validación o de base de datos.
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

    @Override
    public List<ProductoEstadoDTO> buscarProductosPorNombre(String filtro) throws Exception {

        if (filtro == null || filtro.trim().isEmpty()) {
            throw new Exception("Debe ingresar un nombre para realizar la búsqueda.");
        }

        return productoDAO.buscarProductosPorNombre(filtro.trim());
    }

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
