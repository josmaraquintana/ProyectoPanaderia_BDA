/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

import Negocio.DTOs.UsuarioDTO;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author josmara, ramses, daniel
 */
public class Cliente extends Usuario{
    private int id_cliente; 
    private int edad;
    private Date fecha_nacimiento;
    private int codigo_postal;
    private String colonia;
    private int numero_casa;
    private String calle;

    public Cliente() {
    }

    public Cliente(int id_cliente, int edad, Date fecha_nacimiento, int codigo_postal, String colonia, int numero_casa, 
            String calle, int id_usuario, String nombre_usuario, String contrasena, String nombres, 
            String apellidoPaterno, String apellidoMaterno) {
        super(id_usuario, nombre_usuario, contrasena, nombres, apellidoPaterno, apellidoMaterno);
        this.id_cliente = id_cliente; 
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.codigo_postal = codigo_postal;
        this.colonia = colonia;
        this.numero_casa = numero_casa;
        this.calle = calle;
    }

    public Cliente(int edad, Date fecha_nacimiento, int codigo_postal, String colonia, 
            int numero_casa, String calle, String nombre_usuario, String contrasena, String nombres, 
            String apellidoPaterno, String apellidoMaterno) {
        super(nombre_usuario, contrasena, nombres, apellidoPaterno, apellidoMaterno);
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.codigo_postal = codigo_postal;
        this.colonia = colonia;
        this.numero_casa = numero_casa;
        this.calle = calle;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public int getNumero_casa() {
        return numero_casa;
    }

    public void setNumero_casa(int numero_casa) {
        this.numero_casa = numero_casa;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String toString() {
        return "Cliente{" + "edad=" + edad + ", fecha_nacimiento=" + fecha_nacimiento + ", codigo_postal=" + codigo_postal + ", colonia=" + colonia + ", numero_casa=" + numero_casa + ", calle=" + calle + '}';
    }
    
    

    
    
    
    
}
