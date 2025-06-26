package com.practica.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.ConfiguracionBBDD;
import com.practica.persistencia.GestorBBDD;

public class GestorReserva {
	
	

public int altaReserva(Reserva reserva) throws SQLException {
    return GestorBBDD.altaReserva(reserva); // Llama al método de la capa de persistencia
}

/**
 * Verifica si ya existe una reserva para un empleado en una fecha y hora específicas.
 *
 * Este método consulta la base de datos para determinar si un empleado con el DNI proporcionado
 * ya tiene una reserva registrada en la fecha y hora indicadas.
 *
 * @param dniEmpleado El DNI del empleado.
 * @param fecha La fecha de la reserva (formato `LocalDate`).
 * @param horaInicio La hora de inicio de la reserva (formato `LocalTime`).
 * @return `true` si existe una reserva para el empleado en la fecha y hora especificadas, de lo contrario `false`.
 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
 */

public boolean existeReserva(String dniEmpleado, LocalDate fecha, LocalTime horaInicio) throws SQLException {
    String query = "SELECT COUNT(*) FROM reserva WHERE dniEmpleado = ? AND fecha = ? AND horaInicio = ?";
    try (Connection connection = ConfiguracionBBDD.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, dniEmpleado);
        statement.setDate(2, java.sql.Date.valueOf(fecha));
        statement.setTime(3, java.sql.Time.valueOf(horaInicio));
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
    }
    return false;
}


/**
 * Obtiene una lista de salas disponibles para una fecha y hora específicas.
 *
 * Este método consulta la base de datos para obtener todas las salas que no tienen
 * reservas registradas en la fecha y hora indicadas y que están marcadas como disponibles.
 *
 * @param fecha La fecha de la reserva (formato `LocalDate`).
 * @param horaInicio La hora de inicio de la reserva (formato `LocalTime`).
 * @return Una lista de objetos `SalaReuniones` que representan las salas disponibles.
 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
 */


public List<SalaReuniones> obtenerSalasLibres(LocalDate fecha, LocalTime horaInicio) throws SQLException {

    String query = "SELECT s.id, s.nombre, s.capacidad, s.disponible " +
                   "FROM SalaReuniones s " +
                   "WHERE s.id NOT IN ( " +
                   "    SELECT r.idSala " +
                   "    FROM reserva r " +
                   "    WHERE r.fecha = ? AND r.horaInicio = ? " +
                   ") AND s.disponible = true";

    List<SalaReuniones> salasLibres = new ArrayList<>();

    try (Connection connection = ConfiguracionBBDD.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setDate(1, java.sql.Date.valueOf(fecha));
        statement.setTime(2, java.sql.Time.valueOf(horaInicio));

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SalaReuniones sala = new SalaReuniones(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("capacidad"),
                    resultSet.getBoolean("disponible"),
                    new ArrayList<>() // Puedes agregar lógica para cargar recursos si es necesario
                );
                salasLibres.add(sala);
            }
        }
    }

    return salasLibres;
}


/**
 * Verifica si una sala de reuniones está disponible en un rango de tiempo específico.
 *
 * Este método realiza una consulta a la base de datos para comprobar si existen conflictos
 * de horario para una sala en una fecha y rango de tiempo determinados. Si no hay conflictos,
 * la sala se considera disponible.
 *
 * @param idSala El ID de la sala que se desea verificar.
 * @param fecha La fecha de la reserva.
 * @param horaInicio La hora de inicio de la reserva.
 * @param horaFin La hora de fin de la reserva.
 * @return `true` si la sala está disponible (sin conflictos), `false` en caso contrario.
 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
 *
 * Detalles de la consulta:
 * - La consulta verifica si existe alguna reserva en la misma sala (`idSala`) y fecha (`fecha`)
 *   que se solape con el rango de tiempo especificado (`horaInicio` a `horaFin`).
 * - Se consideran tres casos de solapamiento:
 *   1. La hora de inicio de la reserva existente es anterior a `horaFin` y su hora de fin es posterior a `horaInicio`.
 *   2. La hora de inicio de la reserva existente es anterior a `horaInicio` y su hora de fin es posterior a `horaFin`.
 *   3. La reserva existente está completamente contenida dentro del rango (`horaInicio` a `horaFin`).
 *
 * Ejemplo de uso:
 * <pre>
 * boolean disponible = verificarDisponibilidadSala(1, LocalDate.of(2023, 10, 15), 
 *     LocalTime.of(10, 0), LocalTime.of(12, 0));
 * if (disponible) {
 *     System.out.println("La sala está disponible.");
 * } else {
 *     System.out.println("La sala no está disponible.");
 * }
 * </pre>
 */

public static boolean verificarDisponibilidadSala(int idSala, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) throws SQLException {
    String query = "SELECT COUNT(*) FROM reserva " +
                   "WHERE idSala = ? AND fecha = ? " +
                   "AND ((horaInicio < ? AND horaFin > ?) " +
                   "OR (horaInicio < ? AND horaFin > ?) " +
                   "OR (horaInicio >= ? AND horaFin <= ?))";

    try (Connection connection = ConfiguracionBBDD.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, idSala);
        statement.setDate(2, java.sql.Date.valueOf(fecha));
        statement.setTime(3, java.sql.Time.valueOf(horaFin));
        statement.setTime(4, java.sql.Time.valueOf(horaInicio));
        statement.setTime(5, java.sql.Time.valueOf(horaInicio));
        statement.setTime(6, java.sql.Time.valueOf(horaFin));
        statement.setTime(7, java.sql.Time.valueOf(horaInicio));
        statement.setTime(8, java.sql.Time.valueOf(horaFin));

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0; // Devuelve true si no hay conflictos
            }
        }
    }
    return false; // Devuelve false si hay conflictos
}


    public void bajaReserva(int idReserva) {
    	
    	
    }
    public void modificarReserva(Reserva reserva) {
    	
    	
    }
    public List<Reserva> listarReservas() {
    	
    	return null; // Implementación pendiente
    }
    	
    	
  
}
