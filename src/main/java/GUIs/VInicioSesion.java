/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;


/**
 *
 * @author RAMSES
 */
public class VInicioSesion extends JFrame{
    
    public VInicioSesion(JFrame frame) {
        setTitle("Inicio de sesión");
        setSize(800, 450);
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

        RoundedButton btnEntrar = new RoundedButton("Entrar");
        RoundedButton btnRegistrar = new RoundedButton("Registrarse");
        RoundedButton btnExpress = new RoundedButton("Pedido Express");

        botonesPanel.add(btnEntrar);
        botonesPanel.add(btnRegistrar);
        botonesPanel.add(btnExpress);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(sinSesion, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    
}

    

