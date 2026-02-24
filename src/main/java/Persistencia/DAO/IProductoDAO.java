/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.ProductoDTO;
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
    
    
}
