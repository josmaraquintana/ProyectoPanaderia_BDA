/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author RAMSES
 */
public class PedidoEstadoDTO {
    
    private int id;
    private String resumen;
    private String estado;

    public PedidoEstadoDTO(int id, String resumen, String estado) {
        this.id = id;
        this.resumen = resumen;
        this.estado = estado;
    }

    public PedidoEstadoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PedidoEstadoDTO{" + "id=" + id + ", resumen=" + resumen + ", estado=" + estado + '}';
    }
    
    
    
}
