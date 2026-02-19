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
public class CuponBO implements ICuponBO{
    private final ICuponDAO cuponDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    public CuponBO(ICuponDAO cupon) {
        this.cuponDAO = cupon; //inyeccion de dependencias
    }
}
