/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.*;
import Negocio.BOs.IProductoBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.ProductoDTO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class VTomarPedido extends JFrame {

    private JPanel panelLista;
    private List<ProductoDTO> listaTodosLosProductos;
    private static ClienteDTO cliente;
    private IProductoBO productoBO;
    private FabricaBOs fabricaBO;
    
    public VTomarPedido(ClienteDTO cliente) {
        this.cliente = cliente; 
        fabricaBO = new FabricaBOs();
        productoBO = fabricaBO.obtenerProductoBO();
        listaTodosLosProductos = new ArrayList<>();
        
        try{
            listaTodosLosProductos = productoBO.obtenerListaProductos();
        }catch(NegocioExcepcion ex){
            System.out.println("Error al cargar los productos en la lista.");
        }
        
        
        
        setTitle("Catálogo de Productos");
        // 1. Reducimos el tamaño de la ventana (antes 875x650)
        setSize(805, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(205, 173, 144));
        setLayout(new BorderLayout());


        // =================================================================
        // 1. PANEL IZQUIERDO: CONTENEDOR DE LA LISTA
        // =================================================================
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setOpaque(false);
        // Redujimos un poco los márgenes (antes 20)
        panelLista.setBorder(new EmptyBorder(10, 20, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
       
        //ESTE METODO SE USA PARA ACTUALIZAR LA LISTA QUE APARECE EN LA INTERFAZ
        actualizarListaProductos("");

        // =================================================================
        // 2. PANEL DERECHO: CONTROLES, FORMULARIO Y BOTONES
        // =================================================================
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setOpaque(false);
        // 3. Panel lateral más delgado (antes 320, ahora 260)
        panelDerecho.setPreferredSize(new Dimension(260, 0));
        panelDerecho.setBorder(new EmptyBorder(10, 10, 10, 20));

        // --- LOGO ---
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        URL logoURL = getClass().getResource("/img/icon.png"); 
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            // 4. Logo más pequeño (antes 180, ahora 130)
            Image img = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("[ LOGO AQUI ]"); 
            lblLogo.setForeground(Color.WHITE);
        }

        // --- CAMPOS DE TEXTO ---
        PlaceholderTextField txtNombre = new PlaceholderTextField("");
        // 5. Cajas de texto más compactas (antes 250x40, ahora 200x35)
        txtNombre.setMaximumSize(new Dimension(200, 35));
        txtNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtNombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
            private void filtrar() { actualizarListaProductos(txtNombre.getText()); }
        });
        
        // 6. Textos descriptivos un poco más pequeños (antes 16, ahora 14)
        LabelPersonalizado lblNombreDesc = new LabelPersonalizado("Nombre del producto", 14, new Color(120, 95, 80));
        lblNombreDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        PlaceholderTextField txtCantidad = new PlaceholderTextField("");
        txtCantidad.setMaximumSize(new Dimension(200, 35));
        txtCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtCantidad.setHorizontalAlignment(JTextField.CENTER); 

        LabelPersonalizado lblCantidadDesc = new LabelPersonalizado("Cantidad", 14, new Color(120, 95, 80));
        lblCantidadDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SUBTOTAL ---
        LabelPersonalizado lblSubtotal = new LabelPersonalizado("Subtotal: $00.00", 16, new Color(120, 95, 80));
        lblSubtotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BOTONES ---
        // 7. Botones más pequeños (antes 220x45, ahora 190x38)
        Dimension sizeBotones = new Dimension(190, 38);

        RoundedButton btnAgregar = new RoundedButton("Agregar producto");
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregar.setMaximumSize(sizeBotones);

        RoundedButton btnRealizar = new RoundedButton("Realizar pedido");
        btnRealizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRealizar.setMaximumSize(sizeBotones);

        RoundedButton btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setMaximumSize(sizeBotones);

        // --- ENSAMBLADO DEL PANEL DERECHO ---
        // 8. Espaciados (VerticalStrut) reducidos para que todo quede más junto
        panelDerecho.add(lblLogo);
        panelDerecho.add(Box.createVerticalStrut(15)); 
        
        panelDerecho.add(txtNombre);
        panelDerecho.add(Box.createVerticalStrut(2));
        panelDerecho.add(lblNombreDesc);
        panelDerecho.add(Box.createVerticalStrut(10));
        
        panelDerecho.add(txtCantidad);
        panelDerecho.add(Box.createVerticalStrut(2));
        panelDerecho.add(lblCantidadDesc);
        panelDerecho.add(Box.createVerticalStrut(15));
        
        panelDerecho.add(lblSubtotal);
        panelDerecho.add(Box.createVerticalStrut(15));
        
        panelDerecho.add(btnAgregar);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(btnRealizar);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(btnCancelar);

        // =================================================================
        // 3. ENSAMBLADO FINAL EN LA VENTANA
        // =================================================================
        add(scrollPane, BorderLayout.CENTER);   
        add(panelDerecho, BorderLayout.EAST);   
        
        btnAgregar.addActionListener(e -> {
            
            try{
                
                
                
            }catch(Exception ex){
                
            }
            
        });
    }

    private void actualizarListaProductos(String textoBusqueda) {
        panelLista.removeAll();
        String busqueda = textoBusqueda.toLowerCase();
        ButtonGroup grupoSeleccion = new ButtonGroup();
        for (ProductoDTO p : listaTodosLosProductos) {
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                
                PanelProducto panel_producto = new PanelProducto(p.getNombre(), p.getEstado(), p.getDescripcion(), ""+p.getPrecio(), grupoSeleccion);

                panelLista.add(panel_producto);
                // Reduje la separación entre los cuadros café a 10px
                panelLista.add(Box.createVerticalStrut(10)); 
            }
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VTomarPedido(cliente).setVisible(true);
        });
    }
    
}