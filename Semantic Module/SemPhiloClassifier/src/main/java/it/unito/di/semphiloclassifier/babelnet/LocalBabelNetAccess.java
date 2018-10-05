package it.unito.di.semphiloclassifier.babelnet;

import java.io.IOException;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem.PhiloTag;


public class LocalBabelNetAccess implements BabelNetAccess {
	
	private BabelNet bn;
	private Language lang;

	public LocalBabelNetAccess(Language l) {
		bn = BabelNet.getInstance();
		lang =l;
	}
	
	
	/**
	 * Data una parola e un PoS, restituisce la lista dei babel synset ad essi relativi
	 */
	
	public List<BabelSynset> getSynsets(String word, BabelPOS pos)  {
		
		try {
			return bn.getSynsets(word,this.lang,BabelPOS.NOUN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Restituisce gli URI di DBPedia collegati al babel synset
	 * @param b il babel synsent di riferimento
	 * @return la lista degli URI collegati
	 */
	
	public List<String> getDBPediaURIs(BabelSynset b) {
		return b.getDBPediaURIs(lang);
	}
	
	public void getRelationsByType(BabelSynset b) {
		
		List<BabelSynsetIDRelation> relations = b.getEdges();
		for(BabelSynsetIDRelation rel : relations) {
			System.out.println(rel);
		}
		
	}

	public BabelSynset getSynsetByID(String id) {
		
		try {
			return bn.getSynset(new BabelSynsetID(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidBabelSynsetIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
