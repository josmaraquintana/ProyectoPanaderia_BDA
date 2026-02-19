/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.UsuarioDTO;
import NegocioException.NegocioExcepcion;

/**
 *
 * @author josma
 */
public interface IUsuarioBO {
    public UsuarioDTO login(String nombre_usuario, String contrasena) throws NegocioExcepcion;
}
