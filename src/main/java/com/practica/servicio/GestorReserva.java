package com.practica.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.ConfiguracionBBDD;
import com.practica.persistencia.GestorBBDD;

/**
 * Clase que gestiona las operaciones relacionadas con las reservas de salas de
 * reuniones. Proporciona métodos para registrar, modificar, cancelar y listar
 * reservas, así como verificar la disponibilidad de salas.
 */
public class GestorReserva {

	/**
	 * Registra una nueva reserva en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	/**
	 * Registra una nueva reserva en el sistema.
	 * 
	 * Este método solicita al usuario los datos necesarios para crear una reserva,
	 * como el DNI del empleado, el ID de la sala, la fecha, la hora de inicio y la
	 * hora de fin. Luego, crea un objeto de tipo `Reserva` y lo registra en el
	 * sistema utilizando la clase `GestorReserva`.
	 *
	 * @param scanner Objeto `Scanner` para leer la entrada del usuario.
	 * 
	 *                Pasos: 1. Solicita al usuario el DNI del empleado. 2. Solicita
	 *                el ID de la sala. 3. Solicita la fecha de la reserva en
	 *                formato `YYYY-MM-DD`. 4. Solicita la hora de inicio en formato
	 *                `HH:MM`. 5. Solicita la hora de fin en formato `HH:MM`. 6.
	 *                Crea un objeto `Reserva` con los datos ingresados. 7. Intenta
	 *                registrar la reserva en el sistema utilizando `GestorReserva`.
	 *                8. Si el registro es exitoso, muestra el ID generado. Si
	 *                ocurre un error, muestra un mensaje de error.
	 * 
	 *                Validaciones: - Verifica que el DNI del empleado exista en la
	 *                base de datos y que su formato sea correcto (8 dígitos
	 *                seguidos de una letra). - Verifica que la fecha y hora de
	 *                inicio sean futuras. - Verifica que la hora de fin sea
	 *                posterior a la hora de inicio. - Verifica que no exista una
	 *                reserva para el mismo empleado en la misma fecha y hora.
	 *                Excepciones: - Captura cualquier excepción que ocurra durante
	 *                el registro de la reserva y muestra un mensaje de error al
	 *                usuario.
	 */

