import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorSalaReuniones;

class GestorSalaReunionesTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outContent));
	}

	@Test
	void testAltaSala() {
		// Simular entrada del usuario
		String simulatedInput = "Sala de Pruebas\n100\nsí\nProyector, Pizarra\n";
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		Scanner scanner = new Scanner(System.in);

		// Usar un mock para GestorBBDD
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			// Simular el comportamiento de agregarSala y listarSalas
			mockedBBDD.when(() -> GestorBBDD.agregarSala(any(SalaReuniones.class))).thenAnswer(invocation -> {
				// Simulación exitosa, no hacer nada
				return null;
			});

			mockedBBDD.when(GestorBBDD::listarSalas).thenReturn(Arrays
					.asList(new SalaReuniones(1, "Sala de Pruebas", 100, true, Arrays.asList("Proyector", "Pizarra"))));

			// Ejecutar el método
			GestorSalaReuniones.altaSala(scanner);

			// Verificar la salida
			String output = outContent.toString();
			assertTrue(output.contains("Sala registrada exitosamente."));

			// Verificar que la sala fue registrada
			List<SalaReuniones> salas = GestorBBDD.listarSalas();
			assertTrue(salas.stream().anyMatch(sala -> sala.getNombre().equals("Sala de Pruebas")));
		} catch (Exception e) {
			fail("Error al verificar la sala registrada: " + e.getMessage());
		}
	}

	@Test
	void testModificarSala() {
		// Crear una sala existente
		SalaReuniones salaOriginal = new SalaReuniones(1, "Sala Original", 50, true, Arrays.asList("Proyector"));

		// Simular entrada del usuario para modificar la sala
		String simulatedInput = "1\nSala Modificada\n75\nno\nPizarra\n";
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		Scanner scanner = new Scanner(System.in);

		// Usar un mock para GestorBBDD
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			// Simular el comportamiento de listarSalas y modificarSala
			mockedBBDD.when(GestorBBDD::listarSalas).thenReturn(Arrays.asList(salaOriginal));
			mockedBBDD.when(() -> GestorBBDD.modificarSala(any(SalaReuniones.class))).thenAnswer(invocation -> {
				// Simulación exitosa, no hacer nada
				return null;
			});

			// Ejecutar el método
			GestorSalaReuniones.modificarSala(scanner);

			// Verificar la salida
			String output = outContent.toString();
			assertTrue(output.contains("Sala modificada exitosamente."));

			// Verificar que la sala fue modificada
			List<SalaReuniones> salas = GestorBBDD.listarSalas();
			assertTrue(salas.stream()
					.anyMatch(sala -> sala.getNombre().equals("Sala Modificada") && sala.getCapacidad() == 75));
		} catch (Exception e) {
			fail("Error al verificar la sala modificada: " + e.getMessage());
		}
	}

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
			mockedBBDD.when(GestorBBDD::listarSalas)
					.thenReturn(Arrays.asList(
							new SalaReuniones(1, "Sala A", 10, true, Arrays.asList("Proyector", "Pizarra")),
							new SalaReuniones(2, "Sala B", 20, false, Arrays.asList("Monitor", "Altavoces"))));

			GestorSalaReuniones.listarSalas(new Scanner(new ByteArrayInputStream("no\n".getBytes())));

			mockedBBDD.verify(GestorBBDD::listarSalas, times(1));
		}
	}
}

