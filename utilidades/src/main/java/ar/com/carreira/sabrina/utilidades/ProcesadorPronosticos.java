package ar.com.carreira.sabrina.utilidades;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import ar.com.carreira.sabrina.entidades.*;

public class ProcesadorPronosticos {
	
	//Para no pasar tantas veces los mismos parámetros, estos mètodos no pueden ser estáticos
	
	//Lista de atributos necesarios para los métodos
	private Connection cnx = null; //En constructor
	private String nombreTabla =""; //En constructor
	private ResultSet resultadoConsulta = null; //se obtiene con setter
	private HashMap<String,Equipo> mapaEquipos = null;//se obtiene con un setter
	private ArrayList<Ronda> rondas = null; // se obtiene con un setter
	private ArrayList<Partido> todosLosPartidos = null;
	
	//Constructor
	public ProcesadorPronosticos(Connection cnx,String nombreTabla) {
		this.cnx = cnx;
		this.nombreTabla = nombreTabla;
	}
			
	public void setResultadoConsulta() {
		//Aqui se leen los prontosticos de la BD, luego de procesan en otro método
		String query = "SELECT * FROM " + this.nombreTabla;
		ResultSet rs = null;
		try {
			Statement consulta = this.cnx.createStatement();
			rs = consulta.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.resultadoConsulta = rs;
	}
	
	
	public void setMapaEquipos(HashMap<String,Equipo> mapaEquipos) {
		this.mapaEquipos = mapaEquipos;
		
	}//
	
	public void setRondas(ArrayList<Ronda> rondas) {
		this.rondas = rondas;
	}//
	
	public void setTodosLosPartidos() {
		//Toma todas las ArrayList de cada ronda y las combina en una sola ArrayList de Partido	
		ArrayList<Partido> temp = new ArrayList<>(); 
		
		for(Ronda ronda: this.rondas ) {
			temp.addAll(ronda.getPartidos());
		}
		this.todosLosPartidos = temp;
	}//
	
	public ArrayList<Pronostico> instanciarPronosticos() {
		//Devuelve un ArrayList de pronosticos
		ArrayList<Pronostico> pronosticos = new ArrayList<>();
		
		try {
			while(this.resultadoConsulta.next()) {
				//Se decide que si hay empate, como equipo se pase null, Partido ya està preparado para eso
				//int id = this.resultadoConsulta.getInt(1);
				String numRonda = this.resultadoConsulta.getString(2);
				String participante = this.resultadoConsulta.getString(3);
				String nombreEq1 = this.resultadoConsulta.getString(4);
				//El que tenga la X será verdadero
				boolean gana1 = (this.resultadoConsulta.getString(5).equals("X")) ? true : false;
				boolean empata = (this.resultadoConsulta.getString(6).equals("X")) ? true : false;
				boolean gana2 = (this.resultadoConsulta.getString(7).equals("X")) ? true : false;
				String nombreEq2 = this.resultadoConsulta.getString(8);
				
				String codigoEq1 = obtenerCodigoEquipo(nombreEq1);
				String codigoEq2 = obtenerCodigoEquipo(nombreEq2);
				
				String aliasPartido = codigoEq1 + "-" + codigoEq2;
				
				//Teniendo el alias, ya puedo buscar el partido
				Partido partido = obtenerPartido(aliasPartido);
				
				ResultadoEnum resultado;
				
				Equipo equipoElegido = null;
				
				if (gana1 == true) {
					equipoElegido = obtenerInstanciaEquipo(nombreEq1);
					
				}
				if (gana2 == true) {
					equipoElegido = obtenerInstanciaEquipo(nombreEq2);
				}
				if (empata == true) {
					equipoElegido = null;		
				}
				
				//Ya tendrìa toda la información para instanciar los pronósticos
				Pronostico pr = new Pronostico(numRonda, participante, partido);
				pr.setAliasPartido(aliasPartido);
				pr.setPartido(partido);
				pr.setEquipo(equipoElegido);
				pr.setResultado();
				
				//Agrego el pronostico a la ArrayList
				pronosticos.add(pr);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pronosticos;
		
	}//
	
	
	public Partido obtenerPartido(String aliasPartido) {
		//Se busca la instancia de Partido que coincide con el de la apuestas (se busca por alias)
		
		//Requerimiento: haber seteado el atributo todosLosPartidos
		
		Partido partidoCoincidente = null;
		
		for(int i = 0; i < this.todosLosPartidos.size(); i++) {
			Partido p = todosLosPartidos.get(i);
			if (aliasPartido.equals(p.getAliasPartido())) {
				partidoCoincidente = p;
				break;
			}
		}
		return partidoCoincidente;	
	}//
	
	
	
	public Equipo obtenerInstanciaEquipo(String nombreEquipo) {
		//Se busca la instancia de Equipo cuyo nombre coincide con la clave del HM
		return this.mapaEquipos.get(nombreEquipo);	
	}
	
	public String obtenerCodigoEquipo(String nombreEquipo) {
		//Devuelve el codigo de pais a partir de un nombre de equipo leyendo la BD
		String codPais ="";
		PreparedStatement ps = null; //para no tener problemas con las comillas
		ResultSet rs =null;
		String queryCodPais = "SELECT codigo FROM equipos WHERE nombre = ?";
		
		try {
			ps = this.cnx.prepareStatement(queryCodPais);
			ps.setString(1,nombreEquipo);
			rs = ps.executeQuery();
			rs.next(); //CUIDADO ! si no se mueve el cursor da error !
			codPais = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codPais;
	}//

}//end of class
