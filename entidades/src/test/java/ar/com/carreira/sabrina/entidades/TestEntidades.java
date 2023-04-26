package ar.com.carreira.sabrina.entidades;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestEntidades
{
	Equipo eq1 = new Equipo("ARG", "Argentina");
	Equipo eq2 = new Equipo("COL", "Colombia");
	Equipo eq3 = new Equipo("URU", "Uruguay");
	Equipo eq4 = new Equipo("JAP", "Japon");
	
    @Test
    public void testAliasPartido() {
    	Partido part1 = new Partido(eq3, eq4, 5, 1);
    	String aliasEsperado = "URU-JAP";
    	String resultado = part1.getAliasPartido();
    	assertEquals(aliasEsperado,resultado);
    	
    }
    
    @Test
    public void testResultadoPartido() {
    	Partido part2 = new Partido(eq1, eq2, 4, 3);
    	ResultadoEnum resultadoEsperado = ResultadoEnum.GANADOR;
    	ResultadoEnum resultado = part2.resultado(eq1);
    	assertEquals(resultadoEsperado,resultado);
    }
}
