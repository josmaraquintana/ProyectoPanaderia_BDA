/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.BOs;

import Negocio.DTOs.ClienteDTO;
import NegocioException.NegocioExcepcion;

/**
 *
 * @author josma
 */
public interface IClienteBO {

    public void registrarCliente(ClienteDTO cliente) throws NegocioExcepcion;

    public ClienteDTO obtenerCliente(int id_cliente) throws NegocioExcepcion;

    public void actualizarCliente(ClienteDTO cliente) throws NegocioExcepcion;

    public void inactivarCuenta(int idCliente) throws NegocioExcepcion;

}
