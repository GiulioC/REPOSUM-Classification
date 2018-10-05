package it.unito.di.semphiloclassifier.babelnet;

import java.io.IOException;
import java.util.List;


import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem.PhiloTag;

public class RESTBabelNetAccess implements BabelNetAccess {

	public List<BabelSynset> getSynsets(String word, BabelPOS pos){
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getDBPediaURIs(BabelSynset b) {
		// TODO Auto-generated method stub
		return null;
	}

	public BabelSynset filterBabelSynsetsByNETag(List<BabelSynset> l, PhiloTag t) {
		// TODO Auto-generated method stub
		return null;
	}

	public BabelSynset getSynsetByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
