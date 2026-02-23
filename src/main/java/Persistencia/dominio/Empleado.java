/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

/**
 *
 * @author josma
 */
public class Empleado extends Usuario {
    private int id_empleado;

    public Empleado() {
    }

    public Empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Empleado(int id_empleado, int id_usuario, String nombre_usuario, String contrasena, String nombres, String apellido_Paterno, String apellido_Materno) {
        super(id_usuario, nombre_usuario, contrasena, nombres, apellido_Paterno, apellido_Materno);
        this.id_empleado = id_empleado;
    }

    public Empleado(int id_empleado, String nombre_usuario, String contrasena, String nombres, String apellido_Paterno, String apellido_Materno) {
        super(nombre_usuario, contrasena, nombres, apellido_Paterno, apellido_Materno);
        this.id_empleado = id_empleado;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
           
    
}
