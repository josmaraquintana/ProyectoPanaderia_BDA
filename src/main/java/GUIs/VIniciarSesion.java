/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Negocio.BOs.IUsuarioBO;
import Negocio.DTOs.UsuarioDTO;
import Persistencia.DAO.IClienteDAO;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author josma, daniel, ramses
 */
public class VIniciarSesion extends JDialog {

    private JTextField Usuario;
    private JPasswordField Contrasena;
    private JButton btnEntrar;
    private JButton btnRegistrarse;
    private JButton btnPedidoExpress;
    //Creamos el BO y DAO para verificación y conexion
    private IUsuarioBO usuarioBO;
    private IClienteDAO clienteDAO;

    public VIniciarSesion(JFrame frame, boolean modal,
            IUsuarioBO usuarioBO,
            IClienteDAO clienteDAO) {
        super(frame, modal);
        this.usuarioBO = usuarioBO;
        this.clienteDAO = clienteDAO;

        setTitle("Inicio de sesión");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(4, 1, 5, 5));

        panelCentral.add(new JLabel("Usuario: "));
        Usuario = new JTextField();
        panelCentral.add(Usuario);

        panelCentral.add(new JLabel("Contrasena: "));
        Contrasena = new JPasswordField();
        panelCentral.add(Contrasena);

        // Panel inferior
        JPanel panelBtn = new JPanel(new FlowLayout());

        btnEntrar = new JButton("Entrar");
        btnRegistrarse = new JButton("Registrarse");
        btnPedidoExpress = new JButton("Pedido Express");

        panelBtn.add(btnEntrar);
        panelBtn.add(btnRegistrarse);
        panelBtn.add(btnPedidoExpress);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);
        
        btnEntrar.addActionListener(e -> iniciarSesion());

    }
    
    private void iniciarSesion() {

    String nombre_usuario = Usuario.getText();
    String contrasena = new String(Contrasena.getPassword());

    try {

        UsuarioDTO usuario = usuarioBO.login(nombre_usuario, contrasena);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos");
            return;
        }

        int id = usuario.getId();

        if (clienteDAO.clienteExiste(id)) {

            JOptionPane.showMessageDialog(this,
                    "Bienvenido cliente");

            this.dispose(); // cerrar login

            VPedidoCliente ventanaCliente =
                    new VPedidoCliente(usuario);

            ventanaCliente.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this,
                    "No eres cliente");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al iniciar sesión: " + ex.getMessage());
    }
}
}
