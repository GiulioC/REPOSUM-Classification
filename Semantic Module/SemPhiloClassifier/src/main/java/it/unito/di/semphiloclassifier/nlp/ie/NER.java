package it.unito.di.semphiloclassifier.nlp.ie;

import java.util.List;
import java.util.Set;

import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;


/**
 * La classe implementa l'infrastruttura di base per un modulo
 * di Named Entity Recognition.
 * 
 * Andrà poi estesa con una classe che vada ad utilizzare una
 * particolare tecnica di named entity recognition
 * (implementando in particolare il metodo findNes)
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class NER {
	
	/**
	 * il testo da analizzare
	 */
	private String text;
	
	/**
	 * Lista delle named entities trovate
	 */
	private Set<PhiloItem> nes;
	
	
	
	public String getText() {
		return text;
	}



	public Set<PhiloItem> getNes() {
		if(nes == null)
			findNes();
		return nes;
	}

	public void setNes(Set<PhiloItem> nes) {
		this.nes = nes;
	}



	public void setText(String text) {
		this.text = text;
		this.nes = null;
	}
	
	public NER(String t) {
		this.text = t;
	}
	
	public NER() {}
	
	public void findNes() {}

}
