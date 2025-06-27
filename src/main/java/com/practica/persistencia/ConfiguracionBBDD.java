package com.practica.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * Clase ConfiguracionBBDD
 *
 * Esta clase proporciona la configuración necesaria para establecer una
 * conexión con la base de datos MySQL utilizada en el sistema de gestión de
 * reservas de salas. Contiene constantes con los parámetros de conexión y un
 * método para obtener una conexión activa a la base de datos.
 *
 * Configuración: - Protocolo JDBC - Dirección IP del servidor - Puerto de la
 * base de datos - Nombre de la base de datos - Usuario y contraseña -
 * Configuración para deshabilitar el uso de SSL - Driver JDBC de MySQL
 *
 * Métodos: - getConnection(): Devuelve una conexión activa a la base de datos.
 */
public class ConfiguracionBBDD {
	/** Protocolo JDBC utilizado para la conexión */
	public static final String PROTOCOL = "jdbc:mysql://";
	/** Dirección IP del servidor de la base de datos */
	public static final String IP_SERVIDOR = "127.0.0.1";
	/** Puerto en el que escucha la base de datos */
	public static final String PORT_BBDD = "3306";
	/** Nombre de la base de datos */
	public static final String NOM_BBDD = "ReservaSalas";
	/** Parámetro para deshabilitar el uso de SSL */
	public static final String NO_USE_SSL = "?useSSL=false";
	/** Usuario de la base de datos */
	public static final String USER_BBDD = "root";
	/** Contraseña del usuario de la base de datos */
	public static final String PASWORD_BBDD = "root";
	/** Nombre del driver JDBC de MySQL */
	public static final String NOM_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
	/** URL completa de la base de datos */
	public static final String IP_SERVIDOR_BBDD = PROTOCOL + IP_SERVIDOR + ":" + PORT_BBDD + "/" + NOM_BBDD;

	
	/**
	 * Obtiene una conexión activa a la base de datos.
	 *
	 * @return Objeto Connection para interactuar con la base de datos.
	 * @throws SQLException Si ocurre un error al intentar conectarse.
	 */

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(IP_SERVIDOR_BBDD, USER_BBDD, PASWORD_BBDD);
	}

}
