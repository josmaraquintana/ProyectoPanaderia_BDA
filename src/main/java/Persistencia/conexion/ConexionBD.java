/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implementación concreta de la interfaz {@link IConexionBD}.
 *
 * Esta clase se encarga de crear una conexión real con la base de datos
 * utilizando JDBC y el {@link DriverManager}.
 *
 * Aquí se centralizan los datos de conexión, como la cadena de conexión, el
 * usuario y la contraseña, evitando que esta información se repita en cada
 * clase DAO.
 *
 * Las clases DAO deben depender de la interfaz {@link IConexionBD} y no
 * directamente de esta clase, para mantener un bajo acoplamiento.
 *
 * @author josmara, ramses, daniel
 */
public class ConexionBD implements IConexionBD {

    /**
     * Cadena de conexión utilizada para establecer comunicación con la base de
     * datos.
     */
    private final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/panaderia";

    /**
     * Usuario de la base de datos.
     */
    private final String USUARIO = "root";

    /**
     * Contraseña asociada al usuario de la base de datos.
     */

    private final String CONTRASENIA = "JoskPOTRO23";


    /**
     * Crea y retorna una conexión activa con la base de datos.
     *
     * Este método utiliza el {@link DriverManager} para establecer la conexión
     * con los parámetros definidos en esta clase.
     *
     * @return una {@link Connection} activa lista para ser utilizada por los
     * DAO
     * @throws SQLException si ocurre un error al intentar establecer la
     * conexión
     */
    @Override
    public Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA);
    }

}
