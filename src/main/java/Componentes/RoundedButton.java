/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private Color fondo = new Color(255, 229, 180); // color interior
    private Color borde = new Color(139, 69, 19);   // café oscuro
    private int radio = 40;

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

