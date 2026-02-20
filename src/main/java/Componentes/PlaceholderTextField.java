package Componentes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 *
 * @author RAMSES
 */
public class PlaceholderTextField extends JTextField {

    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color fondo = Color.WHITE;
    private int radio = 30;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(Color.DARK_GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo redondeado
        g2.setColor(fondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

        super.paintComponent(g2);

        // Placeholder
        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() + fm.getAscent()) / 2 - 2;
            g2.drawString(placeholder, insets.left, y);
        }

        g2.dispose();
    }
}
