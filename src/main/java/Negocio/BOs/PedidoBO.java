/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoPedido;
import Negocio.DTOs.PedidoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class PedidoBO implements IPedidoBO {
    private final IPedidoDAO pedidoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(PedidoBO.class.getName());

    public PedidoBO(IPedidoDAO pedido) {
        this.pedidoDAO = pedido; //inyeccion de dependencias
    }
    
    public List<PedidoDTO> consultarHistorial(String fecha_inicio, String fecha_fin, String tipo, EstadoPedido estado) throws NegocioExcepcion{
        try{
             if (fecha_inicio == null || fecha_fin == null) {
                throw new NegocioExcepcion("Las fechas no pueden estar vacias");
            }

            if (tipo == null || tipo.isBlank()) {
                throw new NegocioExcepcion("Debe seleccionar un tipo de pedido");
            }

            if (estado == null) {
                throw new NegocioExcepcion("Debe seleccionar un estado");
            }
            
            Date fecha_inicio_format = Date.valueOf(fecha_inicio);
            Date fecha_fin_format = Date.valueOf(fecha_fin);
            
            if (fecha_inicio_format.after(fecha_fin_format)) {
                throw new NegocioExcepcion("La fecha inicio no puede ser mayor que la fecha fin");
            }
            
            return pedidoDAO.traerHistorial(fecha_inicio_format, fecha_fin_format,tipo,estado);
        } catch (IllegalArgumentException e) {
            throw new NegocioExcepcion("Formato de fecha inválido", e);
        } catch (PersistenciaExcepcion e) {
            throw new NegocioExcepcion("Error al consultar el historial", e);
        }
    }
}
