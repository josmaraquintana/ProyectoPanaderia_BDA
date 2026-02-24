/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.EmpleadoBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.EmpleadoDTO;
import Negocio.DTOs.LoginDTO;
import Negocio.DTOs.UsuarioDTO;
import Persistencia.DAO.UsuarioDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class VInicioSesion extends JFrame {
    private TelefonoBO telefono;
    private UsuarioBO usuarioBO; 
    private PedidoBO pedido;
    private EmpleadoBO empleadoBO;
    private ClienteBO clienteBO;
    
    public VInicioSesion(PedidoBO pedido, UsuarioBO usuarioBO, TelefonoBO telefono, EmpleadoBO empleadoBO, ClienteBO clienteBO) {
        
        this.empleadoBO = empleadoBO;
        this.usuarioBO = usuarioBO;
        this.pedido = pedido; 
        this.telefono = telefono; 
        this.clienteBO = clienteBO;
        
        
        setTitle("Inicio de sesión");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Inicio de sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(130, 130));
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        // Si luego quieres cargar la imagen:
        URL url = getClass().getResource("/img/icon.png");

        if (url == null) {
            System.err.println("ERROR: No se encontró /img/logo.png");
        } else {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(fondo);
        topPanel.add(titulo, BorderLayout.CENTER);
        topPanel.add(lblLogo, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(fondo);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        PlaceholderTextField txtUsuario = new PlaceholderTextField("Usuario");
        PlaceholderTextField txtContrasena = new PlaceholderTextField("Contraseña");

        formPanel.add(txtUsuario);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(txtContrasena);

        JLabel sinSesion = new JLabel("¿No tienes sesión? REGISTRATE");
        sinSesion.setForeground(Color.WHITE);
        sinSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        sinSesion.setBorder(new EmptyBorder(20, 0, 10, 0));

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonesPanel.setBackground(fondo);

        RoundedButton btn_entrar = new RoundedButton("Entrar");
        RoundedButton btn_registrar = new RoundedButton("Registrarse");
        RoundedButton btn_express = new RoundedButton("Pedido Express");

        botonesPanel.add(btn_entrar);
        botonesPanel.add(btn_registrar);
        botonesPanel.add(btn_express);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(sinSesion, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);

        setVisible(true);

        /**
         * Evento para el boton de inicio de sesión, solo se esta trabaja con LoginDTO que maneja usuario
         * y contraseña.
         * Con instanceof revisamos si existe el usuario en alguna de las entidades hijas para poder hacer el
         * redireccionamiento
         */
        btn_entrar.addActionListener(e -> {
            
            try {
               LoginDTO loginDTO = new LoginDTO(txtUsuario.getText().trim(), txtContrasena.getText().trim());
               
               //Llamamos al BO
               UsuarioDTO usuario = usuarioBO.login(loginDTO);
               /**
                * Aqui ocurrre el direccionamiento, que dependiendo de lo que se reciba en los txt
                * puede ser cliente o empleado
                */
               
               if(usuario instanceof ClienteDTO){
                   //Hacemos casting o conversion
                   VOpcionesCliente ventana_op_cliente = new VOpcionesCliente(pedido,(ClienteDTO) usuario, telefono, clienteBO);
                   ventana_op_cliente.setVisible(true);
                   this.dispose();
               }else if(usuario instanceof EmpleadoDTO){
                   VOpcionesEmpleado ventana_op_empleado =  new VOpcionesEmpleado((EmpleadoDTO) usuario, empleadoBO, usuarioBO, pedido, telefono);
                   ventana_op_empleado.setVisible(true);
                   this.dispose();
               }else{
                   JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
                   txtUsuario.setText("");
                   txtContrasena.setText("");
                   txtUsuario.requestFocus();
               }
               
            } catch (Exception ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(null, "Usuario inválido: " + ex.getMessage());
            }
            
        });
        
        btn_registrar.addActionListener(e ->{
            new VRegistrarCliente(clienteBO, this).setVisible(true);
            this.setVisible(false);
        });
        
        btn_express.addActionListener(e ->{
            new VTomarPedidoExpress(this, null).setVisible(true);
            this.setVisible(false);
        });
        
    }

}
