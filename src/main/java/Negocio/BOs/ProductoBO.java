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
public class ProductoBO {
    private final IProductoDAO productoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(CuponBO.class.getName());

    public ProductoBO(IProductoDAO producto) {
        this.productoDAO = producto; //inyeccion de dependencias
    }
}
