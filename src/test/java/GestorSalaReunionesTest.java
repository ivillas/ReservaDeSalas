import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorSalaReuniones;

class GestorSalaReunionesTest {


    @Test
    void testBajaSala() {
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(() -> GestorBBDD.eliminarSala(1)).thenAnswer(invocation -> null);

            GestorSalaReuniones.bajaSala(new Scanner(System.in));

            mockedBBDD.verify(() -> GestorBBDD.eliminarSala(1), times(1));
        }
    }

    @Test
    void testListarSalas() {
        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            mockedBBDD.when(GestorBBDD::listarSalas).thenReturn(Arrays.asList(
                new SalaReuniones(1, "Sala A", 10, true, Arrays.asList("Proyector", "Pizarra")),
                new SalaReuniones(2, "Sala B", 20, false, Arrays.asList("Monitor", "Altavoces"))
            ));

            GestorSalaReuniones.listarSalas(new Scanner(new ByteArrayInputStream("no\n".getBytes())));

            mockedBBDD.verify(GestorBBDD::listarSalas, times(1));
        }
    }
}


