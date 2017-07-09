package it.polito.tdp.gestionale.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		model.generaGrafo();
		
		List<Integer> stat = model.getStatCorsi();
		
		int count = 0;
		for(Integer i : stat){
			System.out.println(count + " --> " +i);
			count++;
		}
		
		System.out.println(model.findMinimalSet());
		
	}

}
