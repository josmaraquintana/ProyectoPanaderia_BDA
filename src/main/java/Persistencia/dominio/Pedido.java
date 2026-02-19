/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

import ClasesEnum.EstadoPedido;
import java.time.LocalDate;

/**
 *
 * @author josma
 */
public class Pedido {
    
    private int id_pedido;
    private int id_usuario;
    private int numero_pedido;
    private LocalDate fecha_registro;
    private int cantidad_productos;
    private double subtotal;
    private double total;
    private EstadoPedido estado_pedido;

    public Pedido() {
    }

    public Pedido(int id_pedido, int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.numero_pedido = numero_pedido;
        this.fecha_registro = fecha_registro;
        this.cantidad_productos = cantidad_productos;
        this.subtotal = subtotal;
        this.total = total;
        this.estado_pedido = estado_pedido;
    }

    public Pedido(int id_usuario, int numero_pedido, LocalDate fecha_registro, int cantidad_productos, double subtotal, double total, EstadoPedido estado_pedido) {
        this.id_usuario = id_usuario;
        this.numero_pedido = numero_pedido;
        this.fecha_registro = fecha_registro;
        this.cantidad_productos = cantidad_productos;
        this.subtotal = subtotal;
        this.total = total;
        this.estado_pedido = estado_pedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(int numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getCantidad_productos() {
        return cantidad_productos;
    }

    public void setCantidad_productos(int cantidad_productos) {
        this.cantidad_productos = cantidad_productos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoPedido getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(EstadoPedido estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id_pedido=" + id_pedido + ", id_usuario=" + id_usuario + ", numero_pedido=" + numero_pedido + ", fecha_registro=" + fecha_registro + ", cantidad_productos=" + cantidad_productos + ", subtotal=" + subtotal + ", total=" + total + ", estado_pedido=" + estado_pedido + '}';
    }
    
    
    
}
