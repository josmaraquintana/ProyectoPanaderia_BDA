/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.CuponDTO;
import PersistenciaException.PersistenciaExcepcion;

/**
 *
 * @author josma
 */
public interface ICuponDAO {
    
    public CuponDTO obtenerCupon(int codigo) throws PersistenciaExcepcion;
    
    public CuponDTO obtenerCuponNombre(String nombre) throws PersistenciaExcepcion;
    
    public CuponDTO agregarCupon(CuponDTO cupon) throws PersistenciaExcepcion;
}
