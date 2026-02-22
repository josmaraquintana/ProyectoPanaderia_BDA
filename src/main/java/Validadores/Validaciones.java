/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validadores;

/**
 *
 * @author josma
 */
public class Validaciones {
    
    public static boolean validarNombres(String texto){
        return texto.matches("[a-zA-Z]+");
    }
    
    public static boolean validarEnteros(String texto){
        return texto.matches("\\d+");
    }
}
