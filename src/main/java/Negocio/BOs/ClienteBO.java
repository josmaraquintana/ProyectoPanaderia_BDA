/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.BOs;
import org.mindrot.jbcrypt.BCrypt;
import Negocio.DTOs.ClienteDTO;
import NegocioException.NegocioExcepcion;
import Persistencia.DAO.*;
import Persistencia.DAO.IPedidoProgramadoDAO;
import PersistenciaException.PersistenciaExcepcion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author josma
 */
public class ClienteBO implements IClienteBO{
    private final IClienteDAO clienteDAO; //para conectarnos a la capa de datos
    private final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    public ClienteBO(IClienteDAO cliente) {
        this.clienteDAO = cliente; //inyeccion de dependencias
    }
    
    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioExcepcion{
        
        if(cliente.getNombre_usuario() == null || cliente.getNombre_usuario().isBlank()){
            throw new NegocioExcepcion("Este campo es obligatorio");
        }
        
        if(cliente.getContrasena() == null || cliente.getContrasena().isBlank()){
            throw new NegocioExcepcion("Ingrese una contrasena");
        }
        //AQUI ES DONDE VAMOS A ENCRIPTAR LA CONTRASENA
        //HOLA CHICOS PARA QUE ESTO NO LES MARQUE ERROR TIENEN QUE METERSE AL POM E INSERTAR LA DEPENDENCIA
        //CLARO SI EL GITHUB YA LES ENVIA TODO CHIDO NO HACEN NADA Y ESTO NO LES MARCA ERROR
        String passwordEncriptada = BCrypt.hashpw(cliente.getContrasena(), BCrypt.gensalt());
        //Le cambiamos la contraseña al cliente por la encriptada
        cliente.setContrasena(passwordEncriptada);
        if(cliente.getEdad() <= 0){
            throw new NegocioExcepcion("Ingrese una edad valida");
        }
        
        if(cliente.getFecha_nacimiento() == null){
            throw new NegocioExcepcion("Ingrese una fecha valida");
        }
        try{
            clienteDAO.registrarUsuario(cliente);
        }catch(PersistenciaExcepcion ex){
            throw new NegocioExcepcion("Error al registrar al cliente " + ex.getMessage());
        }
        
    }
    
    @Override
    public ClienteDTO obtenerCliente(int id_cliente) throws NegocioExcepcion{
        if (id_cliente <= 0) {
            throw new NegocioExcepcion("Id de usuario invalido");
        }
        
        try{
            ClienteDTO cliente = clienteDAO.obtenerClientePorUsuario(id_cliente);
            
            if (cliente == null) {
                throw new NegocioExcepcion("No eciste el cliente asociado");
                
            }
            return cliente;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioExcepcion("Error al obtener cliente");
        }
    }
    
    @Override
    public void actualizarCliente(ClienteDTO cliente) throws NegocioExcepcion {

        if (cliente.getId_cliente() <= 0) {
            throw new NegocioExcepcion("Id de cliente inválido");
        }

        if (cliente.getEdad() <= 0) {
            throw new NegocioExcepcion("Edad inválida");
        }

        if (cliente.getCalle() == null || cliente.getCalle().isBlank()) {
            throw new NegocioExcepcion("La calle es obligatoria");
        }

        if (cliente.getColonia() == null || cliente.getColonia().isBlank()) {
            throw new NegocioExcepcion("La colonia es obligatoria");
        }

        if (cliente.getCodigo_postal() <= 0) {
            throw new NegocioExcepcion("Código postal inválido");
        }

        try {
            clienteDAO.actualizarCliente(cliente);
        } catch (PersistenciaExcepcion ex) {
            LOG.log(Level.SEVERE, "Error al actualizar cliente", ex);
            throw new NegocioExcepcion("No se pudo actualizar el cliente");
        }
    }
}
