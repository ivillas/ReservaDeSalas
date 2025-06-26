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
	 * Este método solicita al usuario los datos del empleado, valida el formato del DNI
	 * y del email, y luego registra al empleado en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */

	/**
	 * Registra una nueva sala de reuniones en el sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

    public static void altaEmpleado(Scanner scanner) throws SQLException {
	    System.out.println("Ingrese los datos del empleado:");

	    // Validar el formato del DNI
	    String dni;
	    while (true) {
	        System.out.print("DNI (formato: 46254789Y): ");
	        dni = scanner.nextLine();
	        if (dni.matches("\\d{8}[A-Z]")) { // 8 dígitos seguidos de una letra mayúscula
	            break;
	        } else {
	            System.out.println("Formato de DNI incorrecto. Inténtelo de nuevo.");
	        }
	    }

	    // Validar el formato del email
	    String email;
	    while (true) {
	        System.out.print("Email (formato: email@mail.com): ");
	        email = scanner.nextLine();
	        if (email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { // Validación básica de email
	            break;
	        } else {
	            System.out.println("Formato de email incorrecto. Inténtelo de nuevo.");
	        }
	    }

	    System.out.print("Nombre: ");
	    String nombre = scanner.nextLine();

	    System.out.print("Apellidos: ");
	    String apellidos = scanner.nextLine();

	    System.out.print("Departamento: ");
	    String departamento = scanner.nextLine();

	    // Crear el objeto Empleado
	    Empleado empleado = new Empleado(dni, nombre, apellidos, email, departamento);

	    // Agregar el empleado al modelo
	    GestorBBDD.agregarEmpleado(empleado);
	    System.out.println("Empleado agregado exitosamente.");
	}

	/**
	 * Elimina un empleado del sistema.
	 *
	 * @param scanner Objeto Scanner para leer la entrada del usuario.
	 */

	public static void bajaEmpleado(Scanner scanner) {
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
	            // Verificar si el empleado tiene reservas futuras
	            GestorReserva gestorReserva = new GestorReserva();
	            List<Reserva> reservasFuturas = new ArrayList<>();
	            for (Reserva reserva : gestorReserva.listarReservas()) {
	                if (reserva.getDniEmpleado().equals(dni) && reserva.getFecha().isAfter(LocalDate.now())) {
	                    reservasFuturas.add(reserva);
	                }
	            }

	            if (!reservasFuturas.isEmpty()) {
	                System.out.println("El empleado tiene las siguientes reservas futuras:");
	                for (Reserva reserva : reservasFuturas) {
	                    System.out.println("ID Reserva: " + reserva.getIdReserva() + ", Fecha: " + reserva.getFecha() +
	                            ", Hora Inicio: " + reserva.getHoraInicio() + ", Hora Fin: " + reserva.getHoraFin());
	                }

	                System.out.print("¿Desea eliminar estas reservas y continuar con la baja del empleado? (sí/no): ");
	                String opcion = scanner.nextLine().trim().toLowerCase();

	                if (opcion.equals("sí") || opcion.equals("si") || opcion.equals("s")) {
	                    // Eliminar las reservas futuras
	                    for (Reserva reserva : reservasFuturas) {
	                        gestorReserva.bajaReserva(Integer.parseInt(reserva.getIdReserva()));
	                    }
	                    System.out.println("Reservas eliminadas exitosamente.");
	                } else {
	                    System.out.println("Operación cancelada.");
	                    return;
	                }
	            }

	            // Eliminar el empleado
	            GestorBBDD.eliminarEmpleado(dni);
	            System.out.println("Empleado eliminado exitosamente.");
	        } catch (SQLException e) {
	            System.err.println("Error al procesar la baja del empleado: " + e.getMessage());
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
	
	
	public static void modificarEmpleado(Scanner scanner) {
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

	        // Validar el formato del email
	        while (true) {
	            System.out.print("Email (" + empleadoAModificar.getEmail() + "): ");
	            String nuevoEmail = scanner.nextLine();
	            if (nuevoEmail.isBlank()) {
	                break; // Mantener el valor actual si está en blanco
	            }
	            if (nuevoEmail.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { // Validación básica de email
	                empleadoAModificar.setEmail(nuevoEmail);
	                break;
	            } else {
	                System.out.println("Formato de email incorrecto. Inténtelo de nuevo.");
	            }
	        }

	        System.out.print("Departamento (" + empleadoAModificar.getDepartamento() + "): ");
	        String nuevoDepartamento = scanner.nextLine();
	        if (!nuevoDepartamento.isBlank()) {
	            empleadoAModificar.setDepartamento(nuevoDepartamento);
	        }

	        try {
	            // Llamar al método que sincroniza con la base de datos
	            GestorBBDD.modificarEmpleado( empleadoAModificar);
	        } catch (SQLException e) {
	            System.err.println("Error al modificar el empleado en la base de datos: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No se encontró un empleado con el DNI proporcionado.");
	    }
	}

    /**
     * Obtiene la lista de empleados.
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
    
    public static void mostrarEmpleados() {
        try {
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
 * Este método realiza una consulta a la base de datos para comprobar si 
 * existe un registro en la tabla `empleados` con el DNI proporcionado.
 *
 * @param dni El DNI del empleado que se desea verificar.
 * @return `true` si el empleado existe en la base de datos, `false` en caso contrario.
 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
 *
 * Ejemplo de uso:
 * <pre>
 * boolean existe = GestorEmpleado.existeEmpleado("12345678A");
 * if (existe) {
 *     System.out.println("El empleado existe.");
 * } else {
 *     System.out.println("El empleado no existe.");
 * }
 * </pre>
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
    
    

