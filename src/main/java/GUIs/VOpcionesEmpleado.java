/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author RAMSES
 */
public class VOpcionesEmpleado extends JFrame {
    
    // Declaración de los nuevos botones
    private RoundedButton btn_agregar_producto;
    private RoundedButton btn_agregar_cupon;
    private RoundedButton btn_cambiar_estado;
    
    // Declaración de las etiquetas
    private LabelPersonalizado lbl_usuario;
    private LabelPersonalizado lbl_nombre_usuario; 

    public VOpcionesEmpleado() {
        setTitle("Opciones de Usuario");
        setSize(700, 450); // Ajusté el tamaño para que tenga esa forma rectangular y horizontal
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Color de fondo (café claro)
        getContentPane().setBackground(Color.decode("#c4a484"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        /**
         * ==========================================
         * 1. SECCIÓN SUPERIOR (HEADER)
         * ==========================================
         */
        
        // --- Etiqueta "Usuario: " ---
        lbl_usuario = new LabelPersonalizado("Usuario: ", Color.white);
        lbl_usuario.setFont(new Font("SansSerif", Font.BOLD, 36)); 
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.NORTHWEST; // Pegado arriba a la izquierda
        gbc.insets = new Insets(20, 30, 0, 5); // Márgenes (arriba, izquierda, abajo, derecha)
        gbc.weightx = 0.0;
        add(lbl_usuario, gbc);

        // --- Etiqueta del Nombre ("Josmara") ---
        lbl_nombre_usuario = new LabelPersonalizado("Josmara", Color.decode("#6e4a2e")); // Color café oscuro
        lbl_nombre_usuario.setFont(new Font("Monotype Corsiva", Font.ITALIC, 40)); // Fuente estilo cursiva
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.weightx = 1.0; // Le damos peso para que empuje el logo hacia la derecha
        add(lbl_nombre_usuario, gbc);

        // --- Logo Nekutik ---
        JLabel lbl_logo = new JLabel();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2; // Hacemos que ocupe el espacio de dos filas hacia abajo
        gbc.anchor = GridBagConstraints.NORTHEAST; // Pegado arriba a la derecha
        gbc.insets = new Insets(10, 0, 0, 30);
        gbc.weightx = 0.0;
        
        URL url = getClass().getResource("/img/icon.png");
        if (url == null) {
            System.err.println("ERROR: No se encontró /img/icon.png");
            lbl_logo.setText("[ LOGO ]");
            lbl_logo.setForeground(Color.WHITE);
        } else {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));
        }
        add(lbl_logo, gbc);

        /**
         * ==========================================
         * 2. SECCIÓN CENTRAL (BOTONES)
         * ==========================================
         */
        
        // Restauramos configuraciones del GridBag para los botones
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.gridx = 0; 
        gbc.gridwidth = 3; // Le decimos que ocupe las 3 columnas para que se centre perfectamente
        gbc.anchor = GridBagConstraints.CENTER; // Centrado absoluto
        
        Dimension tamanoBotones = new Dimension(240, 50);

        // --- Botón 1: Agregar Producto ---
        btn_agregar_producto = new RoundedButton("Agregar Producto");
        btn_agregar_producto.setPreferredSize(tamanoBotones);
        gbc.gridy = 1; 
        gbc.insets = new Insets(30, 0, 15, 0); // Margen superior grande para separarlo del encabezado
        add(btn_agregar_producto, gbc);

        // --- Botón 2: Agregar cupón ---
        btn_agregar_cupon = new RoundedButton("Agregar cupon");
        btn_agregar_cupon.setPreferredSize(tamanoBotones);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 15, 0); // Solo margen inferior
        add(btn_agregar_cupon, gbc);

        // --- Botón 3: Cambiar estado productos ---
        // Usamos etiquetas HTML de Java Swing para dividir el texto en dos líneas y centrarlo
        btn_cambiar_estado = new RoundedButton("<html><center>Cambiar estado<br>productos</center></html>");
        btn_cambiar_estado.setPreferredSize(tamanoBotones);
        gbc.gridy = 3;
        gbc.weighty = 1.0; // Empujamos todo hacia arriba para que los botones no se vayan hasta el fondo
        gbc.anchor = GridBagConstraints.NORTH;
        add(btn_cambiar_estado, gbc);
    }

}
