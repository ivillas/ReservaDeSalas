
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
	 * Registra una nueva sala en el sistema.
	 *
	 * Este método solicita al usuario los datos de la sala, valida que el nombre de
	 * la sala no se repita también valida el formato de la capacidad y las
	 * longitudes, tiene la opcion de tener la sala para reservas o no segun su
	 * estado( por ejemplo si esta en obras) y los recursos disponibles. Tiene la
	 * opcion de salir en cada introduccion de datos.
	 * 
	 * @param sala El objeto `SalaReuniones` que contiene los datos de la sala a
	 *             agregar.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */

	public static void altaSala(Scanner scanner) {
		System.out.println("Ingrese los datos de la sala:");

		String nombre;
		while (true) {
			System.out.print("Nombre: ");
			nombre = scanner.nextLine();

			if (nombre.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			if (nombre.length() > 50) {
				System.out.println(
						"El nombre no puede tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				continue; // Volver a solicitar el dato
			}

			try {
				if (GestorBBDD.existeSalaPorNombre(nombre)) { // Comprobar si el nombre ya existe
					System.out.println(
							"El nombre de la sala ya existe. Intente con un nombre diferente o escriba 'salir' para cancelar:");
				} else {
					break; // Salir del bucle si el nombre no existe y es válido
				}
			} catch (Exception e) {
				System.err.println("Error al comprobar el nombre de la sala: " + e.getMessage());
				return; // Salir del método en caso de error
			}
		}

		int capacidad;
		while (true) {
			System.out.print("Capacidad: ");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			if (!input.matches("\\d+")) { // Verifica que solo contenga dígitos
				System.out.println(
						"La capacidad debe ser un número entero positivo. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				continue;
			}

			if (input.length() > 11) { // Verifica que no exceda los 11 caracteres
				System.out.println(
						"La capacidad no puede tener más de 11 dígitos. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				continue;
			}

			try {
				capacidad = Integer.parseInt(input); // Convierte el valor a entero
				break; // Salir del bucle si es válido
			} catch (NumberFormatException e) {
				System.out.println(
						"El número ingresado es demasiado grande. Inténtelo de nuevo o escriba 'salir' para cancelar:");
			}
		}

		System.out.print("¿Está disponible para reservar? (sí/no) o escriba 'salir' para cancelar: ");
		String disponibleInput = scanner.nextLine().trim().toLowerCase();
		if (disponibleInput.equalsIgnoreCase("salir")) {
			System.out.println("Operación cancelada.");
			return;
		}
		boolean disponible = disponibleInput.equals("sí") || disponibleInput.equals("si")
				|| disponibleInput.equals("s");

		System.out.print("Recursos disponibles (separados por comas)  o escriba 'salir' para cancelar: ");
		String recursosInput = scanner.nextLine();
		if (recursosInput.equalsIgnoreCase("salir")) {
			System.out.println("Operación cancelada.");
			return;
		}
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
	 * Elimina una sala de reuniones del sistema solicitando si id. Hace
	 * comprovaciones del id de formato, longitud y si existe.
	 * 
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void bajaSala(Scanner scanner) {
		while (true) {
			System.out
					.print("Ingrese el ID de la sala a eliminar (máximo 11 dígitos) o escriba 'salir' para cancelar: ");
			String input = scanner.nextLine();

			if (input.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			if (!input.matches("\\d+")) {
				System.out.println("El ID debe contener solo números. Inténtelo de nuevo.");
				continue;
			}

			if (input.length() > 11) {
				System.out.println("El ID no puede tener más de 11 dígitos. Inténtelo de nuevo.");
				continue;
			}

			try {
				int idSala = Integer.parseInt(input);
				GestorBBDD.eliminarSala(idSala);
				System.out.println("Sala eliminada exitosamente.");
				break;
			} catch (Exception e) {
				System.err.println("Error al eliminar la sala: " + e.getMessage());
			}
		}
	}

	/**
	 * Modifica los datos de una sala de reuniones existente.
	 *
	 * Este método solicita al usuario el id de la sala, valida el id de formato,
	 * longitud y si existe. Muestra los datos actuales y deja cambiarlos, dejarlos
	 * como estan o salir de la operacion.
	 * 
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void modificarSala(Scanner scanner) {
		while (true) {
			System.out.print(
					"Ingrese el ID de la sala a modificar (máximo 11 dígitos) o escriba 'salir' para cancelar: ");
			String idInput = scanner.nextLine();

			if (idInput.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}

			if (!idInput.matches("\\d+")) {
				System.out.println(
						"El ID debe contener solo números. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
				continue;
			}

			if (idInput.length() > 11) {
				System.out.println(
						"El ID no puede tener más de 11 dígitos. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
				continue;
			}

			int idSala;
			try {
				idSala = Integer.parseInt(idInput);
			} catch (NumberFormatException e) {
				System.out.println("ID inválido. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
				continue;
			}

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
							"Sala encontrada. Ingrese los nuevos datos (deje en blanco para mantener el valor actual) o escriba 'salir' para cancelar: ");

					while (true) {
						System.out.print("Nombre (" + salaAModificar.getNombre() + ", máximo 50 caracteres): ");
						String nuevoNombre = scanner.nextLine();
						if (nuevoNombre.equalsIgnoreCase("salir")) {
							System.out.println("Operación cancelada.");
							return;
						}
						if (nuevoNombre.length() > 50) {
							System.out.println(
									"El nombre no puede tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar: .");
							continue;
						}
						if (!nuevoNombre.isBlank()) {
							salaAModificar.setNombre(nuevoNombre);
						}
						break;
					}

					while (true) {
						System.out.print("Capacidad (" + salaAModificar.getCapacidad() + ", máximo 11 dígitos): ");
						String nuevaCapacidad = scanner.nextLine();
						if (nuevaCapacidad.equalsIgnoreCase("salir")) {
							System.out.println("Operación cancelada.");
							return;
						}
						if (!nuevaCapacidad.isBlank()) {
							if (!nuevaCapacidad.matches("\\d+")) {
								System.out.println(
										"La capacidad debe contener solo números. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
								continue;
							}
							if (nuevaCapacidad.length() > 11) {
								System.out.println(
										"La capacidad no puede tener más de 11 dígitos. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
								continue;
							}
							try {
								salaAModificar.setCapacidad(Integer.parseInt(nuevaCapacidad));
							} catch (NumberFormatException e) {
								System.out.println(
										"Capacidad inválida. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
								continue;
							}
						}
						break;
					}

					while (true) {
						System.out.print("¿Está disponible para reservar? (sí/no) ("
								+ (salaAModificar.isDisponible() ? "sí" : "no")
								+ ") o escriba 'salir' para cancelar:  ");
						String nuevaDisponibilidad = scanner.nextLine().trim().toLowerCase();
						if (nuevaDisponibilidad.equalsIgnoreCase("salir")) {
							System.out.println("Operación cancelada.");
							return;
						}
						if (!nuevaDisponibilidad.isBlank()) {
							salaAModificar.setDisponible(nuevaDisponibilidad.equals("sí")
									|| nuevaDisponibilidad.equals("si") || nuevaDisponibilidad.equals("s"));
						}
						break;
					}

					while (true) {
						System.out.print(
								"Recursos disponibles (" + String.join(", ", salaAModificar.getRecursosDisponibles())
										+ ") o escriba 'salir' para cancelar:  ");
						String nuevosRecursos = scanner.nextLine();
						if (nuevosRecursos.equalsIgnoreCase("salir")) {
							System.out.println("Operación cancelada.");
							return;
						}
						if (!nuevosRecursos.isBlank()) {
							salaAModificar.setRecursosDisponibles(Arrays.asList(nuevosRecursos.split(",")));
						}
						break;
					}

					GestorBBDD.modificarSala(salaAModificar);
					System.out.println("Sala modificada exitosamente.");
					break;
				} else {
					System.out.println(
							"No se encontró una sala con el ID proporcionado. Inténtelo de nuevo o escriba 'salir' para cancelar: ");
				}
			} catch (Exception e) {
				System.err.println("Error al modificar la sala: " + e.getMessage());
			}
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

    

