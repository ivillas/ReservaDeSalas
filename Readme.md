
# Proyecto de Gestión de Empleados

Este proyecto es una aplicación básica para la gestión de reservas de salas para reuniones de una 
empresa con todo el CRUD de empleados, salas y reservas, evitando conflictos de duplicados y cruces 
de datos. Desarrollada en Java utilizando maven, JUnit 5, Mockito y JaCoCo para las pruebas unitarias.

## Estructura del Proyecto
```
src  
├── main  
│   └── java  
│       └── com  
│           └── practica  
│               └── main  
│                   └── MenuConsola.java  
│               └── modelo  
│                   ├── Empleado.java 
│                   ├── SalaReuniones.java
│                   └── Reserva.java
│               └── persistencia
│                   ├── ConfiguracionBBDD.java 
│                   └── GestorBBDD.java
│               └── servicio 
│                   ├── GestorEmpleado.java 
│                   ├── GestorReserva.java
│                   └── GestorSalaReuniones.java
├─── test  
│    └── java  
│        ├── EmpleadoTest.java  
│        ├── GestorEmpleadoTest.java
│        ├── GestorReservaTest.java
│        ├── GestorSalaReunionesTest.java
│        ├── ReservaTest.java
│        └── SalaReunionesTest.java
├── documentacion/
├── mysql/
├── pom.xml
└── README.md
```

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

El proyecto incluye un conjunto completo de pruebas unitarias diseñadas para garantizar la funcionalidad y estabilidad 
de las principales características de la aplicación. 
Estas pruebas se han implementado utilizando JUnit 5 y Mockito, y se centran en las siguientes áreas clave:

### Cobertura de las Pruebas


### Gestión de Empleados:

 - **Alta de Empleados:** Verifica que los empleados se registren correctamente en la base de datos.
 - **Modificación de Empleados:** Valida que los datos de los empleados se actualicen correctamente.
 - **Baja de Empleados:** Comprueba que los empleados se eliminen correctamente, asegurando que no tengan reservas futuras.
 - **Listado de Empleados:** Asegura que se recupera la lista completa de empleados desde la base de datos.

### Gestión de Reservas:

 - **Creación de Reservas:** Valida que las reservas se registren correctamente, verificando la disponibilidad de las salas.
 - **Modificación de Reservas:** Comprueba que las reservas se actualicen correctamente, incluyendo la validación de conflictos de horarios.
 - **Cancelación de Reservas:** Verifica que las reservas se cancelen correctamente.
 - **Disponibilidad de Salas:** Asegura que las salas estén disponibles en los horarios solicitados.

### Gestión de Salas de Reuniones:

 - **Alta, Baja y Modificación de Salas:** Valida que las salas se gestionen correctamente en la base de datos.
 - **Listado de Salas Disponibles:** Comprueba que se recuperen las salas disponibles para una fecha y hora específicas.

### Simulación de Base de Datos:

 - **Uso de MockedStatic: Simula métodos estáticos de la clase GestorBBDD.
 - **Verificación de llamadas: Métodos como listarReservas, altaReserva, bajaReserva, listarSalas, entre otros.

### Herramientas Utilizadas

 - **JUnit 5:** Framework principal para la creación de pruebas unitarias.
 - **Mockito:** Utilizado para simular dependencias y métodos estáticos.
 - **JaCoCo:** Herramienta para medir la cobertura de las pruebas.


## Instrucciones para Ejecutar los Tests

1. Asegúrate de que las dependencias de pruebas (JUnit 5 y Mockito) estén configuradas en el archivo `pom.xml`.
2. Para ejecutar los tests desde el IDE:
   - Navega a la carpeta `test` en el proyecto.
   - Haz clic derecho en la clase de prueba o en el paquete y selecciona "Run Tests".
3. Para ejecutar los tests desde la terminal, dirigete a la carpeta raiz del proyecto desde la terminal y usa el siguiente comando:
   ```bash
   mvn test
   ```
4. Los resultados de las pruebas se mostrarán en la consola.


 - **Explicación de las Pruebas:**


 - **testExisteReserva:**  Verifica si una reserva existe para un empleado en una fecha y hora específicas.
 - **testObtenerSalasLibres:** Comprueba que se obtienen las salas disponibles para una fecha y hora.
 - **testVerificarDisponibilidadSala:** Valida si una sala está disponible en un rango de tiempo.
 - **testAltaReserva:** Simula el registro de una nueva reserva y verifica el ID generado.
 - **testCancelacionReserva:** Comprueba que una reserva se cancela correctamente.

Estas pruebas utilizan MockedStatic para simular los métodos estáticos de GestorBBDD. Asegúrate de incluir las dependencias de JUnit 5 y Mockito en tu archivo pom.xml.


## Información de la Base de Datos

La base de datos utilizada en este proyecto se llama `ReservaSalas`. A continuación, se detalla cómo crearla y qué contiene cada archivo SQL.

### Creación de la Base de Datos

1. Asegúrate de tener un servidor MySQL en ejecución.
2. Configura la conexión a la base de datos en el archivo `ConfiguracionBBDD.java`.
3. Ejecuta el archivo `Mysql/bd_ReservaSalas.sql` para crear la base de datos con todas las tablas/columnas vacías o `Mysql/bd_ReservaSalas_Full.sql` para crear la base de datos completa con todas las tablas y datos iniciales.

### Archivos SQL y su Contenido

- **`diagrama_er.jpg`**:  
  Archivo de imagen que muestra el diagrama ER de la base de datos.

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
2. Importa el proyecto en tu IDE (por ejemplo, IntelliJ IDEA, ECLIPSE...).
3. Asegúrate de tener configurado **Java 11** o superior y **Maven**.

## Instrucciones para Ejecutar la Aplicación

1. Configura la conexión a la base de datos en `ConfiguracionBBDD.java`.
2. Ejecuta la clase principal `MenuConsola.java` desde el IDE o con Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="com.practica.main.MenuConsola"
   ```


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

