package GUIs;

import Componentes.*;
import Negocio.BOs.ProductoBO;
import Negocio.DTOs.ProductoEstadoDTO;
import ClasesEnum.EstadoProducto;
import java.awt.*;
import java.net.URL;
import java.util.List;
import javax.swing.*;

/**
 * Interfaz administrativa para la actualización masiva de estados de productos.
 * <p>
 * Permite buscar productos por nombre y modificar su disponibilidad
 * (Disponible/Agotado) directamente desde una tabla dinámica. Utiliza una
 * arquitectura de celdas personalizadas para integrar {@link JRadioButton}
 * dentro del {@link JTable}.</p>
 *
 * * @author Josmara
 * @version 1.0
 */
public class VCambiarEstadoProducto extends JFrame {

    private PlaceholderTextField txtBusqueda;
    private TablaEstadoProductoPanel tablaProductos; // tabla especializada
    private ProductoBO productoBO;
    private RoundedButton btnSalir;
    private RoundedButton btnCambiarEstado;
    private RoundedButton btnBuscar;
    private JFrame ventanaPadre;

    /**
     * Construye la interfaz de cambio de estado.
     * <p>
     * Configura la tabla con {@link RadioCellHelper} y su correspondiente
     * editor para permitir que los botones de la tabla respondan a los clics
     * del usuario.</p>
     *
     * * @param productoBO Instancia de la lógica de negocio.
     * @param ventanaPadre Ventana de origen para gestionar la visibilidad al
     * cerrar esta ventana.
     */
    public VCambiarEstadoProducto(ProductoBO productoBO, JFrame ventanaPadre) {
        this.productoBO = productoBO;
        this.ventanaPadre = ventanaPadre;
        setTitle("Cambiar Estado de Productos");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#c4a484"));

        Color colorTextoOscuro = new Color(110, 74, 46);

        /**
         * 1. ZONA NORTE: ENCABEZADO Y LOGO
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
         * 2. ZONA CENTRO: FORMULARIO Y TABLA
         */
        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weightx = 1.0;

        LabelPersonalizado lblNombreProd = new LabelPersonalizado("Nombre del producto para buscar", 14, colorTextoOscuro);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        pnlCentro.add(lblNombreProd, gbc);

        txtBusqueda = new PlaceholderTextField("");
        txtBusqueda.setPreferredSize(new Dimension(300, 35));
        txtBusqueda.setMinimumSize(new Dimension(300, 35));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        pnlCentro.add(txtBusqueda, gbc);

        // Tabla especializada con 4ta columna para radios
        String[] columnas = {"Id producto", "Producto", "Estado Actual", "Nuevo Estado"};
        tablaProductos = new TablaEstadoProductoPanel(columnas);

        tablaProductos.configurarColumnasEspeciales(new RadioCellHelper(), new RadioCellHelper.RadioCellEditor());

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 40, 20, 40);
        pnlCentro.add(tablaProductos, gbc);

        add(pnlCentro, BorderLayout.CENTER);

        /**
         * 3. ZONA SUR: BOTONES
         */
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        pnlSur.setOpaque(false);

        Dimension tamanoBotones = new Dimension(160, 40);

        btnSalir = new RoundedButton("Salir");
        btnSalir.setPreferredSize(tamanoBotones);

        btnBuscar = new RoundedButton("Buscar");
        btnBuscar.setPreferredSize(tamanoBotones);

        btnCambiarEstado = new RoundedButton("Actualizar");
        btnCambiarEstado.setPreferredSize(tamanoBotones);

        pnlSur.add(btnSalir);
        pnlSur.add(btnBuscar);
        pnlSur.add(btnCambiarEstado);

        add(pnlSur, BorderLayout.SOUTH);

        // --- ASIGNACIÓN DE EVENTOS ---
        btnBuscar.addActionListener(e -> accionBuscar());

        btnCambiarEstado.addActionListener(e -> accionActualizar());

        btnSalir.addActionListener(e -> {
            if (this.ventanaPadre != null) {
                this.ventanaPadre.setVisible(true); // Mostramos la de opciones otra vez
            }
            this.dispose(); // Cerramos la actual de estados
        });

        SwingUtilities.invokeLater(() -> btnSalir.requestFocusInWindow());
    }

    /**
     * Realiza una consulta a la base de datos basada en el filtro de búsqueda.
     * <p>
     * Por cada resultado, se instancia un {@link PanelBotones} que sincroniza
     * el estado visual inicial con el valor actual en la base de datos.</p>
     */
    private void accionBuscar() {
        String filtro = txtBusqueda.getText().trim();
        if (filtro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre de un producto.");
            return;
        }

        try {
            List<ProductoEstadoDTO> lista = productoBO.buscarProductosPorNombre(filtro);
            tablaProductos.limpiar();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre.");
                return;
            }

            for (ProductoEstadoDTO p : lista) {
                PanelBotones pb = new PanelBotones();

                // Marcamos el radio inicial basado en el Enum que viene de la BD
                if (p.getEstado_producto() == EstadoProducto.DISPONIBLE) {
                    pb.rbDisponible.setSelected(true);
                } else {
                    pb.rbAgotado.setSelected(true);
                }

                tablaProductos.agregarFila(
                        p.getId_producto(),
                        p.getNombre_producto(),
                        p.getEstado_producto(),
                        pb
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }
    }

    /**
     * Recorre todas las filas de la tabla y extrae el estado seleccionado en el
     * {@link PanelBotones}.
     * <p>
     * Ejecuta actualizaciones individuales en la capa de negocio para cada
     * producto presente en la vista actual.</p>
     */
    private void accionActualizar() {
        JTable t = tablaProductos.getTabla();
        if (t.getRowCount() == 0) {
            return;
        }

        try {
            for (int i = 0; i < t.getRowCount(); i++) {
                int id = (int) t.getValueAt(i, 0);
                PanelBotones pb = (PanelBotones) t.getValueAt(i, 3);

                EstadoProducto nuevoEstado = pb.rbDisponible.isSelected()
                        ? EstadoProducto.DISPONIBLE
                        : EstadoProducto.AGOTADO;

                productoBO.actualizarEstadoProducto(id, nuevoEstado);
            }
            JOptionPane.showMessageDialog(this, "¡Estados actualizados con éxito!");
            accionBuscar(); // Refrescar para ver cambios en la columna de "Estado Actual"
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    /**
     * Clase interna que actúa como componente contenedor dentro de las celdas
     * de la tabla.
     * <p>
     * Proporciona un grupo de botones exclusivo por fila para alternar entre
     * los estados de {@link EstadoProducto}.</p>
     */

    class PanelBotones extends JPanel {

        public JRadioButton rbDisponible = new JRadioButton("Disponible");
        public JRadioButton rbAgotado = new JRadioButton("Agotado");
        public ButtonGroup grupo = new ButtonGroup();

        /**
         * Inicializa el panel con diseño horizontal y transparencia para
         * coincidir con la estética de la tabla.
         */
        public PanelBotones() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            setOpaque(true);
            rbDisponible.setOpaque(false);
            rbAgotado.setOpaque(false);
            grupo.add(rbDisponible);
            grupo.add(rbAgotado);
            add(rbDisponible);
            add(rbAgotado);
        }
    }
}
