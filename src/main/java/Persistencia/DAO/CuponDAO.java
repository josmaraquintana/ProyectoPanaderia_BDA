/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAO;

import Negocio.DTOs.CuponDTO;
import Persistencia.conexion.IConexionBD;
import Persistencia.dominio.Cupon;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * DAO para la gestión de Cupones de descuento.
 * <p>
 * Esta clase implementa la lógica de persistencia para el catálogo de cupones,
 * incluyendo el control de inventario de usos (max_uso) y la recuperación de
 * identificadores generados automáticamente.</p>
 *
 * * @author josma
 * @version 1.1
 */
public class CuponDAO implements ICuponDAO {

    /**
     * Componente encargado de crear conexiones con la base de datos. Se inyecta
     * por constructor para reducir acoplamiento y facilitar pruebas.
     */
    private final IConexionBD conexionBD;

    /**
     * Logger para registrar información relevante durante operaciones de
     * persistencia.
     */
    private static final Logger LOG = Logger.getLogger(CuponDAO.class.getName());

    /**
     * Constructor que inicializa la dependencia de conexión.
     *
     * @param conexionBD objeto que gestiona la creación de conexiones a la base
     * de datos
     */
    public CuponDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Obtiene los detalles de un cupón y descuenta un uso de forma atómica.
     * <p>
     * El método realiza un SELECT seguido de un UPDATE. Nota: En entornos de
     * alta concurrencia, se recomienda envolver esta operación en una
     * transacción para evitar inconsistencias en el contador.</p>
     *
     * * @param codigo Identificador único del cupón.
     * @return {@link CuponDTO} con los datos del cupón o null si no existe/está
     * agotado.
     * @throws PersistenciaExcepcion Si ocurre un error en la base de datos.
     */
    @Override
    public CuponDTO obtenerCupon(int codigo) throws PersistenciaExcepcion {
        String comando = """
                         SELECT id_cupon, nombre, descuento, vigencia, max_uso FROM cupones WHERE id_cupon = ? AND max_uso > 0;
                         """;
        //Comando para actualizar el cupon
        String actualizar = """
                            UPDATE Cupones 
                            SET max_uso = max_uso - 1 
                            WHERE id_cupon = ?;
                            """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando); PreparedStatement ps_actualizar = conn.prepareStatement(actualizar)) {
            ps.setInt(1, codigo);
            try (ResultSet resul = ps.executeQuery()) {
                if (resul.next()) {
                    CuponDTO cupon_obtenido = new CuponDTO();

                    cupon_obtenido.setNombre(resul.getString("nombre"));
                    cupon_obtenido.setDesc(resul.getDouble("descuento"));

                    cupon_obtenido.setVigencia(resul.getDate("vigencia"));

                    cupon_obtenido.setMax_usos(resul.getInt("max_uso"));

                    // 2. Configuramos el ID para el UPDATE y lo ejecutamos
                    ps_actualizar.setInt(1, codigo); // Le pasamos el mismo código
                    ps_actualizar.executeUpdate();   // <--- ESTA ES LA LÍNEA QUE HACE LA MAGIA

                    // 3. Retornamos el cupón real en lugar de null
                    return cupon_obtenido;

                }
            }
            return null;

        } catch (SQLException ex) {
            LOG.warning("No se encontro ningun cupon");
            throw new PersistenciaExcepcion("Hubo un error al querer consultar los cupones.", ex);
        }

    }

    @Override
    public CuponDTO obtenerCuponNombre(String nombre) throws PersistenciaExcepcion {
        String comando = """
                         SELECT id_cupon, nombre FROM cupones WHERE nombre = ?;
                         """;

        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando)) {
            ps.setString(1, nombre);
            try (ResultSet resul = ps.executeQuery()) {
                if (resul.next()) {
                    CuponDTO cupon_obtenido = new CuponDTO();

                    cupon_obtenido.setId(resul.getInt("id_cupon"));

                    // 3. Retornamos el cupón real en lugar de null
                    return cupon_obtenido;

                }
            }
            return null;

        } catch (SQLException ex) {
            LOG.warning("No se encontro ningun cupon");
            throw new PersistenciaExcepcion("Hubo un error al querer consultar el cupon.", ex);
        }

    }

    /**
     * Registra un nuevo cupón y recupera su ID autogenerado.
     * <p>
     * Utiliza la bandera {@code Statement.RETURN_GENERATED_KEYS} para obtener
     * el ID asignado por el motor de BD sin realizar una segunda consulta.</p>
     *
     * * @param cupon DTO con los datos del nuevo cupón.
     * @return El mismo DTO actualizado con el ID generado.
     * @throws PersistenciaExcepcion Si falla la inserción.
     */
    @Override
    public CuponDTO agregarCupon(CuponDTO cupon) throws PersistenciaExcepcion {
        String comandoSQL = "INSERT INTO Cupones (nombre, descuento, vigencia, max_uso) VALUES (?,?,?,?)";
        try (Connection conn = this.conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {
            /**
             * Con el return generated keys vamos a devolver el id para evitar
             * otra consulta y asi podremos saber que id se le asigno al cupon
             * de manera sencilla
             *
             */
            ps.setString(1, cupon.getNombre());
            ps.setDouble(2, cupon.getDesc());
            ps.setDate(3, new java.sql.Date(cupon.getVigencia().getTime()));
            ps.setInt(4, cupon.getMax_usos());

            int filas_afectadas = ps.executeUpdate();
            //Para poder saber si si se agrego asginamos las filas que se afectaron 
            //si es que se afectaron 
            if (filas_afectadas == 0) {
                LOG.warning("No se pudo agregar el cupon");
            }
            //Si se agrego pasamos a este try para obtener el id del cupon
            try (ResultSet id_generado = ps.getGeneratedKeys()) {
                if (id_generado.next()) {
                    cupon.setId(id_generado.getInt(1));
                } else {
                    LOG.warning("No se pudo obtener el id del cupon, revisa de nuevo");
                }
            }
            return cupon;
            //Una vez hecha esa validacion
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("No se pudo agregar el cupon", ex.getCause());
        }
    }

}
