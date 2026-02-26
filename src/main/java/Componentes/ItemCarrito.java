/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import Negocio.DTOs.ProductoDTO;

/**
 * Representa un elemento individual dentro del carrito de compras.
 * Esta clase vincula un producto específico con la cantidad solicitada por el usuario
 * y el cálculo del costo parcial (subtotal) resultante.
 * * @author RAMSES
 * @version 1.0
 */
public class ItemCarrito {
    /** El producto seleccionado por el cliente. */
    ProductoDTO producto;
    /** La cantidad de unidades de este producto en el carrito. */
    int cantidad;
    /** El costo total de este item (precio del producto multiplicado por la cantidad). */
    double subtotal;
    /**
     * Constructor por defecto. Crea una instancia vacía de ItemCarrito.
     */
    public ItemCarrito() {
    }
    /**
     * Constructor con parámetros para inicializar un item del carrito con datos específicos.
     * * @param producto El objeto {@link ProductoDTO} seleccionado.
     * @param cantidad Número de piezas del producto.
     * @param subtotal Monto acumulado para esta línea del carrito.
     */
    public ItemCarrito(ProductoDTO producto, int cantidad, double subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }
    /**
     * Obtiene el producto asociado a este item.
     * @return El {@link ProductoDTO} actual.
     */
    public ProductoDTO getProducto() {
        return producto;
    }
    /**
     * Asigna el producto a este item del carrito.
     * @param producto El nuevo {@link ProductoDTO} a establecer.
     */
    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }
    /**
     * Obtiene la cantidad de unidades solicitadas.
     * @return Valor entero de la cantidad.
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * Establece la cantidad de unidades para este item.
     * @param cantidad Cantidad de productos (debe ser mayor a cero en lógica de negocio).
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /**
     * Obtiene el subtotal calculado para este item.
     * @return El monto del subtotal.
     */
    public double getSubtotal() {
        return subtotal;
    }
    /**
     * Establece el subtotal para este item.
     * @param subtotal El nuevo monto calculado.
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    /**
     * Genera una representación en cadena de texto del objeto ItemCarrito.
     * @return Un String con los detalles del producto, cantidad y subtotal.
     */
    @Override
    public String toString() {
        return "ItemCarrito{" + "producto=" + producto + ", cantidad=" + cantidad + ", subtotal=" + subtotal + '}';
    }
    
    
    
}
