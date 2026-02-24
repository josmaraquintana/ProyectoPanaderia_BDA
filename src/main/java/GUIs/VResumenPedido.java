/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.ItemCarrito;
import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.ICuponBO;
import Negocio.BOs.IPedidoProgramadoBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.CuponDTO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.EmptyBorder;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class VResumenPedido extends JFrame {


    private ClienteBO clienteBO;
    private ClienteDTO cliente;
    private PedidoBO pedido;
    private TelefonoBO telefono;
    private double cupon = 0.0;
    private ICuponBO cuponBO;
    private FabricaBOs fabricaBO;
    private double subtotal;
    private double total;
    private List<CuponDTO>  lista_cupones;
    private String notas;
    private UsuarioBO usuarioBO;
    private JFrame ventanaAnterior;
    

    public VResumenPedido(PedidoBO pedido,ClienteDTO cliente,TelefonoBO telefono, List<ItemCarrito> carrito, ClienteBO clienteBO, UsuarioBO usuarioBO, JFrame ventanaAnterior) {
        this.telefono = telefono;
        this.ventanaAnterior = ventanaAnterior;
        this.usuarioBO = usuarioBO;
        this.pedido = pedido;
        this.cliente = cliente; 
        lista_cupones = new ArrayList<>();
        fabricaBO = new FabricaBOs();
        subtotal = 0.0;
        total = 0.0;
        notas = "";
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
        notas = area_resumen.getText();

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
        
        //Realizamos el pedido
        btn_aceptar.addActionListener(e -> {
            //Obtenemos el BO para trabajar
            IPedidoProgramadoBO pedidoBO = fabricaBO.obtenerPedidoProgramadoBO();
            
            try{
                
                // 1. Obtenemos TODO el texto que hay en el área en el momento de dar clic
                String texto_completo = area_resumen.getText();
                String separador = "-----------------------------------------";
                String nota_final = ""; // Empezamos con una nota vacía por si acaso
                
                // 2. Buscamos dónde empieza la línea de guiones
                int indice = texto_completo.indexOf(separador);
                
                //Si si encontro la línea (el usuario no la borró por accidente)
                if (indice != -1) {
                    //Cortamos el texto desde donde termina el separador hasta el final
                    nota_final = texto_completo.substring(indice + separador.length());
                    
                    //Le quitamos los saltos de linea y espacios en blanco al inicio y al final
                    nota_final = nota_final.trim();
                    
                    //Evitamos que pase de 200 caracteres para la BD
                    if (nota_final.length() > 200) {
                        nota_final = nota_final.substring(0, 200);
                        System.out.println("La nota era muy larga y se recortó.");
                    }
                }
                
                //Realizamos los registros de pedido en la base de datos
                pedidoBO.realizarRegistrosPedidosClientesCupones(carrito, cliente, lista_cupones, subtotal, total, nota_final);
                JOptionPane.showMessageDialog(null, "Se realizo el pedido exitosamente");
                VOpcionesCliente menu_cliente = new VOpcionesCliente(pedido, cliente, telefono, clienteBO, usuarioBO, this);
                menu_cliente.setVisible(true);
                this.dispose();
                
            }catch(NegocioExcepcion ex){
                System.out.println("Hubo un error al querer hacer los registros del pedido.");
                System.out.println(ex.getMessage());
            }
            
        });
        
        
        //Cerrar ventana en caso de cambiar de cancelar
        btn_cancelar.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        });

        
        //Boton para agregar cupon 
        btn_cupon.addActionListener(e -> {
            
            //obtenemos del campo de texto el codigo del cupon que quiere canjear 
            try{
                String texto_cupon = txt_cupon.getText();
                //Validamos que no este vacio
                if (texto_cupon.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe de ingresar un codigo de cupon.");
                    return;
                }
                //Hacemos una expresion regular para saber si es que cumple la sintaxis
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(texto_cupon);
                
                if (!matcher.find()) {
                    JOptionPane.showMessageDialog(null, "No debe de ingresar letras o numero invalidos\nen el campo de cupon.");
                    return;
                }
                
                
                cuponBO = fabricaBO.obtenerCuponBO();
                //Convertimos a entero el codigo del texto
                int codigo = Integer.parseInt(texto_cupon);
                //Obtenemos el cupon que estamos buscando
                CuponDTO cupon_obtenido = cuponBO.obtenerCupon(codigo);
                //Lo agregamos a los cupones que usamos en este pedido
                lista_cupones.add(cupon_obtenido);
                //Establecemos los nuevos valores del cupon y actualizamos los valores en los campos de texto
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
