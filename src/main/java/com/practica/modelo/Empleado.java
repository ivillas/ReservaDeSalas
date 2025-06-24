package com.practica.modelo;

/**
 * Clase que representa un empleado en el sistema de reservas de salas.
 * Contiene información básica del empleado como DNI, nombre, apellidos, email y departamento.
 */

import java.util.Objects;

public class Empleado {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String departamento;
    
    	/** 
    	 * Constructor vacío
		 */

    public Empleado() {
		super();
	}


    	/** 
    	 * Constructor con DNI
		 * @param dni Identificador del empleado
		 */
    
	public Empleado(String dni) {
		super();
		this.dni = dni;
	}



	/**
	 * Constructor con todos los campos
	 * @param dni
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param departamento
	 */

	public Empleado(String dni, String nombre, String apellidos, String email, String departamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.departamento = departamento;
    }

	/**
	 * Métodos getter y setter para los atributos de la clase Empleado.
	 */

	public String getDni() {
		return dni;
	}

				
	
					

	public void setDni(String dni) {
		this.dni = dni;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getApellidos() {
		return apellidos;
	}





	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getDepartamento() {
		return departamento;
	}





	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}





	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}


				
		/**
		 * Método equals para comparar dos objetos Empleado por su DNI.
		 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(dni, other.dni);
	}

	/**
	 * Método toString para representar el objeto Empleado como una cadena de texto.
	 * @return Cadena con la información del empleado.
	 */
	
	@Override
	public String toString() {
		return "dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", departamento=" + departamento + ".\n";
	}


	
}
