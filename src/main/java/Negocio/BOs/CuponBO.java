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
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class CuponBO implements ICuponBO{
    private final ICuponDAO cuponDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    public CuponBO(ICuponDAO cupon) {
        this.cuponDAO = cupon; //inyeccion de dependencias
    }

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
}
