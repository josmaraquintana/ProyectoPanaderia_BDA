package Persistencia.dominio;

import ClasesEnum.EstadoPedido;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josma
 */
public class PedidoProgramado extends Pedido{

    public PedidoProgramado() {
    }

    public PedidoProgramado(int id_pedido, int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        super(id_pedido, id_usuario, numero_pedido, fecha_registro, cantidad_productos, subtotal, total, estado_pedido);
    }

    public PedidoProgramado(int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        super(id_usuario, numero_pedido, fecha_registro, cantidad_productos, subtotal, total, estado_pedido);
    }
    
    
    
}