	public static void altaReserva(Scanner scanner) {
		System.out.println("Ingrese los datos de la reserva:");

		String dniEmpleado = null;
		final LocalDate[] fechaReserva = { null };
		final LocalTime[] horaInicioReserva = { null };

		// Validar que el DNI del empleado exista en la base de datos y su formato sea
		// correcto
		while (dniEmpleado == null) {
			System.out.print("DNI del empleado (o escriba 'salir' para cancelar): ");
			String dniIngresado = scanner.nextLine();

			if (dniIngresado.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			// Validar formato del DNI (ejemplo: 8 dígitos seguidos de una letra)
			if (!dniIngresado.matches("\\d{8}[A-Za-z]")) {
				System.out.println(
						"El DNI debe tener 8 dígitos seguidos de una letra. Inténtelo de nuevo o escriba 'salir' para cancelar");
				continue;
			}

			try {
				if (GestorEmpleado.existeEmpleado(dniIngresado)) {
					dniEmpleado = dniIngresado;
				} else {
					System.out.println(
							"El DNI ingresado no corresponde a ningún empleado. Por favor, inténtelo de nuevo o escriba 'salir' para cancelar");
				}
			} catch (Exception e) {
				System.out.println("Error al verificar el DNI: " + e.getMessage());
			}
		}

		// Validar entrada de fecha y hora
		boolean fechaHoraValida = false;
		while (!fechaHoraValida) {
			System.out.print("Fecha (YYYY-MM-DD) o escriba 'salir' para cancelar: ");
			String fecha = scanner.nextLine();
			if (fecha.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			System.out.print("Hora de inicio (HH:MM) o escriba 'salir' para cancelar: ");
			String horaInicio = scanner.nextLine();
			if (horaInicio.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			try {
				fechaReserva[0] = LocalDate.parse(fecha);
				horaInicioReserva[0] = LocalTime.parse(horaInicio);

				// Validar que la fecha y hora sean futuras
				if (fechaReserva[0].isBefore(LocalDate.now()) || (fechaReserva[0].isEqual(LocalDate.now())
						&& horaInicioReserva[0].isBefore(LocalTime.now()))) {
					System.out.println(
							"La fecha y hora deben ser posteriores a la actual. Inténtelo de nuevo o escriba 'salir' para cancelar");
				} else {
					fechaHoraValida = true;
				}
			} catch (Exception e) {
				System.out.println(
						"Formato de fecha u hora incorrecto. Por favor, inténtelo de nuevo o escriba 'salir' para cancelar");
			}
		}

		try {
			GestorReserva gestor = new GestorReserva();

			// Verificar si ya existe una reserva para el DNI, fecha y hora
			if (gestor.existeReserva(dniEmpleado, fechaReserva[0], horaInicioReserva[0])) {
				System.out.println("Ya existe una reserva para este empleado en la fecha y hora especificadas.");
				System.out.print("¿Desea cambiar la fecha y hora? (sí/no)  o escriba 'salir' para cancelar: ");
				String opcion = scanner.nextLine().trim().toLowerCase();
				if (opcion.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
					altaReserva(scanner); // Reiniciar el proceso
					return;
				} else {
					System.out.println("Operación cancelada.");
					return;
				}
			}

			// Solicitar hora de fin y validar
			final LocalTime[] horaFinReserva = { null };
			while (horaFinReserva[0] == null) {
				System.out.print("Hora de fin (HH:MM): ");
				String horaFin = scanner.nextLine();
				if (horaFin.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				try {
					horaFinReserva[0] = LocalTime.parse(horaFin);

					// Validar que la hora de fin sea posterior a la hora de inicio
					if (horaFinReserva[0].isBefore(horaInicioReserva[0])
							|| horaFinReserva[0].equals(horaInicioReserva[0])) {
						System.out.println(
								"La hora de fin debe ser posterior a la hora de inicio. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
						horaFinReserva[0] = null;
					}
				} catch (Exception e) {
					System.out.println(
							"Formato de hora incorrecto. Por favor, inténtelo de nuevo o escriba 'salir' para cancelar: ");
				}
			}

			// Mostrar salas libres considerando la hora de fin
			List<SalaReuniones> salasLibres = gestor.obtenerSalasLibres(fechaReserva[0], horaInicioReserva[0]);
			salasLibres.removeIf(sala -> {
				try {
					return !GestorReserva.verificarDisponibilidadSala(sala.getId(), fechaReserva[0],
							horaInicioReserva[0], horaFinReserva[0]);
				} catch (SQLException e) {
					System.err.println("Error al verificar disponibilidad de la sala: " + e.getMessage());
					return true; // Excluir la sala en caso de error
				}
			});

			if (salasLibres.isEmpty()) {
				System.out.println("No hay salas disponibles para la fecha y hora especificadas.");
				return;
			}

			System.out.println("Salas disponibles:");
			for (SalaReuniones sala : salasLibres) {
				System.out.println("ID: " + sala.getId() + ", Nombre: " + sala.getNombre() + ", Capacidad: "
						+ sala.getCapacidad());
			}

			System.out.print("Seleccione el ID de la sala o escriba 'salir' para cancelar: ");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			int idSala;
			while (true) {
				try {
					idSala = Integer.parseInt(input); // Convertir a entero si no es "salir"

					// Validar si el ID existe en la base de datos
					if (!GestorBBDD.existeSalaPorId(idSala)) {
						System.out.println(
								"El ID ingresado no corresponde a ninguna sala existente. Inténtelo de nuevo.");
						System.out.print("Seleccione el ID de la sala o escriba 'salir' para cancelar: ");
						input = scanner.nextLine().trim(); // Leer nueva entrada
						if (input.equalsIgnoreCase("salir")) {
							System.out.println("Operación cancelada.");
							return;
						}
						continue; // Volver al inicio del bucle
					}

					break; // Salir del bucle si el ID es válido
				} catch (NumberFormatException e) {
					if (input.equalsIgnoreCase("salir")) {
						System.out.println("Operación cancelada.");
						return;
					}
					System.out.println("Entrada inválida. Debe ser un número entero o 'salir'.");
					System.out.print("Seleccione el ID de la sala o escriba 'salir' para cancelar: ");
					input = scanner.nextLine().trim(); // Leer nueva entrada
				} catch (SQLException e) {
					System.err.println("Error al comprobar el ID en la base de datos: " + e.getMessage());
					return;
				}
			}

			Reserva reserva = new Reserva(dniEmpleado, idSala, fechaReserva[0], horaInicioReserva[0],
					horaFinReserva[0]);

			// Registrar la reserva
			int idGenerado = GestorBBDD.altaReserva(reserva);
			System.out.println("Reserva registrada exitosamente con ID: " + idGenerado + " pulsa enter.");

		} catch (Exception e) {
			System.err.println("Error al registrar la reserva: pulsa enter." + e.getMessage());
		}
		scanner.nextLine();
	}

	/**
	 * Verifica si ya existe una reserva para un empleado en una fecha y hora
	 * específicas.
	 *
	 * Este método consulta la base de datos para determinar si un empleado con el
	 * DNI proporcionado ya tiene una reserva registrada en la fecha y hora
	 * indicadas.
	 *
	 * @param dniEmpleado El DNI del empleado.
	 * @param fecha       La fecha de la reserva (formato `LocalDate`).
	 * @param horaInicio  La hora de inicio de la reserva (formato `LocalTime`).
	 * @return `true` si existe una reserva para el empleado en la fecha y hora
	 *         especificadas, de lo contrario `false`.
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
	 * Este método consulta la base de datos para obtener todas las salas que no
	 * tienen reservas registradas en la fecha y hora indicadas y que están marcadas
	 * como disponibles.
	 *
	 * @param fecha      La fecha de la reserva (formato `LocalDate`).
	 * @param horaInicio La hora de inicio de la reserva (formato `LocalTime`).
	 * @return Una lista de objetos `SalaReuniones` que representan las salas
	 *         disponibles.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	public List<SalaReuniones> obtenerSalasLibres(LocalDate fecha, LocalTime horaInicio) throws SQLException {

		String query = "SELECT s.id, s.nombre, s.capacidad, s.disponible " + "FROM SalaReuniones s "
				+ "WHERE s.id NOT IN ( " + "    SELECT r.idSala " + "    FROM reserva r "
				+ "    WHERE r.fecha = ? AND r.horaInicio = ? " + ") AND s.disponible = true";

		List<SalaReuniones> salasLibres = new ArrayList<>();

		try (Connection connection = ConfiguracionBBDD.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, java.sql.Date.valueOf(fecha));
			statement.setTime(2, java.sql.Time.valueOf(horaInicio));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					SalaReuniones sala = new SalaReuniones(resultSet.getInt("id"), resultSet.getString("nombre"),
							resultSet.getInt("capacidad"), resultSet.getBoolean("disponible"), new ArrayList<>() // Puedes
																													// agregar
																													// lógica
																													// para
																													// cargar
																													// recursos
																													// si
																													// es
																													// necesario
					);
					salasLibres.add(sala);
				}
			}
		}

		return salasLibres;
	}

	/**
	 * Verifica si una sala de reuniones está disponible en un rango de tiempo
	 * específico.
	 *
	 * Este método realiza una consulta a la base de datos para comprobar si existen
	 * conflictos de horario para una sala en una fecha y rango de tiempo
	 * determinados. Si no hay conflictos, la sala se considera disponible.
	 *
	 * @param idSala     El ID de la sala que se desea verificar.
	 * @param fecha      La fecha de la reserva.
	 * @param horaInicio La hora de inicio de la reserva.
	 * @param horaFin    La hora de fin de la reserva.
	 * @return `true` si la sala está disponible (sin conflictos), `false` en caso
	 *         contrario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 *
	 *                      Detalles de la consulta: - La consulta verifica si
	 *                      existe alguna reserva en la misma sala (`idSala`) y
	 *                      fecha (`fecha`) que se solape con el rango de tiempo
	 *                      especificado (`horaInicio` a `horaFin`). - Se consideran
	 *                      tres casos de solapamiento: 1. La hora de inicio de la
	 *                      reserva existente es anterior a `horaFin` y su hora de
	 *                      fin es posterior a `horaInicio`. 2. La hora de inicio de
	 *                      la reserva existente es anterior a `horaInicio` y su
	 *                      hora de fin es posterior a `horaFin`. 3. La reserva
	 *                      existente está completamente contenida dentro del rango
	 *                      (`horaInicio` a `horaFin`).
	 *
	 *                      Ejemplo de uso:
	 * 
	 *                      <pre>
	 *                      boolean disponible = verificarDisponibilidadSala(1, LocalDate.of(2023, 10, 15), LocalTime.of(10, 0),
	 *                      		LocalTime.of(12, 0));
	 *                      if (disponible) {
	 *                      	System.out.println("La sala está disponible.");
	 *                      } else {
	 *                      	System.out.println("La sala no está disponible.");
	 *                      }
	 *                      </pre>
	 */

	public static boolean verificarDisponibilidadSala(int idSala, LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin) throws SQLException {
		String query = "SELECT COUNT(*) FROM reserva " + "WHERE idSala = ? AND fecha = ? "
				+ "AND ((horaInicio < ? AND horaFin > ?) " + "OR (horaInicio < ? AND horaFin > ?) "
				+ "OR (horaInicio >= ? AND horaFin <= ?))";

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

	/**
	 * Cancela una reserva existente en el sistema.
	 *
	 * Este método solicita al usuario el ID de la reserva que desea cancelar,
	 * verifica si la operación fue exitosa y muestra un mensaje adecuado. Si el ID
	 * no existe, informa al usuario que no se encontró la reserva. realiza las
	 * verificaciones necesarias para asegurar que el ID de la reserva sea válido y
	 * que la reserva exista en el sistema. * @throws NumberFormatException Si el ID
	 * ingresado no es un número entero.
	 *
	 * @param scanner Objeto `Scanner` para leer la entrada del usuario.
	 */

	public static void cancelacionReserva(Scanner scanner) {
		System.out.print("Ingrese el ID de la reserva a cancelar (máximo 11 caracteres) o  'salir' para cancelar: ");
		String input = scanner.nextLine().trim();
		if (input.equalsIgnoreCase("salir")) {
			System.out.println("Operación cancelada.");
			return;
		}

		// Validar que el ID tenga un máximo de 11 caracteres
		if (input.length() > 11) {
			System.out.println("El ID no puede tener más de 11 caracteres.");
			cancelacionReserva(scanner);
		}

		int idReserva;
		try {
			idReserva = Integer.parseInt(input); // Convertir a entero
		} catch (NumberFormatException e) {
			System.out.println("El ID debe ser un número entero.");
			cancelacionReserva(scanner);
			return;
		}

		try {
			// Verificar si la reserva existe
			Reserva reserva = GestorBBDD.obtenerReservaPorId(idReserva);
			if (reserva == null) {
				System.out.println("No se encontró una reserva con el ID especificado.");
				cancelacionReserva(scanner);
				return;
			}

			// Mostrar los datos de la reserva
			System.out.println("Datos de la reserva:");
			System.out.println("ID: " + reserva.getIdReserva());
			System.out.println("DNI Empleado: " + reserva.getDniEmpleado());
			System.out.println("ID Sala: " + reserva.getIdSala());
			System.out.println("Fecha: " + reserva.getFecha());
			System.out.println("Hora Inicio: " + reserva.getHoraInicio());
			System.out.println("Hora Fin: " + reserva.getHoraFin());

			// Confirmar eliminación
			System.out.print("¿Está seguro de que desea cancelar esta reserva? (sí/no): ");
			String confirmacion = scanner.nextLine().trim().toLowerCase();
			if (!confirmacion.equals("sí") && !confirmacion.equals("si") || !confirmacion.equals("s")) {
				System.out.println("Operación cancelada.");
				return;
			}

			// Eliminar la reserva
			boolean exito = GestorBBDD.bajaReserva(idReserva);
			if (exito) {
				System.out.println("Reserva cancelada exitosamente.");
			} else {
				System.out.println("No se pudo cancelar la reserva. Inténtelo de nuevo.");
			}
		} catch (Exception e) {
			System.err.println("Error al cancelar la reserva: " + e.getMessage());
		}
	}

	/**
	 * Modifica los datos de una reserva existente en el sistema.
	 *
	 * Este método permite al usuario modificar los datos de una reserva existente,
	 * como la fecha, la hora de inicio y la hora de fin. Realiza validaciones para
	 * garantizar que los datos ingresados sean válidos y consistentes.
	 *
	 * @param scanner Objeto `Scanner` utilizado para leer la entrada del usuario
	 *                desde la consola.
	 *
	 *                Flujo del método: 1. Solicita al usuario el ID de la reserva y
	 *                valida que sea un número entero positivo. 2. Verifica si
	 *                existe una reserva con el ID proporcionado. Si no se
	 *                encuentra, solicita nuevamente el ID. 3. Muestra los datos
	 *                actuales de la reserva. 4. Permite modificar la fecha,
	 *                validando que no sea anterior a la fecha actual. 5. Permite
	 *                modificar la hora de inicio, validando que sea posterior a la
	 *                hora actual (si la fecha es el día actual) y que sea anterior
	 *                a la hora de fin. 6. Permite modificar la hora de fin,
	 *                validando que sea posterior a la hora de inicio. 7. Actualiza
	 *                la reserva en el sistema si todas las modificaciones son
	 *                válidas.
	 *
	 *                Validaciones realizadas: - El ID de la reserva debe ser un
	 *                número entero positivo. - La fecha no puede ser anterior a la
	 *                fecha actual. - La hora de inicio no puede ser anterior a la
	 *                hora actual si la fecha es el día actual. - La hora de inicio
	 *                debe ser anterior a la hora de fin. - La hora de fin debe ser
	 *                posterior a la hora de inicio.
	 *
	 *                verificar si ya existe una reserva para el DNI, fecha y hora
	 *                especificadas antes de modificar la reserva.
	 * 
	 *                Si ya existe una reserva, se le pregunta al usuario si desea
	 *                cambiar la fecha y hora. Si acepta, se reinicia el proceso de
	 *                alta de reserva.
	 * 
	 *                Excepciones manejadas: - `NumberFormatException`: Si el ID
	 *                ingresado no es un número entero. - `DateTimeParseException`:
	 *                Si la fecha u hora ingresada no tienen el formato correcto. -
	 *                `Exception`: Para manejar errores generales durante la
	 *                búsqueda o modificación de la reserva.
	 */

	public static void modificarReserva(Scanner scanner) {
		int idReserva = -1;
		Reserva reserva = null;

		// Validar que el ID de la reserva sea un entero y que exista
		while (true) {
			System.out.print("Ingrese el ID de la reserva a modificar o escriba 'salir' para cancelar: ");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			try {
				idReserva = Integer.parseInt(input);
				if (idReserva > 0) { // Asegurarse de que sea un ID válido (positivo)
					GestorReserva gestor = new GestorReserva();
					reserva = gestor.obtenerReservaPorId(idReserva); // Intentar obtener la reserva
					if (reserva != null) {
						break; // Salir del bucle si se encuentra la reserva
					} else {
						System.out.println("No se encontró una reserva con el ID especificado. Inténtelo de nuevo.");
					}
				} else {
					System.out.println("El ID debe ser un número entero positivo. Inténtelo de nuevo.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
			} catch (Exception e) {
				System.err.println("Error al buscar la reserva: " + e.getMessage());
			}
		}

		boolean horaFinOk = false;

		System.out.println("Datos actuales de la reserva:");
		System.out.println("DNI del empleado: " + reserva.getDniEmpleado());
		System.out.println("ID de la sala: " + reserva.getIdSala());
		System.out.println("Fecha: " + reserva.getFecha());
		System.out.println("Hora de inicio: " + reserva.getHoraInicio());
		System.out.println("Hora de fin: " + reserva.getHoraFin());

		// Validar y solicitar nueva fecha
		while (true) {
			System.out.print(
					"Nueva fecha (YYYY-MM-DD, deje en blanco para mantener el valor actual) o escriba 'salir' para cancelar:  ");
			String nuevaFecha = scanner.nextLine();
			if (nuevaFecha.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (nuevaFecha.isBlank())
				break;

			try {
				LocalDate fecha = LocalDate.parse(nuevaFecha);
				if (fecha.isBefore(LocalDate.now())) {
					System.out.println("La fecha no puede ser anterior a la actual.");
				} else {
					reserva.setFecha(fecha);
					break;
				}
			} catch (Exception e) {
				System.out.println("Formato de fecha incorrecto. Inténtelo de nuevo.");
			}
		}

		// Validar y solicitar nueva hora de inicio
		while (true) {
			if (horaFinOk)
				break;
			System.out.print(
					"Nueva hora de inicio (HH:MM, deje en blanco para mantener el valor actual) o escriba 'salir' para cancelar:  ");
			String nuevaHoraInicio = scanner.nextLine();
			if (nuevaHoraInicio.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (nuevaHoraInicio.isBlank())
				break;

			try {
				LocalTime horaInicio = LocalTime.parse(nuevaHoraInicio);

				// Validar que la nueva hora de inicio sea posterior a la hora actual si es el
				// mismo día
				if (reserva.getFecha().isEqual(LocalDate.now()) && horaInicio.isBefore(LocalTime.now())) {
					System.out.println("La hora de inicio no puede ser anterior a la hora actual.");
					continue;
				}

				// Validar que la nueva hora de inicio sea inferior a la hora de fin existente
				if (horaInicio.isAfter(reserva.getHoraFin()) || horaInicio.equals(reserva.getHoraFin())) {
					System.out
							.println("La nueva hora de inicio no puede ser posterior o igual a la hora de fin actual.");
					System.out.print("Ingrese una nueva hora de fin (HH:MM) o escriba 'salir' para cancelar:  ");
					while (true) {
						String nuevaHoraFin = scanner.nextLine();
						try {
							LocalTime horaFin = LocalTime.parse(nuevaHoraFin);
							if (horaFin.isAfter(horaInicio)) {
								reserva.setHoraFin(horaFin);
								horaFinOk = true;
								break;
							} else {
								System.out.println(
										"La hora de fin debe ser posterior a la nueva hora de inicio. Inténtelo de nuevo.");
							}
						} catch (Exception e) {
							System.out.println("Formato de hora incorrecto. Por favor, inténtelo de nuevo.");
						}
					}
				} else {
					reserva.setHoraInicio(horaInicio);
					break;
				}
			} catch (Exception e) {
				System.out.println("Formato de hora incorrecto. Inténtelo de nuevo.");
			}
		}

		// Validar y solicitar nueva hora de fin
		while (true) {
			if (horaFinOk)
				break;
			System.out.print(
					"Nueva hora de fin (HH:MM, deje en blanco para mantener el valor actual) o escriba 'salir' para cancelar:  ");
			String nuevaHoraFin = scanner.nextLine();
			if (nuevaHoraFin.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (nuevaHoraFin.isBlank())
				break;
			try {
				LocalTime horaFin = LocalTime.parse(nuevaHoraFin);
				if (horaFin.isAfter(reserva.getHoraInicio())) {
					reserva.setHoraFin(horaFin);
					break;
				} else {
					System.out.println("La hora de fin debe ser posterior a la hora de inicio. Inténtelo de nuevo.");
				}
			} catch (Exception e) {
				System.out.println("Formato de hora incorrecto. Por favor, inténtelo de nuevo.");
			}
		}

		try {
			GestorReserva gestor = new GestorReserva();

			// Verificar si ya existe una reserva para el DNI, fecha y hora
			if (gestor.existeReserva(reserva.getDniEmpleado(), reserva.getFecha(), reserva.getHoraInicio())) {
				System.out.println("Ya existe una reserva para este empleado en la fecha y hora especificadas.");
				System.out.print("¿Desea cambiar la fecha y hora? (sí/no)  o escriba 'salir' para cancelar: ");
				String opcion = scanner.nextLine().trim().toLowerCase();
				if (opcion.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
					altaReserva(scanner); // Reiniciar el proceso
					return;
				} else {
					System.out.println("Operación cancelada.");
					return;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al verificar la reserva existente: " + e.getMessage());
			return;
		}
		try {
			GestorBBDD.modificarReserva(reserva); // Actualizar la reserva
			System.out.println("Reserva modificada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error al modificar la reserva: " + e.getMessage());
		}
	}

	/**
	 * Obtiene una reserva por su ID.
	 *
	 * Este método delega la operación de búsqueda a la clase `GestorBBDD`, que
	 * interactúa directamente con la base de datos.
	 *
	 * @param idReserva El ID de la reserva a buscar.
	 * @return Un objeto `Reserva` con los datos de la reserva, o `null` si no se
	 *         encuentra.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	public Reserva obtenerReservaPorId(int idReserva) throws SQLException {
		return GestorBBDD.obtenerReservaPorId(idReserva); // Llama al método de la capa de persistencia
	}

	/**
	 * Obtiene una lista de todas las reservas registradas en el sistema.
	 *
	 * @return Una lista de objetos `Reserva` que representan las reservas
	 *         registradas.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	/**
	 * Lista todas las reservas registradas en el sistema.
	 *
	 * Este método obtiene las reservas desde el gestor y las muestra en la consola.
	 * Si no hay reservas registradas, informa al usuario.
	 */

	public static void listarReservas() {
		try {
			List<Reserva> reservas = GestorBBDD.listarReservas();

			if (reservas.isEmpty()) {
				System.out.println("No hay reservas registradas.");
				return;
			}

			System.out.println("\nListado de reservas:");
			for (Reserva reserva : reservas) {
				System.out.println("ID: " + reserva.getIdReserva() + ", DNI Empleado: " + reserva.getDniEmpleado()
						+ ", ID Sala: " + reserva.getIdSala() + ", Fecha: " + reserva.getFecha() + ", Hora Inicio: "
						+ reserva.getHoraInicio() + ", Hora Fin: " + reserva.getHoraFin());
			}
		} catch (Exception e) {
			System.err.println("Error al listar las reservas: " + e.getMessage());
		}
	}

	/**
	 * Obtiene una lista de todas las reservas registradas en el sistema.
	 *
	 * @return Una lista de objetos `Reserva` que representan las reservas
	 *         registradas.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public List<Reserva> listaReservas() throws SQLException {
		return GestorBBDD.listarReservas();
	}

	/**
	 * Elimina una reserva del sistema.
	 *
	 * @param idReserva El ID de la reserva a eliminar.
	 * @return `true` si la reserva fue eliminada exitosamente, `false` en caso
	 *         contrario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public static boolean bajaReserva(int idReserva) throws SQLException {
		return GestorBBDD.bajaReserva(idReserva); // Devuelve el resultado de la operación
	}

}
