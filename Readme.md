
# Proyecto de Gestión de Empleados

Este proyecto es una aplicación básica para la gestión de empleados, desarrollada en Java utilizando JUnit 5 para las pruebas unitarias.

## Estructura del Proyecto

src  
├── main  
│   └── java  
│       └── com  
│           └── practica  
│               └── modelo  
│                   └── Empleado.java  
│               └── main  
│                   └── Run.java  
└── test  
    └── java  
        └── com  
            └── practica  
                └── modelo  
                    └── EmpleadoTest.java  

## Funcionalidades

- **Clases `Empleado`, `Sala` y `Reserva`**:
  - Constructor vacío y con parámetros.
  - Métodos `getters` y `setters`.
  - Métodos sobrescritos: `equals`, `hashCode`, y `toString`.

- **Pruebas unitarias**:
  - Validación de constructores.
  - Pruebas de métodos `getters` y `setters`.
  - Verificación de `equals`, `hashCode`, y `toString`.
  - **Pruebas de simulación de base de datos**:
  - Uso de `MockedStatic` para simular métodos estáticos de la clase `GestorBBDD`.
  - Verificación de llamadas a métodos estáticos como `agregarSala`, `eliminarSala`, `modificarSala` y `listarSalas`.
  - Validación de que los métodos simulados interactúan correctamente con la lógica de negocio.
  
  
## Pruebas Unitarias

Las pruebas unitarias son una parte fundamental de este proyecto, asegurando que las funcionalidades principales de las entidades `Sala`, `Empleado` y `Reserva` se comporten como se espera. Se han implementado utilizando **JUnit 5** y **Mockito**.

### Cobertura de las Pruebas

1. **Clases Modelo (`Sala`, `Empleado`, `Reserva`)**:

   - Validación de constructores.
   - Pruebas de métodos `getters` y `setters`.
   - Verificación de los métodos sobrescritos: `equals`, `hashCode` y `toString`.

2. **Gestión de Entidades**:
   - **Alta de Entidad**: Verifica que una entidad (`Sala`, `Empleado` o `Reserva`) se agrega correctamente a la base de datos.
   - **Baja de Entidad**: Comprueba que una entidad se elimina correctamente.
   - **Modificación de Entidad**: Valida que los cambios en una entidad se actualizan correctamente.
   - **Listado de Entidades**: Asegura que se recupera la lista completa de entidades desde la base de datos.

3. **Simulación de Base de Datos**:
   - Uso de `MockedStatic` para simular métodos estáticos de la clase `GestorBBDD`.
   - Verificación de llamadas a métodos estáticos como `agregarEntidad`, `eliminarEntidad`, `modificarEntidad` y `listarEntidades`.
   - Validación de que los métodos simulados interactúan correctamente con la lógica de negocio.

### Herramientas Utilizadas

- **JUnit 5**: Framework principal para la creación y ejecución de pruebas unitarias.
- **Mockito**: Utilizado para la simulación de dependencias y métodos estáticos.
- **AssertJ**: Para realizar aserciones más legibles y expresivas (si está configurado).

### Ejemplo de Prueba Generalizada

A continuación, un ejemplo de prueba para el método `bajaSala`:

@Test void testBajaSala() { int idSala = 1;

try (MockedStatic<GestorBBDD> mockedBBDD = mockStatic(GestorBBDD.class)) {
    mockedBBDD.when(() -> GestorBBDD.eliminarSala(idSala)).thenAnswer(invocation -> null);

    gestorSalaReuniones.bajaSala(idSala);

    mockedBBDD.verify(() -> GestorBBDD.eliminarSala(idSala), times(1));
}
}



Este ejemplo muestra cómo se utiliza `MockedStatic` para simular un método estático y verificar que se llama con los parámetros correctos.

### Cobertura de Código

Se recomienda utilizar herramientas como **JaCoCo** para medir la cobertura de las pruebas y garantizar que todas las funcionalidades críticas estén cubiertas.




