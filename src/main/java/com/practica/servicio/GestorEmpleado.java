package com.practica.servicio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.practica.modelo.Empleado;
import com.practica.persistencia.GestorBBDD;

public class GestorEmpleado {
	
    private static List<Empleado> empleados = new ArrayList<>();
	
	
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

}
