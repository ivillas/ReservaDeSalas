


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.practica.modelo.Empleado;

public class EmpleadoTest {

    @Test
    void testConstructorVacio() {
        Empleado empleado = new Empleado();
        assertNull(empleado.getDni());
        assertNull(empleado.getNombre());
        assertNull(empleado.getApellidos());
        assertNull(empleado.getEmail());
        assertNull(empleado.getDepartamento());
    }

    @Test
    void testConstructorConDni() {
        Empleado empleado = new Empleado("12345678A");
        assertEquals("12345678A", empleado.getDni());
        assertNull(empleado.getNombre());
    }

    @Test
    void testConstructorCompleto() {
        Empleado empleado = new Empleado("12345678A", "Juan", "Pérez", "juan@email.com", "IT");
        assertEquals("12345678A", empleado.getDni());
        assertEquals("Juan", empleado.getNombre());
        assertEquals("Pérez", empleado.getApellidos());
        assertEquals("juan@email.com", empleado.getEmail());
        assertEquals("IT", empleado.getDepartamento());
    }

    @Test
    void testSettersYGetters() {
        Empleado empleado = new Empleado();
        empleado.setDni("87654321B");
        empleado.setNombre("Ana");
        empleado.setApellidos("García");
        empleado.setEmail("ana@email.com");
        empleado.setDepartamento("HR");
        assertEquals("87654321B", empleado.getDni());
        assertEquals("Ana", empleado.getNombre());
        assertEquals("García", empleado.getApellidos());
        assertEquals("ana@email.com", empleado.getEmail());
        assertEquals("HR", empleado.getDepartamento());
    }

    @Test
    void testEqualsYHashCode() {
        Empleado e1 = new Empleado("11111111C", "Luis", "Martín", "luis@email.com", "Ventas");
        Empleado e2 = new Empleado("11111111C");
        Empleado e3 = new Empleado("22222222D");
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }

    @Test
    void testToString() {
        Empleado empleado = new Empleado("33333333E", "Sara", "López", "sara@email.com", "Finanzas");
        String esperado = "dni=33333333E, nombre=Sara, apellidos=López, email=sara@email.com, departamento=Finanzas.\n";
        assertEquals(esperado, empleado.toString());
    }
}