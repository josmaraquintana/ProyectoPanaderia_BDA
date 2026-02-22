/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.RoundedButton;
import Negocio.BOs.PedidoBO;
import Negocio.DTOs.ClienteDTO;
import java.awt.*;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author josma
 */
public class VOpcionesCliente extends JFrame {
    private ClienteDTO cliente; 
    private PedidoBO pedido; 
    private RoundedButton btn_pedido;
    private RoundedButton btn_historial;
    private RoundedButton btn_editar_datos;
    private RoundedButton btn_agregar_telefono;
    private RoundedButton btn_volver;
    private LabelPersonalizado lbl_usuario;
    private LabelPersonalizado lbl_nombre_usuario; 
    public VOpcionesCliente(PedidoBO pedido,ClienteDTO cliente) {
        
        //Valor para el cliente
        this.cliente = cliente; 
        this.pedido = pedido; 
        
        
        setTitle("Opciones de Cliente");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#c4a484"));

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // espacio entre componentes
        gbc.anchor = GridBagConstraints.CENTER; // centrados
        gbc.fill = GridBagConstraints.NONE; // para que no se expandan 
        /**
         * Con GridBagLayout podemos hacer tablas que no abarquen todo como con el GridLayout
         * Además de controlar donde se centran, se inserta por coordenadas
         *
        **/
        
        
        //CONFIGURAR ETIQUETAS
        lbl_usuario = new LabelPersonalizado("Usuario: ",Color.white);
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        add(lbl_usuario, gbc);
        lbl_nombre_usuario = new LabelPersonalizado(cliente.getNombre_usuario(), Color.white);
        lbl_nombre_usuario.setFont(new Font("Monotype Corsiva", Font.ITALIC, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(lbl_nombre_usuario,gbc);
        
        //--> para el tamaño de los botones 
        Dimension tamano = new Dimension(200, 40);
        //CONFIGURAR BOTONES
        btn_pedido = new RoundedButton("Agregar pedido");
        gbc.gridx = 1;  // columna del centro
        gbc.gridy = 1;  // segunda fila
        btn_pedido.setPreferredSize(tamano);
        add(btn_pedido, gbc);
        btn_historial = new RoundedButton("Historial");
        gbc.gridx = 1;
        gbc.gridy = 2;
        btn_historial.setPreferredSize(tamano);
        add(btn_historial,gbc);
        btn_editar_datos = new RoundedButton("Editar datos");
        gbc.gridx = 1;
        gbc.gridy = 3;
        btn_editar_datos.setPreferredSize(tamano);
        add(btn_editar_datos,gbc);
        btn_agregar_telefono = new RoundedButton("Telefono");
        gbc.gridx = 1;
        gbc.gridy = 4;
        btn_agregar_telefono.setPreferredSize(tamano);
        add(btn_agregar_telefono,gbc);
        btn_volver = new RoundedButton("Volver");
        gbc.gridx  = 1;
        gbc.gridy = 5;
        btn_volver.setPreferredSize(tamano);
        add(btn_volver,gbc);
        
        
        //AGREGAR EL LOGO
        JLabel lbl_logo = new JLabel();
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(lbl_logo, gbc);
        lbl_logo.setPreferredSize(new Dimension(130, 130));
        // Si luego quieres cargar la imagen:
        URL url = getClass().getResource("/img/icon.png");

        if (url == null) {
            System.err.println("ERROR: No se encontró /img/logo.png");
        } else {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));
        }
        btn_pedido.addActionListener(e->{
            new VTomarPedido(cliente).setVisible(true);
            this.dispose();
        });
        
        btn_historial.addActionListener(e->{
            new VHistorial(pedido,cliente).setVisible(true);
            this.dispose();
        });
        
        btn_editar_datos.addActionListener(e->{
            new VEditarDatos(cliente).setVisible(true);
            this.dispose();
        });
        
        btn_agregar_telefono.addActionListener(e->{
            new VAgregarTelefonos(cliente).setVisible(true);
        });
        
        btn_volver.addActionListener(e -> {
            new VInicioSesion(null,null).setVisible(true);
            this.dispose();
        });
        

    }
}
