/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

import ClasesEnum.EstadoPedido;
import java.time.LocalDate;

/**
 *
 * @author RAMSES
 */
public class PedidoExpress extends Pedido{
    
    private String folio;
    private int pin; 

    public PedidoExpress() {
    }

    public PedidoExpress(String folio, int pin, int id_pedido, int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        super(id_pedido, id_usuario, numero_pedido, fecha_registro, cantidad_productos, subtotal, total, estado_pedido);
        this.folio = folio;
        this.pin = pin;
    }

    public PedidoExpress(String folio, int pin, int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        super(id_usuario, numero_pedido, fecha_registro, cantidad_productos, subtotal, total, estado_pedido);
        this.folio = folio;
        this.pin = pin;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "PedidoExpress{" + "folio=" + folio + ", pin=" + pin + '}';
    }
    
    
    
}
