/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.ProductoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.ClienteDTO;
import NegocioException.NegocioExcepcion;
import java.awt.*;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author josma
 */
public class VOpcionesCliente extends JFrame {

    private ProductoBO productoBO;
    private ClienteDTO cliente;
    private PedidoBO pedido;
    private TelefonoBO telefono;
    private RoundedButton btn_pedido;
    private RoundedButton btn_historial;
    private RoundedButton btn_editar_datos;
    private RoundedButton btn_agregar_telefono;
    private RoundedButton btn_inactivar;
    private RoundedButton btn_volver;
    private RoundedButton btn_estado_pedido; 
    private LabelPersonalizado lbl_usuario;
    private LabelPersonalizado lbl_nombre_usuario;
    private ClienteBO clienteBO;
    private UsuarioBO usuarioBO;
    private JFrame ventanaAnterior;

    public VOpcionesCliente(PedidoBO pedido, ClienteDTO cliente, TelefonoBO telefono, ClienteBO clienteBO, UsuarioBO usuarioBO, JFrame ventanaAnterior) {

        //Valor para el cliente
        this.productoBO = productoBO;
        this.cliente = cliente;
        this.clienteBO = clienteBO;
        this.pedido = pedido;
        this.telefono = telefono;
        this.usuarioBO = usuarioBO;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Opciones de Cliente");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#c4a484"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // espacio entre componentes
        gbc.anchor = GridBagConstraints.CENTER; // centrados
        gbc.fill = GridBagConstraints.NONE; // para que no se expandan 
        /**
         * Con GridBagLayout podemos hacer tablas que no abarquen todo como con
         * el GridLayout Además de controlar donde se centran, se inserta por
         * coordenadas
         *
         *
         */

        //CONFIGURAR ETIQUETAS
        lbl_usuario = new LabelPersonalizado("Cliente: ", Color.white);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lbl_usuario, gbc);
        lbl_nombre_usuario = new LabelPersonalizado(cliente.getNombre_usuario(), Color.white);
        lbl_nombre_usuario.setFont(new Font("Monotype Corsiva", Font.ITALIC, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(lbl_nombre_usuario, gbc);

        //--> para el tamaño de los botones 
        Dimension tamano = new Dimension(250, 40);
        //CONFIGURAR BOTONES
        btn_pedido = new RoundedButton("Agregar pedido");
        gbc.gridx = 1;  // columna del centro
        gbc.gridy = 1;  // segunda fila
        btn_pedido.setPreferredSize(tamano);
        add(btn_pedido, gbc);
        btn_historial = new RoundedButton("Historial");
        gbc.gridx = 1;
        gbc.gridy = 2;
        btn_historial.setPreferredSize(tamano);
        add(btn_historial, gbc);
        btn_editar_datos = new RoundedButton("Editar datos");
        gbc.gridx = 1;
        gbc.gridy = 3;
        btn_editar_datos.setPreferredSize(tamano);
        add(btn_editar_datos, gbc);
        btn_agregar_telefono = new RoundedButton("Telefono");
        gbc.gridx = 1;
        gbc.gridy = 4;
        btn_agregar_telefono.setPreferredSize(tamano);
        add(btn_agregar_telefono, gbc);
        btn_inactivar = new RoundedButton("Inactivar Cuenta");
        gbc.gridx = 1;
        gbc.gridy = 5;
        btn_inactivar.setPreferredSize(tamano);
        add(btn_inactivar, gbc);
        btn_estado_pedido = new RoundedButton("Estado Pedido");
        gbc.gridx = 1;
        gbc.gridy = 6;
        btn_estado_pedido.setPreferredSize(tamano);
        add(btn_estado_pedido, gbc);
        btn_volver = new RoundedButton("Volver");
        gbc.gridx = 1;
        gbc.gridy = 7;
        btn_volver.setPreferredSize(tamano);
        add(btn_volver, gbc);

        //AGREGAR EL LOGO
        JLabel lbl_logo = new JLabel();
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(lbl_logo, gbc);
        lbl_logo.setPreferredSize(new Dimension(130, 130));
        // Si luego quieres cargar la imagen:
        URL url = getClass().getResource("/img/icon.png");

        if (url == null) {
            System.err.println("ERROR: No se encontró /img/logo.png");
        } else {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));
        }
        btn_pedido.addActionListener(e -> {
            new VTomarPedido(pedido, cliente, telefono, clienteBO, usuarioBO, this).setVisible(true);
            this.dispose();
        });

        btn_historial.addActionListener(e -> {
            new VHistorial(pedido, cliente, telefono, clienteBO, usuarioBO, this).setVisible(true);
            this.dispose();
        });

        btn_editar_datos.addActionListener(e -> {
            new VEditarDatos(cliente, clienteBO, this).setVisible(true);
            this.dispose();
        });

        btn_agregar_telefono.addActionListener(e -> {
            new VAgregarTelefonos(pedido, cliente, telefono, clienteBO, usuarioBO, this).setVisible(true);
        });

        btn_volver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        });
        
        btn_estado_pedido.addActionListener(e->{
            new VCambioEstadoPedido(null, this, pedido, cliente, telefono, clienteBO, usuarioBO).setVisible(true);
            this.dispose();
        });

        btn_inactivar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas inactivar tu cuenta?\n"
                    + "Perderás el acceso inmediat",
                    "Confirmar Inactivación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    clienteBO.inactivarCuenta(this.cliente.getId_cliente());
                    JOptionPane.showMessageDialog(this, "Cuenta inactivada con éxito. Sesión cerrada.");
                    this.dispose();
                    ventanaAnterior.setVisible(true);
                } catch (NegocioExcepcion ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}
