/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.ClienteDTO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.SQLException;

/**
 *
 * @author josma
 */
public interface IClienteDAO {
    public boolean clienteExiste(int id_usuario) throws PersistenciaExcepcion;
    
    public void registrarUsuario(ClienteDTO cliente) throws PersistenciaExcepcion;
    public ClienteDTO obtenerClientePorUsuario(int id_cliente) throws PersistenciaExcepcion;
}

