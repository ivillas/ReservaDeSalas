package com.practica.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.practica.modelo.Empleado;
import com.practica.modelo.Reserva;
import com.practica.persistencia.ConfiguracionBBDD;
import com.practica.persistencia.GestorBBDD;

public class GestorEmpleado {

	private static List<Empleado> empleados = new ArrayList<>();

	// Métodos para Empleado

	/**
	 * Registra un nuevo empleado en el sistema.
	 *
	 * Este método solicita al usuario los datos del empleado, valida el formato del
	 * DNI y del email, comprueba que el dni no exista en la base de datos y luego
	 * registra al empleado en el sistema. Da la opcion de salir durante los bucles
	 * de dni y email.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	public static void altaEmpleado(Scanner scanner) throws SQLException {
		System.out.println("Ingrese los datos del empleado:");

		// Validar el formato del DNI
		String dni;
		while (true) {
			System.out.print("DNI (formato: 46254789Y): ");
			dni = scanner.nextLine();
			if (dni.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (!dni.matches("\\d{8}[A-Z]")) {
				System.out.println("Formato de DNI incorrecto. Inténtelo de nuevo o escriba 'salir' para cancelar.");
				continue;
			}
			if (GestorEmpleado.existeEmpleado(dni)) {
				System.out.println(
						"Ya existe un empleado con este DNI. Intente con un DNI diferente o escriba 'salir' para cancelar.");
				continue;
			}
			break;
		}

		// Validar el formato del email
		String email;
		while (true) {
			System.out.print("Email (formato: email@mail.com, máximo 100 caracteres): ");
			email = scanner.nextLine();
			if (email.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (email.length() > 100) {
				System.out.println(
						"El email no puede tener más de 100 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar.");
				continue;
			}
			if (email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
				break;
			} else {
				System.out.println("Formato de email incorrecto. Inténtelo de nuevo o escriba 'salir' para cancelar.");
			}
		}

		String nombre;
		while (true) {
			System.out.print("Nombre (máximo 50 caracteres): ");
			nombre = scanner.nextLine();
			if (nombre.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (nombre.length() <= 50) {
				break;
			} else {
				System.out.println("El nombre no puede tener más de 50 caracteres. Inténtelo de nuevoo escriba 'salir' para cancelar.");
			}
		}
		String apellidos;
		while (true) {
			System.out.print("Apellidos (máximo 50 caracteres): ");
			apellidos = scanner.nextLine();
			if (apellidos.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (apellidos.length() <= 50) {
				break;
			} else {
				System.out.println("Los apellidos no pueden tener más de 50 caracteres. Inténtelo de nuevoo escriba 'salir' para cancelar.");
			}
		}
		String departamento;
		while (true) {
			System.out.print("Departamento (máximo 50 caracteres): ");
			departamento = scanner.nextLine();
			if (departamento.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (departamento.length() <= 50) {
				break;
			} else {
				System.out.println("El departamento no puede tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar.");
			}
		}

		Empleado empleado = new Empleado(dni, nombre, apellidos, email, departamento);

		GestorBBDD.agregarEmpleado(empleado);
		System.out.println("Empleado agregado exitosamente.");
	}

	/**
	 * Elimina un empleado del sistema. Este método solicita al usuario el DNI del
	 * empleado que desea eliminar, valida el formato del DNI luego busca reservas
	 * futuras del empleado en el sistema, si las encuentra las muestra con un
	 * mensaje de confirmación de eliminacion de las reservas y el empleado o
	 * cancelación de la operación. Da la opcion de salir durante los bucles de dni
	 * y email.
	 * 
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void bajaEmpleado(Scanner scanner) {
		System.out.println("Ingrese el DNI del empleado a eliminar (formato: 46254789Y) o escriba 'salir' para cancelar.:");

		// Validar el formato del DNI
		String dni;
		while (true) {
			dni = scanner.nextLine();
			if (dni.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return;
			}
			if (dni.matches("\\d{8}[A-Z]")) {
				break;
			} else {
				System.out.println(
						"Formato de DNI incorrecto. Inténtelo de nuevo (formato: 46254789Y) o escriba 'salir' para cancelar.:");
			}
		}

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
				// Verificar si el empleado tiene reservas futuras
				GestorReserva gestorReserva = new GestorReserva();
				List<Reserva> reservasFuturas = new ArrayList<>();
				for (Reserva reserva : gestorReserva.listaReservas()) {
					if (reserva.getDniEmpleado().equals(dni) && reserva.getFecha().isAfter(LocalDate.now())) {
						reservasFuturas.add(reserva);
					}
				}

				if (!reservasFuturas.isEmpty()) {
					System.out.println("El empleado tiene las siguientes reservas futuras:");
					for (Reserva reserva : reservasFuturas) {
						System.out.println("ID Reserva: " + reserva.getIdReserva() + ", Fecha: " + reserva.getFecha()
								+ ", Hora Inicio: " + reserva.getHoraInicio() + ", Hora Fin: " + reserva.getHoraFin());
					}

					System.out.print("¿Desea eliminar estas reservas y continuar con la baja del empleado? (sí/no): ");
					String opcion = scanner.nextLine().trim().toLowerCase();

					if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
						// Eliminar las reservas futuras
						for (Reserva reserva : reservasFuturas) {
							GestorReserva.bajaReserva(Integer.parseInt(reserva.getIdReserva()));
						}
						System.out.println("Reservas eliminadas exitosamente.");
					} else {
						System.out.println("Operación cancelada.");
						return;
					}
				}

				// Eliminar el empleado
				GestorBBDD.eliminarEmpleado(dni);
			} catch (SQLException e) {
				System.err.println("Error al procesar la baja del empleado: " + e.getMessage());
			}
		} else {
			System.out.println("No se encontró un empleado con el DNI proporcionado.");
		}
	}

	/**
	 * Este método solicita al usuario el dni del empleado que desea modificar, va
	 * solicitando los datos que desea cambiar mostrando el dato original y pudiendo
	 * dejarlo en blanco para no modificarlo. El unico dato no modificable es el
	 * DNI, que se valida al inicio del método. También valida el formato del email
	 * y el DNI. y da la opcion de salir en la iteracion del DNI.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void modificarEmpleado(Scanner scanner) {
		System.out.println("Ingrese el DNI del empleado a modificar o escriba 'salir' para cancelar:");
		// Validar el formato del DNI
		String dni;
		while (true) {
			dni = scanner.nextLine();
			if (dni.equalsIgnoreCase("salir")) {
				System.out.println("Operación cancelada.");
				return; // Salir del método
			}
			if (dni.matches("\\d{8}[A-Z]")) { // 8 dígitos seguidos de una letra mayúscula
				break;
			} else {
				System.out.println(
						"Formato de DNI incorrecto. Inténtelo de nuevo (formato: 46254789Y) o escriba 'salir' para cancelar:");
			}
		}

		// Buscar el empleado por DNI
		Empleado empleadoAModificar = null;
		for (Empleado empleado : GestorEmpleado.listaEmpleados()) {
			if (empleado.getDni().equals(dni)) {
				empleadoAModificar = empleado;
				break;
			}
		}

		if (empleadoAModificar != null) {
			System.out.println(
					"Empleado encontrado. Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");

			// Validar el nombre
			while (true) {
				System.out.print("Nombre (" + empleadoAModificar.getNombre() + "): ");
				String nuevoNombre = scanner.nextLine();
				if (nuevoNombre.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (nuevoNombre.isBlank() || nuevoNombre.length() <= 50) {
					if (!nuevoNombre.isBlank()) {
						empleadoAModificar.setNombre(nuevoNombre);
					}
					break;
				} else {
					System.out.println("El nombre no puede tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				}
			}

			// Validar los apellidos
			while (true) {
				System.out.print("Apellidos (" + empleadoAModificar.getApellidos() + "): ");
				String nuevosApellidos = scanner.nextLine();
				if (nuevosApellidos.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (nuevosApellidos.isBlank() || nuevosApellidos.length() <= 50) {
					if (!nuevosApellidos.isBlank()) {
						empleadoAModificar.setApellidos(nuevosApellidos);
					}
					break;
				} else {
					System.out.println("Los apellidos no pueden tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				}
			}

			// Validar el email
			while (true) {
				System.out.print("Email (" + empleadoAModificar.getEmail() + "): ");
				String nuevoEmail = scanner.nextLine();
				if (nuevoEmail.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (nuevoEmail.isBlank()) {
					break; // Mantener el valor actual si está en blanco
				}
				if (nuevoEmail.length() > 100) {
					System.out.println("El email no puede tener más de 100 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar:");
					continue;
				}
				if (nuevoEmail.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { // Validación básica de email
					empleadoAModificar.setEmail(nuevoEmail);
					break;
				} else {
					System.out.println("Formato de email incorrecto. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				}
			}

			// Validar el departamento
			while (true) {
				System.out.print("Departamento (" + empleadoAModificar.getDepartamento() + "): ");
				String nuevoDepartamento = scanner.nextLine();
				if (nuevoDepartamento.equalsIgnoreCase("salir")) {
					System.out.println("Operación cancelada.");
					return;
				}
				if (nuevoDepartamento.isBlank() || nuevoDepartamento.length() <= 50) {
					if (!nuevoDepartamento.isBlank()) {
						empleadoAModificar.setDepartamento(nuevoDepartamento);
					}
					break;
				} else {
					System.out.println("El departamento no puede tener más de 50 caracteres. Inténtelo de nuevo o escriba 'salir' para cancelar:");
				}
			}

			try {
				// Llamar al método que sincroniza con la base de datos
				GestorBBDD.modificarEmpleado(empleadoAModificar);
			} catch (SQLException e) {
				System.err.println("Error al modificar el empleado en la base de datos: " + e.getMessage());
			}
		} else {
			System.out.println("No se encontró un empleado con el DNI proporcionado.");
			modificarEmpleado(scanner);
		}
	}

	/**
	 * Metodo que Obtiene la lista de empleados.
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

	/**
	 * Metodo que muestra la lista de empleados.
	 * 
	 * @return Lista de empleados.
	 */

	public static void mostrarEmpleados() {
		try {
			System.out.println("Los de empleados registrados son:\n");
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
	 * Este método realiza una consulta a la base de datos para comprobar si existe
	 * un registro en la tabla `empleados` con el DNI proporcionado.
	 *
	 * @param dni El DNI del empleado que se desea verificar.
	 * @return `true` si el empleado existe en la base de datos, `false` en caso
	 *         contrario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 *
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
    

