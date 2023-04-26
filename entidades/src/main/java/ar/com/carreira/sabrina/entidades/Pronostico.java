package ar.com.carreira.sabrina.entidades;

public class Pronostico {
	private String numRonda =""; // constructor
	private String participante = ""; // constructor
	private Partido partido;//constructor
	private Equipo equipo;//con un setter, en base a lo que se lee de tabla sql
	private ResultadoEnum resultado; // se calcula con un metodo auxiliar, pasando el equipo por el que se apostó
	private String aliasPartido = ""; //Ejemplo "ARG-POL", ayuda a buscar partidos para cotejar
	
	
	//Constructor
	public Pronostico(String numRonda, String participante, Partido partido) {
		this.numRonda = numRonda;
		this.participante = participante;
		this.partido = partido;	
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	public Partido getPartido() {
		return this.partido;
	}
	
	public String getParticipante() {
		return this.participante;
	}
	
	public int getNumRonda() {
		//El numero de ronda se castea a int
		return Integer.parseInt(this.numRonda);
	}
	
	public void setAliasPartido(String alias) {
		this.aliasPartido = alias;
	}
	
	public void setEquipo(Equipo equipo) {
		this.equipo =equipo;
	}
	
	public void setResultado(){
		//Cuando el equipo es nulo, es que no se apostó ni por eq1 ni por eq2, es empate !
		//Partido ya està preparada para eso
		if(this.equipo == null) {
			this.resultado = ResultadoEnum.EMPATE;
		}else {
			this.resultado = ResultadoEnum.GANADOR; // nadie apuesta a que un equipo pierde...
		}
	}
	
	public void setPartido(Partido partido) {
		//El partido se recupera de una ArrayList de Partido construida a partir de la union de todos
		//los partidos de todas las rondas. Se identifican por aliasPartido
		this.partido = partido;
	}
	
	public int puntos(ResultadoEnum resultadoPartido) {
		//ESTO PODRÍA NECESITAR UN REFACTOR
		int puntos = 0;
		if (this.resultado == resultadoPartido) { // o bien pasar un boolean match, si match punto
			puntos =1;
		}	
		return puntos;
	}

	@Override
	public String toString() {
		return "Pronostico [numRonda=" + numRonda + ", participante=" + participante + ", partido=" + partido
				+ ", equipo=" + equipo + ", resultado=" + resultado + ", aliasPartido=" + aliasPartido + "]";
	}
	
}//end of class
