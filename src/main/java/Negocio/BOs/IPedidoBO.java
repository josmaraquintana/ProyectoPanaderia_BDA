/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoPedido;
import Componentes.ItemCarrito;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import NegocioException.NegocioExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IPedidoBO {

    public List<PedidoDTO> consultarHistorial(String fecha_inicio, String fecha_fin, int id_cliente, String tipo, EstadoPedido estado) throws NegocioExcepcion;

    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws NegocioExcepcion;

    public boolean cambiarEstadoPedido(String texto_id, String estado) throws NegocioExcepcion;

    public boolean entregarPedidoExpress(String folio, String pin) throws NegocioExcepcion;

    public void realizarRegistroExpress(List<ItemCarrito> carrito, double subtotal, String folio, String pin) throws NegocioExcepcion;

}
