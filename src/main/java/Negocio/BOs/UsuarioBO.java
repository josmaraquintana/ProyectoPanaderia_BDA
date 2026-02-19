/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Persistencia.DAO.*;
import java.util.logging.Logger;

/**
 *
 * @author josma
 */
public class UsuarioBO implements IUsuarioBO{
    private final IUsuarioDAO usuarioDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

    public UsuarioBO(IUsuarioDAO usuario) {
        this.usuarioDAO = usuario; //inyeccion de dependencias
    }
}
