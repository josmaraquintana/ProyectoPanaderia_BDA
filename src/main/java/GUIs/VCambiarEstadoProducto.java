/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Componentes.TablaSimplePanel;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author RAMSES
 */
public class VCambiarEstadoProducto extends JFrame {

    private PlaceholderTextField txtIdProducto;
    private TablaSimplePanel tablaProductos;

    private RoundedButton btnSalir;
    private RoundedButton btnCambiarEstado;
    
    // private List<>

    public VCambiarEstadoProducto() {
        setTitle("Cambiar Estado de Productos");
        setSize(700, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Usamos BorderLayout como esquema principal para evitar que los elementos colapsen
        setLayout(new BorderLayout()); 
        getContentPane().setBackground(Color.decode("#c4a484"));

        Color colorTextoOscuro = new Color(110, 74, 46);

        /**
         * ==========================================
         * 1. ZONA NORTE: ENCABEZADO Y LOGO
         * ==========================================
         */
        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.setOpaque(false);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        LabelPersonalizado lblTitulo = new LabelPersonalizado("Cambiar estado de productos", 34, Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34)); 
        pnlNorte.add(lblTitulo, BorderLayout.WEST);

        JLabel lblLogo = new JLabel();
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("[ LOGO ]");
        }
        pnlNorte.add(lblLogo, BorderLayout.EAST);
        
        add(pnlNorte, BorderLayout.NORTH);

        /**
         * ==========================================
         * 2. ZONA CENTRO: FORMULARIO Y TABLA
         * ==========================================
         */
        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // CORRECCIÓN: Le damos peso horizontal para que la columna se expanda y no se apriete
        gbc.gridx = 0; 
        gbc.weightx = 1.0; 

        // Etiqueta Id de producto
        LabelPersonalizado lblIdProducto = new LabelPersonalizado("Id de producto", 14, colorTextoOscuro);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Solo ocupa el espacio necesario
        gbc.insets = new Insets(0, 0, 5, 0); 
        pnlCentro.add(lblIdProducto, gbc);

        // Input Id de producto
        txtIdProducto = new PlaceholderTextField("");
        txtIdProducto.setPreferredSize(new Dimension(250, 35));
        // CORRECCIÓN: Forzamos el tamaño mínimo para evitar que se haga un pequeño círculo
        txtIdProducto.setMinimumSize(new Dimension(250, 35)); 
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        pnlCentro.add(txtIdProducto, gbc);

        // Etiqueta Productos
        LabelPersonalizado lblProductos = new LabelPersonalizado("Productos", 14, colorTextoOscuro);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        pnlCentro.add(lblProductos, gbc);

        // Tabla Simple Panel
        String[] columnas = {"Id producto", "Producto", "Estado"};
        tablaProductos = new TablaSimplePanel(columnas);
        
        
        
        // Forzamos a que el JScrollPane muestre SIEMPRE la barra vertical
        Component[] componentes = tablaProductos.getComponents();
        for (Component c : componentes) {
            if (c instanceof JScrollPane) {
                ((JScrollPane) c).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        gbc.gridy = 3;
        gbc.weighty = 1.0; // Permite que la tabla se expanda a lo alto
        gbc.fill = GridBagConstraints.BOTH; // Expande la tabla a lo ancho y a lo alto
        gbc.insets = new Insets(0, 40, 0, 40); // Márgenes a los lados de la tabla
        pnlCentro.add(tablaProductos, gbc);

        add(pnlCentro, BorderLayout.CENTER);

        /**
         * ==========================================
         * 3. ZONA SUR: BOTONES
         * ==========================================
         */
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlSur.setOpaque(false);

        Dimension tamanoBotones = new Dimension(160, 40); 

        btnSalir = new RoundedButton("Salir");
        btnSalir.setPreferredSize(tamanoBotones);
        pnlSur.add(btnSalir);

        btnCambiarEstado = new RoundedButton("Cambiar estado");
        btnCambiarEstado.setPreferredSize(tamanoBotones);
        pnlSur.add(btnCambiarEstado);

        add(pnlSur, BorderLayout.SOUTH);
        
        // Quitar el foco inicial
        SwingUtilities.invokeLater(() -> btnSalir.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VCambiarEstadoProducto().setVisible(true);
        });
    }
}