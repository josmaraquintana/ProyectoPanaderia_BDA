/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import java.sql.Date;

/**
 *
 * @author RAMSES
 */
public class ClienteDTO extends UsuarioDTO{
    private int edad;
    private Date fecha_nacimiento; 
    private String calle;
    private int codigo_postal;
    private int numero_casa;
    private String colonia;

    public ClienteDTO(int edad, Date fecha_nacimiento, String calle, String colonia, int codigo_postal, int numero_casa) {
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.calle = calle;
        this.codigo_postal = codigo_postal;
        this.numero_casa = numero_casa;
        this.colonia = colonia;
    }
    
    public ClienteDTO() {
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public void setNumero_casa(int numero_casa) {
        this.numero_casa = numero_casa;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public int getNumero_casa() {
        return numero_casa;
    }

    public String getColonia() {
        return colonia;
    }
    
    
    
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

  
}
