/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Componentes.TablaSimplePanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class VHistorial extends JFrame{
    public VHistorial() {
        setTitle("Historial");
        setSize(700, 560);
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
                new LabelPersonalizado("Historial", 30, Color.WHITE);
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

        // ================= FILTROS =================
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setOpaque(false);
        // Reduje un poco los bordes laterales (de 90 a 50) para darles más espacio para estirarse
        panelFiltros.setBorder(new EmptyBorder(10, 50, 10, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        // Balanceamos los márgenes: 5px arriba/abajo, 15px izquierda/derecha
        gbc.insets = new Insets(5, 15, 5, 15); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // <--- ESTA ES LA CLAVE PARA QUE SE ESTIREN

        PlaceholderTextField txtFechaInicio =
                new PlaceholderTextField("Fecha inicio");
        PlaceholderTextField txtFechaFin =
                new PlaceholderTextField("Fecha fin");
        PlaceholderTextField txtEstado =
                new PlaceholderTextField("Estado");

        // Aumentamos un poco la dimensión preferida
        txtFechaInicio.setPreferredSize(new Dimension(180, 40));
        txtFechaFin.setPreferredSize(new Dimension(180, 40));
        txtEstado.setPreferredSize(new Dimension(180, 40));

        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(txtFechaInicio, gbc);

        gbc.gridx = 1;
        panelFiltros.add(txtFechaFin, gbc);

        gbc.gridx = 2;
        panelFiltros.add(txtEstado, gbc);

        // ================= RADIO BUTTONS =================
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Centramos los radios
        panelRadio.setOpaque(false);

        JRadioButton rbProgramado = new JRadioButton("Programado");
        JRadioButton rbExpress = new JRadioButton("Express");

        rbProgramado.setOpaque(false);
        rbExpress.setOpaque(false);
        rbProgramado.setForeground(Color.WHITE);
        rbExpress.setForeground(Color.WHITE);

        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbProgramado);
        grupoTipo.add(rbExpress);

        panelRadio.add(rbProgramado);
        panelRadio.add(rbExpress);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        gbc.weightx = 0.0; // Restablecemos el peso para que los radios no hagan cosas raras
        panelFiltros.add(panelRadio, gbc);

        // ================= TABLA =================
        JPanel panelTablaContenedor = new JPanel();
        panelTablaContenedor.setOpaque(false);
        panelTablaContenedor.setLayout(new BoxLayout(panelTablaContenedor, BoxLayout.Y_AXIS));
        panelTablaContenedor.setBorder(new EmptyBorder(10, 60, 10, 60));

        LabelPersonalizado lblPedidos =
                new LabelPersonalizado("Pedidos", 18, Color.WHITE);
        lblPedidos.setAlignmentX(Component.CENTER_ALIGNMENT);

        TablaSimplePanel tablaPedidos = new TablaSimplePanel(
                new String[]{"Número Pedido", "Estado", "Tipo pedido", "Fecha de registro"}
        );

        // Datos de ejemplo
        tablaPedidos.agregarFila("001", "Entregado", "Programado", "16-02-2026");
        tablaPedidos.agregarFila("002", "Pendiente", "Express", "17-02-2026");

        panelTablaContenedor.add(lblPedidos);
        panelTablaContenedor.add(Box.createVerticalStrut(10));
        panelTablaContenedor.add(tablaPedidos);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setOpaque(false);

        RoundedButton btnSalir = new RoundedButton("Salir");
        RoundedButton btnAgregar = new RoundedButton("Agregar");

        panelBotones.add(btnSalir);
        panelBotones.add(btnAgregar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFiltros, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panelTablaContenedor, BorderLayout.CENTER);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.SOUTH);

        setVisible(true);
    }

    
}
