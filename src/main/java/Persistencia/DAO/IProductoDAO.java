/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoProducto;
import Negocio.DTOs.ProductoDTO;
import Negocio.DTOs.ProductoEstadoDTO;
import Negocio.DTOs.ProductoIdDTO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IProductoDAO {
    
    public List<ProductoDTO> obtenerListaProductos() throws PersistenciaExcepcion;
    
    public List<ProductoIdDTO> obtenerListaProductosId() throws PersistenciaExcepcion;
    public void registrarProducto(ProductoDTO producto) throws PersistenciaExcepcion;
    public List<ProductoEstadoDTO> buscarProductosPorNombre(String filtro) throws PersistenciaExcepcion;
    public void actualizarEstadoProducto(int id, EstadoProducto nuevoEstado) throws Exception;
    
    
}
