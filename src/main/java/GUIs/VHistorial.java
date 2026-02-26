/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import ClasesEnum.EstadoPedido;
import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Componentes.TablaSimplePanel;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.PedidoDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.PedidoDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 * Ventana de consulta de historial de pedidos para el cliente.
 * <p>Permite filtrar la información por rangos de fecha, tipo de pedido (Express o Programado) 
 * y estado actual del pedido. La clase realiza una validación estricta de formatos de fecha 
 * y estados basados en el enumerado {@link EstadoPedido}.</p>
 * * @author RAMSES
 * @version 1.0
 */
public class VHistorial extends JFrame {
    
    private PlaceholderTextField txtFechaInicio;
    private PlaceholderTextField txtFechaFin;
    private PlaceholderTextField txtEstado;
    private JRadioButton rbProgramado;
    private JRadioButton rbExpress;
    private TablaSimplePanel tablaPedidos;
    private ClienteDTO cliente;
    private PedidoBO pedidoBO;
    private TelefonoBO telefono;
    private ClienteBO clienteBO;
    private UsuarioBO usuarioBO;
    private JFrame ventanaAnterior;
    /**
     * Construye la interfaz de historial cargando las dependencias del negocio.
     * * @param pedidoBO        Lógica de negocio para la consulta de pedidos.
     * @param cliente         DTO del cliente autenticado.
     * @param telefono        BO para gestión de teléfonos (reservado para futuras expansiones).
     * @param clienteBO       BO para gestión de clientes.
     * @param usuarioBO       BO para gestión de usuarios.
     * @param ventanaAnterior Referencia para retornar al menú principal.
     */
    public VHistorial(PedidoBO pedidoBO, ClienteDTO cliente, TelefonoBO telefono, ClienteBO clienteBO, UsuarioBO usuarioBO, JFrame ventanaAnterior) {
        this.cliente = cliente;
        this.usuarioBO = usuarioBO;
        this.clienteBO = clienteBO;
        this.pedidoBO = pedidoBO;
        this.ventanaAnterior = ventanaAnterior;
        setTitle("Historial");
        setSize(700, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        getContentPane().setBackground(fondo);

        // ================= PANEL SUPERIOR =================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(20, 30, 10, 30));

        LabelPersonalizado lblTitulo
                = new LabelPersonalizado("Historial", 30, Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        URL logoURL = getClass().getResource("/img/icon.png");
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblLogo, BorderLayout.EAST);

        // ================= FILTROS =================
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setOpaque(false);
        // Reduje un poco los bordes laterales (de 90 a 50) para darles mas espacio para estirarse
        panelFiltros.setBorder(new EmptyBorder(10, 50, 10, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        // Balanceamos los margenes: 5px arriba/abajo, 15px izquierda/derecha
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtFechaInicio = new PlaceholderTextField("Inicio YYYY-MM-DD");
        txtFechaFin = new PlaceholderTextField("Fin YYYY-MM-DD");
        txtEstado = new PlaceholderTextField("Estado");

        txtFechaInicio.setPreferredSize(new Dimension(180, 40));
        txtFechaFin.setPreferredSize(new Dimension(180, 40));
        txtEstado.setPreferredSize(new Dimension(180, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFiltros.add(txtFechaInicio, gbc);

        gbc.gridx = 1;
        panelFiltros.add(txtFechaFin, gbc);

        gbc.gridx = 2;
        panelFiltros.add(txtEstado, gbc);

        // ================= RADIO BUTTONS =================
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Centramos los radios
        panelRadio.setOpaque(false);

        rbProgramado = new JRadioButton("Programado");
        rbExpress = new JRadioButton("Express");

        rbProgramado.setOpaque(false);
        rbExpress.setOpaque(false);
        rbProgramado.setForeground(Color.WHITE);
        rbExpress.setForeground(Color.WHITE);

        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbProgramado);
        grupoTipo.add(rbExpress);

        panelRadio.add(rbProgramado);
        panelRadio.add(rbExpress);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.0; // Restablecemos el peso para que los radios no hagan cosas raras
        panelFiltros.add(panelRadio, gbc);

        // ================= TABLA =================
        JPanel panelTablaContenedor = new JPanel();
        panelTablaContenedor.setOpaque(false);
        panelTablaContenedor.setLayout(new BoxLayout(panelTablaContenedor, BoxLayout.Y_AXIS));
        panelTablaContenedor.setBorder(new EmptyBorder(10, 60, 10, 60));

        LabelPersonalizado lblPedidos
                = new LabelPersonalizado("Pedidos", 18, Color.WHITE);
        lblPedidos.setAlignmentX(Component.CENTER_ALIGNMENT);

        tablaPedidos = new TablaSimplePanel(
                new String[]{"Número Pedido", "Estado", "Tipo pedido", "Fecha de registro"}
        );

        panelTablaContenedor.add(lblPedidos);
        panelTablaContenedor.add(Box.createVerticalStrut(10));
        panelTablaContenedor.add(tablaPedidos);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setOpaque(false);

        RoundedButton btnSalir = new RoundedButton("Salir");
        RoundedButton btnConsultar = new RoundedButton("Consultar");

        panelBotones.add(btnSalir);
        panelBotones.add(btnConsultar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFiltros, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panelTablaContenedor, BorderLayout.CENTER);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.SOUTH);

        setVisible(true);
        btnSalir.addActionListener(e -> {

            ventanaAnterior.setVisible(true);
            this.dispose();
        });
        /**
         * Manejador de la acción de consulta.
         * <p>Realiza las siguientes validaciones antes de invocar al BO:
         * 1. Presencia de fechas de inicio y fin.
         * 2. Selección obligatoria de tipo de pedido mediante RadioButtons.
         * 3. Mapeo del texto de estado al enum {@link EstadoPedido}.
         * 4. Verificación de integridad de la sesión del cliente.</p>
         */

        btnConsultar.addActionListener(e -> {
            try {
                //Validams que las fechas no vengan vacias o con espacios
                String fecha_inicio = txtFechaInicio.getText().trim();
                String fecha_fin = txtFechaFin.getText().trim();
                //Lanzamos una ventana con el formato correcto en caso de que ingresen mal la fecha
                if (fecha_inicio.isEmpty() || fecha_fin.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar ambas fechas (AAAA-MM-DD)");
                    return;
                }

                // Determinamos el tipo de pedido con los radiobutton
                String tipo_pedido = "";
                if (rbProgramado.isSelected()) {
                    tipo_pedido = "Programado";
                } else if (rbExpress.isSelected()) {
                    tipo_pedido = "Express";
                } else {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de pedido (Programado o Express)");
                    return;
                }

                //Validamos el enum de ls pedidos y el damos el formato para que lo acepte la base de datos
                String estado_letras = txtEstado.getText().trim().toUpperCase();
                EstadoPedido estado_pedido;
                try {
                    estado_pedido = EstadoPedido.valueOf(estado_letras);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "El estado '" + estado_letras + "' no es válido.\nEstados permitidos: PENDIENTE, LISTO, ENTREGADO, CANCELADO, DESATENDIDO", "Error de Estado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Validar el id del cliente
                /**
                 * Esto lo tuvimos que resolver(por cuestion de tiempo) haciendo una consulta de id
                 * dentro del comandosql para asginar los id a una sola variable por asi decirlo
                 */
                if (cliente == null || cliente.getId_cliente()<= 0) {
                    JOptionPane.showMessageDialog(this, "Error: No se ha cargado la información del cliente actual.", "Error de Sesión", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Formamos el bo
                List<PedidoDTO> lista = pedidoBO.consultarHistorial(
                        fecha_inicio,
                        fecha_fin,
                        cliente.getId_cliente(), //--> volvimos al id cliente porque cambiamos la consulta
                        tipo_pedido,
                        estado_pedido
                );

                //Si no se encontraron pedidos 
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No se encontraron pedidos con los filtros seleccionados.",
                            "Sin resultados",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    actualizarTabla(lista);
                }

            } catch (NegocioExcepcion ex) {
                //Estos catch  son para cuando las fechas de formato fallan (que ya no deberian de fallar pero los voy a dejar)
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Negocio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                //Esto es ya por si otra cosa me falla (que espero que no) en caso de que no sea ni el enum o la fecha y sea algo más
                JOptionPane.showMessageDialog(this, "Paso algo inesperado: " + ex.getMessage(), "Error Critico", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

    }
    /**
     * Actualiza el modelo visual de la tabla con los resultados obtenidos del negocio.
     * <p>Transforma los objetos {@link PedidoDTO} en filas legibles para el componente 
     * {@link TablaSimplePanel}.</p>
     * * @param lista Colección de pedidos que coinciden con los criterios de búsqueda.
     */
    private void actualizarTabla(List<PedidoDTO> lista) {

        tablaPedidos.limpiar();

        for (PedidoDTO pedido : lista) {

            tablaPedidos.agregarFila(
                    String.valueOf(pedido.getIdPedido()),
                    pedido.getEstado().toString(),
                    pedido.getTipo(),
                    pedido.getFecha().toString()
            );
        }
    }

}
