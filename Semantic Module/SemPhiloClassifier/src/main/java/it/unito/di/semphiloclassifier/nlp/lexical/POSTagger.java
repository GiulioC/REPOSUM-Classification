package it.unito.di.semphiloclassifier.nlp.lexical;

import java.util.List;
import java.util.Set;

import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;

/**
 * La classe implementa la struttura di base di un
 * Part-Of-Speech Tagger
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class POSTagger {
	
	// il testo da analizzare
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public POSTagger(String t) {
		text = t;
	}
	
	public POSTagger() {}
	
	public void tag() {}
	
	public List<String> getInterestingPhilosophicalWordSequences() {
		return null;
	}
	
	public void printResult() {}

	public Set<PhiloItem> filterPossiblyPhilosophicalConcepts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getAllNouns() {
		// TODO Auto-generated method stub
		return null;
	}

}
