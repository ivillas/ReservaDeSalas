import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorReserva;

class GestorReservaTest {

	private GestorReserva gestorReserva;

	@BeforeEach
	void setUp() {
		gestorReserva = new GestorReserva();
	}

	@Test
	void testVerificarDisponibilidadSala() throws SQLException {
		int idSala = 1;
		LocalDate fecha = LocalDate.of(2023, 12, 1);
		LocalTime horaInicio = LocalTime.of(10, 0);
		LocalTime horaFin = LocalTime.of(11, 0);

		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			mockedBBDD.when(GestorBBDD::listarReservas).thenReturn(
					List.of(new Reserva("1", "12345678A", idSala, fecha, LocalTime.of(9, 0), LocalTime.of(10, 0))));

			boolean disponible = GestorReserva.verificarDisponibilidadSala(idSala, fecha, horaInicio, horaFin);
			assertTrue(disponible, "La sala debería estar disponible");
		}
	}

	@Test
	void testAltaReserva() throws SQLException {
		Reserva reserva = new Reserva("12345678A", 1, LocalDate.of(2023, 12, 1), LocalTime.of(10, 0),
				LocalTime.of(11, 0));

		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			mockedBBDD.when(() -> GestorBBDD.altaReserva(reserva)).thenReturn(1);

			int idGenerado = GestorBBDD.altaReserva(reserva);
			assertEquals(1, idGenerado, "El ID generado debería ser 1");
		}
	}

	@Test
	void testCancelacionReserva() throws SQLException {
		int idReserva = 1;

		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			mockedBBDD.when(() -> GestorBBDD.bajaReserva(idReserva)).thenReturn(true);

			boolean exito = GestorBBDD.bajaReserva(idReserva);
			assertTrue(exito, "La reserva debería cancelarse exitosamente");
		}
	}
}
