/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author DANIEL
 */
public class VPedido extends JFrame{
    
    private JCheckBox cbSapito;
    private JCheckBox cbPanIntegral;
    private JCheckBox cbDona;
    private JCheckBox cbCochito;

    private JTextField txtCantidad;
    private JLabel lblSubtotal;

    private JButton btnAgregar;
    private JButton btnRealizar;
    private JButton btnCancelar;

    public VPedido() {

        setTitle("Pedido");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel productos
        JPanel panelProductos = new JPanel(new GridLayout(4,1));

        cbSapito = new JCheckBox("Sapito - $15");
        cbPanIntegral = new JCheckBox("Pan integral - $10");
        cbDona = new JCheckBox("Dona de chocolate - $17");
        cbCochito = new JCheckBox("Cochito - $17");

        panelProductos.add(cbSapito);
        panelProductos.add(cbPanIntegral);
        panelProductos.add(cbDona);
        panelProductos.add(cbCochito);

        add(panelProductos, BorderLayout.WEST);

        // Panel datos
          JPanel panelDatos = new JPanel(new GridLayout(6,1,5,5));

        panelDatos.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelDatos.add(txtCantidad);

        lblSubtotal = new JLabel("Subtotal: $0.00");
        panelDatos.add(lblSubtotal);

        btnAgregar = new JButton("Agregar producto");
        btnRealizar = new JButton("Realizar pedido");
        btnCancelar = new JButton("Cancelar");

        panelDatos.add(btnAgregar);
        panelDatos.add(btnRealizar);
        panelDatos.add(btnCancelar);

        add(panelDatos, BorderLayout.CENTER);
        
        setVisible(true);
    }
}