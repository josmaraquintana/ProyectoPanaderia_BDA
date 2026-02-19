/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.conexion;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *Interfaz que define el contrato para establecer una conexión con la base de datos.
 *
 * Esta interfaz tiene como objetivo desacoplar la lógica de acceso a datos de la
 * forma específica en que se crea la conexión con la base de datos.
 *
 * Gracias a esta abstracción, es posible cambiar la implementación de la conexión
 * sin afectar a las clases DAO que la utilizan, así como facilitar el uso de
 * conexiones simuladas o falsas durante pruebas.
 *
 * Las clases DAO deben depender de esta interfaz y no de una implementación
 * concreta de conexión.
 * @author josmara, ramses, daniel
 */
public interface IConexionBD {
    Connection crearConexion() throws SQLException; 
}
