/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.DTOs.EmpleadoDTO;
import Validadores.Validaciones;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author josma
 */
public class VRegistrarEmpleado extends JFrame {
    private EmpleadoDTO empleado;
    
    private LabelPersonalizado lbl_tipo;
    private PlaceholderTextField txt_nombre;
    private PlaceholderTextField txt_apellido_paterno;
    private PlaceholderTextField txt_apellido_materno;
    private PlaceholderTextField txt_nombre_usuario;
    private PlaceholderTextField txt_contrasena;
    private RoundedButton btn_agregar;
    private RoundedButton btn_cancelar; 
    public VRegistrarEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
        Color color  = Color.decode("#c4a484");
        
        
        setTitle("Registro Empleado");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#c4a484"));
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(4, 4, 4, 4); // espacio entre componentes
        gbc.anchor = GridBagConstraints.CENTER; // centrados
        gbc.fill = GridBagConstraints.NONE; // para que no se expandan 
        

        
        //CONFIGURAR ETIQUETAS
        lbl_tipo = new LabelPersonalizado("Registro Usuario",Color.white);
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        add(lbl_tipo, gbc);   
        lbl_tipo = new LabelPersonalizado("Registro Usuario", color);
        gbc.gridx = 1; 
        gbc.gridy = 0; 
        add(lbl_tipo, gbc); 
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
        //AGREGAR LOS CAMPOS A LLENAR
        txt_nombre = new PlaceholderTextField("Nombre");
        gbc.gridx = 0; 
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txt_nombre, gbc);
        //AGREGAR LOS CAMPOS A LLENAR
        txt_apellido_paterno = new PlaceholderTextField("Apellido paterno");
        gbc.gridx = 0; 
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txt_apellido_paterno, gbc);
        txt_apellido_materno = new PlaceholderTextField("Apellido materno");
        gbc.gridx = 0; 
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txt_apellido_materno, gbc);
        txt_nombre_usuario = new PlaceholderTextField("Usuario");
        gbc.gridx = 1; 
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txt_nombre_usuario, gbc);
        txt_contrasena = new PlaceholderTextField("Contraseña");
        gbc.gridx = 1; 
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txt_contrasena, gbc);
        
        //AGREGAR BOTONES       
        btn_agregar = new RoundedButton("Agregar");
        gbc.gridx = 2; 
        gbc.gridy = 1;
        add(btn_agregar, gbc);
        btn_cancelar = new RoundedButton("Cancelar");
        gbc.gridx = 2; 
        gbc.gridy = 2;
        add(btn_cancelar, gbc);
        setVisible(true);
        
        
        btn_agregar.addActionListener(e ->{
            if (!Validaciones.validarNombres(txt_nombre.getText())) {
                JOptionPane.showMessageDialog(this,"El nombre no cumple con el formato");
                return;
            }
            if (!Validaciones.validarNombres(txt_nombre.getText())) {
                JOptionPane.showMessageDialog(this,"El nombre no cumple con el formato");
                return;
            }
            if (!Validaciones.validarNombres(txt_apellido_paterno.getText())) {
                JOptionPane.showMessageDialog(this,"El apellido paterno no cumple con el formato");
                return;
            }
            if (!Validaciones.validarNombres(txt_apellido_materno.getText())) {
                JOptionPane.showMessageDialog(this,"El apellido materno no cumple con el formato");
                return;
            }
        });
        
        btn_cancelar.addActionListener(e ->{
            new VOpcionesEmpleado(null).setVisible(true);
            this.dispose();
        });
    }
    
       
}
