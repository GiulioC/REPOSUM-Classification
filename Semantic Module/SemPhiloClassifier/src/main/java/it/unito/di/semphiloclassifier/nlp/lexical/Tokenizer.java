package it.unito.di.semphiloclassifier.nlp.lexical;

import java.util.List;

/**
 * La classe implementa la base per una classe
 * che implementi il processo di tokenizzazione
 * riguardo ad un testo, utilizzando una
 * determinata metodologia.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class Tokenizer {
	
	/**
	 * il testo da tokenizzare
	 */
	private String text;
	
	/**
	 * i tokens relativi al testo
	 */
	private List<String> tokens;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	
	public Tokenizer(String text) {
		this.text = text;
	}
	
}
 