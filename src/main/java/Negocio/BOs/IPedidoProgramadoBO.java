/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import NegocioException.NegocioExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IPedidoProgramadoBO {
    
    public void realizarRegistrosPedidosClientesCupones(List<ItemCarrito> carrito, ClienteDTO cliente, List<CuponDTO> lista_cupones, double subtotal, double total, String notas) throws NegocioExcepcion;
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws NegocioExcepcion;
    public boolean cambiarEstadoPedido(String texto_id) throws NegocioExcepcion;
}
