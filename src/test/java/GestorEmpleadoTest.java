
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.practica.modelo.Empleado;
import com.practica.persistencia.GestorBBDD;
import com.practica.servicio.GestorEmpleado;

class GestorEmpleadoTest {

	private Empleado empleado;

	@BeforeEach
	void setUp() {
		empleado = new Empleado("33333333E", "Sara", "López", "sara@email.com", "Finanzas");
	}

	@Test
	void testAltaEmpleado() throws SQLException {
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			GestorEmpleado.altaEmpleado(empleado);
			mockedBBDD.verify(() -> GestorBBDD.agregarEmpleado(empleado), times(1));
		}
	}

	@Test
	void testBajaEmpleado() throws SQLException {
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			GestorEmpleado.bajaEmpleado(empleado.getDni());
			mockedBBDD.verify(() -> GestorBBDD.eliminarEmpleado(empleado.getDni()), times(1));
		}
	}

	@Test
	void testModificarEmpleado() throws SQLException {
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			GestorEmpleado.modificarEmpleado(0, empleado);
			mockedBBDD.verify(() -> GestorBBDD.modificarEmpleado(empleado), times(1));
		}
	}

	@Test
	void testListaEmpleados() throws SQLException {
		List<Empleado> empleadosMock = Arrays.asList(empleado);
		try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
			mockedBBDD.when(GestorBBDD::listarEmpleados).thenReturn(empleadosMock);
			List<Empleado> empleados = GestorEmpleado.listaEmpleados();
			assertEquals(1, empleados.size());
			assertEquals(empleado, empleados.get(0));
		}
	}
}


