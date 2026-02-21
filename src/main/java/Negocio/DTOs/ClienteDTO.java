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

    public ClienteDTO(int edad, Date fecha_nacimiento) {
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public ClienteDTO() {
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
