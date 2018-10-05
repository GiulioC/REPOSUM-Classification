package it.unito.di.semphiloclassifier.run;

import it.unito.di.semphiloclassifier.reader.DataReader;
import it.unito.di.semphiloclassifier.reader.PropertiesReader;
import it.unito.di.semphiloclassifier.reader.UKCSVPhiloReader;

public class ReadFromCSVUKPhilo {
	
	/**
	 * Metodo che implementa la lettura dei dati
	 * @param args eventuali parametri in input
	 */
	public static void main(String[] args) {
		DataReader rd = new UKCSVPhiloReader(PropertiesReader.getUKPhiloSourceCSV());
		rd.read();
		rd.toXML("sourceUKPhilo");
		rd.save("sourceUKPhilo");
	}

}
