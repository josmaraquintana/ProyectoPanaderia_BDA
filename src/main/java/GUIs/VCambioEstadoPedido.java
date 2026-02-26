/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import Componentes.LabelPersonalizado;
import Componentes.PlaceholderTextField;
import Componentes.RoundedButton;
import Componentes.TablaSimplePanel;
import Negocio.BOs.ClienteBO;
import Negocio.BOs.IPedidoBO;
import Negocio.BOs.IPedidoProgramadoBO;
import Negocio.BOs.PedidoBO;
import Negocio.BOs.TelefonoBO;
import Negocio.BOs.UsuarioBO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.EmpleadoDTO;
import Negocio.DTOs.PedidoEstadoDTO;
import Negocio.fabrica.FabricaBOs;
import NegocioException.NegocioExcepcion;
import java.util.List;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author RAMSES
 */
public class VCambioEstadoPedido extends JFrame {
    private EmpleadoDTO empleado;
    private JFrame ventanaAnterior;
    private PlaceholderTextField txt_id_pedido;
    private JComboBox<String> cbx_estado_pedido; 
    private TablaSimplePanel tabla_pedidos;

    private RoundedButton btnSalir;
    private RoundedButton btnCambiarEstado;
    private List<PedidoEstadoDTO> lista_pedidos;
    private IPedidoBO pedidoBO;
    private IPedidoProgramadoBO pedidoPrograBO;
    private FabricaBOs fabricaBO;
    
    

    public VCambioEstadoPedido(EmpleadoDTO empleado, JFrame ventanaAnterior,   PedidoBO pedido,ClienteDTO cliente,TelefonoBO telefono, ClienteBO clienteBO, UsuarioBO usuarioBO) {
        fabricaBO = new FabricaBOs();
        this.ventanaAnterior = ventanaAnterior;
        this.empleado = empleado;
        pedidoBO = fabricaBO.obtenerPedidoBO();
        pedidoPrograBO = fabricaBO.obtenerPedidoProgramadoBO();
        
        setTitle("Cambiar Estado de Productos");
        // Aumenté un poco el alto (de 460 a 540) para acomodar el nuevo ComboBox sin aplastar la tabla
        setSize(700, 540); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Usamos BorderLayout como esquema principal para evitar que los elementos colapsen
        setLayout(new BorderLayout()); 
        getContentPane().setBackground(Color.decode("#c4a484"));

        Color colorTextoOscuro = new Color(110, 74, 46);

        /**
         * ZONA NORTE: ENCABEZADO Y LOGO
         */
        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.setOpaque(false);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        LabelPersonalizado lblTitulo = new LabelPersonalizado("Cambiar estado de pedidos", 34, Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34)); 
        pnlNorte.add(lblTitulo, BorderLayout.WEST);

