package it.unito.di.semphiloclassifier.nlp.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import it.uniroma1.lcl.babelnet.BabelSynsetID;

/**
 * La classe mantiene le informazioni relative
 * alla semantica associata ad una particolare tesi di dottorato.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class SemanticThesisData implements Serializable {
	
	// insieme di nomi di filosofi cui si fa riferimento nell'abstract
	private Set<PhiloEntity> philoEntities;
	
	// elenco di lemmi interessanti che caratterizzano la tesi
	private Set<String> thesisLemmas;

	public Set<String> getThesisLemmas() {
		return thesisLemmas;
	}

	public void setThesisLemmas(Set<String> thesisLemmas) {
		this.thesisLemmas = thesisLemmas;
	}

	public Set<PhiloEntity> getPhiloEntities() {
		return philoEntities;
	}

	public void setPhiloEntities(Set<PhiloEntity> philosophers) {
		this.philoEntities = philosophers;
	}
	
	public SemanticThesisData() {}

}
