/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import ClasesEnum.EstadoPedido;
import Negocio.DTOs.PedidoDTO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IPedidoDAO {
    public List<PedidoDTO> traerHistorial(Date fecha_inicio, Date fecha_fin, String tipo, EstadoPedido estado) throws PersistenciaExcepcion;
}
