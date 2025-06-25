package com.practica.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.practica.modelo.Empleado;
import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;

/**
 * Clase que gestiona las operaciones de persistencia relacionadas con la Base de Datos.
 */

public class GestorBBDD {
	
	// Métodos para Empleado
	
    /**
     * Agrega un nuevo empleado a la base de datos.
     *
     * @param empleado El objeto `Empleado` que contiene los datos del empleado a agregar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
	
	public static void agregarEmpleado(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleados (dni, nombre, apellidos, email, departamento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConfiguracionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getDni());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidos());
            stmt.setString(4, empleado.getEmail());
            stmt.setString(5, empleado.getDepartamento());
            stmt.executeUpdate();
        }
    }
	
    /**
     * Modifica los datos de un empleado existente en la base de datos.
     *
     * @param empleado El objeto `Empleado` con los datos actualizados del empleado.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
	
	public static void modificarEmpleado(Empleado empleado) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, apellidos = ?, email = ?, departamento = ? WHERE dni = ?";

        try (Connection conn = ConfiguracionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellidos());
            stmt.setString(3, empleado.getEmail());
            stmt.setString(4, empleado.getDepartamento());
            stmt.setString(5, empleado.getDni());

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Empleado actualizado exitosamente.");
            } else {
                System.out.println("No se encontró un empleado con el DNI especificado.");
            }
        }
    }
	
    /**
     * Obtiene una lista de todos los empleados registrados en la base de datos.
     *
     * @return Una lista de objetos `Empleado` que representan a los empleados registrados.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
	
    public static List<Empleado> listarEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection conn = ConfiguracionBBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Empleado empleado = new Empleado(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("departamento")
                );
                empleados.add(empleado);
             }
        }
        return empleados;
    }

    /**
     * Elimina un empleado de la base de datos por su DNI.
     *
     * @param dni El DNI del empleado a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    public static void eliminarEmpleado(String dni) throws SQLException {
        String sql = "DELETE FROM empleados WHERE dni = ?";

        try (Connection conn = ConfiguracionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);

            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Empleado eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un empleado con el DNI especificado.");
            }
        }
    }

    // Métodos para SalaReuniones
    
    
    /**
     * Agrega una nueva sala de reuniones a la base de datos.
     *
     * @param sala El objeto `SalaReuniones` que contiene los datos de la sala a agregar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    

    public static void agregarSala(SalaReuniones sala) throws SQLException {
    	String sql = "INSERT INTO SalaReuniones (id, nombre, capacidad, disponible, recursosDisponibles) VALUES (?, ?, ?, ?, ?)";
    	try (Connection conn = ConfiguracionBBDD.getConnection();
    			PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setInt(1, sala.getId());
    		stmt.setString(2, sala.getNombre());
    		stmt.setInt(3, sala.getCapacidad());
    		stmt.setBoolean(4, sala.isDisponible());

    		// Aqui se convierte la lista de recursos a un string separado por comas
    		String recursos = String.join(",", sala.getRecursosDisponibles());
    		stmt.setString(5, recursos);

    		stmt.executeUpdate();
    	}
    }

    /**
     * Modifica los datos de una sala de reuniones existente en la base de datos.
     *
     * @param sala El objeto `SalaReuniones` con los datos actualizados de la sala.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void modificarSala(SalaReuniones sala) throws SQLException {
        String sql = "UPDATE SalaReuniones SET nombre = ?, capacidad = ?, disponible = ?, recursosDisponibles = ? WHERE id = ?";
        try (Connection conn = ConfiguracionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sala.getNombre());
            stmt.setInt(2, sala.getCapacidad());
            stmt.setBoolean(3, sala.isDisponible());

         // Aqui se convierte la lista de recursos a un string separado por comas
            String recursos = String.join(",", sala.getRecursosDisponibles());
            stmt.setString(4, recursos);

            stmt.setInt(5, sala.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Elimina una sala de reuniones de la base de datos por su ID.
     *
     * @param idSala El identificador único de la sala a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void eliminarSala(int idSala) throws SQLException {
        String sql = "DELETE FROM SalaReuniones WHERE id = ?";
        try (Connection conn = ConfiguracionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene una lista de todas las salas de reuniones registradas en la base de datos.
     *
     * @return Una lista de objetos `SalaReuniones` que representan las salas registradas.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static List<SalaReuniones> listarSalas() throws SQLException {
        List<SalaReuniones> salas = new ArrayList<>();
        String sql = "SELECT * FROM SalaReuniones";
        try (Connection conn = ConfiguracionBBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Aqui convertimos el string de recursos a una lista de strings cojiendo cada string separado por una coma.
                String recursosString = rs.getString("recursosDisponibles");
                List<String> recursos = recursosString != null && !recursosString.isEmpty()
                    ? Arrays.asList(recursosString.split(","))
                    : new ArrayList<>();

                SalaReuniones sala = new SalaReuniones(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("capacidad"),
                    rs.getBoolean("disponible"),
                    recursos
                );
                salas.add(sala);
            }
        }
        return salas;
    }
    

   
}
