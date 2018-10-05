package it.unito.di.semphiloclassifier.reader;

import java.io.FileNotFoundException;
import java.util.List;

import it.unito.di.semphiloclassifier.exceptions.DatasetNotFoundException;

/**
 * Interfaccia di base per la costruzione di vari tipi di lettori di dati
 * relativi a tesi presenti in archivi diversi.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public interface DataReader {
	
	public void read();
	public void toXML(String name);
	public void save(String name);
	public void load(String name) throws DatasetNotFoundException;
	public int size();

}