## Información de la Base de Datos

La base de datos utilizada en este proyecto se llama `ReservaSalas`. A continuación, se detalla cómo crearla y qué contiene cada archivo SQL.

### Creación de la Base de Datos

1. Asegúrate de tener un servidor MySQL en ejecución.
2. Configura la conexión a la base de datos en el archivo `ConfiguracionBBDD.java`.
3. Ejecuta el archivo `Mysql/bd_ReservaSalas.sql` para crear la base de datos con todas las tablas/columnas vacías o `Mysql/bd_ReservaSalas_Full.sql` para crear la base de datos completa con todas las tablas y datos iniciales.

### Archivos SQL y su Contenido

- **`Mysql/bd_ReservaSalas.sql`**:  
  Archivo vacío que puede ser utilizado como plantilla para agregar datos o realizar pruebas personalizadas.

- **`Mysql/bd_ReservaSalas_Full.sql`**:  
  Contiene la estructura completa de la base de datos `ReservaSalas`, incluyendo las tablas `Empleados`, `SalaReuniones` y `Reserva`, junto con los datos iniciales. Este archivo es suficiente para crear y poblar la base de datos desde cero.

- **`Mysql/bd_Empleados.sql`**:  
  Contiene los datos iniciales para la tabla `Empleados`, incluyendo información como DNI, nombre, apellidos, email y departamento.

- **`Mysql/bd_Salas.sql`**:  
  Contiene los datos iniciales para la tabla `SalaReuniones`, como el nombre de la sala, capacidad, disponibilidad y recursos disponibles.

- **`bd_Reservas.sql`**:  
  Contiene las reservas iniciales para las salas, incluyendo el DNI del empleado, el ID de la sala, la fecha, la hora de inicio y la hora de fin. También actualiza la disponibilidad de las salas reservadas '(Necesita tener los datos de bd_Empleados.sql y bd_Salas.sql para su correcto funcionamiento)'.

### Ubicación de los Archivos SQL

Todos los archivos SQL se encuentran en la carpeta `Mysql` dentro del proyecto.

## Instrucciones para Ejecutar la Aplicación

1. Asegúrate de tener configurado un entorno de desarrollo con **Java 11** o superior y **Maven**.
2. Clona este repositorio en tu máquina local.
3. Configura la conexión a la base de datos en el archivo `ConfiguracionBBDD.java`.
4. Abre el proyecto en tu IDE (por ejemplo, IntelliJ IDEA).
5. Ejecuta la clase principal del proyecto `Run.java` desde el IDE o con el siguiente comando en la terminal:
   ```bash
   mvn exec:java -Dexec.mainClass="com.practica.main.Run"
   ```

## Instrucciones para Ejecutar los Tests

1. Asegúrate de que las dependencias de pruebas (JUnit 5 y Mockito) estén configuradas en el archivo `pom.xml`.
2. Para ejecutar los tests desde el IDE:
   - Navega a la carpeta `test` en el proyecto.
   - Haz clic derecho en la clase de prueba o en el paquete y selecciona "Run Tests".
3. Para ejecutar los tests desde la terminal, usa el siguiente comando:
   ```bash
   mvn test
   ```
4. Los resultados de las pruebas se mostrarán en la consola.

## Metodología de Desarrollo

Este proyecto ha sido desarrollado utilizando la metodología **Scrum**. Toda la información del proceso, incluyendo las reuniones, sprints y tareas realizadas, está documentada en el archivo `Documentacion_Scrum_Ivan_villa_fontelles.docx`, ubicado en la carpeta `documentacion`.

## Tecnologías Utilizadas

- **Java**
- **JUnit 5**
- **Maven**
- **MySQL**

## Autor

Proyecto desarrollado por [Ivan Villa].

## Licencia

Este proyecto está bajo la licencia [MIT](https://opensource.org/licenses/MIT).

