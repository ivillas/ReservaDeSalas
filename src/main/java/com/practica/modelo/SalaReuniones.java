package com.practica.modelo;

/**
 * Clase que representa un empleado en el sistema de reservas de salas.
 * Contiene información básica del empleado como DNI, nombre, apellidos, email y departamento.
 */

import java.util.List;
import java.util.Objects;

public class SalaReuniones {
    private int id;
    private String nombre;
    private int capacidad;
    private boolean disponible;
    private List<String> recursosDisponibles;

    /**
	 * Constructor vacío
	 */
	public SalaReuniones() {
		super();
	}
		/**
	 * Constructor con ID
	 * @param id Identificador de la sala de reuniones
	 */
	
	public SalaReuniones(int id) {
		super();
		this.id = id;
	}
		/**
	 * Constructor con todos los campos
	 * @param id Identificador de la sala de reuniones
	 * @param nombre Nombre de la sala de reuniones
	 * @param capacidad Capacidad de la sala de reuniones
	 * @param disponible Estado de disponibilidad de la sala de reuniones
	 * @param recursosDisponibles Lista de recursos disponibles en la sala de reuniones
	 */
				
	public SalaReuniones(int id, String nombre, int capacidad, boolean disponible, List<String> recursosDisponibles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.disponible = disponible;
		this.recursosDisponibles = recursosDisponibles;
	}
	
			/**
	 * Métodos getter y setter para los atributos de la clase SalaReuniones.
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public List<String> getRecursosDisponibles() {
		return recursosDisponibles;
	}

	public void setRecursosDisponibles(List<String> recursosDisponibles) {
		this.recursosDisponibles = recursosDisponibles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
 
	/**
	 * Método para comparar dos objetos SalaReuniones por su ID.
	 * @param obj Objeto a comparar
	 * @return true si los IDs son iguales, false en caso contrario
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaReuniones other = (SalaReuniones) obj;
		return id == other.id;
	}

	
	/**
	 * Método toString para representar la sala de reuniones como una cadena.
	 * @return Cadena con la información de la sala de reuniones
	 */
	@Override
	public String toString() {
		return "SalaReuniones [id=" + id + ", nombre=" + nombre + ", capacidad=" + capacidad + ", disponible="
				+ disponible + ", recursosDisponibles=" + recursosDisponibles + "]";
	}

 
    
    
    
}
