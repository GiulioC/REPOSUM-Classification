package it.unito.di.semphiloclassifier.reader;

public class UKPhiloRecordData extends RecordData {

	/**
	 * Gestisce la costruzione di una istanza di tipo UKRecordData
	 * sulla base del formato delle tesi inglesi
	 * 
	 * @param icol la posizione in cui si trova la label corrente nel file sorgente, usata per discriminare sul nome della variabile
	 * @param s il valore associato alla label indicata da -icol-
	 */
	public void insert(int icol, String s) {
		switch(icol) {
		case 2:
			setUniversita(s);
			break;
		case 0:
			setTitolo(s);
			break;
		case 1:
			setAutore(s);
			break;
		case 3:
			setPaesePubblicazione(s);
			break;
		case 4:
			setAnnoPubblicazione(s);
			break;
		case 5:
			setAbstractTesi(s);
			break;
		case 6:
			setTipoFonte(s);
			break;
		case 7:
			setSoggetto(s);
			break;
		case 8:
			setIdDocumento(s);
			break;
		case 9:
			setPhilosophy(s);
			break;
		case 10:
			setClassification(s);
			break;
		case 11:
			setProb0(s);
			return;
		case 12:
			setProb1(s);
			return;
		default:
			throw new IllegalArgumentException("indice ignoto: "+icol);
		}
	}
	
}
