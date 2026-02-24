/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.BOs.ClienteBO;
import Negocio.DTOs.ClienteDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class VEditarDatos extends JFrame{
    private ClienteDTO cliente;
    private ClienteBO clienteBO;
    public VEditarDatos(ClienteDTO cliente, ClienteBO clienteBO) {
        this.cliente = cliente; 
        this.clienteBO = clienteBO;
        setTitle("Editar datos");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        Color texto = Color.WHITE;

        getContentPane().setBackground(fondo);

        // ================= PANEL SUPERIOR (TITULO + LOGO) =================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(20, 30, 10, 30));

        LabelPersonalizado lblTitulo =
                new LabelPersonalizado("Editar datos", 28, texto);
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

        // ================= PANEL FORMULARIO =================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(new EmptyBorder(20, 60, 20, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // ===== CAMPOS =====
        PlaceholderTextField txtNumCasa1 = new PlaceholderTextField("Número de casa");
        PlaceholderTextField txtCalle = new PlaceholderTextField("Calle");
        PlaceholderTextField txtColonia = new PlaceholderTextField("Colonia");
        PlaceholderTextField txtCodigoPostal = new PlaceholderTextField("Código Postal");
        PlaceholderTextField txtNumCasa2 = new PlaceholderTextField("Número de casa");
        PlaceholderTextField txtEdad = new PlaceholderTextField("Edad");

        Dimension campoSize = new Dimension(250, 40);
        txtNumCasa1.setPreferredSize(campoSize);
        txtCalle.setPreferredSize(campoSize);
        txtColonia.setPreferredSize(campoSize);
        txtCodigoPostal.setPreferredSize(campoSize);
        txtNumCasa2.setPreferredSize(campoSize);
        txtEdad.setPreferredSize(campoSize);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(txtNumCasa1, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCalle, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(txtColonia, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigoPostal, gbc);

        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(txtNumCasa2, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEdad, gbc);

        // ================= PANEL BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setOpaque(false);

        RoundedButton btnCancelar = new RoundedButton("Cancelar");
        
        
        RoundedButton btnRegistrar = new RoundedButton("Editar");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
    

}
