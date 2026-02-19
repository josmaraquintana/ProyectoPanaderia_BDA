/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

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

    public VIniciarSesion(JFrame frame, boolean modal) {
        super(frame, modal);
        setTitle("Inicio de sesión");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(4,1,5,5));
        
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
        add(panelBtn,BorderLayout.SOUTH);
        
 
    }
}