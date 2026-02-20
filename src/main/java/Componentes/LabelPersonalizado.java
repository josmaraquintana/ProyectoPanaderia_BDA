/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author josma
 */
public class LabelPersonalizado extends JLabel{
    // Constructor principal
    public LabelPersonalizado(String texto, int tamaño, Color color) {
        super(texto); // Texto del label
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, tamaño)); // Fuente gordita y redondeada
        setForeground(color); // Color del texto
        setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
    }

    // Constructor simple con tamaño y color por defecto
    public LabelPersonalizado(String texto) {
        this(texto, 24, Color.BLACK); // Tamaño 24 y negro por defecto
    }
     // Tercer constructor: texto + color, tamaño por defecto
    public LabelPersonalizado(String texto, Color color) {
        this(texto, 24, color); // Tamaño 24 por defecto
    }
}
