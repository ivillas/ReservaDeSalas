import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorReserva;

class GestorReservaTest {

    @Test
    void testExisteReserva() throws SQLException {
        String dniEmpleado = "12345678A";
        LocalDate fecha = LocalDate.of(2023, 10, 15);
        LocalTime horaInicio = LocalTime.of(10, 0);

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.listarReservas()).thenReturn(Arrays.asList(
                new Reserva("1", dniEmpleado, 1, fecha, horaInicio, horaInicio.plusHours(2))
            ));

            GestorReserva gestor = new GestorReserva();
            boolean existe = gestor.existeReserva(dniEmpleado, fecha, horaInicio);

            assertTrue(existe);
            mockedBBDD.verify(GestorBBDD::listarReservas, times(1));
        }
    }

    @Test
    void testObtenerSalasLibres() throws SQLException {
        LocalDate fecha = LocalDate.of(2023, 10, 15);
        LocalTime horaInicio = LocalTime.of(10, 0);

        List<SalaReuniones> salasMock = Arrays.asList(
            new SalaReuniones(1, "Sala A", 10, true, Arrays.asList("Proyector")),
            new SalaReuniones(2, "Sala B", 20, true, Arrays.asList("Pizarra"))
        );

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.listarSalas()).thenReturn(salasMock);

            GestorReserva gestor = new GestorReserva();
            List<SalaReuniones> salasLibres = gestor.obtenerSalasLibres(fecha, horaInicio);

            assertEquals(2, salasLibres.size());
            assertEquals("Sala A", salasLibres.get(0).getNombre());
            mockedBBDD.verify(GestorBBDD::listarSalas, times(1));
        }
    }

    @Test
    void testVerificarDisponibilidadSala() throws SQLException {
        int idSala = 1;
        LocalDate fecha = LocalDate.of(2023, 10, 15);
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFin = LocalTime.of(12, 0);

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.listarReservas()).thenReturn(Arrays.asList(
                new Reserva("1", "12345678A", idSala, fecha, horaInicio, horaFin)
            ));

            boolean disponible = GestorReserva.verificarDisponibilidadSala(idSala, fecha, horaInicio, horaFin);

            assertFalse(disponible);
            mockedBBDD.verify(GestorBBDD::listarReservas, times(1));
        }
    }

    @Test
    void testBajaReserva() throws SQLException {
        int idReserva = 1;

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.bajaReserva(idReserva)).thenReturn(true);

            boolean exito = GestorReserva.bajaReserva(idReserva);

            assertTrue(exito);
            mockedBBDD.verify(() -> GestorBBDD.bajaReserva(idReserva), times(1));
        }
    }

    @Test
    void testListarReservas() throws SQLException {
        List<Reserva> reservasMock = Arrays.asList(
            new Reserva("1", "12345678A", 1, LocalDate.of(2023, 10, 15), LocalTime.of(10, 0), LocalTime.of(12, 0)),
            new Reserva("2", "87654321B", 2, LocalDate.of(2023, 10, 16), LocalTime.of(14, 0), LocalTime.of(16, 0))
        );

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(GestorBBDD::listarReservas).thenReturn(reservasMock);

            GestorReserva gestor = new GestorReserva();
            List<Reserva> reservas = gestor.listaReservas();

            assertEquals(2, reservas.size());
            assertEquals("12345678A", reservas.get(0).getDniEmpleado());
            mockedBBDD.verify(GestorBBDD::listarReservas, times(1));
        }
    }
}

