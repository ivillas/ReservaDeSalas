package com.practica.main;


import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;
import com.practica.servicio.GestorEmpleado;
import com.practica.servicio.GestorReserva;
import com.practica.servicio.GestorSalaReuniones;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {

	/**
	 * Muestra el menú principal y gestiona la interacción con el usuario.
	 *
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	public void mostrarMenu() throws SQLException {

		// Implementación del menú principal

		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println();
			System.out.println();
			System.out.println("***********************************************");
			System.out.println("******    Gestor de Reserva De Salas     ******");
			System.out.println("***********************************************");
			System.out.println();
			System.out.println("\n           --- Menú Principal ---\n");
			System.out.println("******    1. Alta de empleado            ******");
			System.out.println("******    2. Baja de empleado            ******");
			System.out.println("******    3. Modificación de empleado    ******");
			System.out.println("******    4. Alta de sala                ******");
			System.out.println("******    5. Baja de sala                ******");
			System.out.println("******    6. Modificación de sala        ******");
			System.out.println("******    7. Realizar una reserva        ******");
			System.out.println("******    8. Cancelar reserva            ******");
			System.out.println("******    9. Modificar una reserva       ******");
			System.out.println("******    10. Listar empleados           ******");
			System.out.println("******    11. Listar salas               ******");
			System.out.println("******    12. Listar reservas            ******");
			System.out.println("******     0. Salir                      ******");
			System.out.print("\nSeleccione una opción: ");

			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				GestorEmpleado.altaEmpleado(scanner);
				break;
			case 2:
				GestorEmpleado.bajaEmpleado(scanner);
				break;
			case 3:
				GestorEmpleado.modificarEmpleado(scanner);
				break;
			case 4:
				GestorSalaReuniones.altaSala(scanner);
				break;
			case 5:
				GestorSalaReuniones.bajaSala(scanner);
				break;
			case 6:
				GestorSalaReuniones.modificarSala(scanner);
				break;
			case 7:
				altaReserva(scanner);
				break;
			case 8:
				cancelacionReserva(scanner);
				break;
			case 9:
				modificarReserva(scanner);
				break;
			case 10:
				GestorEmpleado.mostrarEmpleados();
				break;
			case 11:
			    GestorSalaReuniones.listarSalas(scanner);
				break;
			case 12:
				listarReservas();
				break;
			case 0:
				System.out.println("Saliendo del sistema...");
				break;
			default:
				System.out.println("Opción no válida. Intente nuevamente.");
			}
		} while (opcion != 0);

		scanner.close();
	}




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
	 *                Excepciones: - Captura cualquier excepción que ocurra durante
	 *                el registro de la reserva y muestra un mensaje de error al
	 *                usuario.
	 */

	private void altaReserva(Scanner scanner) {
		System.out.println("Ingrese los datos de la reserva:");

		String dniEmpleado = null;
		final LocalDate[] fechaReserva = { null };
		final LocalTime[] horaInicioReserva = { null };

		// Validar que el DNI del empleado exista en la base de datos
		while (dniEmpleado == null) {
			System.out.print("DNI del empleado: ");
			String dniIngresado = scanner.nextLine();
			try {
				if (GestorEmpleado.existeEmpleado(dniIngresado)) {
					dniEmpleado = dniIngresado;
				} else {
					System.out.println(
							"El DNI ingresado no corresponde a ningún empleado. Por favor, inténtelo de nuevo.");
				}
			} catch (Exception e) {
				System.out.println("Error al verificar el DNI: " + e.getMessage());
			}
		}

		// Validar entrada de fecha y hora
		boolean fechaHoraValida = false;
		while (!fechaHoraValida) {
			System.out.print("Fecha (YYYY-MM-DD): ");
			String fecha = scanner.nextLine();
			System.out.print("Hora de inicio (HH:MM): ");
			String horaInicio = scanner.nextLine();
			try {
				fechaReserva[0] = LocalDate.parse(fecha);
				horaInicioReserva[0] = LocalTime.parse(horaInicio);

				// Validar que la fecha y hora sean futuras
				if (fechaReserva[0].isBefore(LocalDate.now()) || (fechaReserva[0].isEqual(LocalDate.now())
						&& horaInicioReserva[0].isBefore(LocalTime.now()))) {
					System.out.println("La fecha y hora deben ser posteriores a la actual. Inténtelo de nuevo.");
				} else {
					fechaHoraValida = true;
				}
			} catch (Exception e) {
				System.out.println("Formato de fecha u hora incorrecto. Por favor, inténtelo de nuevo.");
			}
		}

		try {
			GestorReserva gestor = new GestorReserva();

			// Verificar si ya existe una reserva para el DNI, fecha y hora
			if (gestor.existeReserva(dniEmpleado, fechaReserva[0], horaInicioReserva[0])) {
				System.out.println("Ya existe una reserva para este empleado en la fecha y hora especificadas.");
				System.out.print("¿Desea cambiar la fecha y hora? (sí/no): ");
				String opcion = scanner.nextLine().trim().toLowerCase();

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
				try {
					horaFinReserva[0] = LocalTime.parse(horaFin);

					// Validar que la hora de fin sea posterior a la hora de inicio
					if (horaFinReserva[0].isBefore(horaInicioReserva[0])
							|| horaFinReserva[0].equals(horaInicioReserva[0])) {
						System.out
								.println("La hora de fin debe ser posterior a la hora de inicio. Inténtelo de nuevo.");
						horaFinReserva[0] = null;
					}
				} catch (Exception e) {
					System.out.println("Formato de hora incorrecto. Por favor, inténtelo de nuevo.");
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

			System.out.print("Seleccione el ID de la sala: ");
			int idSala = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer

			Reserva reserva = new Reserva(dniEmpleado, idSala, fechaReserva[0], horaInicioReserva[0],
					horaFinReserva[0]);

			// Registrar la reserva
			int idGenerado = gestor.altaReserva(reserva);
			System.out.println("Reserva registrada exitosamente con ID: " + idGenerado);

		} catch (Exception e) {
			System.err.println("Error al registrar la reserva: " + e.getMessage());
		}
	}

	/**
	 * Cancela una reserva existente en el sistema.
	 *
	 * Este método solicita al usuario el ID de la reserva que desea cancelar,
	 * verifica si la operación fue exitosa y muestra un mensaje adecuado. Si el ID
	 * no existe, informa al usuario que no se encontró la reserva.
	 *
	 * @param scanner Objeto `Scanner` para leer la entrada del usuario.
	 */

	private void cancelacionReserva(Scanner scanner) {
		System.out.print("Ingrese el ID de la reserva a cancelar: ");
		int idReserva = scanner.nextInt();
		scanner.nextLine(); // Limpiar el buffer

		try {
			GestorReserva gestor = new GestorReserva();
			boolean exito = gestor.bajaReserva(idReserva); // Verificar si se eliminó la reserva
			if (exito) {
				System.out.println("Reserva cancelada exitosamente.");
			} else {
				System.out.println("No se encontró una reserva con el ID especificado.");
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
	 *                Excepciones manejadas: - `NumberFormatException`: Si el ID
	 *                ingresado no es un número entero. - `DateTimeParseException`:
	 *                Si la fecha u hora ingresada no tienen el formato correcto. -
	 *                `Exception`: Para manejar errores generales durante la
	 *                búsqueda o modificación de la reserva.
	 */

	private void modificarReserva(Scanner scanner) {
		int idReserva = -1;
		Reserva reserva = null;

		// Validar que el ID de la reserva sea un entero y que exista
		while (true) {
			System.out.print("Ingrese el ID de la reserva a modificar: ");
			String input = scanner.nextLine();
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
			System.out.print("Nueva fecha (YYYY-MM-DD, deje en blanco para mantener el valor actual): ");
			String nuevaFecha = scanner.nextLine();
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
			System.out.print("Nueva hora de inicio (HH:MM, deje en blanco para mantener el valor actual): ");
			String nuevaHoraInicio = scanner.nextLine();
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
					System.out.print("Ingrese una nueva hora de fin (HH:MM): ");
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
			System.out.print("Nueva hora de fin (HH:MM, deje en blanco para mantener el valor actual): ");
			String nuevaHoraFin = scanner.nextLine();
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
			gestor.modificarReserva(reserva); // Actualizar la reserva
			System.out.println("Reserva modificada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error al modificar la reserva: " + e.getMessage());
		}
	}

	/**
	 * Lista las salas de reuniones registradas en el sistema.
	 */




	/**
	 * Lista todas las reservas registradas en el sistema.
	 *
	 * Este método obtiene las reservas desde el gestor y las muestra en la consola.
	 * Si no hay reservas registradas, informa al usuario.
	 */
	private void listarReservas() {
		try {
			GestorReserva gestor = new GestorReserva();
			List<Reserva> reservas = gestor.listarReservas();

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

}
