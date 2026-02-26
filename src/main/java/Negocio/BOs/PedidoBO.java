/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import ClasesEnum.EstadoPedido;
import Componentes.ItemCarrito;
import Negocio.DTOs.PedidoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import Negocio.DTOs.ProductoIdDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.fabrica.FabricaDAO;
import PersistenciaException.PersistenciaExcepcion;
import Validadores.Validaciones;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Business Object (BO) encargado de la gestión integral de pedidos.
 * <p>
 * Esta clase centraliza la lógica para consultar historiales, realizar
 * registros de ventas express y gestionar el ciclo de vida de los estados de un
 * pedido (RECIBIDO, EN_PREPARACION, ENTREGADO, etc.).</p>
 *
 * * @author josma
 * @version 1.1
 */
public class PedidoBO implements IPedidoBO {

    private final IPedidoDAO pedidoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(PedidoBO.class.getName());

    /**
     * Consulta el historial de pedidos filtrado por un rango de fechas y
     * criterios específicos.
     * <p>
     * Realiza un pre-procesamiento de las cadenas de fecha para asegurar un
     * formato compatible con SQL (AAAA-MM-DD) y valida la coherencia
     * cronológica.</p>
     *
     * * @param fecha_inicio Fecha inicial en formato String.
     * @param fecha_fin Fecha final en formato String.
     * @param id_cliente Identificador único del cliente.
     * @param tipo Categoría del pedido ("Programado" o "Express").
     * @param estado Valor del Enum {@link EstadoPedido}.
     * @return Lista de {@link PedidoDTO} que coinciden con los filtros.
     * @throws NegocioExcepcion Si el formato de fecha es incorrecto o las
     * fechas son incoherentes.
     */
    public PedidoBO(IPedidoDAO pedido) {
        this.pedidoDAO = pedido; //inyeccion de dependencias
    }

    @Override
    public List<PedidoDTO> consultarHistorial(String fecha_inicio, String fecha_fin, int id_cliente, String tipo, EstadoPedido estado) throws NegocioExcepcion {
        try {
            //Validacion de fechas vacias
            if (fecha_inicio == null || fecha_fin == null || fecha_inicio.isBlank() || fecha_fin.isBlank()) {
                throw new NegocioExcepcion("Las fechas de búsqueda no pueden estar vacías.");
            }
            //Validacion del id del cliente, que el problema con el id ya se soluciono en la dao jeje
            if (id_cliente <= 0) {
                throw new NegocioExcepcion("ID de cliente no válido para la consulta.");
            }
            //el tipo que es programado o express
            if (tipo == null || tipo.isBlank()) {
                throw new NegocioExcepcion("Debe especificar el tipo de pedido (Programado/Express).");
            }
            //el enum que en este caso se ingresa por texto, igual tiene validaciones
            if (estado == null) {
                throw new NegocioExcepcion("El estado del pedido es obligatorio.");
            }

            // Aqui estamos poniendonos estrictos con el tema de las fechas
            //seteandolas para que hagan click de manera perfecta a la hora de consultar
            String fecha_inicio_limpia = fecha_inicio.trim().replaceAll("[^0-9-]", "");
            String fecha_fin_limpia = fecha_fin.trim().replaceAll("[^0-9-]", "");

            // Declaracion de variables date para luego darles el formato
            Date fecha_inicio_format;
            Date fecha_fin_format;

            try {
                fecha_inicio_format = Date.valueOf(fecha_inicio_limpia);
                fecha_fin_format = Date.valueOf(fecha_fin_limpia);
            } catch (IllegalArgumentException e) {
                LOG.warning("Error de formato en fechas: " + fecha_inicio_limpia + " o " + fecha_fin_limpia);
                throw new NegocioExcepcion("Formato de fecha inválido. Use AAAA-MM-DD (ejemplo: 2026-02-23)");
            }

            //La fecha de inicio no puede estar despues a la fecha fin
            if (fecha_inicio_format.after(fecha_fin_format)) {
                throw new NegocioExcepcion("La fecha de inicio no puede ser posterior a la fecha de fin.");
            }

            // Llamada al dao
            return pedidoDAO.traerHistorial(fecha_inicio_format, fecha_fin_format, id_cliente, tipo, estado);

        } catch (PersistenciaExcepcion e) {
            LOG.severe("Error de persistencia al traer historial: " + e.getMessage());
            throw new NegocioExcepcion("No se pudo obtener el historial debido a un error en la base de datos.");
        }
    }

