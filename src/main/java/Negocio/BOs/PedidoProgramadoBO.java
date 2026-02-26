/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoDatosDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import Negocio.DTOs.ProductoIdDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.fabrica.FabricaDAO;
import PersistenciaException.PersistenciaExcepcion;
import Validadores.Validaciones;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author josmara, ramses, daniel
 */
public class PedidoProgramadoBO implements IPedidoProgramadoBO{
    private final IPedidoProgramadoDAO pedidoProgramadoDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(PedidoProgramadoBO.class.getName());

    public PedidoProgramadoBO(IPedidoProgramadoDAO pedido_programado) {
        this.pedidoProgramadoDAO = pedido_programado; //inyeccion de dependencias
    }

    @Override
    public void realizarRegistrosPedidosClientesCupones(List<ItemCarrito> carrito, ClienteDTO cliente, List<CuponDTO> lista_cupones, double subtotal, double total, String notas) throws NegocioExcepcion {
        //Verificamos que el carrito no este vacio
        try{
            if (carrito == null || carrito.isEmpty()) {
                LOG.warning("El carrito esta vacio.");
                throw new NegocioExcepcion("El carrito esta vacio no es posible hacer el pedido.");
            }
            //Verificamos que el subtotal no este en ceros
            if (subtotal == 0.0) {
                LOG.warning("El subtotal esta en ceros.");
                throw new NegocioExcepcion("No hay precio del subtotal.");
            }
            //Verificamos que el cliente no este vacio
            if (cliente == null) {
                LOG.warning("El cliente no existe.");
                throw new NegocioExcepcion("El cliente esta vacio o no existe.");
            }
            
            //Mapeamos el cliente 
            PedidoDatosDTO pedidoDTO = new PedidoDatosDTO();
            pedidoDTO.setFecha(LocalDateTime.now());
            pedidoDTO.setNum_productos(carrito.size());
            pedidoDTO.setSubtotal(subtotal);
            pedidoDTO.setTotal(total);
            
            //Creamos los objetos para los metodos DAO
            FabricaDAO fabricaDAO = new FabricaDAO();
            IProductoDAO productoDAO = fabricaDAO.obtenerProductoDAO();
            ICuponDAO cuponDAO = fabricaDAO.obtenerCuponDAO();
            List<CuponDTO> lista_cupones_id = new ArrayList<>(); 
            //Creamos una lista para los cupones
            for (CuponDTO cupon : lista_cupones) {
                lista_cupones_id.add(cuponDAO.obtenerCuponNombre(cupon.getNombre())); //Agregamos los cupones a la lista
            }
            //Obtenemos los productos de la base de datos
            List<ProductoIdDTO> lista_productos_id = productoDAO.obtenerListaProductosId();
            List<Integer> lista_id_productos = new ArrayList<>();
            //Obtenemos los id de los productos
            for (ItemCarrito item : carrito) {
                for (ProductoIdDTO productoId : lista_productos_id) {
                    if (productoId.getNombre().equalsIgnoreCase(item.getProducto().getNombre())) {
                        lista_id_productos.add(productoId.getId());
                    }
                }
            }
            //Mandamos a llamar el registro del pedido
            pedidoProgramadoDAO.realizarRegistroPedidoProgramado(carrito, cliente, lista_cupones_id, pedidoDTO, notas, lista_id_productos);
            
        }catch(PersistenciaExcepcion ex){
            LOG.warning("No se logro hacer el resgistro del pedido completo.");
            throw new NegocioExcepcion("Hubo un error al querer mandar los registros.");
        }
        
        
    }
    
    @Override
    public List<PedidoEstadoDTO> obtenerListaPedidosConResumen() throws NegocioExcepcion{
        
        try{
            //Hacemos la lista que obtendra todo 
            List<PedidoEstadoDTO> listaPedidos = pedidoProgramadoDAO.obtenerListaPedidosConResumen();
            
            //Validamos que la lista no este vacia 
            if (listaPedidos.isEmpty() || listaPedidos == null) {
                LOG.warning("La lista que se obtuvo esta vacia.");
                throw new NegocioExcepcion("No se obtuvo ninguna lista.");
            }
            //Retornamos la lista a el metodo de la ventana 
            return listaPedidos;
            
        }catch(PersistenciaExcepcion ex){
            LOG.warning("Hubo un error al obtener la lista de pedidos con sus resumenes o notas.");
            throw new NegocioExcepcion("No se completo el regresar la lista de pedidos con notas.");
        }
    }
    
    @Override
    public boolean cambiarEstadoPedido(String texto_id) throws NegocioExcepcion{
        
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
        
        try{
            
            boolean valor = pedidoProgramadoDAO.cambiarEstadoPedido(id_pedido);
            return valor;
                    
        }catch(PersistenciaExcepcion ex){
            LOG.warning("No se pudo actualizar el estado.");
            throw new NegocioExcepcion("Hubo un error al querer mandar los datos para actualizar.");
        }
        
        
        
    }
}
