package it.unito.di.semphiloclassifier.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import it.unito.di.semphiloclassifier.exceptions.DatasetNotFoundException;

public class CorpusReader implements DataReader {
	
	/**
	 * la sorgente da cui andare a leggere i dati
	 */
	private File source;
	
	/**
	 * Restituisce l'istanza relativa al file da cui leggere i dati
	 * @return il file sorgente
	 */
	public File getSource() {
		return source;
	}
	
	// lista comprendente i vari record presenti
	// nelle tesi statunitensi
	private List<RecordData> data;
	
	/**
	 * Restituisce il riferimento alla lista dei record presenti nell'archivio
	 * @return
	 */
	public List<RecordData> getData() {
		return data;
	}

	/**
	 * Sostiuisce la lista dei record con quella passata come parametro
	 * @param data la nuova lista
	 */
	public void setData(List<RecordData> data) {
		this.data = data;
	}
	
	/**
	 * Aggiunge un record alla lista
	 */
	public void insertRecord(RecordData d) {
		this.data.add(d);
	}
	
	/**
	 * Costruttore da usarsi quando si vuole accedere a strutture dati 
	 * già generate nelle fasi precedenti e salvate in file dat.
	 */
	public CorpusReader() {
		data = new ArrayList<RecordData>();
	}
	
	/**
	 * Costruttore generico per un lettore di dati
	 * @param p il path relativo all'archivio sorgente
	 * @param ext l'estensione relativa alla tipologia di archivio
	 */
	public CorpusReader(String p, String ext) {
		// verifico se l'estensione del file sia corretta
		String extension = "";
		int i = p.lastIndexOf('.');
		if (i >= 0) 
			extension = p.substring(i+1);
		if(extension.equals(ext)) {
			// verifico se il path corrisponde effettivamente ad un file
			try {
				File f = new File(p);
				FileInputStream fis = new FileInputStream(f);
				source = f;
				data = new ArrayList<RecordData>();
			}
			catch (FileNotFoundException ecc) {
				throw new IllegalArgumentException("Archivio non trovato");
			}
		}
		else
			throw new IllegalArgumentException("Tipo di file non valido.");
	}

	public void read() {
		// TODO Auto-generated method stub

	}

	public void toXML(String name) {
		// TODO Auto-generated method stub

	}

	/**
	 * Il metodo salva la struttura dati generata in modo
	 * che possa essere successivamente ricaricata in modo efficiente
	 */
	public void save(String name) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(PropertiesReader.getOutputDir()+"/"+name+".dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.data);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica i dati presenti nel file dat precedentemente generato,
	 * qualora il file in oggetto sia presente
	 */
	public void load(String name) throws DatasetNotFoundException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(PropertiesReader.getOutputDir()+"/"+name+".dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.data = (List<RecordData>) ois.readObject();
		} catch (FileNotFoundException ecc) {
			throw new DatasetNotFoundException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Restituisce il numero di record in archivio
	 */
	public int size() {
		return data.size();
	}

}
