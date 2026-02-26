/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Componente visual personalizado que representa una tarjeta de producto en la interfaz.
 * Utiliza un diseño de tres secciones (Nombre, Descripción/Estado y Precio/Selección) 
 * con un fondo café redondeado mediante renderizado 2D.
 * * @author RAMSES
 * @version 1.0
 */
public class PanelProducto extends JPanel {
    
    /** Nombre del producto que identifica la tarjeta. */
    private String nombre;
    /** Botón de selección radial para marcar este producto en una lista. */
    private JRadioButton rbSeleccion;
    /**
     * Constructor que inicializa la tarjeta con toda la información del producto.
     * * @param nombre      Nombre del producto (soporta saltos de línea automáticos).
     * @param estado      Disponibilidad actual (ej. "Disponible", "Agotado").
     * @param descripcion Detalle breve de las características del producto.
     * @param precio      Precio formateado como texto (ej. "$15.00").
     * @param grupo       El {@link ButtonGroup} al que pertenecerá el botón de selección para exclusividad.
     */
    public PanelProducto(String nombre, String estado, String descripcion, String precio, ButtonGroup grupo) {
        this.nombre = nombre;
        
        setOpaque(false); // Necesario para que se vean las esquinas redondeadas
        setLayout(new BorderLayout(15, 0));
        setBorder(new EmptyBorder(15, 20, 15, 20)); // Padding interno
        
        // Limitar la altura para que se vean como rectángulos uniformes
        setMaximumSize(new Dimension(600, 120)); 

        Color colorTexto = new Color(245, 222, 179); // Color crema/blanco para el texto

        // --- SECCIÓN IZQUIERDA: Nombre ---
        JLabel lblNombre = new JLabel("<html><center>" + nombre.replace(" ", "<br>") + "</center></html>");
        lblNombre.setFont(new Font("Serif", Font.BOLD, 11));
        lblNombre.setForeground(colorTexto);
        lblNombre.setPreferredSize(new Dimension(140, 0)); // Ancho fijo para alinear
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        
        // --- SECCIÓN CENTRAL: Estado y Descripción ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);

        JLabel lblEstado = new JLabel("Estado: " + estado);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstado.setForeground(colorTexto);

        // Usamos JTextArea para que el texto baje de línea automáticamente
        JTextArea txtDescripcion = new JTextArea(descripcion);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtDescripcion.setForeground(colorTexto);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFocusable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        panelCentro.add(lblEstado);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(txtDescripcion);

        // --- SECCIÓN DERECHA: Precio y Radio Button ---
        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
        panelDerecha.setOpaque(false);
        panelDerecha.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel lblPrecio = new JLabel(precio);
        lblPrecio.setFont(new Font("Serif", Font.BOLD, 22));
        lblPrecio.setForeground(colorTexto);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        rbSeleccion = new JRadioButton();
        rbSeleccion.setOpaque(false);
        rbSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Si quieres que el cursor cambie a "manita" al pasar sobre el botón
        rbSeleccion.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

        // Agregamos el botón al grupo global que recibimos por parámetro
        if (grupo != null) {
            grupo.add(rbSeleccion);
        }

        panelDerecha.add(Box.createVerticalGlue()); // Empuja hacia el centro vertical
        panelDerecha.add(lblPrecio);
        panelDerecha.add(Box.createVerticalStrut(5));
        panelDerecha.add(rbSeleccion);
        panelDerecha.add(Box.createVerticalGlue());

        // --- ENSAMBLADO FINAL ---
        add(lblNombre, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
        add(panelDerecha, BorderLayout.EAST);
    }

    /**
     * Sobrescribe el dibujo del componente para aplicar el fondo redondeado.
     * Utiliza {@link Graphics2D} con suavizado (Antialiasing) para mejorar el acabado visual.
     * * @param g El contexto gráfico para pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Antialiasing para que los bordes redondeados se vean suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Color café del fondo
        g2.setColor(new Color(140, 75, 30)); 
        // Dibujamos un rectángulo redondeado (radio de 25px)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        
        g2.dispose();
        super.paintComponent(g);
    }

    /**
     * Verifica si el producto representado en este panel ha sido seleccionado.
     * * @return {@code true} si el radio button está marcado; {@code false} en caso contrario.
     */
    public boolean isSeleccionado() {
        return rbSeleccion.isSelected();
    }
    /**
     * Obtiene el nombre del producto asociado a esta tarjeta.
     * * @return Cadena de texto con el nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }
}