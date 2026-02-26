/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel contenedor que integra una {@link JTable} preconfigurada con un diseño
 * personalizado.
 * <p>
 * Proporciona una estructura base con scroll, un esquema de colores café/tierra
 * y métodos simplificados para la gestión de filas. Por defecto, las celdas de
 * esta tabla no son editables.</p>
 *
 * * @author RAMSES
 * @version 1.0
 */
public class TablaSimplePanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    /**
     * Construye un nuevo panel de tabla con las columnas especificadas.
     * <p>
     * Configura el esquema de colores, bordes, fuentes y el comportamiento de
     * no-edición de las celdas.</p>
     *
     * * @param columnas Array de Strings que representan los nombres de las
     * cabeceras.
     */
    public TablaSimplePanel(String[] columnas) {
        setLayout(new BorderLayout());
        setBackground(new Color(139, 69, 19));
        setBorder(BorderFactory.createLineBorder(new Color(190, 150, 90), 3));

        modelo = new DefaultTableModel(columnas, 0) {
            /**
             * Determina si una celda es editable.
             *
             * @return {@code false} siempre, para mantener la tabla de solo
             * lectura por defecto.
             */
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

    /**
     * Agrega una nueva fila de datos a la tabla de forma dinámica.
     *
     * * @param datos Argumentos variables que representan las celdas de la
     * nueva fila. Deben coincidir en orden y cantidad con las columnas
     * definidas.
     */
    public void agregarFila(Object... datos) {
        modelo.addRow(datos);
    }

    /**
     * Elimina todos los datos actuales de la tabla, dejando solo las cabeceras.
     */
    public void limpiar() {
        modelo.setRowCount(0);
    }

    /**
     * Proporciona acceso a la instancia interna de la tabla.
     * <p>
     * Útil para realizar configuraciones adicionales como asignación de
     * Renderers o Editors específicos.</p>
     *
     * * @return La instancia de {@link JTable} del panel.
     */
    public JTable getTabla() {
        return tabla;
    }
}
