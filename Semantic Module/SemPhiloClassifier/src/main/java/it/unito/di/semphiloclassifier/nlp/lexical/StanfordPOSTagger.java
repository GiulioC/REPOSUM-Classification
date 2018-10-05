package it.unito.di.semphiloclassifier.nlp.lexical;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloConcept;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;

/**
 * La classe implementa il POS Tagger utilizzando
 * la suite StanfordCore NLP
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class StanfordPOSTagger extends POSTagger {
	
	// variabili per l'utilizzo del tagger
	private StanfordCoreNLP pipeline;
	private Properties props;
	
	// elenco delle frasi nel testo, annotate dal tagger
	private List<CoreMap> sentences;
	
	/**
	 * Costruttore.
	 * @param t il testo da analizzare
	 */
	public StanfordPOSTagger(String t) {
		super(t);
		props = new Properties();
		props.setProperty("annotators","tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public StanfordPOSTagger() {
		super();
		props = new Properties();
		props.setProperty("annotators","tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);
	}
	
	/**
	 * Esegue la fase di tagging sul testo salvato nell'istanza.
	 */
	@Override
	public void tag() {
		Annotation ann = new Annotation(getText());
		pipeline.annotate(ann);
		sentences = ann.get(CoreAnnotations.SentencesAnnotation.class);
	}
	
	@Override
	public void printResult() {
		for (CoreMap sentence : sentences) {
			List<CoreLabel> sentLabels = sentence.get(CoreAnnotations.TokensAnnotation.class);
		    for (int i=0;i<sentLabels.size();i++) {
		    	CoreLabel token = sentLabels.get(i);
	    		// token non ancora analizzato
	    		String word = token.get(CoreAnnotations.TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		        // mantieni solo nomi
	        	System.out.println(word + "/" + pos);
		    }
		}
	}
	
	@Override
	public Set<PhiloItem> filterPossiblyPhilosophicalConcepts() {
		Set<PhiloItem> concepts = new HashSet<PhiloItem>();
		String previousPos = "";
		String previousToken = "";
		String previousPreviousToken = "";
		String previousPreviousPos = "";
		for (CoreMap sentence : sentences) {
			List<CoreLabel> sentLabels = sentence.get(CoreAnnotations.TokensAnnotation.class);
		    for (int i=0;i<sentLabels.size();i++) {
		    	CoreLabel token = sentLabels.get(i);
	    		// token non ancora analizzato
	    		String word = token.get(CoreAnnotations.TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		        String lemma = token.getString(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
		        if(previousPreviousPos.equals("NN") && previousPos.equals("IN") && pos.equals("NN"))
		        	concepts.add(new PhiloConcept(previousPreviousToken+" "+previousToken+" "+lemma));
		        if(previousPos.equals("NN") && pos.equals("NN") || previousPos.equals("JJ") && pos.equals("NN"))
		        	concepts.add(new PhiloConcept(previousToken+" "+lemma));
		        if(pos.equals("NN"))
		        	concepts.add(new PhiloConcept(lemma));
		        previousPreviousPos = previousPos;
		        previousPreviousToken = previousToken;
		        previousToken = lemma;
		        previousPos = pos;
		    }
		}
		return concepts;
	}
	
	@Override
	public Set<String> getAllNouns() {
		Set<String> nouns = new HashSet();
		for (CoreMap sentence : sentences) {
			List<CoreLabel> sentLabels = sentence.get(CoreAnnotations.TokensAnnotation.class);
		    for (int i=0;i<sentLabels.size();i++) {
		    	CoreLabel token = sentLabels.get(i);
	    		// token non ancora analizzato
	    		String word = token.get(CoreAnnotations.TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		        // mantieni solo nomi
		        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
		        if(pos.equals("NN"))
		        	nouns.add(lemma);
		    }
		}
		return nouns;
	}
	
}
