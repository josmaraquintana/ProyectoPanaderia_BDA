/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.ItemCarrito;
import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.BOs.ICuponBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author RAMSES
 */
public class VResumenPedido extends JFrame {

    private ClienteDTO cliente;
    private double cupon = 0.0;
    private ICuponBO cuponBO;
    private FabricaBOs fabricaBO;
    private double subtotal;
    private double total;
    
    public VResumenPedido(ClienteDTO cliente, java.util.List<ItemCarrito> carrito) {
        this.cliente = cliente; 
        subtotal = 0.0;
        total = 0.0;
        setTitle("Resumen del pedido");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(205, 173, 144);
        Color cafe = new Color(139, 69, 19);
        Color dorado = new Color(230, 190, 120);

        getContentPane().setBackground(fondo);

        // ================= PANEL SUPERIOR (TITULO + LOGO) =================
        JPanel panel_superior = new JPanel(new BorderLayout());
        panel_superior.setOpaque(false);
        panel_superior.setBorder(new EmptyBorder(15, 25, 10, 25));

        LabelPersonalizado lbl_titulo =
                new LabelPersonalizado("Resumen del pedido", 26, Color.WHITE);
        lbl_titulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lbl_logo = new JLabel();
        lbl_logo.setHorizontalAlignment(SwingConstants.RIGHT);

        URL logo_URL = getClass().getResource("/img/icon.png");
        if (logo_URL != null) {
            ImageIcon icon = new ImageIcon(logo_URL);
            Image img = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));
        } else {
            System.err.println("No se encontró el logo");
        }

        panel_superior.add(lbl_titulo, BorderLayout.WEST);
        panel_superior.add(lbl_logo, BorderLayout.EAST);

        // ================= AREA DE RESUMEN =================
        JTextArea area_resumen = new JTextArea();
        area_resumen.setEditable(true);
        area_resumen.setLineWrap(true);
        area_resumen.setWrapStyleWord(true);
        area_resumen.setFont(new Font("Arial", Font.PLAIN, 14));
        area_resumen.setForeground(Color.WHITE);
        area_resumen.setBackground(cafe);
        area_resumen.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        //Obtenemos el resumen de su pedido
        String resumen = "Panes que se escogieron-->\n";
        
        for (ItemCarrito item : carrito) {
            resumen += item.getProducto().getNombre() + " -- $"+item.getProducto().getPrecio() + " -- cant: "+item.getCantidad()+"\n";
            subtotal += item.getSubtotal();
            total = subtotal;
        }
        resumen += "Notas para los productos:\n Escriba las notas que guste\n-----------------------------------------";
        //Hacemos que el mensaje aparezca en la pantalla
        area_resumen.setText(resumen);
        

        JScrollPane scroll_resumen = new JScrollPane(area_resumen);
        scroll_resumen.setBorder(BorderFactory.createEmptyBorder());
        scroll_resumen.setPreferredSize(new Dimension(520, 300));

        JPanel panel_resumen = new JPanel(new BorderLayout());
        panel_resumen.setBackground(cafe);
        panel_resumen.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel_resumen.add(scroll_resumen, BorderLayout.CENTER);

        // ================= PANEL TOTALES =================
        JPanel panel_totales = new JPanel();
        panel_totales.setOpaque(false);
        panel_totales.setLayout(new BoxLayout(panel_totales, BoxLayout.Y_AXIS));
        panel_totales.setBorder(new EmptyBorder(40, 30, 30, 30));
        
        LabelPersonalizado label_subtotal = new LabelPersonalizado("Subtotal:      $"+subtotal, 18, Color.WHITE);
        LabelPersonalizado label_cupon = new LabelPersonalizado("Cupón:      $0.0", 18, Color.WHITE);
        LabelPersonalizado label_total = new LabelPersonalizado("Total:      $"+total, 20, dorado);
        
        panel_totales.add(label_subtotal);
        panel_totales.add(Box.createVerticalStrut(15));
        panel_totales.add(label_cupon);
        panel_totales.add(Box.createVerticalStrut(15));
        panel_totales.add(label_total);

        // ================= PANEL CENTRAL =================
        JPanel panel_centro = new JPanel(new BorderLayout(20, 0));
        panel_centro.setOpaque(false);
        panel_centro.setBorder(new EmptyBorder(10, 20, 20, 20));

        panel_centro.add(panel_resumen, BorderLayout.CENTER);
        panel_centro.add(panel_totales, BorderLayout.EAST);

        // ================= PANEL INFERIOR =================
        JPanel panel_inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel_inferior.setOpaque(false);

        PlaceholderTextField txt_cupon = new PlaceholderTextField("Código del cupón");
        txt_cupon.setPreferredSize(new Dimension(200, 40));

        RoundedButton btn_cupon = new RoundedButton("Insertar Cupón");
        RoundedButton btn_cancelar = new RoundedButton("Cancelar");
        RoundedButton btn_aceptar = new RoundedButton("Aceptar");

        panel_inferior.add(txt_cupon);
        panel_inferior.add(btn_cupon);
        panel_inferior.add(btn_cancelar);
        panel_inferior.add(btn_aceptar);

        // ================= ENSAMBLADO =================
        add(panel_superior, BorderLayout.NORTH);
        add(panel_centro, BorderLayout.CENTER);
        add(panel_inferior, BorderLayout.SOUTH);

        setVisible(true);
        
        
        btn_cupon.addActionListener(e -> {
            
            
            try{
                String texto_cupon = txt_cupon.getText();
                
                if (texto_cupon.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe de ingresar un codigo de cupon.");
                    return;
                }
                
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(texto_cupon);
                
                if (!matcher.find()) {
                    JOptionPane.showMessageDialog(null, "No debe de ingresar letras o numero invalidos\nen el campo de cupon.");
                    return;
                }
                
                fabricaBO = new FabricaBOs();
                cuponBO = fabricaBO.obtenerCuponBO();
                
                int codigo = Integer.parseInt(texto_cupon);
                
                CuponDTO cupon_obtenido = cuponBO.obtenerCupon(codigo);
                
                label_cupon.setText("Cupón:      $"+cupon_obtenido.getDesc());
                
                total -= cupon_obtenido.getDesc();
                
                label_total.setText("Total:      $"+total);
                
            }catch(NegocioExcepcion ex){
                System.out.println("Hubo un error al querer insertar un cupon.");
                System.out.println(ex.getMessage());
            }
        });
    }
    

}
