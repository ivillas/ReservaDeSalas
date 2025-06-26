package com.practica.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.practica.modelo.Empleado;
import com.practica.persistencia.ConfiguracionBBDD;
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
    

/**
 * Verifica si un empleado con el DNI especificado existe en la base de datos.
 *
 * Este método realiza una consulta a la base de datos para comprobar si 
 * existe un registro en la tabla `empleados` con el DNI proporcionado.
 *
 * @param dni El DNI del empleado que se desea verificar.
 * @return `true` si el empleado existe en la base de datos, `false` en caso contrario.
 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
 *
 * Ejemplo de uso:
 * <pre>
 * boolean existe = GestorEmpleado.existeEmpleado("12345678A");
 * if (existe) {
 *     System.out.println("El empleado existe.");
 * } else {
 *     System.out.println("El empleado no existe.");
 * }
 * </pre>
 */


    public static boolean existeEmpleado(String dni) throws SQLException {
        String query = "SELECT COUNT(*) FROM empleados WHERE dni = ?";
        try (Connection connection = ConfiguracionBBDD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, dni);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Devuelve true si el conteo es mayor a 0
                }
            }
        }
        return false; // Devuelve false si no se encuentra el empleado
    }
}
    
    

