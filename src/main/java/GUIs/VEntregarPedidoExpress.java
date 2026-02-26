/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField; // <-- Asegúrate de que esta importación sea correcta según tu paquete
import Componentes.RoundedButton;
import Negocio.BOs.IPedidoBO;
import Negocio.BOs.PedidoBO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author josma
 */
public class VEntregarPedidoExpress extends JFrame {

    // Cambiamos a tu clase PlaceholderTextField
    private IPedidoBO pedidoBO;
    private PlaceholderTextField txtFolio;
    private PlaceholderTextField txtPin;

    public VEntregarPedidoExpress() {

        FabricaBOs fabrica = new FabricaBOs();

        // 2. Asignar el BO a la variable de la clase
        this.pedidoBO = fabrica.obtenerPedidoBO();

        setTitle("Entregar Pedido Express");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        Color blanco = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        LabelPersonalizado titulo
                = new LabelPersonalizado("Entregar Pedido Express", 28, blanco);
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

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

        LabelPersonalizado info = new LabelPersonalizado(
                "Ingrese los datos proporcionados por el cliente:",
                16,
                blanco
        );
        info.setHorizontalAlignment(SwingConstants.LEFT);

        // Panel para los campos de captura
        JPanel datosPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        datosPanel.setBackground(fondo);

        LabelPersonalizado lblFolio
                = new LabelPersonalizado("Folio del pedido:", 18, blanco);
        lblFolio.setHorizontalAlignment(SwingConstants.LEFT);

        // Instanciamos usando tu clase y le pasamos el texto de fondo (placeholder)
        txtFolio = new PlaceholderTextField("Ej. EXP-1234");
        txtFolio.setFont(new Font("Arial", Font.PLAIN, 16));

        LabelPersonalizado lblPin
                = new LabelPersonalizado("PIN de seguridad:", 18, blanco);
        lblPin.setHorizontalAlignment(SwingConstants.LEFT);

        // Instanciamos usando tu clase y le pasamos el texto de fondo (placeholder)
        txtPin = new PlaceholderTextField("Ej. 9876");
        txtPin.setFont(new Font("Arial", Font.PLAIN, 16));

        datosPanel.add(lblFolio);
        datosPanel.add(txtFolio);
        datosPanel.add(lblPin);
        datosPanel.add(txtPin);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        botonPanel.setBackground(fondo);

        RoundedButton btnSalir = new RoundedButton("Salir");
        btnSalir.setPreferredSize(new Dimension(140, 40));

        RoundedButton btnEntregar = new RoundedButton("Entregar");
        btnEntregar.setPreferredSize(new Dimension(140, 40));

        botonPanel.add(btnSalir);
        botonPanel.add(btnEntregar);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(fondo);

        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        datosPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Limitamos el tamaño máximo del panel de datos para que los campos de texto no se estiren demasiado
        datosPanel.setMaximumSize(new Dimension(500, 100));

        centerPanel.add(info);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(datosPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(botonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // --- EVENTOS ---
        btnSalir.addActionListener(e -> {
            // Aquí puedes instanciar la ventana anterior para regresar al menú
            this.dispose();
        });
        btnEntregar.addActionListener(e -> {
            try {
                // Llamamos al BO para validar y entregar
                boolean exito = pedidoBO.entregarPedidoExpress(txtFolio.getText().trim(), txtPin.getText().trim());

                if (exito) {
                    JOptionPane.showMessageDialog(this, "¡Pedido entregado con éxito!", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    // Limpiar campos o cerrar ventana
                    txtFolio.setText("");
                    txtPin.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Folio o PIN incorrectos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NegocioExcepcion ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
