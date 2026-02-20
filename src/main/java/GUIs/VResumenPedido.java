/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author RAMSES
 */
public class VResumenPedido extends JFrame {

    public VResumenPedido() {
        setTitle("Resumen del pedido");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        Color cafe = new Color(139, 69, 19);
        Color dorado = new Color(230, 190, 120);

        getContentPane().setBackground(fondo);

        // ================= PANEL SUPERIOR (TITULO + LOGO) =================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(15, 25, 10, 25));

        LabelPersonalizado lblTitulo =
                new LabelPersonalizado("Resumen del pedido", 26, Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        URL logoURL = getClass().getResource("/img/icon.png");
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image img = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            System.err.println("No se encontró el logo");
        }

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblLogo, BorderLayout.EAST);

        // ================= AREA DE RESUMEN =================
        JTextArea areaResumen = new JTextArea();
        areaResumen.setEditable(true);
        areaResumen.setLineWrap(true);
        areaResumen.setWrapStyleWord(true);
        areaResumen.setFont(new Font("Arial", Font.PLAIN, 14));
        areaResumen.setForeground(Color.WHITE);
        areaResumen.setBackground(cafe);
        areaResumen.setBorder(new EmptyBorder(15, 15, 15, 15));
        areaResumen.setText(
                "Panes que se escogieron ->\n\n" +
                "Pan integral - $10.00 - cant: 1\n\n" +
                "Notas para los productos:\n" +
                "Quiero que mi pan tenga miel de abeja y arándanos"
        );

        JScrollPane scrollResumen = new JScrollPane(areaResumen);
        scrollResumen.setBorder(BorderFactory.createEmptyBorder());
        scrollResumen.setPreferredSize(new Dimension(520, 300));

        JPanel panelResumen = new JPanel(new BorderLayout());
        panelResumen.setBackground(cafe);
        panelResumen.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelResumen.add(scrollResumen, BorderLayout.CENTER);

        // ================= PANEL TOTALES =================
        JPanel panelTotales = new JPanel();
        panelTotales.setOpaque(false);
        panelTotales.setLayout(new BoxLayout(panelTotales, BoxLayout.Y_AXIS));
        panelTotales.setBorder(new EmptyBorder(40, 30, 30, 30));

        panelTotales.add(new LabelPersonalizado("Subtotal:   $10.00", 18, Color.WHITE));
        panelTotales.add(Box.createVerticalStrut(15));
        panelTotales.add(new LabelPersonalizado("Cupón:      $0.00", 18, Color.WHITE));
        panelTotales.add(Box.createVerticalStrut(15));
        panelTotales.add(new LabelPersonalizado("Total:      $10.00", 20, dorado));

        // ================= PANEL CENTRAL =================
        JPanel panelCentro = new JPanel(new BorderLayout(20, 0));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(new EmptyBorder(10, 20, 20, 20));

        panelCentro.add(panelResumen, BorderLayout.CENTER);
        panelCentro.add(panelTotales, BorderLayout.EAST);

        // ================= PANEL INFERIOR =================
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelInferior.setOpaque(false);

        PlaceholderTextField txtCupon = new PlaceholderTextField("Código del cupón");
        txtCupon.setPreferredSize(new Dimension(200, 40));

        RoundedButton btnCupon = new RoundedButton("Insertar Cupón");
        RoundedButton btnCancelar = new RoundedButton("Cancelar");
        RoundedButton btnAceptar = new RoundedButton("Aceptar");

        panelInferior.add(txtCupon);
        panelInferior.add(btnCupon);
        panelInferior.add(btnCancelar);
        panelInferior.add(btnAceptar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

}
