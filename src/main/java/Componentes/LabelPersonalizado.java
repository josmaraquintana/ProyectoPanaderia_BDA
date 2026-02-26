/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;
import javax.swing.*;
import java.awt.*;
/**
 * Componente visual personalizado que extiende de {@link JLabel}.
 * Se utiliza para estandarizar el estilo de las etiquetas de texto en la interfaz,
 * aplicando por defecto una fuente redondeada ("Arial Rounded MT Bold") y 
 * permitiendo la personalización de tamaño y color.
 * * @author josma
 * @version 1.0
 */
public class LabelPersonalizado extends JLabel{
    /**
     * Constructor principal que permite definir todas las propiedades visuales.
     * * @param texto El contenido de texto que mostrará la etiqueta.
     * @param tamaño El tamaño de la fuente en puntos.
     * @param color El color del texto (objeto {@link Color}).
     */
    public LabelPersonalizado(String texto, int tamaño, Color color) {
        super(texto); // Texto del label
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, tamaño)); // Fuente gordita y redondeada
        setForeground(color); // Color del texto
        setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
    }

    /**
     * Constructor simple que utiliza un estilo predeterminado.
     * Aplica un tamaño de 24 puntos y color negro.
     * * @param texto El contenido de texto que mostrará la etiqueta.
     */
    public LabelPersonalizado(String texto) {
        this(texto, 24, Color.BLACK); // Tamaño 24 y negro por defecto
    }
    /**
     * Constructor intermedio que permite elegir el color manteniendo el tamaño estándar.
     * * @param texto El contenido de texto que mostrará la etiqueta.
     * @param color El color del texto deseado.
     */
    public LabelPersonalizado(String texto, Color color) {
        this(texto, 24, color); // Tamaño 24 por defecto
    }
}
