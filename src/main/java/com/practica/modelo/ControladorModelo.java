package com.practica.modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.practica.persistencia.GestorBBDD;

/**
 * Clase que actúa como controlador del modelo, gestionando las operaciones de negocio
 * para empleados, salas de reuniones y reservas.
 */
public class ControladorModelo {

    private static List<Empleado> empleados = new ArrayList<>();
    private List<SalaReuniones> salas = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    // Métodos para Empleado

    /**
     * Agrega un nuevo empleado a la lista.
     * 
     * @param empleado El empleado a agregar.
     * @throws SQLException 
     */
    public static void altaEmpleado(Empleado empleado) throws SQLException {
        GestorBBDD.agregarEmpleado(empleado);
    }

    /**
     * Elimina un empleado de la lista.
     * 
     * @param empleado El empleado a eliminar.
     * @throws SQLException 
     */
    public static void bajaEmpleado(String DNI) throws SQLException {
        GestorBBDD.eliminarEmpleado(DNI);
    }

    /**
     * Modifica un empleado existente.
     * 
     * @param index Índice del empleado a modificar.
     * @param empleado El nuevo empleado con los datos actualizados.
     * @throws SQLException 
     */
    public static void modificarEmpleado(int index, Empleado empleado) throws SQLException {
        GestorBBDD.modificarEmpleado(empleado);
    }

    /**
     * Obtiene la lista de empleados.
     * 
     * @return Lista de empleados.
     */
    public static List<Empleado> listaEmpleados() {
        try {
            // Obtiene la lista de empleados desde la base de datos
            empleados = GestorBBDD.listarEmpleados();
        } catch (SQLException e) {
            System.err.println("Error al obtener la lista de empleados: " + e.getMessage());
        }
        return empleados;
    }
    
    public static void mostrarEmpleados() {
        try {
    		for (Empleado empleado : GestorBBDD.listarEmpleados()) {
    			System.out.println(empleado);
        	}
         } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
         }
    }


    /*
    
    public static void mostrarEmpleados() {
    	if(empleados.isEmpty()) {
			System.out.println("No hay empleados registrados.");
		} else {
			System.out.println("Lista de empleados:\n");
		}
		for (Empleado empleado : empleados) {
			System.out.println(empleado);
    	}
       
    }
    
    */
    // Métodos para SalaReuniones

    /**
     * Agrega una nueva sala de reuniones a la lista.
     * 
     * @param sala La sala de reuniones a agregar.
     */
    public void altaSala(SalaReuniones sala) {
        salas.add(sala);
    }

    /**
     * Elimina una sala de reuniones de la lista.
     * 
     * @param sala La sala de reuniones a eliminar.
     */
    public void bajaSala(SalaReuniones sala) {
        salas.remove(sala);
    }

    /**
     * Modifica una sala de reuniones existente.
     * 
     * @param index Índice de la sala a modificar.
     * @param sala La nueva sala de reuniones con los datos actualizados.
     */
    public void modificarSala(int index, SalaReuniones sala) {
        salas.set(index, sala);
    }

    /**
     * Obtiene la lista de salas de reuniones.
     * 
     * @return Lista de salas de reuniones.
     */
    public List<SalaReuniones> listarSalas() {
        return salas;
    }

    // Métodos para Reserva

    /**
     * Agrega una nueva reserva a la lista.
     * 
     * @param reserva La reserva a agregar.
     */
    public void altaReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    /**
     * Elimina una reserva de la lista.
     * 
     * @param reserva La reserva a eliminar.
     */
    public void bajaReserva(Reserva reserva) {
        reservas.remove(reserva);
    }

    /**
     * Modifica una reserva existente.
     * 
     * @param index Índice de la reserva a modificar.
     * @param reserva La nueva reserva con los datos actualizados.
     */
    public void modificarReserva(int index, Reserva reserva) {
        reservas.set(index, reserva);
    }

    /**
     * Obtiene la lista de reservas.
     * 
     * @return Lista de reservas.
     */
    public List<Reserva> listarReservas() {
        return reservas;
    }
}


