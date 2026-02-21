/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.PanelProductoDTO;
import NegocioException.NegocioExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IProductoBO {
    
    public List<PanelProductoDTO> obtenerListaProductos() throws NegocioExcepcion;
    
}
