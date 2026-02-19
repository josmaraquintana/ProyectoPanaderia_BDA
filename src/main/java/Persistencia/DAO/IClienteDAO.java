/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.ClienteDTO;
import PersistenciaException.PersistenciaExcepcion;

/**
 *
 * @author josma
 */
public interface IClienteDAO {
    public boolean clienteExiste(int id_usuario) throws PersistenciaExcepcion;
}
