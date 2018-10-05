package it.unito.di.semphiloclassifier.run;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.unito.di.semphiloclassifier.exceptions.DatasetNotFoundException;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloEntity;
import it.unito.di.semphiloclassifier.nlp.entities.PhiloEntity.PhiloTag;
import it.unito.di.semphiloclassifier.nlp.entities.SemanticThesisData;
import it.unito.di.semphiloclassifier.nlp.semantic.BabelNetSemanticAnalysis;
import it.unito.di.semphiloclassifier.reader.CorpusReader;
import it.unito.di.semphiloclassifier.reader.PropertiesReader;
import it.unito.di.semphiloclassifier.reader.RecordData;
import it.unito.di.semphiloclassifier.reader.UKCSVPhiloReader;
import it.unito.di.semphiloclassifier.reader.UKPhiloReader;

public class ThesisClassificationByPhiloEntities {
	
	public static void main(String[] args) {
		evaluateResults();
	}
	
	private static void numPhiloInThesis() {
		
		CorpusReader reader = new UKPhiloReader();
		try {
			reader.load("sourceUKPhilo");
		} catch(DatasetNotFoundException ecc) {
			reader = new UKCSVPhiloReader(PropertiesReader.getUKPhiloSourceCSV());
			reader.read();
			reader.save("sourceUKPhilo");
		}
		
		BabelNetSemanticAnalysis aa = new BabelNetSemanticAnalysis();
		
		List<RecordData> data = reader.getData();
		List<RecordData> newData = new ArrayList<RecordData>();
		Iterator it = data.iterator();
		int count = 1;
		
		double philoInPhilo = 0;
		double philoInNonPhilo = 0;
		
		int minPhilo=Integer.MAX_VALUE,maxPhilo=0,minNonPhilo=Integer.MAX_VALUE,maxNonPhilo=0;
		
		int philoTotal=0;
		int nonPhiloTotal=0;
		int unknown = 0;
		
		System.out.println("Searching for PHILO_ENTITIES ... ");
		
		while(it.hasNext()) {
			
			RecordData d = (RecordData) it.next();
			
			System.out.println("Tesi "+(count++)+" di "+data.size());
			SemanticThesisData ab =  d.getSemData();
			String oldAb = d.getAbstractTesi();
			aa.setTextData(d.getTitolo());
			aa.setSemData(ab);
			aa.extractMWEs();
			aa.extractNEs();
			aa.findPhilosophyEntities();
			
			if(d.getPhilosophy().equals("1")) {
				philoInPhilo += aa.getNes().size();
				philoTotal++;
				if(aa.getNes().size()<minPhilo)
					minPhilo=aa.getNes().size();
				if(aa.getNes().size()>maxPhilo)
					maxPhilo=aa.getNes().size();
			}
			else if(d.getPhilosophy().equals("0")) {
				philoInNonPhilo += aa.getNes().size();
				nonPhiloTotal++;
				if(aa.getNes().size()<minNonPhilo)
					minNonPhilo=aa.getNes().size();
				if(aa.getNes().size()>maxNonPhilo)
					maxNonPhilo=aa.getNes().size();
			}
			else {
				unknown++;
			}
			
		}
		
		System.out.println("media elementi in philo: "+philoInPhilo/philoTotal);
		System.out.println("media elementi in nonPhilo: "+philoInNonPhilo/nonPhiloTotal);
		System.out.println("numero elementi sconosciuti: "+unknown);
		System.out.print("minPhilo: "+minPhilo);
		System.out.print("maxPhilo: "+maxPhilo);
		System.out.print("minNonPhilo: "+minNonPhilo);
		System.out.print("maxNonPhilo: "+maxNonPhilo);
		
		reader.save("UKPhiloWithEntities");
		reader.toXML("UKPhiloWithEntities");
		
		
		
	}
	
