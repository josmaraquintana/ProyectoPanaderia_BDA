/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.CuponDTO;
import NegocioException.NegocioExcepcion;

/**
 *
 * @author josma
 */
public interface ICuponBO {
    
    public CuponDTO obtenerCupon(int codigo) throws NegocioExcepcion;
    
}
