/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author RAMSES
 */
public class TablaSimplePanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    public TablaSimplePanel(String[] columnas) {
        setLayout(new BorderLayout());
        setBackground(new Color(139, 69, 19));
        setBorder(BorderFactory.createLineBorder(new Color(190, 150, 90), 3));

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
        tabla.setBackground(new Color(205, 173, 144));
        tabla.setSelectionBackground(new Color(190, 150, 90));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(420, 140));

        add(scroll, BorderLayout.CENTER);
    }

    // Método reutilizable
    public void agregarFila(Object... datos) {
        modelo.addRow(datos);
    }

    public JTable getTabla() {
        return tabla;
    }
}
