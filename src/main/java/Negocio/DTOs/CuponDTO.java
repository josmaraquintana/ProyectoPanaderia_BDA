/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import java.time.LocalDate;

/**
 *
 * @author RAMSES
 */
public class CuponDTO {
    
    private String nombre;
    private double desc;
    private LocalDate vigencia;
    private int max_usos;

    public CuponDTO() {
    }

    public CuponDTO(String nombre, double desc, LocalDate vigencia, int max_usos) {
        this.nombre = nombre;
        this.desc = desc;
        this.vigencia = vigencia;
        this.max_usos = max_usos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDesc() {
        return desc;
    }

    public void setDesc(double desc) {
        this.desc = desc;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public int getMax_usos() {
        return max_usos;
    }

    public void setMax_usos(int max_usos) {
        this.max_usos = max_usos;
    }

    @Override
    public String toString() {
        return "CuponDTO{" + "nombre=" + nombre + ", desc=" + desc + ", vigencia=" + vigencia + ", max_usos=" + max_usos + '}';
    }
    
    
    
}
