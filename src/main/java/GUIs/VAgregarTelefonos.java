/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class VAgregarTelefonos extends JFrame{
    
    public VAgregarTelefonos() {
        setTitle("Agregar teléfonos");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        getContentPane().setBackground(fondo);

        // ================= PANEL SUPERIOR =================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(20, 30, 10, 30));

        LabelPersonalizado lblTitulo =
                new LabelPersonalizado("Agregar teléfonos", 28, Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        URL logoURL = getClass().getResource("/img/icon.png");
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblLogo, BorderLayout.EAST);

        // ================= CAMPOS =================
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);
        panelCampos.setBorder(new EmptyBorder(10, 60, 10, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        PlaceholderTextField txtTelefono =
                new PlaceholderTextField("Teléfono");
        PlaceholderTextField txtEtiqueta =
                new PlaceholderTextField("Etiqueta");

        txtTelefono.setPreferredSize(new Dimension(250, 40));
        txtEtiqueta.setPreferredSize(new Dimension(250, 40));

        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(txtTelefono, gbc);

        gbc.gridx = 1;
        panelCampos.add(txtEtiqueta, gbc);

        // ================= TABLA =================
        JPanel panelTablaContenedor = new JPanel();
        panelTablaContenedor.setOpaque(false);
        panelTablaContenedor.setLayout(new BoxLayout(panelTablaContenedor, BoxLayout.Y_AXIS));
        panelTablaContenedor.setBorder(new EmptyBorder(10, 60, 10, 60));

        LabelPersonalizado lblTabla =
                new LabelPersonalizado("Teléfonos ya agregados", 16, Color.WHITE);
        lblTabla.setAlignmentX(Component.CENTER_ALIGNMENT);

        TablaSimplePanel tablaTelefonos =
                new TablaSimplePanel(new String[]{"Número", "Etiqueta"});

        // Datos de ejemplo
        tablaTelefonos.agregarFila("6441665434", "Personal");
        tablaTelefonos.agregarFila("6621456798", "Casa");
        tablaTelefonos.agregarFila("6441665434", "Personal");
        tablaTelefonos.agregarFila("6621456798", "Casa");
        tablaTelefonos.agregarFila("6441665434", "Personal");
        tablaTelefonos.agregarFila("6621456798", "Casa");
        tablaTelefonos.agregarFila("6441665434", "Personal");
        tablaTelefonos.agregarFila("6621456798", "Casa");

        panelTablaContenedor.add(lblTabla);
        panelTablaContenedor.add(Box.createVerticalStrut(10));
        panelTablaContenedor.add(tablaTelefonos);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setOpaque(false);

        RoundedButton btnSalir = new RoundedButton("Salir");
        RoundedButton btnAgregar = new RoundedButton("Agregar");

        panelBotones.add(btnSalir);
        panelBotones.add(btnAgregar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panelTablaContenedor, BorderLayout.CENTER);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.SOUTH);

        setVisible(true);
    }

}