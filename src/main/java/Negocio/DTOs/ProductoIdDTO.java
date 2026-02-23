/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author RAMSES
 */
public class ProductoIdDTO {
    
    private int id;
    private String nombre;

    public ProductoIdDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ProductoIdDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProductoIdDTO{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
    
}
