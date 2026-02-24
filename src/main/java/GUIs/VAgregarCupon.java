/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.BOs.CuponBO;
import Negocio.DTOs.CuponDTO;
import NegocioException.NegocioExcepcion;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author RAMSES
 */
public class VAgregarCupon extends JFrame {

    private CuponBO cuponBO;
    private PlaceholderTextField txtNombre;
    private PlaceholderTextField txtCodigo;
    private PlaceholderTextField txtVigencia;
    private PlaceholderTextField txtDescuento;
    private PlaceholderTextField txtUsos; // Nuevo campo
    private JFrame ventanaAnterior;

    private RoundedButton btnCancelar;
    private RoundedButton btnAgregar;

    public VAgregarCupon(JFrame ventanaAnterior, CuponBO cuponBO) {
        this.cuponBO = cuponBO;
        this.ventanaAnterior = ventanaAnterior;
        setTitle("Agregar Cupón");
        setSize(700, 480); // Altura un poco mayor para acomodar el nuevo campo
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.decode("#c4a484"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Color colorTextoOscuro = new Color(110, 74, 46);

        /**
         * ========================================== 1. ENCABEZADO: TÍTULO Y
         * LOGO ==========================================
         */
        LabelPersonalizado lblTitulo = new LabelPersonalizado("Agregar cupón", 36, Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 30, 20, 10);
        gbc.weightx = 1.0;
        add(lblTitulo, gbc);

        JLabel lblLogo = new JLabel();
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("[ LOGO ]");
        }
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 5; // Aumentamos para que abarque más filas hacia abajo y no empuje el resto
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 30);
        add(lblLogo, gbc);

        /**
         * ========================================== 2. FILA 1: ETIQUETAS
         * (NOMBRE Y CÓDIGO) ==========================================
         */
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;

        LabelPersonalizado lblNombre = new LabelPersonalizado("Nombre", 14, colorTextoOscuro);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 2, 10);
        add(lblNombre, gbc);

        LabelPersonalizado lblCodigo = new LabelPersonalizado("Código", 14, colorTextoOscuro);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 2, 10);
        add(lblCodigo, gbc);

        /**
         * ========================================== 3. FILA 2: INPUTS (NOMBRE
         * Y CÓDIGO) ==========================================
         */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtNombre = new PlaceholderTextField("");
        txtNombre.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 30, 15, 20);
        add(txtNombre, gbc);

        txtCodigo = new PlaceholderTextField("");
        txtCodigo.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 15, 20);
        add(txtCodigo, gbc);

        /**
         * ========================================== 4. FILA 3: ETIQUETAS
         * (VIGENCIA Y DESCUENTO) ==========================================
         */
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;

        LabelPersonalizado lblVigencia = new LabelPersonalizado("Vigencia", 14, colorTextoOscuro);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 30, 2, 10);
        add(lblVigencia, gbc);

        LabelPersonalizado lblDescuento = new LabelPersonalizado("Descuento", 14, colorTextoOscuro);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 2, 10);
        add(lblDescuento, gbc);

        /**
         * ========================================== 5. FILA 4: INPUTS
         * (VIGENCIA Y DESCUENTO) ==========================================
         */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtVigencia = new PlaceholderTextField("16-02-2026");
        txtVigencia.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 15, 20); // Margen inferior reducido para hacer espacio a Usos
        add(txtVigencia, gbc);

        txtDescuento = new PlaceholderTextField("");
        txtDescuento.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 15, 20);
        add(txtDescuento, gbc);

        /**
         * ========================================== 6. FILA 5: ETIQUETA (USOS)
         * ==========================================
         */
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;

        LabelPersonalizado lblUsos = new LabelPersonalizado("Usos", 14, colorTextoOscuro);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 30, 2, 10);
        add(lblUsos, gbc);

        /**
         * ========================================== 7. FILA 6: INPUT (USOS)
         * ==========================================
         */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtUsos = new PlaceholderTextField("");
        txtUsos.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 30, 30, 20); // Margen inferior amplio antes de los botones
        add(txtUsos, gbc);

        /**
         * ========================================== 8. BOTONES FINALES
         * ==========================================
         */
        gbc.fill = GridBagConstraints.NONE;
        Dimension tamanoBotones = new Dimension(140, 40);

        btnCancelar = new RoundedButton("Volver");
        btnCancelar.setPreferredSize(tamanoBotones);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(btnCancelar, gbc);

        btnCancelar.addActionListener(e -> {
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            }
            this.dispose();
        });

        btnAgregar = new RoundedButton("Agregar");
        btnAgregar.setPreferredSize(tamanoBotones);
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 30);
        add(btnAgregar, gbc);

        // Quitar el foco inicial de los text fields para que se vea el placeholder si lo hay
        SwingUtilities.invokeLater(() -> btnAgregar.requestFocusInWindow());

        btnAgregar.addActionListener(e -> {
            try {
                //Obtenemos todos los datos de los campos
                //les aplicamos trim para evitar espacios innecesarios
                String nombre = txtNombre.getText().trim();
                String vigenciaStr = txtVigencia.getText().trim();
                String descuentoStr = txtDescuento.getText().trim();
                String usosStr = txtUsos.getText().trim();

                //Una vez obtenido el texto, validamos que no vengan vacios
                if (nombre.isEmpty() || vigenciaStr.isEmpty() || descuentoStr.isEmpty() || usosStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
                    return;
                }

                // 3. Crear el DTO y parsear valores numéricos
                CuponDTO cupon = new CuponDTO();
                cupon.setNombre(nombre);

                try {
                    cupon.setDesc(Double.parseDouble(descuentoStr));
                    cupon.setMax_usos(Integer.parseInt(usosStr));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El descuento y los usos deben ser validos");
                    return;
                }

                //Intentamos convertir la fecha
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                    sdf.setLenient(false); // Esto no nos permite fechas fuera de rango
                    java.util.Date fechaUtil = sdf.parse(vigenciaStr);
                    cupon.setVigencia(new java.sql.Date(fechaUtil.getTime()));
                } catch (java.text.ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use DD-MM-AAAA", "Error de fecha", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.cuponBO.agregarCupon(cupon);

                // 6. Mensaje de éxito y limpieza
                JOptionPane.showMessageDialog(this, "Cupón '" + nombre + "' agregado exitosamente\nID asignado: " + cupon.getId(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();

            } catch (NegocioExcepcion ex) {
                // Errores de logica como que el descuento este mal aplicado
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // Errores que podrian ser ocasionados por la base de datos
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCodigo.setText(""); // Si usas el campo código también
        txtVigencia.setText("");
        txtDescuento.setText("");
        txtUsos.setText("");
        txtNombre.requestFocus();
    }
}
