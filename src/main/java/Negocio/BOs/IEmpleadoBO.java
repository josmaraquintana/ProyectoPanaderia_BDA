/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.EmpleadoDTO;
import NegocioException.NegocioExcepcion;

/**
 *
 * @author DANIEL
 */
public interface IEmpleadoBO {
     public void registrarEmpleado(EmpleadoDTO empleado) throws NegocioExcepcion;
}
