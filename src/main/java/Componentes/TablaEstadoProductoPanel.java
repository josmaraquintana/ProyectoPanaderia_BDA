/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Panel especializado que extiende {@link TablaSimplePanel} para gestionar la visualización 
 * de productos con un selector de estado mediante RadioButtons.
 * <p>Esta clase redefine el modelo de la tabla para permitir que la columna de "Nuevo Estado" 
 * sea interactiva y soporte componentes personalizados.</p>
 * * @author Josmara
 * @version 1.0
 */
public class TablaEstadoProductoPanel extends TablaSimplePanel {
/**
     * Inicializa el panel de la tabla con un modelo de datos personalizado.
     * <p>Configura la columna índice 3 para que sea la única editable y asegura 
     * que la tabla pueda contener objetos complejos en dicha columna.</p>
     * * @param columnas Array de Strings con los nombres de las cabeceras.
     */
    public TablaEstadoProductoPanel(String[] columnas) {
        super(columnas);

        // 1. Creamos el modelo correctamente y lo asignamos
        DefaultTableModel nuevoModelo = new DefaultTableModel(columnas, 0) {
            /**
             * Define qué celdas pueden ser editadas por el usuario.
             * @param row    Índice de la fila.
             * @param column Índice de la columna.
             * @return {@code true} solo si es la columna 3 (Estado).
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna 3 (Nuevo Estado) es editable para permitir clics
                return column == 3;
            }
            /**
             * Devuelve el tipo de dato de la columna.
             * @param columnIndex Índice de la columna.
             * @return {@code Object.class} para la columna 3, permitiendo contenedores Swing.
             */
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Ayuda a la tabla a saber que en la columna 3 hay un Objeto (el Panel de botones)
                return columnIndex == 3 ? Object.class : super.getColumnClass(columnIndex);
            }
        };

        // 2. Le decimos a la JTable que use este nuevo modelo
        getTabla().setModel(nuevoModelo);

        // 3. Ajustes estéticos
        getTabla().setRowHeight(45);
        getTabla().getTableHeader().setReorderingAllowed(false);
        getTabla().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Añade una nueva fila al modelo de datos actual de la tabla.
     * * @param datos Objetos que componen la fila (e.g., ID, Nombre, Precio, Panel de Botones).
     */
    @Override
    public void agregarFila(Object... datos) {
        DefaultTableModel modeloActual = (DefaultTableModel) getTabla().getModel();
        modeloActual.addRow(datos);
    }

    /**
     * Elimina todos los registros existentes en la tabla.
     */
    @Override
    public void limpiar() {
        DefaultTableModel modeloActual = (DefaultTableModel) getTabla().getModel();
        modeloActual.setRowCount(0);
    }

    /**
     * Vincula el renderizador y el editor personalizados a la columna de estados (índice 3).
     * <p>Es esencial llamar a este método después de inicializar la tabla para que los 
     * {@link RadioCellHelper} funcionen correctamente.</p>
     * * @param renderer El componente encargado de dibujar los botones.
     * @param editor   El componente encargado de gestionar la interacción del clic.
     */
    public void configurarColumnasEspeciales(TableCellRenderer renderer, TableCellEditor editor) {
        if (getTabla().getColumnCount() > 3) {
            getTabla().getColumnModel().getColumn(3).setCellRenderer(renderer);
            getTabla().getColumnModel().getColumn(3).setCellEditor(editor);
        }
    }
}