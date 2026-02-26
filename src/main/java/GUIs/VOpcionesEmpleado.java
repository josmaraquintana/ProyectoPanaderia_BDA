/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import Negocio.BOs.CuponBO;
import Negocio.BOs.EmpleadoBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.ProductoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.EmpleadoDTO;
import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author RAMSES
 */
public class VOpcionesEmpleado extends JFrame {

    
    private ProductoBO productoBO;
    private CuponBO cuponBO;
    private EmpleadoDTO empleado;
    private EmpleadoBO empleadoBO;
    private UsuarioBO usuarioBO;
    private PedidoBO pedido;
    private TelefonoBO telefono;
    private RoundedButton btn_agregar_producto;
    private RoundedButton btn_agregar_cupon;
    private RoundedButton btn_cambiar_estado;
    private RoundedButton btn_volver;
    private LabelPersonalizado lbl_usuario;
    private LabelPersonalizado lbl_nombre_usuario;
    private RoundedButton btn_registrar_empleado;
    private RoundedButton btn_estado_producto;
    private JFrame ventanaAnterior;

    public VOpcionesEmpleado(ProductoBO productoBO,CuponBO cuponBO, EmpleadoDTO empleado, EmpleadoBO empleadoBO, UsuarioBO usuarioBO, PedidoBO pedido, TelefonoBO telefono, JFrame ventanaAnterior) {
        this.productoBO = productoBO;
        this.cuponBO = cuponBO;
        this.empleado = empleado;
        this.empleadoBO = empleadoBO;
        this.usuarioBO = usuarioBO;
        this.pedido = pedido;
        this.telefono = telefono;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Opciones de Empleado");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#c4a484"));
        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // espacio entre componentes
        gbc.anchor = GridBagConstraints.CENTER; // centrados
        gbc.fill = GridBagConstraints.NONE; // para que no se expandan 

        //CONFIGURAR ETIQUETAS
        lbl_usuario = new LabelPersonalizado("Empleado: ", Color.white);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lbl_usuario, gbc);
        lbl_nombre_usuario = new LabelPersonalizado(empleado.getNombre_usuario(), Color.white);
        lbl_nombre_usuario.setFont(new Font("Monotype Corsiva", Font.ITALIC, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(lbl_nombre_usuario, gbc);

        //--> para el tamaño de los botones 
        Dimension tamano = new Dimension(200, 40);
        //CONFIGURAR BOTONES

        btn_agregar_cupon = new RoundedButton("Agregar Cupon");
        gbc.gridx = 1;
        gbc.gridy = 1;
        btn_agregar_cupon.setPreferredSize(tamano);
        add(btn_agregar_cupon, gbc);
        btn_cambiar_estado = new RoundedButton("Editar Pedido");
        gbc.gridx = 1;
        gbc.gridy = 2;
        btn_cambiar_estado.setPreferredSize(tamano);
        add(btn_cambiar_estado, gbc);
        btn_registrar_empleado = new RoundedButton("Registrar Empleado");
        gbc.gridx = 1;
        gbc.gridy = 3;
        btn_registrar_empleado.setPreferredSize(tamano);
        add(btn_registrar_empleado, gbc);
        btn_agregar_producto = new RoundedButton("Agregar Producto");
        gbc.gridx = 1;
        gbc.gridy = 4;
        btn_agregar_producto.setPreferredSize(tamano);
        add(btn_agregar_producto, gbc);
        btn_estado_producto = new RoundedButton("Estado Producto");
        gbc.gridx = 1;
        gbc.gridy = 5;
        btn_estado_producto.setPreferredSize(tamano);
        add(btn_estado_producto, gbc);
        btn_volver = new RoundedButton("Volver");
        gbc.gridx = 1;
        gbc.gridy = 6;
        btn_volver.setPreferredSize(tamano);
        add(btn_volver, gbc);

        btn_volver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        });

        btn_registrar_empleado.addActionListener(e -> {
            new VRegistrarEmpleado(empleadoBO, empleado, this).setVisible(true);
            this.setVisible(false);
        });

        btn_cambiar_estado.addActionListener(e -> {
            new VCambioEstadoPedido(empleado, this, null, null, null, null, null).setVisible(true);
            this.setVisible(false);
        });

        btn_agregar_cupon.addActionListener(e -> {
            new VAgregarCupon(this, this.cuponBO).setVisible(true);
            this.dispose();
        });
        btn_agregar_producto.addActionListener(e -> {
            new VAgregarProducto(productoBO, empleado, this).setVisible(true);
            this.dispose();
        });
        btn_estado_producto.addActionListener(e -> {
            new VCambiarEstadoProducto(productoBO, this).setVisible(true);
            this.dispose();
        });

    }

}
