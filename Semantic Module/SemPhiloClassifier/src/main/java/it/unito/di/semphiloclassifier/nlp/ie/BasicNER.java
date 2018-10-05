package it.unito.di.semphiloclassifier.nlp.ie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.unito.di.semphiloclassifier.nlp.entities.PhiloConcept;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloNamedEntity;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloEntity;
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
		Set<PhiloEntity> found = new HashSet<PhiloEntity>();
		Tokenizer t = new StanfordTokenizer(getText());
		List<String> tokens = t.getTokens();
		Iterator<String> itTokens = tokens.iterator();
		String ne = null;
		String previousToken = "";
		while(itTokens.hasNext()) {
			String token = itTokens.next();
			// comincia con maiuscola e il token precedente non è vuoto e neppure equivalente a '.'
			if(token.length() > 1 && Character.isUpperCase(token.charAt(0)))
				found.add(new PhiloNamedEntity(token.toLowerCase(),-1,-1));
			if(token.length() > 1 && Character.isUpperCase(token.charAt(0)) && !(previousToken.equals(".") || previousToken.equals(""))) {
				if(ne == null)
					ne = token.toLowerCase();
				else
					ne += " " + token.toLowerCase();
			}
			else {
				if(ne != null) {
					found.add(new PhiloNamedEntity(ne,-1,-1));
					ne=null;
				}
				
			}
			previousToken = token;
		}
		setNes(found);
	}
	
	public BasicNER() {}

}
