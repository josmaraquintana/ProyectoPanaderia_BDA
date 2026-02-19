/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

import ClasesEnum.EstadoProducto;
import ClasesEnum.TipoProducto;

/**
 *
 * @author josma
 */
public class Producto {
    
    private int id_producto;
    private String nombre_producto;
    private String descripcion;
    private TipoProducto tipo_producto;
    private EstadoProducto estado_producto;
    private float precio;

    public Producto() {
    }

    public Producto(int id_producto, String nombre_producto, String descripcion, TipoProducto tipo_producto, EstadoProducto estado_producto, float precio) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.tipo_producto = tipo_producto;
        this.estado_producto = estado_producto;
        this.precio = precio;
    }

    public Producto(String nombre_producto, String descripcion, TipoProducto tipo_producto, EstadoProducto estado_producto, float precio) {
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.tipo_producto = tipo_producto;
        this.estado_producto = estado_producto;
        this.precio = precio;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoProducto getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(TipoProducto tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public EstadoProducto getEstado_producto() {
        return estado_producto;
    }

    public void setEstado_producto(EstadoProducto estado_producto) {
        this.estado_producto = estado_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id_producto=" + id_producto + ", nombre_producto=" + nombre_producto + ", descripcion=" + descripcion + ", tipo_producto=" + tipo_producto + ", estado_producto=" + estado_producto + ", precio=" + precio + '}';
    }
    
}
