package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private Graph<Food,DefaultWeightedEdge> grafo;
	private Map<Integer,Food> idMap;
	private FoodDao dao;
	private Map<Food,List<FoodNumber>> ciboAdiacenti;
	private double TTOTALE;
	private List<Food> cibiPreparati;
	private PriorityQueue<FoodNumber> queue;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public String creaGrafo(int porzioni) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		ciboAdiacenti = new HashMap<>();
		
		dao.listFoodPorzioni(porzioni, idMap);
		Graphs.addAllVertices(grafo, idMap.values());

		for(Adiacenti a: dao.getArchi(porzioni, idMap)) {
			Graphs.addEdge(grafo, a.getF1(), a.getF2(), a.getPeso());
			
			List<FoodNumber> nuovaLista = new ArrayList<>();
			nuovaLista.add(new FoodNumber(a.getF2(),a.getPeso()));
			if(ciboAdiacenti.containsKey(a.getF1())) {
				nuovaLista.addAll(ciboAdiacenti.get(a.getF1()));
			}
			ciboAdiacenti.put(a.getF1(), nuovaLista);
			
			nuovaLista = new ArrayList<>();
			nuovaLista.add(new FoodNumber(a.getF1(),a.getPeso()));
			if(ciboAdiacenti.containsKey(a.getF2())) {
				nuovaLista.addAll(ciboAdiacenti.get(a.getF1()));
			}
			ciboAdiacenti.put(a.getF2(), nuovaLista);
		}
		
		return "GRAFO CREATO\n#VERTICI: " + grafo.vertexSet().size() + "\n#ARCHI: " + grafo.edgeSet().size();
	}
	
	public List<FoodNumber> getAdiacentiMinimi(Food f){
		List<FoodNumber> result = new ArrayList<>();
		
		List<FoodNumber> totaleLista = this.ciboAdiacenti.get(f);
		Collections.sort(totaleLista);
		if(totaleLista.size()<5) {
			result = totaleLista;
		}else {
			result = totaleLista.subList(0, 5);
		}
		return result;
	}
	
	public List<Food> getVertici(){
		List<Food> result = new ArrayList<>(grafo.vertexSet());
		Collections.sort(result);;
		return result;
	}
	
	public void init(int k, Food partenza) {
		TTOTALE = 0;
		cibiPreparati = new ArrayList<>();
		
		queue = new PriorityQueue<>();
		List<FoodNumber> listaAdiacenti = this.ciboAdiacenti.get(partenza);
		double tempo = 0;
		for(int i = 0 ; i<k && i<listaAdiacenti.size(); i++) {
			FoodNumber fn = listaAdiacenti.get(i);
			queue.add(new FoodNumber(fn.getF(), tempo + fn.getN()));
			this.cibiPreparati.add(fn.getF());			
		}
	}
	
	public void run() {
		FoodNumber fn;
		while((fn = queue.poll()) != null) {
			this.TTOTALE = fn.getN();
			Food f = fn.getF();
			for(FoodNumber fnum : this.ciboAdiacenti.get(f)) {
				Food nuovoFood = fnum.getF();
				if(!cibiPreparati.contains(nuovoFood)) {
					queue.add(new FoodNumber(nuovoFood, TTOTALE + fnum.getN()));
					this.cibiPreparati.add(nuovoFood);
					break;
				}
			}
		}
	}
	
	public double getTempo() {
		return this.TTOTALE;
	}
	
	public int getNumPreparati(){
		return this.cibiPreparati.size();
	}
}