	private static void evaluateResults() {
		
		
		CorpusReader reader = new UKPhiloReader();
		try {
			reader.load("UKPhiloWithEntities");
		} catch(DatasetNotFoundException ecc) {
			numPhiloInThesis();
			try {
				reader.load("UKPhiloWithEntities");
			} catch(Exception ecc2) {
				ecc2.printStackTrace();
			}
		}
		
		double numPhiloOk=0,numTotRec=0,numTotPhilo=0,numTotNoPhilo=0;
		double numNonPhiloOk=0,numElemTotal=0;
		
		int num1_1=0;
		int num0_1=0;
		
		double pe_avg_1_1=0;
		double pe_avg_0_1=0;
		
		
		int[] posRelPhilo = new int[10];
		int[] posRelNonPhilo = new int[10];
		
		for(RecordData d : reader.getData()) {
			if(d.getPhilosophy().equals("1")) {
				numTotPhilo++;
				numElemTotal++;
			}
			else if(d.getPhilosophy().equals("0")) {
				numElemTotal++;
				numTotNoPhilo++;
			}
			int numElems = 0;
			SemanticThesisData sd = d.getSemData();
			numElems = sd.getPhiloEntities().size();
			if(d.getClassification().equals("0")) {
				if(numElems>0) {
					if(d.getPhilosophy().equals("1")) {
						numPhiloOk++;
						num0_1++;
						pe_avg_0_1+=numElems;
						for(PhiloEntity item : sd.getPhiloEntities()) {
							if(item.getTag().equals(PhiloTag.PHILO_CONCEPT) && item.getValue().split(" ").length>=2)
								System.out.println(d.getIdDocumento());
						}
					}
					numTotRec++;
				}
				else {
					if(d.getPhilosophy().equals("0"))
						numNonPhiloOk++;
				}
			}
			else { // se random forest restituisce 1
				if(d.getPhilosophy().equals("1")) {
					if(numElems>0) {
						num1_1++;
						pe_avg_1_1+=numElems;
					}
					numPhiloOk++;
				}
				numTotRec++;
			}
		}
		
		
		System.out.println(numPhiloOk+" su "+numTotPhilo);
		System.out.println(numNonPhiloOk+" su "+numTotNoPhilo);
		System.out.println("prec: "+numPhiloOk/numTotRec);
		System.out.println("rec: "+numPhiloOk/numTotPhilo);
		System.out.println("acc: "+(numPhiloOk+numNonPhiloOk)/numElemTotal);
		double denom = (1/(numPhiloOk/numTotRec) + 1/(numPhiloOk/numTotPhilo));
		System.out.println("f1: "+2.0/denom);
		
		System.out.println("1_1: "+num1_1);
		System.out.println("0_1: "+num0_1);
		
		System.out.println("avg1_1: "+pe_avg_1_1/num1_1);
		System.out.println("avg0_1: "+pe_avg_0_1/num0_1);
		
		try {
			OutputStreamWriter writer =
			     new OutputStreamWriter(new FileOutputStream(PropertiesReader.getOutputDir()+"/semantic_classification.csv"), StandardCharsets.UTF_8);
			writer.write("title\tcreator\tuniversity\tpublisher\tyear\tabstract\ttype\tsubject\tid\tphilosophy\tclassification\tprob_0\tprob_1\tnew_classification\tonly_sem\n");
			for(RecordData d : reader.getData()) { 
				String new_c = "0";
				if(d.getClassification().equals("1") || (d.getClassification().equals("0") && d.getSemData().getPhiloEntities().size()>0))
					new_c = "1";
				String onlySem = (d.getSemData().getPhiloEntities().size()>0?"1":"0");
				writer.write(d.getTitolo().replaceAll("\"","")+
						"\t"+d.getAutore()+"\t"+d.getUniversita()+
						"\t"+d.getPaesePubblicazione()+"\t"+d.getAnnoPubblicazione()+
						"\t"+d.getAbstractTesi().replaceAll("\"","")+"\t"+d.getTipoFonte()
						+"\t"+d.getSoggetto()+"\t"+d.getIdDocumento()+"\t"+d.getPhilosophy()+
						"\t"+d.getClassification()+"\t"+d.getProb0()+"\t"+
						d.getProb1()+"\t"+
						new_c+"\t"+onlySem+"\n");
			}
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
