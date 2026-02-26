/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *
 * @author josma
 */
/**
 * Clase que gestiona el dibujo y la edición de RadioButtons dentro de una
 * JTable.
 */
public class RadioCellHelper implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JPanel) {
            JPanel p = (JPanel) value;
            p.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return p;
        }
        return new JLabel("");
    }

    public static class RadioCellEditor extends DefaultCellEditor {
        private JPanel panel;
        public RadioCellEditor() { super(new JCheckBox()); }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.panel = (JPanel) value;
            return panel;
        }
        @Override
        public Object getCellEditorValue() { return panel; }
    }
}