        JLabel lblLogo = new JLabel();
        URL url = getClass().getResource("/img/icon.png");
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("[ LOGO ]");
        }
        pnlNorte.add(lblLogo, BorderLayout.EAST);
        
        add(pnlNorte, BorderLayout.NORTH);

        /**
         * ZONA CENTRO: FORMULARIO Y TABLA
         */
        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Le damos peso horizontal para que la columna se expanda y no se apriete
        gbc.gridx = 0; 
        gbc.weightx = 1.0; 

        // Etiqueta Id de producto
        LabelPersonalizado lblIdProducto = new LabelPersonalizado("Id del pedido", 14, colorTextoOscuro);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Solo ocupa el espacio necesario
        gbc.insets = new Insets(0, 0, 5, 0); 
        pnlCentro.add(lblIdProducto, gbc);

        // Input Id de producto
        txt_id_pedido = new PlaceholderTextField("");
        txt_id_pedido.setPreferredSize(new Dimension(250, 35));
        txt_id_pedido.setMinimumSize(new Dimension(250, 35)); 
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        pnlCentro.add(txt_id_pedido, gbc);
        
        if (empleado != null) {
            // --- NUEVO: Etiqueta y ComboBox de Estado ---
            LabelPersonalizado lblEstado = new LabelPersonalizado("Nuevo Estado", 14, colorTextoOscuro);
            gbc.gridy = 2; // Bajamos a la posición 2
            gbc.insets = new Insets(0, 0, 5, 0);
            pnlCentro.add(lblEstado, gbc);
            String[] opcionesEstado = {"Pendiente", "Listo", "Entregado", "Cancelado", "Desatendido"};
        
            cbx_estado_pedido = new JComboBox<>(opcionesEstado);
            cbx_estado_pedido.setPreferredSize(new Dimension(250, 35));
            cbx_estado_pedido.setMinimumSize(new Dimension(250, 35));
            cbx_estado_pedido.setBackground(Color.WHITE);
            gbc.gridy = 3; // Bajamos a la posición 3
            gbc.insets = new Insets(0, 0, 15, 0);
            pnlCentro.add(cbx_estado_pedido, gbc);
        }
        

        // Etiqueta Productos
        LabelPersonalizado lblProductos = new LabelPersonalizado("Pedidos", 14, colorTextoOscuro);
        gbc.gridy = 4; // Recorremos este al 4
        gbc.insets = new Insets(0, 0, 5, 0);
        pnlCentro.add(lblProductos, gbc);

        // Tabla Simple Panel
        String[] columnas = {"Id pedido", "Descripcion", "Estado"};
        tabla_pedidos = new TablaSimplePanel(columnas);
        
        //Actualizamos la tabla de los pedidos
        actualizarTablaPedidos();
        
        
        // Forzamos a que el JScrollPane muestre SIEMPRE la barra vertical
        Component[] componentes = tabla_pedidos.getComponents();
        for (Component c : componentes) {
            if (c instanceof JScrollPane) {
                ((JScrollPane) c).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        gbc.gridy = 5; // Recorremos la tabla al 5
        gbc.weighty = 1.0; // Permite que la tabla se expanda a lo alto
        gbc.fill = GridBagConstraints.BOTH; // Expande la tabla a lo ancho y a lo alto
        gbc.insets = new Insets(0, 40, 0, 40); // Márgenes a los lados de la tabla
        pnlCentro.add(tabla_pedidos, gbc);

        add(pnlCentro, BorderLayout.CENTER);

        /**
         * ==========================================
         * 3. ZONA SUR: BOTONES
         * ==========================================
         */
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlSur.setOpaque(false);

        Dimension tamanoBotones = new Dimension(160, 40); 

        btnSalir = new RoundedButton("Volver");
        btnSalir.setPreferredSize(tamanoBotones);
        pnlSur.add(btnSalir);

        btnCambiarEstado = new RoundedButton("Cambiar estado");
        btnCambiarEstado.setPreferredSize(tamanoBotones);
        pnlSur.add(btnCambiarEstado);

        add(pnlSur, BorderLayout.SOUTH);
        
        // Quitar el foco inicial
        SwingUtilities.invokeLater(() -> btnSalir.requestFocusInWindow());
        
        btnCambiarEstado.addActionListener(e -> {
            
            String id_string = txt_id_pedido.getText();
            
            if (id_string.isEmpty() || id_string == "") {
                JOptionPane.showMessageDialog(null, "El campo de id debe tener un id para esta funcion.");
                return;
            }
            
            if (empleado != null) {
                
                String estado = (String) cbx_estado_pedido.getSelectedItem();
                try{

                    boolean valor = pedidoBO.cambiarEstadoPedido(id_string, estado);

                    if (!valor) {
                        JOptionPane.showMessageDialog(null, "No se actualizo el pedido que buscaba.");
                        return;
                    }

                    actualizarTablaPedidos();

                }catch(NegocioExcepcion ex){

                    System.out.println("Hubo un error al quere modificar el estado del pedido.");
                    System.out.println(ex.getMessage());

                }
                
            }else{
                
                try{

                    boolean valor = pedidoPrograBO.cambiarEstadoPedido(id_string);

                    if (!valor) {
                        JOptionPane.showMessageDialog(null, "No se actualizo el pedido que buscaba.");
                        return;
                    }

                    actualizarTablaPedidos();

                }catch(NegocioExcepcion ex){

                    System.out.println("Hubo un error al quere modificar el estado del pedido.");
                    System.out.println(ex.getMessage());

                }
                
            }
            
            
        });
        
        btnSalir.addActionListener(e -> {
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            }
            this.dispose();
        });
        
        
    }
    
    private void actualizarTablaPedidos(){
        tabla_pedidos.limpiar();
        try{
            //Validar de donde viene si del menu de empleados o el de clientes mediante el uso de sus parametros
            if (empleado == null) {
                
                //Llenamos la tabla con los pedidos del cliente
                lista_pedidos = pedidoPrograBO.obtenerListaPedidosConResumen();
                if (lista_pedidos.isEmpty() || lista_pedidos == null) {
                    JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
                }
                for (PedidoEstadoDTO lista_pedido : lista_pedidos) {
                    tabla_pedidos.agregarFila(lista_pedido.getId(), lista_pedido.getResumen(), lista_pedido.getEstado());
                }
                
            }else{
                //Llenamos la tabla con los pedidos para el empleado
                lista_pedidos = pedidoBO.obtenerListaPedidosConResumen();
                if (lista_pedidos.isEmpty() || lista_pedidos == null) {
                    JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
                }
                for (PedidoEstadoDTO lista_pedido : lista_pedidos) {
                    tabla_pedidos.agregarFila(lista_pedido.getId(), lista_pedido.getResumen(), lista_pedido.getEstado());
                }
            }
            
            
        }catch(NegocioExcepcion ex){
            System.out.println("Hubo un error al obtener la lista para mostrar.");
            System.out.println(ex.getMessage());
        }
    }
}