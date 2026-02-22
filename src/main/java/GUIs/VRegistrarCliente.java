/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Validadores.Validaciones;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author RAMSES
 */
public class VRegistrarCliente extends JFrame {

    private JTextField nombreField;
    private JTextField fechaField;
    private JTextField apellidoMaternoField;
    private JTextField numeroCasaField;
    private JTextField coloniaField;
    private JTextField apellidoPaternoField;
    private JTextField calleField;
    private JTextField cpField;
    private JTextField edadField;
    private JTextField telefonoField;
    private JTextField contrasenaField;

    public VRegistrarCliente() {
        setTitle("Registro");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Registro Cliente");
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

        nombreField = crearCampo("Nombre completo");
        fechaField = crearCampo("Fecha de nacimiento(dd-mm-yyyy)");
        apellidoMaternoField = crearCampo("Apellido materno");
        numeroCasaField = crearCampo("Número de casa");
        coloniaField = crearCampo("Colonia");
        apellidoPaternoField = crearCampo("Apellido paterno");
        calleField = crearCampo("Calle");
        cpField = crearCampo("Código postal");
        edadField = crearCampo("Edad");
        telefonoField = crearCampo("Teléfono");
        contrasenaField = crearCampo("Contrasena");

        // Fila 1
        formPanel.add(nombreField);
        formPanel.add(fechaField);
        formPanel.add(new JLabel());

        // Fila 2
        formPanel.add(apellidoMaternoField);
        formPanel.add(numeroCasaField);
        formPanel.add(coloniaField);

        // Fila 3
        formPanel.add(apellidoPaternoField);
        formPanel.add(calleField);
        formPanel.add(cpField);

        // Fila 4
        formPanel.add(edadField);
        formPanel.add(telefonoField);
        formPanel.add(contrasenaField);

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

        btnRegistrar.addActionListener(e -> {
            try {

                if (!Validaciones.validarNombres(nombreField.getText())) {
                    JOptionPane.showMessageDialog(this, "Nombre inválido");
                    return;
                }

                if (!Validaciones.validarNombres(apellidoMaternoField.getText())) {
                    JOptionPane.showMessageDialog(this, "Apellido materno inválido");
                    return;
                }

                if (!Validaciones.validarNombres(apellidoPaternoField.getText())) {
                    JOptionPane.showMessageDialog(this, "Apellido paterno inválido");
                    return;
                }

                if (!Validaciones.validarEnteros(telefonoField.getText())) {
                    JOptionPane.showMessageDialog(this, "Telefono inválido");
                    return;
                }

                if (!Validaciones.validarEnteros(cpField.getText())) {
                    JOptionPane.showMessageDialog(this, "Codigo postal inválido");
                    return;
                }

                if (!Validaciones.validaFecha(fechaField.getText())) {
                    JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida");
                    return;
                }

                if (!Validaciones.validarEnteros(numeroCasaField.getText())) {
                    JOptionPane.showMessageDialog(this, "Numero de casa inválido");
                    return;
                }

                if (!telefonoField.getText().matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido (10 dígitos)");
                    return;
                }

                if (!cpField.getText().matches("\\d{5}")) {
                    JOptionPane.showMessageDialog(this, "Código postal inválido");
                    return;
                }

                if (!Validaciones.validaContrasena(contrasenaField.getText())) {
                    JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres, una letra y un numero");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Registro exitoso");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el registro");
            }
        });       
    }

    private PlaceholderTextField crearCampo(String placeholder) {
        PlaceholderTextField campo = new PlaceholderTextField(placeholder);
        campo.setPreferredSize(new Dimension(220, 40));
        return campo;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VRegistrarCliente().setVisible(true);
        });
    }

}
