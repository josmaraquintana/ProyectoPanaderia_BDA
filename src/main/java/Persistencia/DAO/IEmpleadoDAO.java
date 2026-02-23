/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.EmpleadoDTO;
import PersistenciaException.PersistenciaExcepcion;

/**
 *
 * @author DANIEL
 */
public interface IEmpleadoDAO {
    public void registrarUsuario(EmpleadoDTO empleado) throws PersistenciaExcepcion;
    
    public boolean empleadoExiste(int id_usuario) throws PersistenciaExcepcion;
}
