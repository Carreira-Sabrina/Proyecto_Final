package ar.com.carreira.sabrina.utilidades;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ar.com.carreira.sabrina.entidades.Equipo;


public class ParseoEquipos {
	
	public static HashMap<String,Equipo> instanciarEquiposDeTabla(Connection cnx, String nombreTablaEquipos) {
		//El HashMap donde se almacenarán los equipos, key = nombre value=instancia
		HashMap<String,Equipo> mapaEquipos = new HashMap<>();
		
		try {
			String sqlLeerTabla = "SELECT * FROM " + nombreTablaEquipos;
			Statement stm = cnx.createStatement();
			ResultSet resultadoConsulta =stm.executeQuery(sqlLeerTabla);
			
			while(resultadoConsulta.next()) {
				String codigo = resultadoConsulta.getString(1);
				String nombre = resultadoConsulta.getString(2);
				
				//Se instancia el equipo
				Equipo e = new Equipo(codigo, nombre);
				
				//Se guarda en el HashMap
				mapaEquipos.put(nombre, e);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapaEquipos;	
	}
	
	
	
}//end of class


