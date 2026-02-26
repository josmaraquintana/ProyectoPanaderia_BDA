/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClasesEnum;

/**
 * Representa los estados posibles en los que se puede encontrar una cuenta
 * dentro del sistema.
 * * <p>Este enumerado se utiliza para controlar el acceso y la operatividad 
 * de los servicios asociados a la cuenta.</p>
 * * @author Josmara
 * @version 1.0
 */
public enum EstadoCuenta {
    /**
     * Indica que la cuenta está operativa y tiene permiso para 
     * realizar todas las transacciones permitidas.
     */
    ACTIVO, 
    /**
     * Indica que la cuenta ha sido suspendida o deshabilitada. 
     * Las operaciones suelen estar restringidas en este estado.
     */
    INACTIVO;
}
