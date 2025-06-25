package com.practica.servicio;

import java.sql.SQLException;
import java.util.List;

import com.practica.modelo.Reserva;
import com.practica.persistencia.GestorBBDD;

public class GestorReserva {
	
	

public int altaReserva(Reserva reserva) throws SQLException {
    return GestorBBDD.altaReserva(reserva); // Llama al método de la capa de persistencia
}

	
	
    public void bajaReserva(int idReserva) {
    	
    	
    }
    public void modificarReserva(Reserva reserva) {
    	
    	
    }
    public List<Reserva> listarReservas() {
    	
    	return null; // Implementación pendiente
    }
    	
    	
  
}
