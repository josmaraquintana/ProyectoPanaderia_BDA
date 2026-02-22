/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import ClasesEnum.EstadoPedido;
import java.sql.Date;

/**
 *
 * @author RAMSES
 */
public class PedidoDTO {
     private int idPedido;
    private Date fecha;
    private EstadoPedido estado;
    private String tipo;

    public PedidoDTO(int idPedido, Date fecha, EstadoPedido estado, String tipo) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.tipo = tipo;
    }

    public PedidoDTO() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
