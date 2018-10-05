package it.unito.di.semphiloclassifier.nlp.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.data.BabelCategory;
import it.uniroma1.lcl.babelnet.data.BabelDomain;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.jlt.util.Language;
import it.unito.di.semphiloclassifier.babelnet.BabelNetAccess;
import it.unito.di.semphiloclassifier.babelnet.LocalBabelNetAccess;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloConcept;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;
import it.unito.di.semphiloclassifier.nlp.entities.SemanticThesisData;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem.PhiloTag;
import it.unito.di.semphiloclassifier.nlp.ie.BasicNER;
import it.unito.di.semphiloclassifier.nlp.ie.NER;
import it.unito.di.semphiloclassifier.nlp.ie.StanfordNER;
import it.unito.di.semphiloclassifier.nlp.lexical.POSTagger;
import it.unito.di.semphiloclassifier.nlp.lexical.StanfordPOSTagger;

/**
 * La classe implementa metodologie di analisi semantica
 * per quanto riguarda gli abstract relativi alle tesi statunitensi,
 * utilizzando risorse quali BabelNet, BabelFy e StanfordCore NLP.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class BabelNetSemanticAnalysis {
	
	/**
	 * variabile per l'accesso a Babelnet
	 */
	private BabelNetAccess bn;
	
	/**
	 * riferimento al modulo di Named Entity Recognition di base
	 */
	private BasicNER ner2;
	
	/**
	 * riferimento al modulo di Named Entity Recognition di Stanford
	 */
	private StanfordNER ner1;
	
	/**
	 * Part-Of-Speech tagger
	 */
	private POSTagger posTagger;
	
	/**
	 * variabile contenente il testo da analizzare
	 */
	private String textData;
	
	/**
	 * variabile contenente informazioni semantiche
	 */
	private SemanticThesisData semData;
	
	/**
	 * la lista delle Named Entity trovate
	 */
	private Set<PhiloItem> nes;
	
	/**
	 * la lista delle mwe trovate candidate ad essere entità filosofiche
	 */
	private Set<PhiloItem> mwes;
	
	
	
	public Set<PhiloItem> getMwes() {
		return mwes;
	}

	public void setMwes(Set<PhiloItem> mwes) {
		this.mwes = mwes;
	}

	public Set<PhiloItem> getNes() {
		return nes;
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String ab) {
		this.textData = ab;
	}
	
	

	public SemanticThesisData getSemData() {
		return semData;
	}

	public void setSemData(SemanticThesisData semData) {
		this.semData = semData;
	}

	/**
	 * Costruttore. istanzia la variabile contenente l'abstract da analizzare
	 * nonchè l'istanza di accesso a BabelNet.
	 * @param t
	 */
	public BabelNetSemanticAnalysis(String text, SemanticThesisData t) {
		this.textData = text;
		this.semData = t;
		bn = new LocalBabelNetAccess(Language.EN);
		ner2 = new BasicNER();
		ner1 = new StanfordNER();
		posTagger = new StanfordPOSTagger();
	}
	
	/**
	 * Costruttore. Istanzia accesso a BabelNet.
	 * @param t
	 */
	public BabelNetSemanticAnalysis() {
		bn = new LocalBabelNetAccess(Language.EN);
		ner2 = new BasicNER();
		ner1 = new StanfordNER();
		posTagger = new StanfordPOSTagger();
	}
	
	/**
	 * Trova le Named Entities presenti nel testo
	 */
	public void extractNEs() {
		if(textData == null)
			return;
		ner1.setText(textData);
		ner2.setText(textData);
		nes = ner1.getNes();
		nes.addAll(ner2.getNes());
	}
	
	/**
	 * estrae multiword expressions come potenziali entità filosofiche
	 * 
	 * JJ NN  (es 'political philosophy')
	 * NN IN NN (es. 'philosophy of religion')
	 * NN NN (es. 'quantum theory')
	 */
	public void extractMWEs() {
		
		posTagger.setText(textData);
		posTagger.tag();
		this.mwes = posTagger.filterPossiblyPhilosophicalConcepts();
		
	}
	
	/**
	 * Restituisce l'insieme di Named Entities riconosciute come nomi di entità filosofiche.
	 * @return la lista di Named Entities filtrata.
	 */
	public SemanticThesisData findPhilosophyEntities() {
		
		if(nes == null)
			return null;
		
		Set<PhiloItem> philoEntities = new HashSet<PhiloItem>();
		
		// unisco nes e mwes
		Set<PhiloItem> temp = (Set<PhiloItem>)((HashSet)nes).clone();
		if(mwes!=null)
			temp.addAll(mwes);
				
		for(PhiloItem ne : temp) {
			
			if(ne.getTag() == PhiloTag.PHILO_CONCEPT || 
					ne.getTag() == PhiloTag.PHILO_ENTITY) {
				String value = ne.getValue();
				BabelSynset bs;
				List<BabelSynset> syns = bn.getSynsets(value,BabelPOS.NOUN);
				
				
				BabelSynset isPhilo = null;
				
				double position=0;
				
				for(BabelSynset b : syns) {
					List<BabelSynsetIDRelation> relations = b.getEdges();
					// parola filosofia e filosofo
					if(b.getId().getID().equals("bn:00061984n") || b.getId().getID().equals("bn:00061979n")) {
						isPhilo = b;
						position = -1;
					}
					if(isPhilo == null) {
						List<BabelSynsetIDRelation> isa = new ArrayList();
						List<BabelSynsetIDRelation> occupation = new ArrayList();
						for(BabelSynsetIDRelation rel : relations) {
							if(rel.getPointer().getShortName().equals("is-a"))
								isa.add(rel);
							if(rel.getPointer().getName().equals("occupation"))
								occupation.add(rel);
						}
						for(BabelSynsetIDRelation rel : occupation) {
							if(rel.getBabelSynsetIDTarget().getID().equals("bn:00061979n")){   // philosopher
								{
									isPhilo = b;
									position = -1;
									break;
								}
							}
						}
						int k=0;
						for(BabelSynsetIDRelation rel : isa) {
							if(rel.getBabelSynsetIDTarget().getID().equals("bn:00061979n") ||   // philosopher
										//rel.getBabelSynsetIDTarget().getID().equals("bn:00061981n") || // philosophical_theory
										//rel.getBabelSynsetIDTarget().getID().equals("bn:03260067n") ||  // philosophical_movement
										//rel.getBabelSynsetIDTarget().getID().equals("bn:00045834n") ||  // ideology
										//rel.getBabelSynsetIDTarget().getID().equals("bn:00028013n") ||  // doctrine
										// rel.getBabelSynsetIDTarget().getID().equals("bn:00049915n") ||  // terminology
										//rel.getBabelSynsetIDTarget().getID().equals("bn:00025995n") ||  // deity
										rel.getBabelSynsetIDTarget().getID().equals("bn:00061984n")){   // philosophy
								{
									position =(1.0*k)/isa.size();
									//position = rel.getWeight();
									isPhilo = b;
									break;
								}
							}
							k++;
						}
					}
					if(isPhilo != null) {
						ne.setSynset(b.getId().getID());
						ne.setPositionInRelation(position);
						philoEntities.add(ne);
						break;
					}
				}
			}
		}
		
		semData.setPhiloEntities(philoEntities);
		return semData;
		
		
	
	}
}
