/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class PedidoExpressFinal extends JFrame {

    public PedidoExpressFinal(String folio, String pin, double total) {
        setTitle("Pedido Express");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        Color blanco = Color.WHITE;
        Color naranja = new Color(201, 104, 0);

        // ================= Panel principal =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // ================= Título =================
        LabelPersonalizado titulo =
                new LabelPersonalizado("Pedido Express", 28, blanco);
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        // ================= Logo =================
        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(140, 140));
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(fondo);
        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(lblLogo, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // ================= Texto informativo =================
        LabelPersonalizado info = new LabelPersonalizado(
                "<html>Guarde estos datos para la recolección de su pedido express.<br>" +
                "RECUERDE QUE SOLO TIENE 20 MIN PARA RECOGER SU PEDIDO EXPRESS →</html>",
                14,
                naranja
        );
        info.setHorizontalAlignment(SwingConstants.LEFT);

        // ================= Datos =================
        JPanel datosPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        datosPanel.setBackground(fondo);

        LabelPersonalizado lblFolio =
                new LabelPersonalizado("Folio: " + folio, 18, blanco);
        lblFolio.setHorizontalAlignment(SwingConstants.LEFT);

        LabelPersonalizado lblTotal =
                new LabelPersonalizado("Total   $" + String.format("%.2f", total), 18, blanco);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        LabelPersonalizado lblPin =
                new LabelPersonalizado("PIN: " + pin, 18, blanco);
        lblPin.setHorizontalAlignment(SwingConstants.LEFT);

        datosPanel.add(lblFolio);
        datosPanel.add(lblTotal);
        datosPanel.add(lblPin);
        datosPanel.add(new JLabel()); // espacio visual

        // ================= Botón =================
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        botonPanel.setBackground(fondo);

        RoundedButton btnOk = new RoundedButton("OK");
        btnOk.setPreferredSize(new Dimension(140, 40));

        botonPanel.add(btnOk);

        // ================= Centro =================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(fondo);

        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        datosPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(info);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(datosPanel);

        // ================= Ensamblado =================
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(botonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ================= Main de prueba =================
    public static void main(String[] args) {
        new PedidoExpressFinal("1234", "12345678", 20.00);
    }
}
