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

    private JPanel panel_lista;
    private List<ProductoDTO> listaTodosLosProductos;
    private static ClienteDTO cliente;
    private IProductoBO productoBO;
    private FabricaBOs fabricaBO;
    
    private LabelPersonalizado lbl_subtotal;
    private PlaceholderTextField txt_cantidad;
    private double subtotal_total = 0.0;
    
    // Aquí guardaremos el producto que el usuario haya seleccionado en la lista

    // Esta es tu lista de carrito
    private List<ItemCarrito> carrito = new ArrayList<>();
    
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
        panel_lista = new JPanel();
        panel_lista.setLayout(new BoxLayout(panel_lista, BoxLayout.Y_AXIS));
        panel_lista.setOpaque(false);
        // Redujimos un poco los márgenes (antes 20)
        panel_lista.setBorder(new EmptyBorder(10, 20, 10, 10));

        JScrollPane scroll_pane = new JScrollPane(panel_lista);
        scroll_pane.setOpaque(false);
        scroll_pane.getViewport().setOpaque(false);
        scroll_pane.setBorder(BorderFactory.createEmptyBorder());
        scroll_pane.getVerticalScrollBar().setUnitIncrement(16);
       
        //ESTE METODO SE USA PARA ACTUALIZAR LA LISTA QUE APARECE EN LA INTERFAZ
        actualizarListaProductos("");

        // =================================================================
        // 2. PANEL DERECHO: CONTROLES, FORMULARIO Y BOTONES
        // =================================================================
        JPanel panel_derecho = new JPanel();
        panel_derecho.setLayout(new BoxLayout(panel_derecho, BoxLayout.Y_AXIS));
        panel_derecho.setOpaque(false);
        // 3. Panel lateral más delgado (antes 320, ahora 260)
        panel_derecho.setPreferredSize(new Dimension(260, 0));
        panel_derecho.setBorder(new EmptyBorder(10, 10, 10, 20));

        // --- LOGO ---
        JLabel lbl_logo = new JLabel();
        lbl_logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        URL logo_URL = getClass().getResource("/img/icon.png"); 
        if (logo_URL != null) {
            ImageIcon icon = new ImageIcon(logo_URL);
            // 4. Logo más pequeño (antes 180, ahora 130)
            Image img = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));
        } else {
            lbl_logo.setText("[ LOGO AQUI ]"); 
            lbl_logo.setForeground(Color.WHITE);
        }

        // --- CAMPOS DE TEXTO ---
        PlaceholderTextField txt_nombre = new PlaceholderTextField("");
        // 5. Cajas de texto más compactas (antes 250x40, ahora 200x35)
        txt_nombre.setMaximumSize(new Dimension(200, 35));
        txt_nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txt_nombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
            private void filtrar() { actualizarListaProductos(txt_nombre.getText()); }
        });
        
        // 6. Textos descriptivos un poco más pequeños (antes 16, ahora 14)
        LabelPersonalizado lbl_nombre_desc = new LabelPersonalizado("Nombre del producto", 14, new Color(120, 95, 80));
        lbl_nombre_desc.setAlignmentX(Component.CENTER_ALIGNMENT);

       txt_cantidad = new PlaceholderTextField("");
        txt_cantidad.setMaximumSize(new Dimension(200, 35));
        txt_cantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_cantidad.setHorizontalAlignment(JTextField.CENTER); 

        LabelPersonalizado lbl_cantidad_desc = new LabelPersonalizado("Cantidad", 14, new Color(120, 95, 80));
        lbl_cantidad_desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SUBTOTAL ---
        lbl_subtotal = new LabelPersonalizado("Subtotal: $00.00", 16, new Color(120, 95, 80));
        lbl_subtotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BOTONES ---
        // 7. Botones más pequeños (antes 220x45, ahora 190x38)
        Dimension sizeBotones = new Dimension(200, 39);

        RoundedButton btn_agregar = new RoundedButton("Agregar producto");
        btn_agregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_agregar.setMaximumSize(sizeBotones);
        
        RoundedButton btn_eliminar = new RoundedButton("Eliminar producto");
        btn_eliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_eliminar.setMaximumSize(sizeBotones);

        RoundedButton btn_realizar = new RoundedButton("Realizar pedido");
        btn_realizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_realizar.setMaximumSize(sizeBotones);

        RoundedButton btn_cancelar = new RoundedButton("Cancelar");
        btn_cancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelar.setMaximumSize(sizeBotones);

        // --- ENSAMBLADO DEL PANEL DERECHO ---
        // 8. Espaciados (VerticalStrut) reducidos para que todo quede más junto
        panel_derecho.add(lbl_logo);
        panel_derecho.add(Box.createVerticalStrut(15)); 
        
        panel_derecho.add(txt_nombre);
        panel_derecho.add(Box.createVerticalStrut(2));
        panel_derecho.add(lbl_nombre_desc);
        panel_derecho.add(Box.createVerticalStrut(10));
        
        panel_derecho.add(txt_cantidad);
        panel_derecho.add(Box.createVerticalStrut(2));
        panel_derecho.add(lbl_cantidad_desc);
        panel_derecho.add(Box.createVerticalStrut(15));
        
        panel_derecho.add(lbl_subtotal);
        panel_derecho.add(Box.createVerticalStrut(15));
        
        panel_derecho.add(btn_agregar);
        panel_derecho.add(Box.createVerticalStrut(10));
        panel_derecho.add(btn_eliminar);
        panel_derecho.add(Box.createVerticalStrut(10));
        panel_derecho.add(btn_realizar);
        panel_derecho.add(Box.createVerticalStrut(10));
        panel_derecho.add(btn_cancelar);

        // =================================================================
        // 3. ENSAMBLADO FINAL EN LA VENTANA
        // =================================================================
        add(scroll_pane, BorderLayout.CENTER);   
        add(panel_derecho, BorderLayout.EAST);   
        
        
        btn_realizar.addActionListener(e -> {
            
            if (carrito.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El carrito esta vacio no es posible esta opcion.");
                return;
            }
            
            VResumenPedido ventana = new VResumenPedido(cliente, carrito);
            ventana.setVisible(true);
            this.dispose();
            
        });
        
        //Metodo para eliminar el ultimo producto
        btn_eliminar.addActionListener(e -> {
            //Verificamos que la lista no este vacia
            if (carrito.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El carrito esta vacio.");
                return;
            }
            //Obtenemos la cantidad de elementos que halla en la lista
            int posi = carrito.size();
            //Quitamos el ultimo producto agregado
            ItemCarrito item = carrito.remove(posi-1);
            subtotal_total -= item.getSubtotal();
            lbl_subtotal.setText(String.format("Subtotal: $%.2f", subtotal_total));
            JOptionPane.showMessageDialog(null, "Se elimino el producto "+item.getProducto().getNombre());
            
        });
        
        
        //Metodo para cuando el boton de agregar producto se presione
        btn_agregar.addActionListener(e -> {
            try {
                ProductoDTO producto_seleccionado = null;

                // Buscamos cuál panel está seleccionado actualmente en la pantalla
                // Recorremos todos los componentes dentro del panelLista
                for (Component comp : panel_lista.getComponents()) {
                    if (comp instanceof PanelProducto) {
                        PanelProducto panel = (PanelProducto) comp;
                        if (panel.isSeleccionado()) {
                            // Si este panel está seleccionado, buscamos el DTO correspondiente en nuestra lista global
                            for (ProductoDTO p : listaTodosLosProductos) {
                                if (p.getNombre().equals(panel.getNombre())) {
                                    producto_seleccionado = p;
                                    break; // Ya lo encontramos, salimos del ciclo interno
                                }
                            }
                            break; // Ya encontramos el panel seleccionado, salimos del ciclo principal
                        }
                    }
                }

                // 2. Validar que sí hayan seleccionado algo
                if (producto_seleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto de la lista.");
                    return;
                }

                // 3. Validar la cantidad
                String texto_cantidad = txt_cantidad.getText().trim();
                if (texto_cantidad.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa una cantidad.");
                    return;
                }

                int cantidad = Integer.parseInt(texto_cantidad);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero.");
                    return;
                }

                // 4. Calcular el subtotal
                double precio = producto_seleccionado.getPrecio();
                double subtotal_articulo = precio * cantidad;
                
                boolean bandera = true;
                int cont = 0;
                for (ItemCarrito item_carrito : carrito) {
                    bandera = false;
                    //Si ya existe un producto con el mismo nombre en el carrito entonces se suma la cantidad y el subtotal 
                    if (item_carrito.getProducto().getNombre().equals(producto_seleccionado.getNombre())) {
                        carrito.get(cont).setCantidad(carrito.get(cont).getCantidad() + cantidad);
                        carrito.get(cont).setSubtotal( carrito.get(cont).getSubtotal() + subtotal_articulo);
                    }else{
                        //Si no se agrega como nuevo
                        carrito.add(new ItemCarrito(producto_seleccionado, cantidad, subtotal_articulo));
                    }
                }
                
                //Si no hay elementos se agrega 
                if (bandera) {
                    carrito.add(new ItemCarrito(producto_seleccionado, cantidad, subtotal_articulo));
                }

                // 6. Actualizar totales y visuales
                subtotal_total += subtotal_articulo;
                lbl_subtotal.setText(String.format("Subtotal: $%.2f", subtotal_total));

                // 7. Limpiar para el siguiente
                txt_cantidad.setText("");
                
                // Opcional: Deseleccionar el RadioButton actual limpiando el ButtonGroup
                // Para hacer esto tendrías que declarar el ButtonGroup como global en VTomarPedido
                
                JOptionPane.showMessageDialog(this, "Producto agregado: " + cantidad + "x " + producto_seleccionado.getNombre());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar el producto: " + ex.getMessage());
            }
        });
    }

    private void actualizarListaProductos(String textoBusqueda) {
        panel_lista.removeAll();
        String busqueda = textoBusqueda.toLowerCase();
        ButtonGroup grupoSeleccion = new ButtonGroup();
        for (ProductoDTO p : listaTodosLosProductos) {
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                
                PanelProducto panel_producto = new PanelProducto(p.getNombre(), p.getEstado(), p.getDescripcion(), ""+p.getPrecio(), grupoSeleccion);

                panel_lista.add(panel_producto);
                // Reduje la separación entre los cuadros café a 10px
                panel_lista.add(Box.createVerticalStrut(10)); 
            }
        }
        panel_lista.revalidate();
        panel_lista.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VTomarPedido(cliente).setVisible(true);
        });
    }
    
}