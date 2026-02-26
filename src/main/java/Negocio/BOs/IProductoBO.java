/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoProducto;
import Negocio.DTOs.ProductoDTO;
import Negocio.DTOs.ProductoEstadoDTO;
import NegocioException.NegocioExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IProductoBO {
    
    public List<ProductoDTO> obtenerListaProductos() throws NegocioExcepcion;
    public void registrarProducto(ProductoDTO producto) throws NegocioExcepcion;
    
    // Método para la búsqueda con el nuevo DTO
    List<ProductoEstadoDTO> buscarProductosPorNombre(String filtro) throws Exception;
    
    // Método para la actualización individual
    void actualizarEstadoProducto(int id, EstadoProducto nuevoEstado) throws Exception;
}
