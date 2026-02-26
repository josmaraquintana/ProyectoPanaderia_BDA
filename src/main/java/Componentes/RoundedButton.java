/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Botón personalizado con bordes redondeados y estilo visual definido.
 * <p>
 * Esta clase extiende {@link JButton} para proporcionar un diseño moderno
 * mediante el uso de gráficos 2D, suavizado de bordes (anti-aliasing) y un
 * grosor de borde personalizado.</p>
 *
 * * @author RAMSES
 * @version 1.0
 */
public class RoundedButton extends JButton {

    private Color fondo = new Color(255, 229, 180); // color interior
    private Color borde = new Color(139, 69, 19);   // café oscuro
    private int radio = 40;

    /**
     * Crea un nuevo botón redondeado con el texto especificado.
     * <p>
     * Configura el botón para que sea transparente en sus áreas estándar y
     * establece una tipografía y tamaño preferido predeterminados.</p>
     *
     * * @param texto El texto que se mostrará en el botón.
     */
    public RoundedButton(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(borde);
        setPreferredSize(new Dimension(160, 45));
    }

    /**
     * Sobrescribe el proceso de pintura del componente para dibujar el fondo
     * redondeado.
     *
     * * @param g El contexto gráfico {@code Graphics} utilizado para pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo
        g2.setColor(fondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

        // Texto
        super.paintComponent(g2);
        g2.dispose();
    }

    /**
     * Sobrescribe el proceso de pintura del borde para dibujar un contorno
     * redondeado.
     * <p>
     * Utiliza un {@link BasicStroke} de 4 píxeles para dar grosor al borde.</p>
     *
     * * @param g El contexto gráfico {@code Graphics} utilizado para pintar el
     * borde.
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(borde);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(
                2, 2,
                getWidth() - 5,
                getHeight() - 5,
                radio, radio
        );

        g2.dispose();
    }
}
