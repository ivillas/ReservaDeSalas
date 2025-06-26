import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.Arrays;

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
    void testAltaEmpleadoConScanner() throws SQLException {
        String simulatedInput = "33333333E\nsara@email.com\nSara\nLópez\nFinanzas\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            // Simular el método estático agregarEmpleado
            mockedBBDD.when(() -> GestorBBDD.agregarEmpleado(any(Empleado.class))).thenAnswer(invocation -> null);

            // Ejecutar el método
            GestorEmpleado.altaEmpleado(new java.util.Scanner(System.in));

            // Verificar que se llamó al método estático con un Empleado específico
            mockedBBDD.verify(() -> GestorBBDD.agregarEmpleado(argThat(empleado ->
                empleado.getDni().equals("33333333E") &&
                empleado.getNombre().equals("Sara") &&
                empleado.getApellidos().equals("López") &&
                empleado.getEmail().equals("sara@email.com") &&
                empleado.getDepartamento().equals("Finanzas")
            )), times(1));
        }
    }


    @Test
    void testModificarEmpleadoConScanner() throws SQLException {
        String simulatedInput = "33333333E\nNuevoNombre\nNuevoApellido\nnuevo@email.com\nIT\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
            // Simular el método estático listarEmpleados
            mockedBBDD.when(GestorBBDD::listarEmpleados).thenReturn(Arrays.asList(empleado));

            // Simular el método estático modificarEmpleado
            mockedBBDD.when(() -> GestorBBDD.modificarEmpleado(any(Empleado.class))).thenAnswer(invocation -> null);

            // Ejecutar el método
            GestorEmpleado.modificarEmpleado(new java.util.Scanner(System.in));

            // Verificar que se llamó al método estático
            mockedBBDD.verify(() -> GestorBBDD.modificarEmpleado(any(Empleado.class)), times(1));
        }
    }

    
}


