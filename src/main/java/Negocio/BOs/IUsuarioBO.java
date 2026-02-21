/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.LoginDTO;
import Negocio.DTOs.UsuarioDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.dominio.Usuario;

/**
 *
 * @author josma
 */
public interface IUsuarioBO {
    public UsuarioDTO login(LoginDTO loginDTO) throws NegocioExcepcion;
}
