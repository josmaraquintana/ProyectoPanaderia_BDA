/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.ItemCarrito;
import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import Negocio.BOs.IPedidoBO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;

/**
 * Ventana Final de Pedido Express
 * Muestra el Folio y PIN al cliente y los registra en la BD.
 */
public class VPedidoExpressFinal extends JFrame {

    private LabelPersonalizado lbl_folio;
    private LabelPersonalizado lbl_total;
    private LabelPersonalizado lbl_pin;
    
    private IPedidoBO pedidoBO;
    private String folio;
    private String pin;
    private double total;
    private List<ItemCarrito> carrito;

    public VPedidoExpressFinal(List<ItemCarrito> carrito, double total, JFrame ventanaAnterior) {
        this.total = total;
        this.carrito = carrito;
        
        // 1. INICIALIZACIÓN DE LÓGICA
        FabricaBOs fabrica = new FabricaBOs();
        this.pedidoBO = fabrica.obtenerPedidoBO();
        
        // Generamos los códigos antes de que la GUI los pinte
        crearFolioYPin();

        // 2. CONFIGURACIÓN DE VENTANA
        setTitle("Pedido Express Finalizado");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Color fondo = new Color(205, 173, 144);
        Color blanco = Color.WHITE;
        Color naranja = new Color(201, 104, 0);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- ZONA NORTE: TÍTULO Y LOGO ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        LabelPersonalizado titulo = new LabelPersonalizado("Pedido Express", 32, blanco);
        topPanel.add(titulo, BorderLayout.WEST);

        JLabel lblLogo = new JLabel();
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }
        topPanel.add(lblLogo, BorderLayout.EAST);

        // --- ZONA CENTRO: INFORMACIÓN Y DATOS ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Usamos HTML para que el JLabel acepte saltos de línea y negritas
        JLabel info = new JLabel("<html><div style='text-align: left; color: #C96800;'>"
                + "Guarde estos datos para la recolección de su pedido express.<br>"
                + "<b>RECUERDE: SOLO TIENE 20 MINUTOS PARA RECOGERLO.</b></div></html>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 16));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel de cuadrícula para Folio, PIN y Total
        JPanel datosPanel = new JPanel(new GridLayout(2, 2, 10, 20));
        datosPanel.setOpaque(false);
        datosPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        lbl_folio = new LabelPersonalizado("FOLIO: " + folio, 22, blanco);
        lbl_pin = new LabelPersonalizado("PIN: " + pin, 22, blanco);
        lbl_total = new LabelPersonalizado("TOTAL: $" + String.format("%.2f", total), 22, blanco);

        datosPanel.add(lbl_folio);
        datosPanel.add(lbl_total);
        datosPanel.add(lbl_pin);
        datosPanel.add(new JLabel("")); // Espacio vacío

        centerPanel.add(info);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(datosPanel);

        // --- ZONA SUR: BOTÓN ---
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setOpaque(false);

        RoundedButton btnOk = new RoundedButton("FINALIZAR");
        btnOk.setPreferredSize(new Dimension(180, 45));
        botonPanel.add(btnOk);

        // ENSAMBLE FINAL
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(botonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // --- REGISTRO EN BASE DE DATOS ---
        registrarPedidoEnBD();

        // EVENTO DEL BOTÓN
        btnOk.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        });
    }

    private void crearFolioYPin() {
        this.folio = generarAleatorio(8); // Folio de 8 dígitos
        this.pin = generarAleatorio(4);   // PIN de 4 dígitos
    }

    private String generarAleatorio(int longitud) {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(sr.nextInt(10));
        }
        return sb.toString();
    }

    private void registrarPedidoEnBD() {
        // Ejecutamos en un hilo separado para no congelar la GUI
        new Thread(() -> {
            try {
                pedidoBO.realizarRegistroExpress(carrito, total, folio, pin);
            } catch (NegocioExcepcion ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, 
                        "Error al guardar pedido: " + ex.getMessage(), 
                        "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }
}
