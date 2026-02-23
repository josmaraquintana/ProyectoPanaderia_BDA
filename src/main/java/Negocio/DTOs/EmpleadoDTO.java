/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author josma
 */
public class EmpleadoDTO extends UsuarioDTO {
    private int id_empleado;

    public EmpleadoDTO(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public EmpleadoDTO() {
    }
    
    
}
