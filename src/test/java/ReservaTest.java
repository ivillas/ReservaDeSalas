

import org.junit.jupiter.api.Test;

import com.practica.modelo.Empleado;
import com.practica.modelo.Reserva;
import com.practica.modelo.SalaReuniones;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {
    @Test
    void testConstructorVacio() {
        Reserva reserva = new Reserva();
        assertNull(reserva.getEmpleado());
        assertNull(reserva.getSala());
        assertNull(reserva.getFecha());
        assertNull(reserva.getHoraInicio());
        assertNull(reserva.getHoraFin());
    }

    @Test
    void testConstructorConId() {
        Reserva reserva = new Reserva("R001");
        assertEquals("R001", reserva.equals(new Reserva("R001")) ? "R001" : null);
    }

    @Test
    void testConstructorCompleto() {
        Empleado empleado = new Empleado("12345678A", "Juan", "Pérez", "juan@email.com", "IT");
        SalaReuniones sala = new SalaReuniones(1, "Sala Azul", 10, true, null);
        LocalDate fecha = LocalDate.of(2025, 6, 24);
        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        Reserva reserva = new Reserva("R002", empleado, sala, fecha, inicio, fin);
        assertEquals("R002", reserva.equals(new Reserva("R002")) ? "R002" : null);
        assertEquals(empleado, reserva.getEmpleado());
        assertEquals(sala, reserva.getSala());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(inicio, reserva.getHoraInicio());
        assertEquals(fin, reserva.getHoraFin());
    }

    @Test
    void testSettersYGetters() {
        Reserva reserva = new Reserva();
        Empleado empleado = new Empleado("87654321B", "Ana", "García", "ana@email.com", "HR");
        SalaReuniones sala = new SalaReuniones(2, "Sala Verde", 20, false, null);
        LocalDate fecha = LocalDate.of(2025, 7, 1);
        LocalTime inicio = LocalTime.of(9, 30);
        LocalTime fin = LocalTime.of(10, 30);
        reserva.setEmpleado(empleado);
        reserva.setSala(sala);
        reserva.setFecha(fecha);
        reserva.setHoraInicio(inicio);
        reserva.setHoraFin(fin);
        assertEquals(empleado, reserva.getEmpleado());
        assertEquals(sala, reserva.getSala());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(inicio, reserva.getHoraInicio());
        assertEquals(fin, reserva.getHoraFin());
    }

    @Test
    void testEqualsYHashCode() {
        Reserva r1 = new Reserva("R003");
        Reserva r2 = new Reserva("R003");
        Reserva r3 = new Reserva("R004");
        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    void testToString() {
        Empleado empleado = new Empleado("12345678A", "Juan", "Pérez", "juan@email.com", "IT");
        SalaReuniones sala = new SalaReuniones(1, "Sala Azul", 10, true, null);
        LocalDate fecha = LocalDate.of(2025, 6, 24);
        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        Reserva reserva = new Reserva("R005", empleado, sala, fecha, inicio, fin);
        String str = reserva.toString();
        assertTrue(str.contains("empleado="));
        assertTrue(str.contains("sala="));
        assertTrue(str.contains("fecha=2025-06-24"));
        assertTrue(str.contains("horaInicio=10:00"));
        assertTrue(str.contains("horaFin=11:00"));
    }
}
