package com.practica.main;

import com.practica.servicio.GestorEmpleado;
import com.practica.servicio.GestorReserva;
import com.practica.servicio.GestorSalaReuniones;
import java.sql.SQLException;
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

		    // Validar entrada
		    while (true) {
		        try {
		            opcion = Integer.parseInt(scanner.nextLine());
		            break; // Salir del bucle si la entrada es válida
		        } catch (NumberFormatException e) {
		            System.out.println("Entrada no válida. Por favor, introduzca un número entero.");
		            System.out.print("Seleccione una opción: ");
		        }
		    }

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
				GestorReserva.altaReserva(scanner);
				break;
			case 8:
				GestorReserva.cancelacionReserva(scanner);
				break;
			case 9:
				GestorReserva.modificarReserva(scanner);
				break;
			case 10:
				GestorEmpleado.mostrarEmpleados();
				break;
			case 11:
			    GestorSalaReuniones.listarSalas(scanner);
				break;
			case 12:
				GestorReserva.listarReservas();
				break;
			case 0:
				System.out.println("Saliendo del sistema...");
				break;
			default: 
				while (true) { 
					System.out.println("Opción no válida. Intente nuevamente."); 
					System.out.print("Seleccione una opción: "); 
					try { opcion = Integer.parseInt(scanner.nextLine()); 
					break; 
					}
					catch (NumberFormatException e) { 
						System.out.println("Entrada no válida. Por favor, introduzca un número entero."); 
						} 
					} break;
			}
		} while (opcion != 0);
		
		scanner.close();
	}
	
	public static void main(String[] args) {
	    MenuConsola menu = new MenuConsola();
	    try {
	        menu.mostrarMenu();
	    } catch (SQLException e) {
	        System.err.println("Ocurrió un error al interactuar con la base de datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}

