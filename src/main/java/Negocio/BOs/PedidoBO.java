/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoPedido;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import PersistenciaException.PersistenciaExcepcion;
import Validadores.Validaciones;
import java.sql.Date;
import java.util.ArrayList;
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
    
    
    @Override
    public List<PedidoDTO> consultarHistorial(String fecha_inicio, String fecha_fin, int id_cliente, String tipo, EstadoPedido estado) throws NegocioExcepcion {
        try {
            // 1. Validaciones de nulidad y vacíos
            if (fecha_inicio == null || fecha_fin == null || fecha_inicio.isBlank() || fecha_fin.isBlank()) {
                throw new NegocioExcepcion("Las fechas de búsqueda no pueden estar vacías.");
            }

            if (id_cliente <= 0) {
                throw new NegocioExcepcion("ID de cliente no válido para la consulta.");
            }

            if (tipo == null || tipo.isBlank()) {
                throw new NegocioExcepcion("Debe especificar el tipo de pedido (Programado/Express).");
            }

            if (estado == null) {
                throw new NegocioExcepcion("El estado del pedido es obligatorio.");
            }

            // 2. Limpieza de Strings de fecha (elimina espacios y caracteres invisibles)
            // Solo permite números y guiones
            String f1_limpia = fecha_inicio.trim().replaceAll("[^0-9-]", "");
            String f2_limpia = fecha_fin.trim().replaceAll("[^0-9-]", "");

            // 3. Conversión de String a java.sql.Date
            Date fecha_inicio_format;
            Date fecha_fin_format;

            try {
                fecha_inicio_format = Date.valueOf(f1_limpia);
                fecha_fin_format = Date.valueOf(f2_limpia);
            } catch (IllegalArgumentException e) {
                LOG.warning("Error de formato en fechas: " + f1_limpia + " o " + f2_limpia);
                throw new NegocioExcepcion("Formato de fecha inválido. Use AAAA-MM-DD (ejemplo: 2026-02-23)");
            }

            // 4. Validación de lógica de fechas
            if (fecha_inicio_format.after(fecha_fin_format)) {
                throw new NegocioExcepcion("La fecha de inicio no puede ser posterior a la fecha de fin.");
            }

            // 5. Llamada al DAO con los 5 parámetros
            // (fecha_inicio, fecha_fin, id_cliente, tipo, estado)
            return pedidoDAO.traerHistorial(fecha_inicio_format, fecha_fin_format, id_cliente, tipo, estado);

        } catch (PersistenciaExcepcion e) {
            LOG.severe("Error de persistencia al traer historial: " + e.getMessage());
            throw new NegocioExcepcion("No se pudo obtener el historial debido a un error en la base de datos.");
        }
    }

    
    
    @Override
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws NegocioExcepcion{
        
        try{
            //Hacemos la lista que obtendra todo 
            List<PedidoEstadoDTO> listaPedidos = pedidoDAO.obtenerListaPedidosConResumen();
            
            //Validamos que la lista no este vacia 
            if (listaPedidos.isEmpty() || listaPedidos == null) {
                LOG.warning("La lista que se obtuvo esta vacia.");
                throw new NegocioExcepcion("No se obtuvo ninguna lista.");
            }
            //Retornamos la lista a el metodo de la ventana 
            return listaPedidos;
            
        }catch(PersistenciaExcepcion ex){
            LOG.warning("Hubo un error al obtener la lista de pedidos con sus resumenes o notas.");
            throw new NegocioExcepcion("No se completo el regresar la lista de pedidos con notas.");
        }
    }
    
    
    @Override
    public boolean cambiarEstadoPedido(String texto_id, String estado) throws NegocioExcepcion{
        
        Validaciones validar = new Validaciones();
        
        if (!validar.validarEnteros(texto_id)) {
            LOG.warning("El texto del id debe ser solamente numeros.");
            throw new NegocioExcepcion("El texto del id esta mal.");
        }
        System.out.println(texto_id);
        int id_pedido = Integer.parseInt(texto_id);
        
        if (id_pedido <= 0) {
            LOG.warning("El id del pedido no debe ser negativo.");
            throw new NegocioExcepcion("El id es negativo.");
        }
        
        try{
            
            boolean valor = pedidoDAO.cambiarEstadoPedido(id_pedido, estado);
            return valor;
                    
        }catch(PersistenciaExcepcion ex){
            LOG.warning("No se pudo actualizar el estado.");
            throw new NegocioExcepcion("Hubo un error al querer mandar los datos para actualizar.");
        }
        
        
        
    }
}
