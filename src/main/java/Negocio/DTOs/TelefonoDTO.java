/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author josma
 */
public class TelefonoDTO {
    private int id; 
    private int id_cliente; 
    private String telefono;
    private String tipo;

    public TelefonoDTO(int id, int id_cliente, String telefono, String tipo) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.telefono = telefono;
        this.tipo = tipo; 
    }
    

    public TelefonoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    
}
