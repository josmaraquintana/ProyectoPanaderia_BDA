/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;
import java.time.LocalDate;

/**
 *
 * @author RAMSES
 */
public class PedidoDatosDTO {
    
    private LocalDate fecha;
    private int num_productos;
    private double subtotal;
    private double total;

    public PedidoDatosDTO(LocalDate fecha, int num_productos, double subtotal, double total) {
        this.fecha = LocalDate.now();
        this.num_productos = num_productos;
        this.subtotal = subtotal;
        this.total = total;
    }

    public PedidoDatosDTO() {
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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
