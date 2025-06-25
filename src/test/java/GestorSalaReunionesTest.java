
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorSalaReuniones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class GestorSalaReunionesTest {

    private GestorSalaReuniones gestorSalaReuniones;

    @BeforeEach
    void setUp() {
        gestorSalaReuniones = new GestorSalaReuniones();
    }

    @Test
    void testAltaSala() {
        SalaReuniones sala = new SalaReuniones(1, "Sala A", 10, true, Arrays.asList("Proyector", "Pizarra"));

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.agregarSala(sala)).thenAnswer(invocation -> null);

            gestorSalaReuniones.altaSala(sala);

            mockedBBDD.verify(() -> GestorBBDD.agregarSala(sala), times(1));
        }
    }

    @Test
    void testBajaSala() {
        int idSala = 1;

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.eliminarSala(idSala)).thenAnswer(invocation -> null);

            gestorSalaReuniones.bajaSala(idSala);

            mockedBBDD.verify(() -> GestorBBDD.eliminarSala(idSala), times(1));
        }
    }

    @Test
    void testModificarSala() {
        SalaReuniones sala = new SalaReuniones(1, "Sala B", 15, false, Arrays.asList("Televisión"));

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.modificarSala(sala)).thenAnswer(invocation -> null);

            gestorSalaReuniones.modificarSala(sala);

            mockedBBDD.verify(() -> GestorBBDD.modificarSala(sala), times(1));
        }
    }

    @Test
    void testListarSalas() {
        List<SalaReuniones> salasMock = Arrays.asList(
            new SalaReuniones(1, "Sala A", 10, true, Arrays.asList("Proyector")),
            new SalaReuniones(2, "Sala B", 15, false, Arrays.asList("Televisión"))
        );

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(GestorBBDD::listarSalas).thenReturn(salasMock);

            List<SalaReuniones> salas = gestorSalaReuniones.listarSalas();

            assertEquals(2, salas.size());
            assertEquals("Sala A", salas.get(0).getNombre());
            assertEquals("Sala B", salas.get(1).getNombre());

            mockedBBDD.verify(GestorBBDD::listarSalas, times(1));
        }
    }
}

