package ar.com.carreira.sabrina.utilidades;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	//Clase que provee de métodos estaticos para facilitar la conexión a la base de datos
	
	public static Connection conectarABD(HashMap<String,String> mapaConfig)  {
		//Realiza la conexion a la BD y devuelve un objeto Connection

		//Parámetros de la conexión, obtenidos del HashMap
		final String URL_BD = mapaConfig.get("url_bd") + mapaConfig.get("nombre_bd");
		final String USUARIO = mapaConfig.get("usuario");
		final String PASSWORD = mapaConfig.get("password");
		
		Connection cnx = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cnx = DriverManager.getConnection(URL_BD, USUARIO, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnx;
	}

}
