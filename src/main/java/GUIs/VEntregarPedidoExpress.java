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

    /**
     * Interfaz gráfica diseñada para el proceso de entrega de pedidos bajo la
     * modalidad "Express".
     * <p>
     * Esta ventana actúa como un punto de validación donde el empleado debe
     * ingresar el folio del pedido y un PIN de seguridad proporcionado por el
     * cliente. La autenticación y el cambio de estado del pedido se gestionan
     * de forma atómica en la capa de negocio.</p>
     *
     * * @author josma, RAMSES
     * @version 1.0
     */
    private IPedidoBO pedidoBO;
    private PlaceholderTextField txtFolio;
    private PlaceholderTextField txtPin;
    /**
     * Inicializa la ventana de entrega express.
     * <p>Utiliza {@link FabricaBOs} para obtener una implementación de {@link IPedidoBO}, 
     * desacoplando la GUI de la implementación concreta de la lógica.</p>
     * <p>El diseño emplea {@link BoxLayout} y {@link GridLayout} para mantener una 
     * estética limpia y centrada para los campos de captura.</p>
     */
    public VEntregarPedidoExpress() {

        FabricaBOs fabrica = new FabricaBOs();

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

        txtFolio = new PlaceholderTextField("Ej. EXP-1234");
        txtFolio.setFont(new Font("Arial", Font.PLAIN, 16));

        LabelPersonalizado lblPin
                = new LabelPersonalizado("PIN de seguridad:", 18, blanco);
        lblPin.setHorizontalAlignment(SwingConstants.LEFT);


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
            
            this.dispose();
        });
        /**
         * Evento de entrega de pedido.
         * <p>Recupera los datos de los campos de texto, elimina espacios en blanco 
         * innecesarios y solicita la validación al {@link IPedidoBO}.</p>
         * <p>Si el método {@code entregarPedidoExpress} devuelve true, se notifica 
         * al usuario y se limpian los campos para una nueva operación.</p>
         * * @throws NegocioExcepcion Si ocurre un error técnico o de validación en la capa de datos.
         */
        btnEntregar.addActionListener(e -> {
            try {
                
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
