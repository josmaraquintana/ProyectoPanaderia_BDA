/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;

import Componentes.ItemCarrito;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.DTOs.PedidoDatosDTO;
import Negocio.DTOs.ProductoIdDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.fabrica.FabricaDAO;
import PersistenciaException.PersistenciaExcepcion;
import java.time.LocalDate;
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
        
        try{
            if (carrito == null || carrito.isEmpty()) {
                LOG.warning("El carrito esta vacio.");
                throw new NegocioExcepcion("El carrito esta vacio no es posible hacer el pedido.");
            }
            
            if (subtotal == 0.0) {
                LOG.warning("El subtotal esta en ceros.");
                throw new NegocioExcepcion("No hay precio del subtotal.");
            }
            
            if (cliente == null) {
                LOG.warning("El cliente no existe.");
                throw new NegocioExcepcion("El cliente esta vacio o no existe.");
            }
            
            
            PedidoDatosDTO pedidoDTO = new PedidoDatosDTO();
            pedidoDTO.setFecha(LocalDate.now());
            pedidoDTO.setNum_productos(carrito.size());
            pedidoDTO.setSubtotal(subtotal);
            pedidoDTO.setTotal(total);
            
            FabricaDAO fabricaDAO = new FabricaDAO();
            
            IProductoDAO productoDAO = fabricaDAO.obtenerProductoDAO();
            
            ICuponDAO cuponDAO = fabricaDAO.obtenerCuponDAO();
            List<CuponDTO> lista_cupones_id = new ArrayList<>();
            
            for (CuponDTO cupon : lista_cupones) {
                lista_cupones_id.add(cuponDAO.obtenerCuponNombre(cupon.getNombre()));
            }
            
            List<ProductoIdDTO> lista_productos_id = productoDAO.obtenerListaProductosId();
            List<Integer> lista_id_productos = new ArrayList<>();
            
            for (ItemCarrito item : carrito) {
                for (ProductoIdDTO productoId : lista_productos_id) {
                    if (productoId.getNombre().equalsIgnoreCase(item.getProducto().getNombre())) {
                        lista_id_productos.add(productoId.getId());
                    }
                }
            }
            
            pedidoProgramadoDAO.realizarRegistroPedidoProgramado(carrito, cliente, lista_cupones_id, pedidoDTO, notas, lista_id_productos);
            
        }catch(PersistenciaExcepcion ex){
            LOG.warning("No se logro hacer el resgistro del pedido completo.");
            throw new NegocioExcepcion("Hubo un error al querer mandar los registros.");
        }
        
        
    }
}
