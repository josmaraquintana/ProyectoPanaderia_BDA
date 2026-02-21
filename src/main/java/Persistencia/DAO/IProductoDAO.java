/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Persistencia.dominio.Producto;
import PersistenciaException.PersistenciaExcepcion;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IProductoDAO {
    
    public List<Producto> obtenerListaProductos() throws PersistenciaExcepcion;
    
}
