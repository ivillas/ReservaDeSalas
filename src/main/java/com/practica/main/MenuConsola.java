package com.practica.main;

import com.practica.modelo.ControladorModelo;
import com.practica.modelo.Empleado;
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
				altaEmpleado(scanner);
				break;
			case 2:
				bajaEmpleado(scanner);
				break;
			case 3:
				modificarEmpleado(scanner);
				break;
			case 4:
				altaSala(scanner);
				break;
			case 5:
				bajaSala(scanner);
				break;
			case 6:
				modificarSala(scanner);
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
				listarEmpleados();
				break;
			case 11:
				listarSalas(scanner);
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
     * Registra un nuevo empleado en el sistema.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
	
	private void altaEmpleado(Scanner scanner) throws SQLException {

    System.out.println("Ingrese los datos del empleado:");

    System.out.print("DNI: ");
    String dni = scanner.nextLine();

    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();

    System.out.print("Apellidos: ");
    String apellidos = scanner.nextLine();

    System.out.print("Email: ");
    String email = scanner.nextLine();

    System.out.print("Departamento: ");
    String departamento = scanner.nextLine();

    // Crear el objeto Empleado
    Empleado empleado = new Empleado(dni, nombre, apellidos, email, departamento);

    // Agregar el empleado al modelo
    GestorEmpleado.altaEmpleado(empleado);
    System.out.println("Empleado agregado exitosamente.");
}

    /**
     * Elimina un empleado del sistema.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     */

	private void bajaEmpleado(Scanner scanner) {
	    System.out.println("Ingrese el DNI del empleado a eliminar:");
	    String dni = scanner.nextLine();

	    // Verificar si el empleado existe en la lista
	    boolean empleadoEncontrado = false;
	    for (Empleado empleado : GestorEmpleado.listaEmpleados()) {
	        if (empleado.getDni().equals(dni)) {
	            empleadoEncontrado = true;
	            break;
	        }
	    }

	    if (empleadoEncontrado) {
	        try {
	            // Eliminar el empleado enviando el DNI
	        	GestorEmpleado.bajaEmpleado(dni);
        } catch (SQLException e) {
	            System.err.println("Error al eliminar el empleado: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No se encontró un empleado con el DNI proporcionado.");
	    }
	}
	
	/**
	 * Modifica los datos de un empleado existente.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */
	
	
	private void modificarEmpleado(Scanner scanner) {
	    System.out.println("Ingrese el DNI del empleado a modificar:");
	    String dni = scanner.nextLine();

	    // Buscar el empleado por DNI
	    Empleado empleadoAModificar = null;
	    for (Empleado empleado : GestorEmpleado.listaEmpleados()) {
	        if (empleado.getDni().equals(dni)) {
	            empleadoAModificar = empleado;
	            break;
	        }
	    }

	    if (empleadoAModificar != null) {
	        System.out.println("Empleado encontrado. Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");

	        System.out.print("Nombre (" + empleadoAModificar.getNombre() + "): ");
	        String nuevoNombre = scanner.nextLine();
	        if (!nuevoNombre.isBlank()) {
	            empleadoAModificar.setNombre(nuevoNombre);
	        }

	        System.out.print("Apellidos (" + empleadoAModificar.getApellidos() + "): ");
	        String nuevosApellidos = scanner.nextLine();
	        if (!nuevosApellidos.isBlank()) {
	            empleadoAModificar.setApellidos(nuevosApellidos);
	        }

	        System.out.print("Email (" + empleadoAModificar.getEmail() + "): ");
	        String nuevoEmail = scanner.nextLine();
	        if (!nuevoEmail.isBlank()) {
	            empleadoAModificar.setEmail(nuevoEmail);
	        }

	        System.out.print("Departamento (" + empleadoAModificar.getDepartamento() + "): ");
	        String nuevoDepartamento = scanner.nextLine();
	        if (!nuevoDepartamento.isBlank()) {
	            empleadoAModificar.setDepartamento(nuevoDepartamento);
	        }

	        try {
	            // Llamar al método que sincroniza con la base de datos
	        	GestorEmpleado.modificarEmpleado(GestorEmpleado.listaEmpleados().indexOf(empleadoAModificar), empleadoAModificar);
	            System.out.println("Empleado modificado exitosamente.");
	        } catch (SQLException e) {
	            System.err.println("Error al modificar el empleado en la base de datos: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No se encontró un empleado con el DNI proporcionado.");
	    }
	}
	
	/**
	 * Registra una nueva sala de reuniones en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	private void altaSala(Scanner scanner) {
	    System.out.println("Ingrese los datos de la sala:");

	    System.out.print("Nombre: ");
	    String nombre = scanner.nextLine();

	    System.out.print("Capacidad: ");
	    int capacidad = scanner.nextInt();
	    scanner.nextLine(); // Limpiar el buffer

	    System.out.print("¿Está disponible para reservar? (sí/no): ");
	    String disponibleInput = scanner.nextLine().trim().toLowerCase();
	    boolean disponible = disponibleInput.equals("sí" ) || disponibleInput.equals("si") || disponibleInput.equals("s");

	    System.out.print("Recursos disponibles (separados por comas): ");
	    String recursosInput = scanner.nextLine();
	    List<String> recursos = Arrays.asList(recursosInput.split(","));

	    SalaReuniones sala = new SalaReuniones(0, nombre, capacidad, disponible, recursos);

	    try {
	        GestorSalaReuniones gestor = new GestorSalaReuniones();
	        gestor.altaSala(sala);
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

	private void bajaSala(Scanner scanner) {
	    System.out.print("Ingrese el ID de la sala a eliminar: ");
	    int idSala = scanner.nextInt();
	    scanner.nextLine(); // Limpiar el buffer

	    try {
	        GestorSalaReuniones gestor = new GestorSalaReuniones();
	        gestor.bajaSala(idSala);
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

	private void modificarSala(Scanner scanner) {
	    System.out.print("Ingrese el ID de la sala a modificar: ");
	    int idSala = scanner.nextInt();
	    scanner.nextLine(); // Limpiar el buffer

	    SalaReuniones salaAModificar = null;

	    try {
	        GestorSalaReuniones gestor = new GestorSalaReuniones();
	        for (SalaReuniones sala : gestor.listarSalas()) {
	            if (sala.getId() == idSala) {
	                salaAModificar = sala;
	                break;
	            }
	        }

	        if (salaAModificar != null) {
	            System.out.println("Sala encontrada. Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");

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

	            System.out.print("¿Está disponible para reservar? (sí/no) (" + (salaAModificar.isDisponible() ? "sí" : "no") + "): ");
	            String nuevaDisponibilidad = scanner.nextLine().trim().toLowerCase();
	            if (!nuevaDisponibilidad.isBlank()) {
	                salaAModificar.setDisponible(nuevaDisponibilidad.equals("sí") || nuevaDisponibilidad.equals("si") || nuevaDisponibilidad.equals("s"));
	            }

	            System.out.print("Recursos disponibles (" + String.join(", ", salaAModificar.getRecursosDisponibles()) + "): ");
	            String nuevosRecursos = scanner.nextLine();
	            if (!nuevosRecursos.isBlank()) {
	                salaAModificar.setRecursosDisponibles(Arrays.asList(nuevosRecursos.split(",")));
	            }

	            gestor.modificarSala(salaAModificar);
	            System.out.println("Sala modificada exitosamente.");
	        } else {
	            System.out.println("No se encontró una sala con el ID proporcionado.");
	        }
	    } catch (Exception e) {
	        System.err.println("Error al modificar la sala: " + e.getMessage());
	    }
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
 * como el DNI del empleado, el ID de la sala, la fecha, la hora de inicio y la hora de fin. 
 * Luego, crea un objeto de tipo `Reserva` y lo registra en el sistema utilizando 
 * la clase `GestorReserva`.
 *
 * @param scanner Objeto `Scanner` para leer la entrada del usuario.
 * 
 * Pasos:
 * 1. Solicita al usuario el DNI del empleado.
 * 2. Solicita el ID de la sala.
 * 3. Solicita la fecha de la reserva en formato `YYYY-MM-DD`.
 * 4. Solicita la hora de inicio en formato `HH:MM`.
 * 5. Solicita la hora de fin en formato `HH:MM`.
 * 6. Crea un objeto `Reserva` con los datos ingresados.
 * 7. Intenta registrar la reserva en el sistema utilizando `GestorReserva`.
 * 8. Si el registro es exitoso, muestra el ID generado. Si ocurre un error, 
 *    muestra un mensaje de error.
 *
 * Excepciones:
 * - Captura cualquier excepción que ocurra durante el registro de la reserva 
 *   y muestra un mensaje de error al usuario.
 */



private void altaReserva(Scanner scanner) {
    System.out.println("Ingrese los datos de la reserva:");

    String dniEmpleado = null;
    final LocalDate[] fechaReserva = {null};
    final LocalTime[] horaInicioReserva = {null};

    // Validar que el DNI del empleado exista en la base de datos
    while (dniEmpleado == null) {
        System.out.print("DNI del empleado: ");
        String dniIngresado = scanner.nextLine();
        try {
            if (GestorEmpleado.existeEmpleado(dniIngresado)) {
                dniEmpleado = dniIngresado;
            } else {
                System.out.println("El DNI ingresado no corresponde a ningún empleado. Por favor, inténtelo de nuevo.");
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
            if (fechaReserva[0].isBefore(LocalDate.now()) ||
                (fechaReserva[0].isEqual(LocalDate.now()) && horaInicioReserva[0].isBefore(LocalTime.now()))) {
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
        final LocalTime[] horaFinReserva = {null};
        while (horaFinReserva[0] == null) {
            System.out.print("Hora de fin (HH:MM): ");
            String horaFin = scanner.nextLine();
            try {
                horaFinReserva[0] = LocalTime.parse(horaFin);

                // Validar que la hora de fin sea posterior a la hora de inicio
                if (horaFinReserva[0].isBefore(horaInicioReserva[0]) || horaFinReserva[0].equals(horaInicioReserva[0])) {
                    System.out.println("La hora de fin debe ser posterior a la hora de inicio. Inténtelo de nuevo.");
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
                return !GestorReserva.verificarDisponibilidadSala(sala.getId(), fechaReserva[0], horaInicioReserva[0], horaFinReserva[0]);
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
            System.out.println("ID: " + sala.getId() + ", Nombre: " + sala.getNombre() + ", Capacidad: " + sala.getCapacidad());
        }

        System.out.print("Seleccione el ID de la sala: ");
        int idSala = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Reserva reserva = new Reserva(dniEmpleado, idSala, fechaReserva[0], horaInicioReserva[0], horaFinReserva[0]);

        // Registrar la reserva
        int idGenerado = gestor.altaReserva(reserva);
        System.out.println("Reserva registrada exitosamente con ID: " + idGenerado);

    } catch (Exception e) {
        System.err.println("Error al registrar la reserva: " + e.getMessage());
    }
}





	
	/**
	 * Elimina una reserva del sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	private void cancelacionReserva(Scanner scanner) {
		System.out.println("Baja de reserva...");
		// Implementar lógica para baja de reserva
	}
	
	/**
	 * Modifica los datos de una reserva existente.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	private void modificarReserva(Scanner scanner) {
		System.out.println("Modificación de reserva...");
		// Implementar lógica para modificación de reserva
	}
	
	/**
	 * Lista los empleados registrados en el sistema.
	 */

	private void listarEmpleados() {
		System.out.println("Listado de empleados:\n");
		ControladorModelo.mostrarEmpleados();
		// Implementar lógica para listar empleados
	}
	
	/**
	 * Lista las salas de reuniones registradas en el sistema.
	 */

	private void listarSalas(Scanner scanner) {
	    try {
	        GestorSalaReuniones gestor = new GestorSalaReuniones();
	        List<SalaReuniones> salas = gestor.listarSalas();

	        if (salas.isEmpty()) {
	            System.out.println("No hay salas registradas.");
	            return;
	        }

	        System.out.println("\nListado de salas:");
	        for (SalaReuniones sala : salas) {
	            System.out.println("ID: " + sala.getId() +
	                               ", Nombre: " + sala.getNombre() +
	                               ", Capacidad: " + sala.getCapacidad() +
	                               ", Disponible: " + (sala.isDisponible() ? "Sí" : "No"));
	        }

	        System.out.print("\n¿Desea realizar una reserva? (sí/no): ");
	        String opcion = scanner.nextLine().trim().toLowerCase();

			if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
				System.out.println("Procediendo a realizar una reserva...");
				// Aquí se llamaa al método para realizar la reserva
	        	altaReserva(scanner);
	        } else {
	            System.out.println("Regresando al menú principal...");
	        }
	    } catch (Exception e) {
	        System.err.println("Error al listar las salas: " + e.getMessage());
	    }
	}
	
	/**
	 * Lista las reservas registradas en el sistema.
	 */

	private void listarReservas() {
		System.out.println("Listado de reservas...");
		// Implementar lógica para listar reservas
	}
	
	/**
	 * Método principal para iniciar la aplicación.
	 *
	 * @param args Argumentos de línea de comandos (no utilizados).
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	public static void main(String[] args) throws SQLException {
		MenuConsola menu = new MenuConsola();
		menu.mostrarMenu();
	}
}
