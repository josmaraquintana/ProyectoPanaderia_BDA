/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClasesEnum;

/**
 * Define los estados de disponibilidad de un producto dentro del catálogo de la panadería.
 * Estos valores determinan si un producto puede ser visualizado y seleccionado 
 * por el cliente en el proceso de compra.
 * * @author RAMSES, JOSMARA, DANIEL
 * @version 1.0
 */
public enum EstadoProducto {
    /**
     * Indica que el producto cuenta con existencias suficientes y puede ser
     * añadido a nuevos pedidos.
     */
    DISPONIBLE, 
    /**
     * Indica que el producto no tiene stock por el momento. El sistema debe
     * restringir su venta hasta que se actualice su inventario.
     */
    AGOTADO;
    
}
