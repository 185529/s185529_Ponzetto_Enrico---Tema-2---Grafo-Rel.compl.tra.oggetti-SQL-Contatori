package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private List<Corso> corsi;
	private List<Studente> studenti;
	private DidatticaDAO didatticaDAO;
	private UndirectedGraph<Nodo, DefaultEdge> graph;
	private Map<Integer, Studente> mappaStudenti;

	public Model() {
		this.graph = new SimpleGraph<Nodo, DefaultEdge>(DefaultEdge.class);
		this.didatticaDAO = new DidatticaDAO();
		this.mappaStudenti = new HashMap<Integer, Studente>();
	}
	
	public void generaGrafo(){
		
		studenti = getTuttiStudenti();
		corsi = getTuttiCorsi();
		
		System.out.println(studenti.size() + " " + corsi.size());
		
		// aggiunta nodi
		
		Graphs.addAllVertices(graph, studenti);
		Graphs.addAllVertices(graph, corsi);
		
		System.out.println(graph.vertexSet().size());
		
		// aggiunta archi
		
		for(Corso c : corsi){
			for(Studente s : c.getStudenti()){
				graph.addEdge(c, s);
			}
		}
		
		System.out.println(graph.edgeSet().size() + "\n\nOK");
		
	}

	public List<Corso> getTuttiCorsi() {

		if(corsi==null){
			corsi = didatticaDAO.getTuttiICorsi();
			getTuttiStudenti();
			for(Corso c : corsi){
				didatticaDAO.setStudentiIscrittiAlCorso(c, mappaStudenti);
			}
		}
				
		return corsi;
		
	}

	public List<Studente> getTuttiStudenti() {
		
		if(studenti==null){
			studenti = didatticaDAO.getTuttiStudenti();
			for(Studente s : studenti){
				mappaStudenti.put(s.getMatricola(), s);
			}
		}
				
		return studenti;
		
	}
	
	public List<Integer> getStatCorsi(){
		
		List<Integer> statCorsi = new ArrayList<Integer>();
		
		// inizializzo il vettore contatori
		
		for(int i=0; i<corsi.size()+1; i++){
			statCorsi.add(0);
		}
		
		// aggiorno le statistiche
		
		for(Studente s : studenti){
			int ncorsi = Graphs.neighborListOf(graph, s).size();
			int counter = statCorsi.get(ncorsi);
			counter++;
			statCorsi.set(ncorsi, counter);
		}
		
		return statCorsi;
		
	}
	
}
