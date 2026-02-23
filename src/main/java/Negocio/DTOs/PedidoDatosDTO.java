/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;
import java.time.LocalDateTime;

/**
 *
 * @author RAMSES
 */
public class PedidoDatosDTO {
    
    private LocalDateTime fecha;
    private int num_productos;
    private double subtotal;
    private double total;

    public PedidoDatosDTO(LocalDateTime fecha, int num_productos, double subtotal, double total) {
        this.fecha = LocalDateTime.now();
        this.num_productos = num_productos;
        this.subtotal = subtotal;
        this.total = total;
    }

    public PedidoDatosDTO() {
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getNum_productos() {
        return num_productos;
    }

    public void setNum_productos(int num_productos) {
        this.num_productos = num_productos;
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

    @Override
    public String toString() {
        return "PedidoDTO{" + "fecha=" + fecha + ", num_productos=" + num_productos + ", subtotal=" + subtotal + ", total=" + total +'}';
    }
    
    
    
    
}
