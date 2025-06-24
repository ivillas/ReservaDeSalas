package com.practica.main;

import com.practica.modelo.ControladorModelo;
import com.practica.modelo.Empleado;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuConsola {

	public void mostrarMenu() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\n           --- Menú Principal ---\n");
			System.out.println("******    1. Alta de empleado            ******");
			System.out.println("******    2. Baja de empleado            ******");
			System.out.println("******    3. Modificación de empleado    ******");
			System.out.println("******    4. Alta de sala                ******");
			System.out.println("******    5. Baja de sala                ******");
			System.out.println("******    6. Modificación de sala        ******");
			System.out.println("******    7. Alta de reserva             ******");
			System.out.println("******    8. Baja de reserva             ******");
			System.out.println("******    9. Modificación de reserva     ******");
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
				bajaReserva(scanner);
				break;
			case 9:
				modificarReserva(scanner);
				break;
			case 10:
				listarEmpleados();
				break;
			case 11:
				listarSalas();
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
    ControladorModelo.altaEmpleado(empleado);
    System.out.println("Empleado agregado exitosamente.");
}

	

	private void bajaEmpleado(Scanner scanner) {
	    System.out.println("Ingrese el DNI del empleado a eliminar:");
	    String dni = scanner.nextLine();

	    // Verificar si el empleado existe en la lista
	    boolean empleadoEncontrado = false;
	    for (Empleado empleado : ControladorModelo.listaEmpleados()) {
	        if (empleado.getDni().equals(dni)) {
	            empleadoEncontrado = true;
	            break;
	        }
	    }

	    if (empleadoEncontrado) {
	        try {
	            // Eliminar el empleado enviando el DNI
	            ControladorModelo.bajaEmpleado(dni);
	            System.out.println("Empleado eliminado exitosamente.");
	        } catch (SQLException e) {
	            System.err.println("Error al eliminar el empleado: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No se encontró un empleado con el DNI proporcionado.");
	    }
	}
	
	private void modificarEmpleado(Scanner scanner) {
	    System.out.println("Ingrese el DNI del empleado a modificar:");
	    String dni = scanner.nextLine();

	    // Buscar el empleado por DNI
	    Empleado empleadoAModificar = null;
	    for (Empleado empleado : ControladorModelo.listaEmpleados()) {
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
	            ControladorModelo.modificarEmpleado(ControladorModelo.listaEmpleados().indexOf(empleadoAModificar), empleadoAModificar);
	            System.out.println("Empleado modificado exitosamente.");
	        } catch (SQLException e) {
	            System.err.println("Error al modificar el empleado en la base de datos: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No se encontró un empleado con el DNI proporcionado.");
	    }
	}
	
	
/*
	
		private void modificarEmpleado(Scanner scanner) {
		    System.out.println("Ingrese el DNI del empleado a modificar:");
		    String dni = scanner.nextLine();

		    // Buscar el empleado por DNI
		    Empleado empleadoAModificar = null;
		    for (Empleado empleado : ControladorModelo.listaEmpleados()) {
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

		        System.out.println("Empleado modificado exitosamente.");
		    } else {
		        System.out.println("No se encontró un empleado con el DNI proporcionado.");
		    }
		}
	*/

	private void altaSala(Scanner scanner) {
		System.out.println("Alta de sala...");
		// Implementar lógica para alta de sala
	}

	private void bajaSala(Scanner scanner) {
		System.out.println("Baja de sala...");
		// Implementar lógica para baja de sala
	}

	private void modificarSala(Scanner scanner) {
		System.out.println("Modificación de sala...");
		// Implementar lógica para modificación de sala
	}

	private void altaReserva(Scanner scanner) {
		System.out.println("Alta de reserva...");
		// Implementar lógica para alta de reserva
	}

	private void bajaReserva(Scanner scanner) {
		System.out.println("Baja de reserva...");
		// Implementar lógica para baja de reserva
	}

	private void modificarReserva(Scanner scanner) {
		System.out.println("Modificación de reserva...");
		// Implementar lógica para modificación de reserva
	}

	private void listarEmpleados() {
		System.out.println("Listado de empleados:\n");
		ControladorModelo.mostrarEmpleados();
		// Implementar lógica para listar empleados
	}

	private void listarSalas() {
		System.out.println("Listado de salas...");
		// Implementar lógica para listar salas
	}

	private void listarReservas() {
		System.out.println("Listado de reservas...");
		// Implementar lógica para listar reservas
	}

	public static void main(String[] args) throws SQLException {
		MenuConsola menu = new MenuConsola();
		menu.mostrarMenu();
	}
}
