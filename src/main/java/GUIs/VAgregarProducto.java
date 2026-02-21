/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author RAMSES
 */
public class VAgregarProducto extends JFrame {

    private PlaceholderTextField txtNombre;
    private PlaceholderTextField txtPrecio;
    private JTextArea txtDescripcion;
    private JTextArea txtIngredientes;

    private JRadioButton rbDulce, rbSalado, rbIntegral;
    private JRadioButton rbDisponible, rbNoDisponible;

    private RoundedButton btnCancelar;
    private RoundedButton btnAgregar;

    public VAgregarProducto() {
        setTitle("Agregar Producto");
        setSize(780, 540); // Reduje un poco la altura al quitar etiquetas
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().setBackground(Color.decode("#c4a484"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Color colorTextoOscuro = new Color(110, 74, 46); 
        Font fuenteRadio = new Font("SansSerif", Font.PLAIN, 14); 

        /**
         * ==========================================
         * 1. ENCABEZADO: TÍTULO Y LOGO
         * ==========================================
         */
        LabelPersonalizado lblTitulo = new LabelPersonalizado("Agregar producto", 34, Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34)); 
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.insets = new Insets(15, 20, 20, 10);
        gbc.weightx = 1.0; 
        add(lblTitulo, gbc);

        JLabel lblLogo = new JLabel();
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("[ LOGO ]");
        }
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.insets = new Insets(10, 10, 20, 30); 
        add(lblLogo, gbc);

        /**
         * ==========================================
         * 2. FILA 1: INPUTS CON PLACEHOLDER (NOMBRE Y PRECIO)
         * ==========================================
         */
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 20, 15, 20); 
        
        // Pasamos el texto directamente al PlaceholderTextField
        txtNombre = new PlaceholderTextField("Nombre");
        txtNombre.setPreferredSize(new Dimension(150, 35)); 
        gbc.gridx = 0; gbc.gridy = 1;
        add(txtNombre, gbc);

        txtPrecio = new PlaceholderTextField("Precio");
        txtPrecio.setPreferredSize(new Dimension(150, 35));
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtPrecio, gbc);

        /**
         * ==========================================
         * 3. FILA 2: ETIQUETA TIPO Y TEXTAREAS
         * ==========================================
         */
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 20, 2, 10);
        
        LabelPersonalizado lblTipo = new LabelPersonalizado("Tipo", 14, colorTextoOscuro);
        gbc.gridx = 0; gbc.gridy = 2; 
        add(lblTipo, gbc);

        // --- TEXTAREA DESCRIPCIÓN ---
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 4; // Abarca las filas de Tipo y Estado
        
        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true); txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        configurarPlaceholderTextArea(txtDescripcion, "Descripción"); // Método al final de la clase
        
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(null); 
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 15, 20);
        add(scrollDesc, gbc);

        // --- TEXTAREA INGREDIENTES ---
        txtIngredientes = new JTextArea();
        txtIngredientes.setLineWrap(true); txtIngredientes.setWrapStyleWord(true);
        txtIngredientes.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        configurarPlaceholderTextArea(txtIngredientes, "Ingredientes");
        
        JScrollPane scrollIngred = new JScrollPane(txtIngredientes);
        scrollIngred.setBorder(null);
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 15, 30);
        add(scrollIngred, gbc);

        /**
         * ==========================================
         * 4. FILA 3 y 4: RADIO BUTTONS (TIPO Y ESTADO)
         * ==========================================
         */
        // --- PANEL TIPO ---
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1; 
        gbc.insets = new Insets(0, 20, 10, 20);
        JPanel pnlTipo = new JPanel(new GridLayout(3, 1, 0, 2)); 
        pnlTipo.setOpaque(false);
        pnlTipo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(colorTextoOscuro, 1, true), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10) 
        ));

        rbDulce = new JRadioButton("Dulce"); rbSalado = new JRadioButton("Salado"); rbIntegral = new JRadioButton("Integral");
        ButtonGroup bgTipo = new ButtonGroup(); bgTipo.add(rbDulce); bgTipo.add(rbSalado); bgTipo.add(rbIntegral);

        for (JRadioButton rb : new JRadioButton[]{rbDulce, rbSalado, rbIntegral}) {
            rb.setOpaque(false); rb.setForeground(colorTextoOscuro); rb.setFont(fuenteRadio);
            pnlTipo.add(rb);
        }
        gbc.gridx = 0; gbc.gridy = 3;  
        add(pnlTipo, gbc);

        // --- PANEL ESTADO ---
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 20, 2, 10);
        LabelPersonalizado lblEstado = new LabelPersonalizado("Estado", 14, colorTextoOscuro);
        gbc.gridx = 0; gbc.gridy = 4; add(lblEstado, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 15, 20);
        JPanel pnlEstado = new JPanel(new GridLayout(2, 1, 0, 2));
        pnlEstado.setOpaque(false);
        pnlEstado.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(colorTextoOscuro, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        rbDisponible = new JRadioButton("Disponible"); rbNoDisponible = new JRadioButton("No disponible");
        ButtonGroup bgEstado = new ButtonGroup(); bgEstado.add(rbDisponible); bgEstado.add(rbNoDisponible);

        for (JRadioButton rb : new JRadioButton[]{rbDisponible, rbNoDisponible}) {
            rb.setOpaque(false); rb.setForeground(colorTextoOscuro); rb.setFont(fuenteRadio);
            pnlEstado.add(rb);
        }
        gbc.gridx = 0; gbc.gridy = 5;
        add(pnlEstado, gbc);

        /**
         * ==========================================
         * 5. BOTONES FINALES
         * ==========================================
         */
        gbc.fill = GridBagConstraints.NONE; 
        gbc.insets = new Insets(10, 10, 20, 10);
        Dimension tamanoBotones = new Dimension(140, 40); 

        btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setPreferredSize(tamanoBotones);
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST; 
        add(btnCancelar, gbc);

        btnAgregar = new RoundedButton("Agregar");
        btnAgregar.setPreferredSize(tamanoBotones);
        gbc.gridx = 2; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST; 
        add(btnAgregar, gbc);
        
        // Foco inicial en un botón para que los placeholders se muestren correctamente
        SwingUtilities.invokeLater(() -> btnAgregar.requestFocusInWindow());
    }

    /**
     * Método auxiliar para darle comportamiento de Placeholder a un JTextArea
     */
    private void configurarPlaceholderTextArea(JTextArea textArea, String placeholder) {
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);

        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().trim().isEmpty()) {
                    textArea.setForeground(Color.GRAY);
                    textArea.setText(placeholder);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VAgregarProducto().setVisible(true);
        });
    }
}