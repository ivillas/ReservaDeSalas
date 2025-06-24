package com.practica.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.practica.modelo.Empleado;

public class GestorBBDD {
	
	
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

}
