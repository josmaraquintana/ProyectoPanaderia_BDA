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
 * Extensión de TablaSimplePanel para permitir el uso de RadioButtons 
 * en la columna de cambio de estado.
 */
public class TablaEstadoProductoPanel extends TablaSimplePanel {

    public TablaEstadoProductoPanel(String[] columnas) {
        super(columnas);

        // 1. Creamos el modelo correctamente y lo asignamos
        DefaultTableModel nuevoModelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna 3 (Nuevo Estado) es editable para permitir clics
                return column == 3;
            }

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
     * Sobrescribimos el método agregarFila para asegurarnos de que use 
     * el modelo que acabamos de crear arriba.
     */
    @Override
    public void agregarFila(Object... datos) {
        DefaultTableModel modeloActual = (DefaultTableModel) getTabla().getModel();
        modeloActual.addRow(datos);
    }

    /**
     * Sobrescribimos limpiar para usar el modelo correcto.
     */
    @Override
    public void limpiar() {
        DefaultTableModel modeloActual = (DefaultTableModel) getTabla().getModel();
        modeloActual.setRowCount(0);
    }

    /**
     * Asigna el Renderer y el Editor a la columna de los RadioButtons.
     */
    public void configurarColumnasEspeciales(TableCellRenderer renderer, TableCellEditor editor) {
        if (getTabla().getColumnCount() > 3) {
            getTabla().getColumnModel().getColumn(3).setCellRenderer(renderer);
            getTabla().getColumnModel().getColumn(3).setCellEditor(editor);
        }
    }
}