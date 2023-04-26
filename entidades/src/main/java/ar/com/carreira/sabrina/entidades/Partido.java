package ar.com.carreira.sabrina.entidades;


public class Partido {
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;
	//El alias del partido es un String que se forma con el código de ambos equipos separados por un guión
	//por ejemplo "ARG-POL" sería el alias de un partido Argentina - Polonia
	private String aliasPartido = ""; 
	
	
	//Constructor
	public Partido(Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.golesEquipo1 = golesEquipo1;
		this.golesEquipo2 = golesEquipo2;
		this.aliasPartido = equipo1.getCodigo() + "-" + equipo2.getCodigo();
	}
	
	public String getAliasPartido() {
		return this.aliasPartido;
	}
	
	public Equipo getEquipo1() {
		return this.equipo1;
	}
	
	public Equipo getEquipo2() {
		return this.equipo2;
	}
	
	
	public ResultadoEnum resultado(Equipo equipo) {
		
		int golesEsteEquipo = 0;
		int golesOtroEquipo = 0;
		
		ResultadoEnum resultado = null; // lo que se devuelve 
		
		//Cuando en un pronóstico, la cruz está en "empata" se pasa null para que automaticamente devuelva empate
		if(equipo == null) {
			resultado = ResultadoEnum.EMPATE;
		}
		
		//OJALA ENCONTRARA UNA FORMA MEJOR...
		if(equipo == this.equipo1) {
			golesEsteEquipo = this.golesEquipo1;
			golesOtroEquipo = this.golesEquipo2;
		}else {
			golesEsteEquipo = this.golesEquipo2;
			golesOtroEquipo = this.golesEquipo1;
		}
		//Comparacion de cantidad de goles
		if(golesEsteEquipo > golesOtroEquipo) {
			resultado = ResultadoEnum.GANADOR;
		}else if(golesEsteEquipo < golesOtroEquipo) {
			resultado = ResultadoEnum.PERDEDOR;
		}else {
			resultado = ResultadoEnum.EMPATE;
		}
		return resultado;
		
	}//end of resultado()
	

	@Override
	public String toString() {
		return "Partido [aliasPartido=" + aliasPartido + "]";
	}
	
	
			
}
