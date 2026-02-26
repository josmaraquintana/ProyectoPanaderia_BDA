/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClasesEnum;

/**
 * Define los estados posibles por los que puede pasar un pedido dentro del sistema.
 * Estos estados controlan el flujo de trabajo desde que el cliente realiza la compra
 * hasta que el producto llega a sus manos o se cierra la transacción.
 * * @author RAMSES, JOSMARA, DANIEL
 * @version 1.0
 */
public enum EstadoPedido {
    /** * El pedido ha sido registrado pero aún no ha sido procesado por el personal. 
     */
    PENDIENTE, 
    /** * El pedido ha sido preparado y está a la espera de ser recolectado o enviado. 
     */
    LISTO, 
    /** * El pedido ha sido recibido satisfactoriamente por el cliente final. 
     */
    ENTREGADO, 
    /** * El pedido fue anulado por el cliente o por el sistema debido a alguna incidencia. 
     */
    CANCELADO, 
    /** * El pedido no fue procesado dentro del tiempo límite establecido o fue ignorado. 
     */
    DESATENDIDO;
    
}
