package com.practica.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa una reserva de sala.
 * Contiene el empleado que realiza la reserva, la sala reservada, la fecha, la hora de inicio y la hora de fin.
 */

public class Reserva {
	private String idReserva;
    private String dniEmpleado;
    private int idSala;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    /**
	 * Constructor vacío
	 */

    public Reserva() {
        super();
    }
    
    /**
	 * Constructor con ID de reserva
	 * @param idReserva Identificador de la reserva
	 */
    
    public Reserva(String idReserva) {
		super();
		this.idReserva = idReserva;
	}
    
        /**
         * Constructor con todos los campos necesarios para una reserva.		
         * @param idReserva
         * @param dniEmpleado
         * @param idSala
         * @param fecha
         * @param horaInicio
         * @param horaFin
         */
    
    
    public Reserva(String idReserva, String dniEmpleado, int idSala, LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin) {
		super();
		this.idReserva = idReserva;
		this.dniEmpleado = dniEmpleado;
		this.idSala = idSala;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Constructor con DNI de empleado, ID de sala, fecha, hora de inicio y hora de
	 * fin.
	 * 
	 * @param dniEmpleado DNI del empleado que realiza la reserva.
	 * @param idSala      ID de la sala reservada.
	 * @param fecha       Fecha de la reserva.
	 * @param horaInicio  Hora de inicio de la reserva.
	 * @param horaFin     Hora de fin de la reserva.
	 */
    
	public Reserva(String dniEmpleado, int idSala, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
		super();
		this.dniEmpleado = dniEmpleado;
		this.idSala = idSala;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}



	/**
	 * Métodos getter y setter para los atributos de la clase Reserva.
	 */
    	
	
	public String getIdReserva() {
		return idReserva;
	}



	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
	}

	
	
    public String getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

    /**
     * hashCode y equals para comparar reservas por su ID.
     * 
     */
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idReserva == null) ? 0 : idReserva.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (idReserva == null) {
			if (other.idReserva != null)
				return false;
		} else if (!idReserva.equals(other.idReserva))
			return false;
		return true;
	}

	@Override
	
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", dniEmpleado=" + dniEmpleado + ", idSala=" + idSala + ", fecha="
				+ fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + "]";
	}
    
    /**
	 * Método toString para representar la reserva como una cadena de texto.
	 * 
	 * @return Cadena que representa la reserva.
	 */
	

}
