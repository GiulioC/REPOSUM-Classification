package it.unito.di.semphiloclassifier.nlp.ie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.unito.di.semphiloclassifier.nlp.entities.PhiloConcept;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloEntity;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;
import it.unito.di.semphiloclassifier.nlp.lexical.StanfordTokenizer;
import it.unito.di.semphiloclassifier.nlp.lexical.Tokenizer;

/**
 * Modulo di Named Entity Recognition 'di base'
 * che riconosce una named entity qualora la parola
 * (o sequenza di parole) comincino con una
 * lettera maiuscola.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class BasicNER extends NER {
	
	public BasicNER(String t) {
		super(t);
	}
	
	public void findNes() {
		if(getText() == null)
			return;
		Set<PhiloItem> found = new HashSet<PhiloItem>();
		Tokenizer t = new StanfordTokenizer(getText());
		List<String> tokens = t.getTokens();
		Iterator<String> itTokens = tokens.iterator();
		String ne = null;
		String previousToken = "";
		while(itTokens.hasNext()) {
			String token = itTokens.next();
			// comincia con maiuscola e il token precedente non è vuoto e neppure equivalente a '.'
			if(token.length() > 1 && Character.isUpperCase(token.charAt(0)))
				found.add(new PhiloConcept(token.toLowerCase()));
			if(token.length() > 1 && Character.isUpperCase(token.charAt(0)) && !(previousToken.equals(".") || previousToken.equals(""))) {
				if(ne == null)
					ne = token.toLowerCase();
				else
					ne += " " + token.toLowerCase();
			}
			else {
				if(ne != null) {
					found.add(new PhiloEntity(ne,-1,-1));
					ne=null;
				}
				
			}
			previousToken = token;
		}
		setNes(found);
	}
	
	public BasicNER() {}

}
