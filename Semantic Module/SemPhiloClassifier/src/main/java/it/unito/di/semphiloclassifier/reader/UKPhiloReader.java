package it.unito.di.semphiloclassifier.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import it.unito.di.semphiloclassifier.nlp.entities.PhiloItem;
import it.unito.di.semphiloclassifier.nlp.entities.SemanticThesisData;

public class UKPhiloReader extends CorpusReader {
	/**
	 * Richiama il costruttore della superclasse
	 */
	public UKPhiloReader(String v, String ext) {
		super(v,ext);
	}
	
	public UKPhiloReader() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Metodo che si occupa del salvataggio dei dati letti in formato XML
	 * 
	 * <archivio>
	 * 	<tesi>
	 * 		<id>...</id>
	 * 		<titolo>...</titolo>
	 * 
	 * 		...
	 * 
	 * 	</tesi>
	 * </archivio>
	 */
	@Override
	public void toXML(String name) {
		Document doc=new Document();
		Element root=new Element("archivio");
		for(RecordData d : getData()) {
			
			Element th = new Element("tesi");
			
			String value = d.getIdDocumento();
			Element el = new Element("id");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getTitolo();
			el = new Element("titolo");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getAutore();
			el = new Element("autore");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getUniversita();
			el = new Element("università");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getOrganizzazione();
			el = new Element("editore");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getAnnoPubblicazione();
			el = new Element("annoPubblicazione");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getAbstractTesi();
			el = new Element("abstract");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getTitoloAccademico();
			el = new Element("titoloAccademico");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getUrlDocumento();
			el = new Element("urlDocumento");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getFonte();
			el = new Element("sorgente");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getSoggetto();
			el = new Element("soggetto");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getPhilosophy();
			el = new Element("philosophy");
			el.addContent(value);
			th.addContent(el);
			
			value = d.getClassification();
			if(value != null) {
				el = new Element("classification");
				el.addContent(value);
				th.addContent(el);
			}
			
			el = new Element("semanticData");
			SemanticThesisData sd;
			if((sd = d.getSemData()) != null) {
				if(sd.getPhiloEntities() != null) {
					Element el1 = new Element("philo_entities");
					for(PhiloItem p : sd.getPhiloEntities()) {
						Element el2 = new Element("entity");
						el2.addContent(p.toString());
						el1.addContent(el2);
					}
					el.addContent(el1);
				}
				if(sd.getThesisLemmas() != null) {
					Element el1 = new Element("lemmi");
					for(String id : sd.getThesisLemmas()) {
						Element el3 = new Element("lemma");
						el3.addContent(id);
						el1.addContent(el3);
					}
					el.addContent(el1);
				}
			}
			th.addContent(el);
			
			root.addContent(th);
		}
		
		doc.setRootElement(root);
		
		XMLOutputter outter=new XMLOutputter();
		outter.setFormat(Format.getPrettyFormat());
		try {
			outter.output(doc, new FileWriter(new File(PropertiesReader.getOutputDir()+"/"+name+".xml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
