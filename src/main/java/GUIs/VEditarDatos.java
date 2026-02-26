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
import Negocio.DTOs.UsuarioDTO;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 * Ventana de actualización de perfil para el cliente.
 * <p>
 * Proporciona un formulario para modificar datos personales y de ubicación,
 * tales como nombre, dirección y edad. Los cambios se validan y se persisten a
 * través de la capa de negocio.</p>
 *
 * * @author RAMSES
 * @version 1.0
 */
public class VEditarDatos extends JFrame {

    private ClienteDTO cliente;
    private ClienteBO clienteBO;
    private JFrame ventanaAnterior;

    /**
     * Inicializa la interfaz de edición de datos.
     * <p>
     * Configura un diseño de cuadrícula flexible mediante {@link GridBagLayout}
     * para organizar los campos de texto con placeholders.</p>
     *
     * * @param cliente DTO del cliente con la información cargada.
     * @param clienteBO Instancia del objeto de negocio para ejecutar la
     * actualización.
     * @param ventanaAnterior Ventana padre a la cual regresar tras cerrar o
     * editar.
     */
    public VEditarDatos(ClienteDTO cliente, ClienteBO clienteBO, JFrame ventanaAnterior) {
        this.cliente = cliente;
        this.clienteBO = clienteBO;
        this.ventanaAnterior = ventanaAnterior;
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

        LabelPersonalizado lblTitulo
                = new LabelPersonalizado("Editar datos", 28, texto);
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
        PlaceholderTextField txtNombre = new PlaceholderTextField("Nombre");
        PlaceholderTextField txtApellidoPaterno = new PlaceholderTextField("Apellido Paterno");
        PlaceholderTextField txtApellidoMaterno = new PlaceholderTextField("Apellido Materno");
        PlaceholderTextField txtCalle = new PlaceholderTextField("Calle");
        PlaceholderTextField txtColonia = new PlaceholderTextField("Colonia");
        PlaceholderTextField txtCodigoPostal = new PlaceholderTextField("Código Postal");
        PlaceholderTextField txtNumCasa = new PlaceholderTextField("Número de casa");
        PlaceholderTextField txtEdad = new PlaceholderTextField("Edad");

        Dimension campoSize = new Dimension(250, 40);
        txtNombre.setPreferredSize(campoSize);
        txtApellidoPaterno.setPreferredSize(campoSize);
        txtApellidoMaterno.setPreferredSize(campoSize);
        txtCalle.setPreferredSize(campoSize);
        txtColonia.setPreferredSize(campoSize);
        txtCodigoPostal.setPreferredSize(campoSize);
        txtNumCasa.setPreferredSize(campoSize);
        txtEdad.setPreferredSize(campoSize);

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(txtNombre, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtApellidoPaterno, gbc);
        gbc.gridx = 2;
        panelFormulario.add(txtApellidoMaterno, gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(txtColonia, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigoPostal, gbc);
        gbc.gridx = 2;
        panelFormulario.add(txtCalle, gbc);

        // Fila 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(txtNumCasa, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEdad, gbc);

        // ================= PANEL BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setOpaque(false);

        RoundedButton btnCancelar = new RoundedButton("Volver");

        btnCancelar.addActionListener(e -> {
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            }
            this.dispose();
        });

        RoundedButton btnRegistrar = new RoundedButton("Editar");
        /**
         * Manejador del evento de edición.
         * <p>
         * Extrae los valores de los campos de texto, realiza el <i>parsing</i>
         * de datos numéricos (Edad, CP, Número de casa) y actualiza el objeto
         * {@link ClienteDTO}.</p>
         *
         * * @throws NegocioExcepcion Si los datos no cumplen con las reglas de
         * negocio.
         * @throws NumberFormatException Si se ingresan caracteres no numéricos
         * en campos de entero.
         */
        btnRegistrar.addActionListener(e -> {

            try {
                cliente.setNombres(txtNombre.getText().trim());
                cliente.setApellido_paterno(txtApellidoPaterno.getText().trim());
                cliente.setApellido_materno(txtApellidoMaterno.getText().trim());

                cliente.setNumero_casa(Integer.parseInt(txtNumCasa.getText().trim()));
                cliente.setCalle(txtCalle.getText().trim());
                cliente.setColonia(txtColonia.getText().trim());
                cliente.setCodigo_postal(Integer.parseInt(txtCodigoPostal.getText().trim()));
                cliente.setEdad(Integer.parseInt(txtEdad.getText().trim()));

                clienteBO.actualizarCliente(cliente);

                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente");
                this.dispose();

            } catch (NegocioExcepcion ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos numéricos inválidos");
            }
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        // ================= ENSAMBLADO =================
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

}
