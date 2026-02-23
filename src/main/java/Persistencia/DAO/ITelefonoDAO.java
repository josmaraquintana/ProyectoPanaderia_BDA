/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.TelefonoDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface ITelefonoDAO {
    public boolean agregarTelefono(String numero, String tipo, int id_usuario) throws SQLException;
    public List<TelefonoDTO> obtenerTelefnos(int id_usuario) throws SQLException;
    
}
