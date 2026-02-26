/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Clase de utilidad que gestiona el renderizado y la edición de componentes 
 * (específicamente contenedores con RadioButtons) dentro de una {@link JTable}.
 * * <p>Esta clase permite que una celda de la tabla se comporte como un contenedor 
 * interactivo, manteniendo la coherencia visual con la selección de la tabla.</p>
 * * @author josma
 * @version 1.0
 */
public class RadioCellHelper implements TableCellRenderer {
    @Override
    /**
     * Configura y devuelve el componente que se encargará de dibujar la celda.
     * * @param table      La {@code JTable} que solicita el renderizador.
     * @param value      El valor de la celda (se espera un {@link JPanel}).
     * @param isSelected Indica si la celda está seleccionada.
     * @param hasFocus   Indica si la celda tiene el foco.
     * @param row        El índice de la fila que se está renderizando.
     * @param column     El índice de la columna que se está renderizando.
     * @return Un {@code Component} (el panel configurado) o un {@code JLabel} vacío si el valor no es válido.
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JPanel) {
            JPanel p = (JPanel) value;
            p.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return p;
        }
        return new JLabel("");
    }
    /**
     * Editor personalizado para celdas que contienen paneles con botones de opción.
     * <p>Permite que los componentes dentro del panel (como RadioButtons) 
     * capturen eventos de clic mientras la celda está en modo edición.</p>
     */
    public static class RadioCellEditor extends DefaultCellEditor {
        private JPanel panel;
        /**
         * Crea una nueva instancia de {@code RadioCellEditor}.
         * Se inicializa con un {@link JCheckBox} ficticio ya que el componente 
         * real devuelto será el panel pasado por valor.
         */
        public RadioCellEditor() { super(new JCheckBox()); }
        /**
         * Prepara el panel para ser mostrado como el editor de la celda.
         * * @param table      La tabla que solicita la edición.
         * @param value      El valor actual de la celda (el {@code JPanel} a editar).
         * @param isSelected Indica si la celda debe mostrarse seleccionada.
         * @param row        Fila de la celda.
         * @param column     Columna de la celda.
         * @return El panel que servirá como interfaz de edición.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.panel = (JPanel) value;
            return panel;
        }
        /**
         * Devuelve el valor contenido en el editor.
         * * @return El {@link JPanel} con su estado actual tras la edición.
         */
        @Override
        public Object getCellEditorValue() { return panel; }
    }
}
