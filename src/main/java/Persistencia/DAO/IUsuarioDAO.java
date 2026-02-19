/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.UsuarioDTO;
import PersistenciaException.PersistenciaExcepcion;

/**
 *
 * @author josma
 */
public interface IUsuarioDAO {
    public UsuarioDTO buscarUsuarioLogin(String usuario_nombre, String contrasena) throws PersistenciaExcepcion;
}
