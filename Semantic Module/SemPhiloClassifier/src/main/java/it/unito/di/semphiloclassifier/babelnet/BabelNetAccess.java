package it.unito.di.semphiloclassifier.babelnet;

import java.io.IOException;
import java.util.List;


import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.data.BabelPOS;

public interface BabelNetAccess {
	
	List<BabelSynset> getSynsets(String word, BabelPOS pos);	
	List<String> getDBPediaURIs(BabelSynset b);
	BabelSynset getSynsetByID(String id);
}
