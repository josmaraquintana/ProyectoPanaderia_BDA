/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoPedido;
import Componentes.ItemCarrito;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IPedidoDAO {

    public List<PedidoDTO> traerHistorial(Date fecha_inicio, Date fecha_fin,int id_cliente, String tipo, EstadoPedido estado) throws PersistenciaExcepcion;

    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws PersistenciaExcepcion;
    
    public boolean cambiarEstadoPedido(int id, String estado) throws PersistenciaExcepcion;
    public void realizarPedidoExpress(List<ItemCarrito> carrito,  String folio, String pin, LocalDateTime fecha, List<Integer> lista_id_productos, double subtotal) throws PersistenciaExcepcion;
    public boolean validarYEntregarPedidoExpress(String folio, String pin) throws PersistenciaExcepcion;
}
