/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.PanelProductoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.dominio.Producto;
import PersistenciaException.PersistenciaExcepcion;
import java.util.List;
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
    public List<PanelProductoDTO> obtenerListaProductos() throws NegocioExcepcion{
        return null; 
        }
}
