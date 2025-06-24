package com.practica.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracionBBDD {
	
	public static final String PROTOCOL = "jdbc:mysql://";
	public static final String IP_SERVIDOR = "127.0.0.1";
	public static final String IP_SERVIDOR_BBDD = "jdbc:mysql://127.0.0.110:3306/ReservaSalas";
	public static final String PORT_BBDD = "3306";
	public static final String NOM_BBDD = "ReservaSalas";
	public static final String NO_USE_SSL = "?useSSL=false";
	public static final String USER_BBDD = "root";
	public static final String PASWORD_BBDD = "root";
	public static final String NOM_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
	
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(IP_SERVIDOR_BBDD, USER_BBDD, PASWORD_BBDD);
    }

}
