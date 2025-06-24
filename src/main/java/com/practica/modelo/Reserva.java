package com.practica.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa una reserva de sala.
 * Contiene el empleado que realiza la reserva, la sala reservada, la fecha, la hora de inicio y la hora de fin.
 */

public class Reserva {
	private String idReserva;
    private Empleado empleado;
    private SalaReuniones sala;
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
     * constructor con todos los campos
     * @param idReserva
     * @param empleado
     * @param sala
     * @param fecha
     * @param horaInicio
     * @param horaFin
     */

    public Reserva(String idReserva, Empleado empleado, SalaReuniones sala, LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin) {
		super();
		this.idReserva = idReserva;
		this.empleado = empleado;
		this.sala = sala;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Métodos getter y setter para los atributos de la clase Reserva.
	 */
    
    public Empleado getEmpleado() {
        return empleado;
    }



	public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public SalaReuniones getSala() {
        return sala;
    }

    public void setSala(SalaReuniones sala) {
        this.sala = sala;
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
    
    /**
	 * Método toString para representar la reserva como una cadena de texto.
	 * 
	 * @return Cadena que representa la reserva.
	 */
	
	@Override
    public String toString() {
        return "Reserva [empleado=" + empleado + ", sala=" + sala + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + "]";
    }
}
