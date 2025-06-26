
import org.junit.jupiter.api.Test;

import com.practica.modelo.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {

	@Test
	void testConstructorVacio() {
		Reserva reserva = new Reserva();
		assertNull(reserva.getDniEmpleado());
		assertEquals(0, reserva.getIdSala());
		assertNull(reserva.getFecha());
		assertNull(reserva.getHoraInicio());
		assertNull(reserva.getHoraFin());
	}

	@Test
	void testConstructorCompleto() {
		String dniEmpleado = "12345678A";
		int idSala = 1;
		LocalDate fecha = LocalDate.of(2025, 6, 24);
		LocalTime inicio = LocalTime.of(10, 0);
		LocalTime fin = LocalTime.of(11, 0);

		Reserva reserva = new Reserva(dniEmpleado, idSala, fecha, inicio, fin);

		assertEquals(dniEmpleado, reserva.getDniEmpleado());
		assertEquals(idSala, reserva.getIdSala());
		assertEquals(fecha, reserva.getFecha());
		assertEquals(inicio, reserva.getHoraInicio());
		assertEquals(fin, reserva.getHoraFin());
	}

	@Test
	void testSettersYGetters() {
		Reserva reserva = new Reserva();
		String dniEmpleado = "87654321B";
		int idSala = 2;
		LocalDate fecha = LocalDate.of(2025, 7, 1);
		LocalTime inicio = LocalTime.of(9, 30);
		LocalTime fin = LocalTime.of(10, 30);

		reserva.setDniEmpleado(dniEmpleado);
		reserva.setIdSala(idSala);
		reserva.setFecha(fecha);
		reserva.setHoraInicio(inicio);
		reserva.setHoraFin(fin);

		assertEquals(dniEmpleado, reserva.getDniEmpleado());
		assertEquals(idSala, reserva.getIdSala());
		assertEquals(fecha, reserva.getFecha());
		assertEquals(inicio, reserva.getHoraInicio());
		assertEquals(fin, reserva.getHoraFin());
	}

	@Test
	void testEqualsYHashCode() {
		String idReserva = "R001";
		Reserva r1 = new Reserva(idReserva);
		Reserva r2 = new Reserva(idReserva);
		Reserva r3 = new Reserva("R002");

		assertEquals(r1, r2);
		assertNotEquals(r1, r3);
		assertEquals(r1.hashCode(), r2.hashCode());
		assertNotEquals(r1.hashCode(), r3.hashCode());
	}

	@Test
	void testToString() {
		String dniEmpleado = "12345678A";
		int idSala = 1;
		LocalDate fecha = LocalDate.of(2025, 6, 24);
		LocalTime inicio = LocalTime.of(10, 0);
		LocalTime fin = LocalTime.of(11, 0);

		Reserva reserva = new Reserva(dniEmpleado, idSala, fecha, inicio, fin);
		String str = reserva.toString();

		assertTrue(str.contains("dniEmpleado=12345678A"));
		assertTrue(str.contains("idSala=1"));
		assertTrue(str.contains("fecha=2025-06-24"));
		assertTrue(str.contains("horaInicio=10:00"));
		assertTrue(str.contains("horaFin=11:00"));
	}
}
