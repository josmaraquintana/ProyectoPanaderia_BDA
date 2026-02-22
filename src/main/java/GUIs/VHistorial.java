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
 *
 * @author RAMSES
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

    public VHistorial(PedidoBO pedidoBO, ClienteDTO cliente) {
        this.cliente = cliente;
        this.pedidoBO = pedidoBO;
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

        txtFechaInicio = new PlaceholderTextField("Fecha inicio");
        txtFechaFin = new PlaceholderTextField("Fecha fin");
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
            new VOpcionesEmpleado(null).setVisible(true);
            this.dispose();
        });

        btnConsultar.addActionListener(e -> {
            try {
                String fecha_inicio = txtFechaInicio.getText().trim();
                String fecha_fin = txtFechaFin.getText().trim();

                if (fecha_inicio.isEmpty() || fecha_fin.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar ambas fechas");
                    return;
                }
                String tipo_pedido = "";
                if (rbProgramado.isSelected()) {
                    tipo_pedido = "Programado";
                } else if (rbExpress.isSelected()) {
                    tipo_pedido = "Express";
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe seleccionar un tipo de pedido");
                    return;
                }
                String estado_letras = txtEstado.getText().trim().toUpperCase();
                EstadoPedido estado_pedido = EstadoPedido.valueOf(estado_letras);

                List<PedidoDTO> lista = pedidoBO.consultarHistorial(fecha_inicio, fecha_fin, tipo_pedido, estado_pedido);

                actualizarTabla(lista);
            } catch (NegocioExcepcion ex) {

                JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,"Estado inválido","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void actualizarTabla(List<PedidoDTO> lista) {

        tablaPedidos.limpiar(); // si tienes este método

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
