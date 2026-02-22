/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author RAMSES
 */
public class ProductoDTO {
    private String nombre;
    private String estado;
    private String descripcion;
    private double precio;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, String estado, String descripcion, double precio) {
        this.nombre = nombre;
        this.estado = estado;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" + "nombre=" + nombre + ", estado=" + estado + ", descripcion=" + descripcion + ", precio=" + precio + '}';
    }
    
    
}
