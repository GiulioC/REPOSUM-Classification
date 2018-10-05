package it.unito.di.semphiloclassifier.nlp.lexical;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

/**
 * La classe si occupa di eseguire il processo di tokenizzazione
 * riguardo ad un testo in lingua inglese
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class StanfordTokenizer extends Tokenizer {
	
	/**
	 * Costruttore. Prende in input il testo da tokenizzare
	 * ed effettua la tokenizzazione tramite StanfordCore NLP Tokenizer.
	 * @param t il testo da tokenizzare
	 */
	public StanfordTokenizer(String t) {
		super(t);
		List<String> temp = new ArrayList<String>();
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(getText()));
		for (List<HasWord> sentence : dp) {
			for(HasWord w : sentence) {
				temp.add(w.word());
			}
        }
		setTokens(temp);
	}

}
