import org.junit.jupiter.api.Test;

import com.practica.modelo.SalaReuniones;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalaReunionesTest {
    @Test
    void testConstructorVacio() {
        SalaReuniones sala = new SalaReuniones();
        assertEquals(0, sala.getId());
        assertNull(sala.getNombre());
        assertEquals(0, sala.getCapacidad());
        assertFalse(sala.isDisponible());
        assertNull(sala.getRecursosDisponibles());
    }

    @Test
    void testConstructorConId() {
        SalaReuniones sala = new SalaReuniones(5);
        assertEquals(5, sala.getId());
        assertNull(sala.getNombre());
    }

    @Test
    void testConstructorCompleto() {
        List<String> recursos = Arrays.asList("Proyector", "Pizarra");
        SalaReuniones sala = new SalaReuniones(1, "Sala Azul", 10, true, recursos);
        assertEquals(1, sala.getId());
        assertEquals("Sala Azul", sala.getNombre());
        assertEquals(10, sala.getCapacidad());
        assertTrue(sala.isDisponible());
        assertEquals(recursos, sala.getRecursosDisponibles());
    }

    @Test
    void testSettersYGetters() {
        SalaReuniones sala = new SalaReuniones();
        sala.setId(2);
        sala.setNombre("Sala Verde");
        sala.setCapacidad(20);
        sala.setDisponible(true);
        List<String> recursos = Collections.singletonList("TV");
        sala.setRecursosDisponibles(recursos);
        assertEquals(2, sala.getId());
        assertEquals("Sala Verde", sala.getNombre());
        assertEquals(20, sala.getCapacidad());
        assertTrue(sala.isDisponible());
        assertEquals(recursos, sala.getRecursosDisponibles());
    }

    @Test
    void testEqualsYHashCode() {
        SalaReuniones sala1 = new SalaReuniones(3);
        SalaReuniones sala2 = new SalaReuniones(3);
        SalaReuniones sala3 = new SalaReuniones(4);
        assertEquals(sala1, sala2);
        assertNotEquals(sala1, sala3);
        assertEquals(sala1.hashCode(), sala2.hashCode());
        assertNotEquals(sala1.hashCode(), sala3.hashCode());
    }

    @Test
    void testToString() {
        List<String> recursos = Arrays.asList("Proyector", "Pizarra");
        SalaReuniones sala = new SalaReuniones(7, "Sala Roja", 15, false, recursos);
        String str = sala.toString();
        assertTrue(str.contains("id=7"));
        assertTrue(str.contains("nombre=Sala Roja"));
        assertTrue(str.contains("capacidad=15"));
        assertTrue(str.contains("disponible=false"));
        assertTrue(str.contains("Proyector"));
        assertTrue(str.contains("Pizarra"));
    }
}