    @Override
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws NegocioExcepcion {

        try {
            //Hacemos la lista que obtendra todo 
            List<PedidoEstadoDTO> listaPedidos = pedidoDAO.obtenerListaPedidosConResumen();

            //Validamos que la lista no este vacia 
            if (listaPedidos.isEmpty() || listaPedidos == null) {
                LOG.warning("La lista que se obtuvo esta vacia.");
                throw new NegocioExcepcion("No se obtuvo ninguna lista.");
            }
            //Retornamos la lista a el metodo de la ventana 
            return listaPedidos;

        } catch (PersistenciaExcepcion ex) {
            LOG.warning("Hubo un error al obtener la lista de pedidos con sus resumenes o notas.");
            throw new NegocioExcepcion("No se completo el regresar la lista de pedidos con notas.");
        }
    }

    @Override
    public boolean cambiarEstadoPedido(String texto_id, String estado) throws NegocioExcepcion {

        Validaciones validar = new Validaciones();

        if (!validar.validarEnteros(texto_id)) {
            LOG.warning("El texto del id debe ser solamente numeros.");
            throw new NegocioExcepcion("El texto del id esta mal.");
        }
        System.out.println(texto_id);
        int id_pedido = Integer.parseInt(texto_id);

        if (id_pedido <= 0) {
            LOG.warning("El id del pedido no debe ser negativo.");
            throw new NegocioExcepcion("El id es negativo.");
        }

        try {

            boolean valor = pedidoDAO.cambiarEstadoPedido(id_pedido, estado);
            return valor;

        } catch (PersistenciaExcepcion ex) {
            LOG.warning("No se pudo actualizar el estado.");
            throw new NegocioExcepcion("Hubo un error al querer mandar los datos para actualizar.");
        }

    }

    /**
     * Procesa y registra una venta bajo la modalidad Express.
     * <p>
     * El método realiza el mapeo entre los productos del carrito y sus IDs de
     * base de datos, calculando la fecha de registro en tiempo real.</p>
     *
     * * @param carrito Lista de objetos {@link ItemCarrito} con productos y
     * cantidades.
     * @param subtotal Monto económico antes de impuestos o descuentos.
     * @param folio Código identificador único para la recolección.
     * @param pin Código de seguridad para validar la entrega.
     * @throws NegocioExcepcion Si el carrito está vacío o ocurre un error en la
     * persistencia.
     */
    @Override
    public void realizarRegistroExpress(List<ItemCarrito> carrito, double subtotal, String folio, String pin) throws NegocioExcepcion {
        try {
            // 1. Validaciones iniciales
            if (carrito == null || carrito.isEmpty()) {
                throw new NegocioExcepcion("El carrito esta vacio.");
            }
            if (subtotal <= 0.0) {
                throw new NegocioExcepcion("El subtotal es invalido.");
            }

            // 2. Preparación de datos
            LocalDateTime fecha = LocalDateTime.now();

            // 3. Obtención de IDs (Tu lógica original)
            FabricaDAO fabricaDAO = new FabricaDAO();
            IProductoDAO productoDAO = fabricaDAO.obtenerProductoDAO();

            List<ProductoIdDTO> lista_productos_id = productoDAO.obtenerListaProductosId();
            List<Integer> lista_id_productos = new ArrayList<>();

            for (ItemCarrito item : carrito) {
                for (ProductoIdDTO productoId : lista_productos_id) {
                    if (productoId.getNombre().equalsIgnoreCase(item.getProducto().getNombre())) {
                        lista_id_productos.add(productoId.getId());
                    }
                }
            }

            // 4. Llamada al DAO (Aquí es donde ocurre el AES_ENCRYPT)
            pedidoDAO.realizarPedidoExpress(carrito, folio, pin, fecha, lista_id_productos, subtotal);

        } catch (PersistenciaExcepcion ex) {
            LOG.warning("Error en persistencia: " + ex.getMessage());
            throw new NegocioExcepcion("No se pudo guardar el pedido en la base de datos.");
        }
    }

    @Override
    public boolean entregarPedidoExpress(String folio, String pin) throws NegocioExcepcion {
        try {
            // El DAO devolverá true si el folio/pin existen y se pudo actualizar el estado
            return pedidoDAO.validarYEntregarPedidoExpress(folio, pin);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioExcepcion("No se pudo procesar la entrega: " + ex.getMessage());
        }
    }

    /**
     * Método auxiliar para generar los números aleatorios
     */
    private String generarCadenaAleatoria(int longitud) {
        java.security.SecureRandom sr = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(sr.nextInt(10));
        }
        return sb.toString();
    }
}
