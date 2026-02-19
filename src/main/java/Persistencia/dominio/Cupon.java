/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.dominio;

import java.time.LocalDate;

/**
 *
 * @author josma
 */
public class Cupon {
    
    private int id_cupon;
    private int id_pedido;
    private String nombre_cupon;
    private double descuento;
    private LocalDate vigencia;
    private int max_uso;

    public Cupon() {
    }

    public Cupon(int id_cupon, int id_pedido, String nombre_cupon, double descuento, LocalDate vigencia, int max_uso) {
        this.id_cupon = id_cupon;
        this.id_pedido = id_pedido;
        this.nombre_cupon = nombre_cupon;
        this.descuento = descuento;
        this.vigencia = vigencia;
        this.max_uso = max_uso;
    }

    public Cupon(int id_pedido, String nombre_cupon, double descuento, LocalDate vigencia, int max_uso) {
        this.id_pedido = id_pedido;
        this.nombre_cupon = nombre_cupon;
        this.descuento = descuento;
        this.vigencia = vigencia;
        this.max_uso = max_uso;
    }

    public int getId_cupon() {
        return id_cupon;
    }

    public void setId_cupon(int id_cupon) {
        this.id_cupon = id_cupon;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getNombre_cupon() {
        return nombre_cupon;
    }

    public void setNombre_cupon(String nombre_cupon) {
        this.nombre_cupon = nombre_cupon;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public int getMax_uso() {
        return max_uso;
    }

    public void setMax_uso(int max_uso) {
        this.max_uso = max_uso;
    }

    @Override
    public String toString() {
        return "Cupon{" + "id_cupon=" + id_cupon + ", id_pedido=" + id_pedido + ", nombre_cupon=" + nombre_cupon + ", descuento=" + descuento + ", vigencia=" + vigencia + ", max_uso=" + max_uso + '}';
    }
    
}
