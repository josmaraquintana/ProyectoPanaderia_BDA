/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoDatosDTO;
import PersistenciaException.PersistenciaExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IPedidoProgramadoDAO {
    
    public void realizarRegistroPedidoProgramado(List<ItemCarrito> carrito, ClienteDTO cliente, 
            List<CuponDTO> lista_cupones, PedidoDatosDTO pedidoDTO, String notas, List<Integer> lista_id_productos) throws PersistenciaExcepcion;
    
}
