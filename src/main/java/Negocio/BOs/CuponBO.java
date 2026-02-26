/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.CuponDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import PersistenciaException.PersistenciaExcepcion;
import java.time.LocalDate;
import java.sql.Date;
import java.util.logging.Logger;

/**
 * Business Object (BO) para la gestión de cupones y promociones.
 * <p>Centraliza la lógica de validación de vigencia temporal y 
 * restricciones de uso de beneficios económicos para los clientes.</p>
 * * @author josma
 * @version 1.0
 */
public class CuponBO implements ICuponBO{
    private final ICuponDAO cuponDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    public CuponBO(ICuponDAO cupon) {
        this.cuponDAO = cupon; //inyeccion de dependencias
    }
/**
     * Recupera un cupón por su código y valida su vigencia.
     * <p>Realiza una comparación lógica entre la fecha actual del sistema 
     * y la fecha de vigencia registrada. Si el cupón es "del futuro", 
     * bloquea su uso mediante una excepción.</p>
     * * @param codigo Identificador numérico del cupón.
     * @return CuponDTO si existe y es válido.
     * @throws NegocioExcepcion Si el cupón no existe o no ha comenzado su periodo de vigencia.
     */
    @Override
    public CuponDTO obtenerCupon(int codigo) throws NegocioExcepcion {
        
        try{
            // Buscamos el cupon
            CuponDTO cupon = cuponDAO.obtenerCupon(codigo);
            
            // Validamos que no este vacio
            if (cupon == null) {
                LOG.warning("El cupon que obtuvimos esta vacio.");
                throw new NegocioExcepcion("No se encontro el cupon.");
            }

            
            // --- AQUÍ OCURRE LA MAGIA DE LA CONVERSIÓN ---
            // 1. Convertimos el Date de SQL a LocalDate
            LocalDate fechaVigencia = cupon.getVigencia().toLocalDate(); 
            LocalDate hoy = LocalDate.now();
            
            // 2. Ahora sí, hacemos la comparación usando el LocalDate
            if (fechaVigencia.isAfter(hoy)) {

                LOG.warning("El cupon todavia no esta habilitado.");
                throw new NegocioExcepcion("El cupon aun no esta vigente.");
            }
            // ----------------------------------------------
            
            // Regresamos el cupon
            return cupon;
            
        }catch(PersistenciaExcepcion ex){
            LOG.warning("No se pudo encontrar el cupon.");
            throw new NegocioExcepcion("Hubo un error al quere encontrar el cupon.");
        }
    }
    /**
     * Crea un nuevo cupón en el sistema previa validación de parámetros.
     * Asegura que:
     * <ul>
     * <li>El nombre no sea nulo o vacío.</li>
     * <li>El descuento esté en el rango (0, 100].</li>
     * <li>La vigencia sea una fecha futura.</li>
     * <li>El límite de usos sea al menos uno.</li>
     * </ul>
     * * @param cupon DTO con la información del nuevo cupón.
     * @return El CuponDTO persistido con su ID generado.
     * @throws NegocioExcepcion Si alguna regla de validación falla.
     */
    @Override
    public CuponDTO agregarCupon(CuponDTO cupon) throws NegocioExcepcion {
        try {
            //Validamos el campo de cupon que no venga vacio
            if (cupon.getNombre() == null || cupon.getNombre().isBlank()) {
                throw new NegocioExcepcion("El nombre del cupon es obligatorio");
            }

            //Que el descuento no se exceda ni sea menos
            if (cupon.getDesc()<= 0 || cupon.getDesc()> 100) {
                throw new NegocioExcepcion("El descuento debe ser mayor a 0 y máximo 100%");
            }

            //Validamos que la fecha no sea una fecha pasada
            java.util.Date hoy = new java.util.Date();
            if (cupon.getVigencia() == null || cupon.getVigencia().before(hoy)) {
                throw new NegocioExcepcion("La fecha de vigencia debe ser posterior a la fecha actual");
            }

            // 4. Validación de uso máximo
            if (cupon.getMax_usos()<= 0) {
                throw new NegocioExcepcion("El cupón debe tener al menos 1 uso permitido");
            }

            //Mnadamos al DAO para insertar
            return cuponDAO.agregarCupon(cupon);

        } catch (PersistenciaExcepcion e) {
            throw new NegocioExcepcion("Error al guardar el cupon: " + e.getMessage());
        }
    }

}
