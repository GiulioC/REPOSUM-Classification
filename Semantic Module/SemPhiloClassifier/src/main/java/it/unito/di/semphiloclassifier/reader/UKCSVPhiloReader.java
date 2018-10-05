package it.unito.di.semphiloclassifier.reader;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UKCSVPhiloReader extends UKPhiloReader implements Serializable {
	
	/**
	 * Richiama il costruttore della superclasse
	 * @param p il path in cui si trova il file da leggere
	 */
	public UKCSVPhiloReader(String p) {
		super(p, "csv");
	}
	
	/**
	 * Implementa la lettura dei dati nel caso particolare delle
	 * tesi inglesi a partire da un file CSV
	 */
	@Override
	public void read(){
		
		// inizializza (o re-inizializza) le strutture dati
		setData(new ArrayList<RecordData>());
		
		List<String> rows = readCSVAsList(getSource());
		int counter = 1;
		Iterator<String> itr = rows.iterator();
		// escludo la prima riga, che contiene le etichette
		itr.next();
	    while (itr.hasNext()) {
	    	System.out.println("Riga "+(counter++)+" di "+(rows.size()-1));
	    	RecordData record = new UKPhiloRecordData();
	    	String[] row = (itr.next()).split("\t");
	    	for(int icol = 0;icol<13;icol++) {
	    		record.insert(icol, normalizeCell(row[icol]));
	    	}
	    	insertRecord(record);
	    }
	      
		
	}
	
	/**
	 * Restituisce la lista di righe (come stringhe) presenti nel file csv di riferimento
	 * @param f l'istanza di File collegata alla sorgente CSV
	 * @return la lista di stringhe relativa
	 */
	private List<String> readCSVAsList(File f) {
		
		List<String> lines = Collections.emptyList();
		try
	    {
	      lines = Files.readAllLines(Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8);
	    }
	    catch (IOException e)
	    {
	 
	      // do something
	      e.printStackTrace();
	    }
	    return lines;
	}
	
	/**
	 * Rimuove eventuali spazi bianchi all'inizio o alla fine del valore della cella
	 * @param c la stringa rappresentante il valore della cella
	 * @return la stringa normalizzata
	 */
	private String normalizeCell(String c) {
		char[] chars = c.toCharArray();
		int start=0,end=chars.length-1;
		if(end >= 0) {
			if(chars[end] == '"') 
				end -=1;
			if(chars[start] == '"')
				start += 1;
			while(chars[start] == ' ') {
				start += 1;
			}
			while(chars[end] == ' ') {
				end -= 1;
			}
		}
		return new String(Arrays.copyOfRange(chars, start, end+1));
	}

}
