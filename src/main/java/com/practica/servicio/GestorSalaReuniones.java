
package com.practica.servicio;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;

/**
 * Clase que gestiona las operaciones relacionadas con las salas de reuniones.
 */
public class GestorSalaReuniones {

	/**
	 * Registra una nueva sala de reuniones en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */
	public static void altaSala(Scanner scanner) {
		System.out.println("Ingrese los datos de la sala:");

		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();

		System.out.print("Capacidad: ");
		int capacidad = scanner.nextInt();
		scanner.nextLine(); // Limpiar el buffer

		System.out.print("¿Está disponible para reservar? (sí/no): ");
		String disponibleInput = scanner.nextLine().trim().toLowerCase();
		boolean disponible = disponibleInput.equals("sí") || disponibleInput.equals("si")
				|| disponibleInput.equals("s");

		System.out.print("Recursos disponibles (separados por comas): ");
		String recursosInput = scanner.nextLine();
		List<String> recursos = Arrays.asList(recursosInput.split(","));

		SalaReuniones sala = new SalaReuniones(0, nombre, capacidad, disponible, recursos);

		try {
			GestorBBDD.agregarSala(sala);
			System.out.println("Sala registrada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error al registrar la sala: " + e.getMessage());
		}
	}

	/**
	 * Elimina una sala de reuniones del sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void bajaSala(Scanner scanner) {
		System.out.print("Ingrese el ID de la sala a eliminar: ");
		int idSala = scanner.nextInt();
		scanner.nextLine(); // Limpiar el buffer

		try {
			GestorBBDD.eliminarSala(idSala);
			System.out.println("Sala eliminada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error al eliminar la sala: " + e.getMessage());
		}
	}

	/**
	 * Modifica los datos de una sala de reuniones existente.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void modificarSala(Scanner scanner) {
		System.out.print("Ingrese el ID de la sala a modificar: ");
		int idSala = scanner.nextInt();
		scanner.nextLine(); // Limpiar el buffer

		SalaReuniones salaAModificar = null;

		try {
			for (SalaReuniones sala : GestorBBDD.listarSalas()) {
				if (sala.getId() == idSala) {
					salaAModificar = sala;
					break;
				}
			}

			if (salaAModificar != null) {
				System.out.println(
						"Sala encontrada. Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");

				System.out.print("Nombre (" + salaAModificar.getNombre() + "): ");
				String nuevoNombre = scanner.nextLine();
				if (!nuevoNombre.isBlank()) {
					salaAModificar.setNombre(nuevoNombre);
				}

				System.out.print("Capacidad (" + salaAModificar.getCapacidad() + "): ");
				String nuevaCapacidad = scanner.nextLine();
				if (!nuevaCapacidad.isBlank()) {
					salaAModificar.setCapacidad(Integer.parseInt(nuevaCapacidad));
				}

				System.out.print("¿Está disponible para reservar? (sí/no) ("
						+ (salaAModificar.isDisponible() ? "sí" : "no") + "): ");
				String nuevaDisponibilidad = scanner.nextLine().trim().toLowerCase();
				if (!nuevaDisponibilidad.isBlank()) {
					salaAModificar.setDisponible(nuevaDisponibilidad.equals("sí") || nuevaDisponibilidad.equals("si")
							|| nuevaDisponibilidad.equals("s"));
				}

				System.out.print(
						"Recursos disponibles (" + String.join(", ", salaAModificar.getRecursosDisponibles()) + "): ");
				String nuevosRecursos = scanner.nextLine();
				if (!nuevosRecursos.isBlank()) {
					salaAModificar.setRecursosDisponibles(Arrays.asList(nuevosRecursos.split(",")));
				}

				GestorBBDD.modificarSala(salaAModificar);
				System.out.println("Sala modificada exitosamente.");
			} else {
				System.out.println("No se encontró una sala con el ID proporcionado.");
			}
		} catch (Exception e) {
			System.err.println("Error al modificar la sala: " + e.getMessage());
		}
	}

	/**
	 * Lista todas las salas de reuniones registradas en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void listarSalas(Scanner scanner) {
		try {
			List<SalaReuniones> salas = GestorBBDD.listarSalas();

			if (salas.isEmpty()) {
				System.out.println("No hay salas registradas.");
				return;
			}

			System.out.println("\nListado de salas:");
			for (SalaReuniones sala : salas) {
				System.out.println("ID: " + sala.getId() + ", Nombre: " + sala.getNombre() + ", Capacidad: "
						+ sala.getCapacidad() + ", Disponible: " + (sala.isDisponible() ? "Sí" : "No"));
			}

			System.out.print("\n¿Desea realizar una reserva? (sí/no): ");
			String opcion = scanner.nextLine().trim().toLowerCase();

			if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
				System.out.println("Procediendo a realizar una reserva...");
				// Aquí se llamaa al método para realizar la reserva
				GestorReserva.altaReserva(scanner);
			} else {
				System.out.println("Regresando al menú principal...");
			}
		} catch (Exception e) {
			System.err.println("Error al listar las salas: " + e.getMessage());
		}
	}
}

    

