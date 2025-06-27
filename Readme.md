
# Proyecto de Gestión de Empleados

Este proyecto es una aplicación básica para la gestión de reservas de salas para reuniones de una empresa con todo el CRUD de empleados, salas y reservas, desarrollada en Java utilizando JUnit 5,Mockito y JaCoCo para las pruebas unitarias.

## Estructura del Proyecto

src  
├── main  
│   └── java  
│       └── com  
│           └── practica  
│               └── modelo  
│                   ├── Empleado.java 
│					├── SalaReuniones.java
│					└── Reserva.java
│ 
│               └── main  
│                   └── Run.java  
└── test  
    └── java  
        └── com  
            └── practica  
                └── modelo  
                    └── EmpleadoTest.java  



## Funcionalidades

1. **Gestión de Empleados**:
   - Alta, baja y modificación de empleados.
   - Validación del formato del DNI.
   - Listado de empleados.

2. **Gestión de Salas de Reuniones**:
   - Alta, baja y modificación de salas.
   - Listado de salas disponibles.
   - Gestión de recursos asociados a las salas.

3. **Gestión de Reservas**:
   - Creación, modificación y cancelación de reservas.
   - Verificación de disponibilidad de salas.
   - Listado de reservas existentes.
  
## Pruebas Unitarias

Las pruebas unitarias son una parte fundamental de este proyecto, asegurando que las funcionalidades principales de las entidades `Sala`, `Empleado` y `Reserva` se comporten como se espera. Se han implementado utilizando **JUnit 5** y **Mockito**.

### Cobertura de las Pruebas

1. **Clases Modelo (`Empleado`, `SalaReuniones`, `Reserva`)**:
   - Validación de constructores.
   - Pruebas de métodos `getters` y `setters`.
   - Verificación de los métodos sobrescritos: `equals`, `hashCode` y `toString`.

2. **Gestión de Entidades**:
   - **Alta de Entidad**: Verifica que las entidades se agregan correctamente a la base de datos.
   - **Baja de Entidad**: Comprueba que las entidades se eliminan correctamente.
   - **Modificación de Entidad**: Valida que los cambios en las entidades se actualizan correctamente.
   - **Listado de Entidades**: Asegura que se recupera la lista completa de entidades desde la base de datos.

3. **Simulación de Base de Datos**:
   - Uso de `MockedStatic` para simular métodos estáticos de la clase `GestorBBDD`.
   - Verificación de llamadas a métodos como `agregarEntidad`, `eliminarEntidad`, `modificarEntidad` y `listarEntidades`.

### Herramientas Utilizadas

- **JUnit 5**: Framework principal para pruebas unitarias.
- **Mockito**: Simulación de dependencias y métodos estáticos.
- **JaCoCo**: Para medir la cobertura de las pruebas.


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

## Instrucciones para Descargar el Proyecto

1. Clona el repositorio:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. Importa el proyecto en tu IDE (por ejemplo, IntelliJ IDEA).
3. Asegúrate de tener configurado **Java 11** o superior y **Maven**.

## Instrucciones para Ejecutar la Aplicación

1. Configura la conexión a la base de datos en `ConfiguracionBBDD.java`.
2. Ejecuta la clase principal `Run.java` desde el IDE o con Maven:
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
- **Mockito**
- **Maven**
- **MySQL**
## Autor

Proyecto desarrollado por [Ivan Villa].

## Licencia

Este proyecto está bajo la licencia [MIT](https://opensource.org/licenses/MIT).

