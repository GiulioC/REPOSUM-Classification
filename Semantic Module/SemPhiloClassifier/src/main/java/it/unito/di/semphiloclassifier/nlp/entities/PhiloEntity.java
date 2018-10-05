package it.unito.di.semphiloclassifier.nlp.entities;

import java.io.Serializable;

import it.uniroma1.lcl.babelnet.BabelSynset;

/**
 * Classe di supporto per la gestione delle Named Entities
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class PhiloEntity implements Serializable,Comparable<PhiloEntity> {
	
	/**
	 * Classe enumerativa rappresentante le possibili tipologie di named entities
	 * 
	 * @author Marco Leontino <leontinomarco@gmail.com>
	 *
	 */
	public enum PhiloTag {
		PHILO_CONCEPT,
		PHILO_NAMED_ENTITY
	}
	
	/**
	 * il testo rappresentante la Named Entity
	 */
	private String value;
	
	/**
	 * carattere d'inizio e di fine della named entity (nel suo complesso, 
	 * non rispetto ai singoli token) all'interno del testo in cui è stata trovata
	 */
	private int charStart,charEnd;
	
	/**
	 * la tipologia della Named Entity:
	 * 
	 * 		- PERSON  (persone)
	 * 		- LOCATION  (luoghi)
	 * 		- ORGANIZATION  (organizzazioni)
	 */
	private PhiloTag tag;
	
	/**
	 * Il babel synset associato alla Named Entity
	 */
	private String synset;
	
	// la posizione, nella lista delle relazioni isa o occupation,
	// in cui ho trovato i concetti filosofici che hanno permesso la selezione
	private double positionInRelation;
	
	public double getPositionInRelation() {
		return positionInRelation;
	}

	public void setPositionInRelation(double positionInRelation) {
		this.positionInRelation = positionInRelation;
	}

	public String getValue() {
		return value;
	}

	public PhiloTag getTag() {
		return tag;
	}
	
	
	public void setSynset(String synset) {
		this.synset = synset;
	}

	public int getCharStart() {
		return charStart;
	}

	public int getCharEnd() {
		return charEnd;
	}

	public String getSynset() {
		return synset;
	}

	/**
	 * Costruttore per una nuova istanza di Named Entity
	 * @param v il testo che rappresenta la Named Entity
	 * @param t la tipologia di Named Entity
	 */
	public PhiloEntity(String v, PhiloTag t, int cs, int ce) {
		value=v;
		tag=t;
		charStart = cs;
		charEnd = ce;
	}
	
	public String toString() {
		if(synset != null)
			return value+" ("+tag+", "+synset+")";
		else
			return value+" ("+tag+")";
		
	}
	
	@Override
	public boolean equals(Object o) {
		PhiloEntity test = (PhiloEntity) o;
		if(synset != null && synset.equals(test.getSynset()))
			return true;
		if(value.equals(test.getValue()) && 
				tag.equals(test.getTag()))
			return true;
		return false;
		
	}
	
	
	public int compareTo(PhiloEntity o) {
		if(this.value.equals(o.getValue()) && this.tag.equals(o.getTag()))
			return 0;
		if(this.synset != null && this.synset.equals(o.getSynset()))
			return 0;
		return -1;
	}
	
	@Override
	public int hashCode() {
		if(synset != null) 
			return synset.hashCode();
		return value.hashCode()+tag.hashCode();
	}
	
	
	
}
