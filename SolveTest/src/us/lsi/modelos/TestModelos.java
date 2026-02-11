package us.lsi.modelos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import us.lsi.carmelo.AssignLocationPLIC;
//import asignacion.AssignLocationTechniquesLevel2PLI;
import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve_test.AuxGrammar;

public class TestModelos {
	
	public static void test_model() throws IOException {
//		AssignLocationPLIC.leeFichero("ficheros/datos3comp.txt");
		AuxGrammar.generate(TestModelos.class,"ficheros/prueba_5.lsi","ficheros/prueba_5.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/prueba_5.lp").orElse(null);
		Locale.setDefault(Locale.of("en", "US"));
		if (solution!=null)
			System.out.println(solution.toString((s,d)->d>0.));
	}

	public static void main(String[] args) throws IOException {
		test_model();

	}

}
