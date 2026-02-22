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
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    
    public static boolean validarEnteros(String texto){
        return texto.matches("\\d+");
    }
    
    public static boolean validaFecha(String texto){
        return texto.matches("\\d{2}-\\d{2}-\\d{4}");
    }
    
    public static boolean validaContrasena(String texto){
       return texto.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$");
}
    }

