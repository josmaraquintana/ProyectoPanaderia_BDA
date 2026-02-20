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
public class RegistrarCliente extends JFrame {

    public RegistrarCliente() {
        setTitle("Registro");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        
        JLabel titulo = new JLabel("Registro");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        
        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(140, 140));
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        // Cargar imagen (opcional)
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(fondo);
        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(lblLogo, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        
        JPanel formPanel = new JPanel(new GridLayout(4, 3, 20, 15));
        formPanel.setBackground(fondo);

        // Fila 1
        formPanel.add(crearCampo("Nombre completo"));
        formPanel.add(crearCampo("Fecha de nacimiento (año-mes-dia)"));
        formPanel.add(new JLabel()); // espacio visual

        // Fila 2
        formPanel.add(crearCampo("Apellido materno"));
        formPanel.add(crearCampo("Número de casa"));
        formPanel.add(crearCampo("Colonia"));

        // Fila 3
        formPanel.add(crearCampo("Apellido paterno"));
        formPanel.add(crearCampo("Calle"));
        formPanel.add(crearCampo("Código postal"));

        // Fila 4
        formPanel.add(crearCampo("Edad"));
        formPanel.add(crearCampo("Número de casa"));
        formPanel.add(crearCampo("Teléfono"));

        
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        botonesPanel.setBackground(fondo);

        RoundedButton btnCancelar = new RoundedButton("Cancelar");
        RoundedButton btnRegistrar = new RoundedButton("Registrarse");

        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnRegistrar);

        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private PlaceholderTextField crearCampo(String placeholder) {
        PlaceholderTextField campo = new PlaceholderTextField(placeholder);
        campo.setPreferredSize(new Dimension(220, 40));
        return campo;
    }
    
    
}
