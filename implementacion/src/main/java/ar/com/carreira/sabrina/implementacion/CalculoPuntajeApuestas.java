package ar.com.carreira.sabrina.implementacion;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import ar.com.carreira.sabrina.entidades.*;
import ar.com.carreira.sabrina.utilidades.*;

public class CalculoPuntajeApuestas {
    public static void main( String[] args ){
        //Arg 0 = archivo config conexion, Arg 1 = archivo resultados
    	
    	// ======================== SETUP ==========================================
    	String archivoConfig = args[0];
    	String archivoResultados = args[1];
    	
    	//1) Leer archivo configuración (contiene datos para conexiòn DB y los puntos extras otorgados
  
    	List<String> datosConfig = LecturaArchivo.leerArchivo(archivoConfig);
    	
    	//2) Parsear archivo configuracion 
    	
    	HashMap<String,String> mapaConfig = ParseoConfig.parsearConfig(datosConfig);
    	
    	//3) Crear conexion con BD
    	
    	Connection conexion = ConexionBD.conectarABD(mapaConfig);
    	
    	//4) Se lee del archivo de configuraciòn los puntos extras otorgados por acertar todos los pronósticos de la
    	//ronda y de la fase
    	
    	int ptsExtrasRondaCompleta = Integer.parseInt(mapaConfig.get("puntos_extras_acertar_todos_los_partidos_ronda"));
    	int ptsExtrasFaseCompleta = Integer.parseInt(mapaConfig.get("puntos_extras_acertar_todos_los_partidos_fase"));
    	
    	System.out.println("SE OTORGARÁN " + ptsExtrasRondaCompleta + " PUNTOS EXTRA A LOS JUGADORES QUE ACIERTEN "
    						+ "TODOS LOS PUNTOS DE LA RONDA");
    	System.out.println("SE OTORGARÁN " + ptsExtrasFaseCompleta + " PUNTOS EXTRA A LOS JUGADORES QUE ACIERTEN "
    						+ "TODOS LOS PUNTOS DE LA FASE \n");
  
    	// =======================INSTANCIACION DE CLASES ===============================
    	
    	//1) Equipos: se leen sus datos de la BD y con ellos se crean instancias que se guardan en HashMap
    	
    	String tablaSqlEquipos = "equipos";
    	
    	//La clave es el nombre del equipo
    	HashMap<String,Equipo> mapaEquipos = ParseoEquipos.instanciarEquiposDeTabla(conexion, tablaSqlEquipos);
    	/*
    	2) Lectura del archivo csv con los resultados: Termina generando un ArrayList de instancias de Ronda
    	cada una contiene las instancias de los partidos correspondientes (ver mas detalles en los comentarios
    	del método auxiliar. Una fase es un conjunto de rondas
    	*/
    	ArrayList<Ronda> fase = ParseoResultados.instanciarPartidosDeArchivo(archivoResultados, mapaEquipos);
    	
    	//Para saber cuantos partidos hay en una fase, se suman la cantidad de partidos de todas las rondas, iterando
    	// por la arraylist de fases. Para el ejemplo, el conjunto de rondas forman una ùnica fase
    	int cantPartidosFases = 0;
    	for (int i =0 ; i < fase.size(); i++) {
    		cantPartidosFases += fase.get(i).getCantPartidos();
    	}
    	
    	//SE IMPRIMEN LA CANTIDAD DE PARTIDOS POR RONDA
    	System.out.println("Cuantas partidos hay por ronda?");
    	for(Ronda ronda : fase) {
    		System.out.println("La ronda " + ronda.getNumero() + " tiene " + ronda.getCantPartidos() + " partidos");
    	}
    	
    	System.out.println("En la fase hay " + cantPartidosFases + " partidos en total");
    	System.out.println("*****************************************************************");
    	System.out.println(" "); 
    	
    	/* 3) PRONOSTICOS
    	 * 		a) Los pronosticos están cargados en la BD
    	 */
    	
    	String tablaSqlPronosticos = "pronosticos";
    	
    	//Instanciar ProcesadorPronosticos para poder usar sus metodos auxiliares
    	ProcesadorPronosticos procesadorPronosticos = new ProcesadorPronosticos(conexion, tablaSqlPronosticos);
    	//Asignar atributos con setters
    	procesadorPronosticos.setResultadoConsulta();
    	procesadorPronosticos.setMapaEquipos(mapaEquipos);
    	procesadorPronosticos.setRondas(fase);
    	procesadorPronosticos.setTodosLosPartidos();

    	//Los pronosticos se instancian y se guardan en una ArrayList
    	ArrayList<Pronostico> pronosticos = procesadorPronosticos.instanciarPronosticos();
    	
    	//Calculo de puntajes apuestas
    	int indice = 0; //se usa para el loop
    	
    	String tablaPuntajes ="puntajes";
    	 	
    	String consultaAgregarPuntajes = "INSERT INTO " + tablaPuntajes + "(numero_ronda,alias_partido,participante,"
    			+ "puntos_obtenidos) VALUES(?,?,?,?)";
    	
    	PreparedStatement ps = null;
    	
    	try {
			ps = conexion.prepareStatement(consultaAgregarPuntajes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	while(indice < pronosticos.size()) {
    		Pronostico pr = pronosticos.get(indice);
    		int numRonda = pr.getNumRonda();
    		String participante = pr.getParticipante();
    		Partido partidoAsociado = pr.getPartido() ;
    		String aliasPartidoAsociado = partidoAsociado.getAliasPartido(); //esto se pasa a la tabla
    		Equipo equipoElegido = pr.getEquipo(); // por quien se apostó
    		//acá se calcula los puntos que otorga el pronostico
    		int puntosObtenidos = pr.puntos(partidoAsociado.resultado(equipoElegido));//que puntaje da ese pronostico

    		try {
				ps.setInt(1,numRonda);
				ps.setString(2,aliasPartidoAsociado);
				ps.setString(3,participante);
				ps.setInt(4,puntosObtenidos);				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		indice+=1;
    	}
    
    	//Es hora de sumar cuantos puntos en total hizo cada participante. Se crean consultas a la BD recien creada
    	    		
    	//Devuelve solo una ocurrencia de nombre de participante
    	String consultaParticipantes = "SELECT DISTINCT participante FROM " + tablaPuntajes;
    	//Devuelve solo los numeros de ronda
    	String consultaNumRondas = "SELECT DISTINCT numero_ronda FROM " + tablaPuntajes;
    	
    	//Esta consulta seria para sumar puntajes por rondas(los ResultSet anteriores se usan como parametros)
    	String consultaPuntajeRondas = "SELECT participante, numero_ronda, SUM(puntos_obtenidos) FROM " 
    									+ tablaPuntajes +
    									" WHERE numero_ronda = ? AND participante = ? GROUP BY participante";
    	
    	Statement st = null;
    	Statement st2 = null;
    	PreparedStatement ps2 = null;
    	
    	try {
    		//Primero se consultan los nombres de los participantes y se itera por su ResultSet
    		st = conexion.createStatement();
			ResultSet rs = st.executeQuery(consultaParticipantes);
			Integer puntosObtenidosEnRonda = 0;
			Integer ronda = 1;
		
			int partidosRondaAcertados = 0;
			int partidosFaseAcertados = 0;
			int puntajeAcumulado = 0;
			
			while(rs.next()) {
				String persona = rs.getString("participante");
				//Ahora realizar la consulta para obtener los numeros de ronda
				st2 = conexion.createStatement();
				ResultSet rs2 = st2.executeQuery(consultaNumRondas);
				while(rs2.next()) {
					ronda = rs2.getInt("numero_ronda");
					//Ahora hay que usar lo anterior como parametro y sumar los puntos por participante por ronda
					ps2 = conexion.prepareStatement(consultaPuntajeRondas);
					ps2.setInt(1, ronda);
					ps2.setString(2, persona);
					ResultSet rs3 = ps2.executeQuery();
					while(rs3.next()) {
						puntosObtenidosEnRonda = rs3.getInt("SUM(puntos_obtenidos)");
						//puntajeAcumulado+= puntosObtenidosEnRonda;
						partidosRondaAcertados += puntosObtenidosEnRonda;
						partidosFaseAcertados += partidosRondaAcertados;
						System.out.println("- participante: " + persona +  " ronda: " + ronda + " puntos: " 
											+ puntosObtenidosEnRonda);
						//Sumar puntos extras si acertó todos los partidos de la ronda
						int cantPartidosEstaRonda = fase.get(ronda-1).getCantPartidos();
						if(partidosRondaAcertados == cantPartidosEstaRonda) {
							//Sumar una cantidad de puntos
							System.out.println("	*** " + persona + " ACERTÓ LOS " + cantPartidosEstaRonda +
												"  PARTIDOS DE ESTA RONDA POR LO TANTO GANA " 
												+ ptsExtrasRondaCompleta + " PUNTOS EXTRA ! \n" );
							
							//puntajeAcumulado += ptsExtrasRondaCompleta;
							puntosObtenidosEnRonda += ptsExtrasRondaCompleta;
							System.out.println(" 		EL TOTAL DE PUNTOS EN ESTA RONDA QUEDA EN " + 
												puntosObtenidosEnRonda+ "\n");
									
						}else{
							System.out.println("	*** " +persona + " NO ACERTÓ TODOS LOS PARTIDOS DE LA RONDA. "
									+ "LA RONDA TENÍA " + cantPartidosEstaRonda + " PERO SE ACERTARON " 
									+ partidosRondaAcertados + " POR LO TANTO, NO SUMA PUNTOS EXTRA \n" );
						}
						//Al terminar una ronda se reinician los puntos de la misma
						
						puntajeAcumulado+= puntosObtenidosEnRonda;
						System.out.println("		" + persona + " ACUMULA HASTA AHORA " + puntajeAcumulado + 
											" PUNTOS \n");
						partidosRondaAcertados = 0;
					}
						
				}
				//A ver si acertò todos los partidos de una fase
				if (partidosFaseAcertados == cantPartidosFases) {
					System.out.println(persona + " LLEVA " + partidosFaseAcertados + " PARTIDOS ACERTADOS EN LA FASE "
										+ "Y LA FASE TIENE " + cantPartidosFases + " POR LO TANTO GANA " + 
										ptsExtrasFaseCompleta + " PUNTOS EXTRA !");
		
					puntajeAcumulado += ptsExtrasFaseCompleta;
					
				}else {
					System.out.println("LLEVA " + partidosFaseAcertados + " PARTIDOS ACERTADOS EN LA FASE Y LA FASE TIENE " 
							+ cantPartidosFases);
				}
				//Se imprime el total de puntos de cada persona
				System.out.println("		[*** EL TOTAL DE PUNTOS ALCANZADOS POR " + persona + " ES " 
				+ puntajeAcumulado + " ]\n");
				
				puntosObtenidosEnRonda =0; // al cambiar de persona, se reinician la cuenta de los puntos
				puntajeAcumulado = 0;
				partidosRondaAcertados = 0;
				partidosFaseAcertados = 0;
				System.out.println("=========================================================");
			
			}
			
    		
    	}catch (SQLException e) {
					e.printStackTrace();
		}
    	
    }//end of main
    
}//end of class
