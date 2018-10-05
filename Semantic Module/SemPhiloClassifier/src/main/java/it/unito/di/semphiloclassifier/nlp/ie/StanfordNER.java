package it.unito.di.semphiloclassifier.nlp.ie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloEntity;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem.PhiloTag;

/**
 * La classe permette di analizzare un testo
 * evidenziando le Named Entities presenti al suo interno,
 * distinte a seconda che siano PERSONE, ORGANIZZAZIONI o LUOGHI.
 * 
 * Utilizza il modulo di Named Entity Recognition
 * messo a disposizione da StanfordCore NLP.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class StanfordNER extends NER {
	
	/**
	 * il classificatore per riconoscere le named entities
	 */
	private AbstractSequenceClassifier classifier;

	/**
	 * Costruttore. prende in input il testo da analizzare.
	 * Istanzia, inoltre, il classificatore.
	 * @param t
	 */
	public StanfordNER(String t) {
		super(t);
		this.classifier = CRFClassifier.getDefaultClassifier();
		
	}
	
	/**
	 * Costruttore. Istanzia il classificatore.
	 * @param t
	 */
	public StanfordNER() {
		super();
		this.classifier = CRFClassifier.getDefaultClassifier();
	}
	
	/**
	 * Determina le named entities
	 */
	public void findNes() {
		if(getText() == null)
			return;
		Set<PhiloItem> found = new HashSet<PhiloItem>();
		List<Triple> res = classifier.classifyToCharacterOffsets(getText());
		for(Triple el : res) {
			String type = (String) el.first;
			// charStart
			int cs = (Integer) el.second;
			// charEnd
			int ce = (Integer) el.third;
			// determino il (o i) token che definiscono la named entity
			String value = getText().substring(cs, ce);
			if(type.equals("PERSON")) 
				found.add(new PhiloEntity(value.toLowerCase(), cs,ce ));
		}
		setNes(found);
	}

}
