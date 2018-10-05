package it.unito.di.semphiloclassifier.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe che gestisce i valori delle proprietà relative al progetto REPOSUM
 * (file config/reposum.var.properties nella root del progetto)
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class PropertiesReader {
	
	/**
	 * l'istanza riferita al file contenente i valori delle proprietà
	 */
	private static Properties props;
	
	
	/**
	 * Restituisce il riferimento al nome del file contenente l'archivio in formato csv
	 * @return la stringa relativa al path del file
	 */
	public static String getUKPhiloSourceCSV() {
		if(props == null) {
			loadProps();
		}
		return props.getProperty("reposum.uk_philo_source_csv");
	}
	
	
	/**
	 * Restituisce il riferimento alla cartella in cui salvare e caricare le strutture
	 * dati generate dal programma
	 * @return la stringa relativa alla cartella di destinazione
	 */
	public static String getOutputDir() {
		if(props == null) {
			loadProps();
		}
		return props.getProperty("reposum.data_structures");
	}
	
	
	/**
	 * Metodo che gestisce l'istanziazione della variabile di tipo Properties
	 */
	private static void loadProps() {
		props = new Properties();
		try {
		    props.load(new FileInputStream("config/reposum.var.properties"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
}
