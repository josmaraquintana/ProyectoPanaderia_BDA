/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author DANIEL
 */
public class VPedidoCliente extends JFrame {
    private JButton btnHacerPedido;
    private JButton btnHistorial;
    private JButton btnEditarDatos;
    private JButton btnAgregarTelefono;
    
    public VPedidoCliente(){
        //creamos la ventana
        setTitle("Opciones");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //se crea el panel donde iran los botones
        JPanel panelCentro = new JPanel(new GridLayout(4,1,5,5));
        
        btnHacerPedido = new JButton("Hacer pedido");
        btnHistorial = new JButton("Historial");
        btnEditarDatos = new JButton("Editar datos");
        btnAgregarTelefono = new JButton("Agregar telefono");
        
        panelCentro.add(btnHacerPedido);
        panelCentro.add(btnHistorial);
        panelCentro.add(btnEditarDatos);
        panelCentro.add(btnAgregarTelefono);
        
        add(panelCentro, BorderLayout.CENTER);
        
        setVisible(true);
    }
}
