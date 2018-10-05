package it.unito.di.semphiloclassifier.reader;

import java.io.Serializable;

import it.unito.di.semphiloclassifier.nlp.entities.SemanticThesisData;

/**
 * La classe gestisce le tipologie di informazioni presenti all'interno
 * di un record riferito ad una particolare tesi.
 * 
 * @author Marco Leontino <leontinomarco@gmail.com>
 *
 */
public class RecordData implements Serializable {
	
	// nell'ordine in cui compaiono nel file ods
	
	private String membroCommissione;
	private String philosophy;
	private String titolo;
	private String collegamenti;
	private String paesePubblicazione;
	private String lingua;
	private String annoPubblicazione;
	private String titoloAlternativo;
	private String luogoPubblicazione;
	private String autore;
	private String copyright;
	private String codiceIstituzioneAccademica;
	private String classification;
	private String idDocumento;
	private String isbn;
	private String testoCompleto;
	private String organizzazione;
	private String fonte;
	private String prob0;
	private String persone;
	private String parolaChiave;
	private String pagine;
	private String dipartimento;
	private String prob1;
	private String abstractTesi;
	private String urlDocumento;
	private String numeroDissertazioneTesi;
	private String localitaUniversita;
	private String universita;
	private String numeroPagine;
	private String soggetto;
	private String tipoFonte;
	private String relatore;
	private String http;
	private String titoloAccademico;
	
	private SemanticThesisData semData = new SemanticThesisData();
	
	public SemanticThesisData getSemData() {
		return semData;
	}
	public void setSemData(SemanticThesisData semData) {
		this.semData = semData;
	}
	public String getMembroCommissione() {
		return membroCommissione;
	}
	public String getPhilosophy() {
		return philosophy;
	}
	public String getTitolo() {
		return titolo;
	}
	public String getCollegamenti() {
		return collegamenti;
	}
	public String getPaesePubblicazione() {
		return paesePubblicazione;
	}
	public String getLingua() {
		return lingua;
	}
	public String getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	public String getTitoloAlternativo() {
		return titoloAlternativo;
	}
	public String getLuogoPubblicazione() {
		return luogoPubblicazione;
	}
	public String getAutore() {
		return autore;
	}
	public String getCopyright() {
		return copyright;
	}
	public String getCodiceIstituzioneAccademica() {
		return codiceIstituzioneAccademica;
	}
	public String getClassification() {
		return classification;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getTestoCompleto() {
		return testoCompleto;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public String getFonte() {
		return fonte;
	}
	public String getProb0() {
		return prob0;
	}
	public String getPersone() {
		return persone;
	}
	public String getParolaChiave() {
		return parolaChiave;
	}
	public String getPagine() {
		return pagine;
	}
	public String getDipartimento() {
		return dipartimento;
	}
	public String getProb1() {
		return prob1;
	}
	public String getAbstractTesi() {
		return abstractTesi;
	}
	public String getUrlDocumento() {
		return urlDocumento;
	}
	public String getNumeroDissertazioneTesi() {
		return numeroDissertazioneTesi;
	}
	public String getLocalitaUniversita() {
		return localitaUniversita;
	}
	public String getUniversita() {
		return universita;
	}
	public String getNumeroPagine() {
		return numeroPagine;
	}
	public String getSoggetto() {
		return soggetto;
	}
	public String getTipoFonte() {
		return tipoFonte;
	}
	public String getRelatore() {
		return relatore;
	}
	public String getHttp() {
		return http;
	}
	public String getTitoloAccademico() {
		return titoloAccademico;
	}
	public void setMembroCommissione(String membroCommissione) {
		this.membroCommissione = membroCommissione;
	}
	public void setPhilosophy(String classificazione) {
		this.philosophy = classificazione;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setCollegamenti(String collegamenti) {
		this.collegamenti = collegamenti;
	}
	public void setPaesePubblicazione(String paesePubblicazione) {
		this.paesePubblicazione = paesePubblicazione;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public void setAnnoPubblicazione(String annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}
	public void setTitoloAlternativo(String titoloAlternativo) {
		this.titoloAlternativo = titoloAlternativo;
	}
	public void setLuogoPubblicazione(String luogoPubblicazione) {
		this.luogoPubblicazione = luogoPubblicazione;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public void setCodiceIstituzioneAccademica(String codiceIstituzioneAccademica) {
		this.codiceIstituzioneAccademica = codiceIstituzioneAccademica;
	}
	public void setClassification(String s) {
		this.classification = s;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setTestoCompleto(String testoCompleto) {
		this.testoCompleto = testoCompleto;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}
	public void setProb0(String database) {
		this.prob0 = database;
	}
	public void setPersone(String persone) {
		this.persone = persone;
	}
	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}
	public void setPagine(String pagine) {
		this.pagine = pagine;
	}
	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
	public void setProb1(String dataTitoloAccademico) {
		this.prob1 = dataTitoloAccademico;
	}
	public void setAbstractTesi(String abstractTesi) {
		this.abstractTesi = abstractTesi;
	}
	public void setUrlDocumento(String urlDocumento) {
		this.urlDocumento = urlDocumento;
	}
	public void setNumeroDissertazioneTesi(String numeroDissertazioneTesi) {
		this.numeroDissertazioneTesi = numeroDissertazioneTesi;
	}
	public void setLocalitaUniversita(String localitaUniversita) {
		this.localitaUniversita = localitaUniversita;
	}
	public void setUniversita(String universita) {
		this.universita = universita;
	}
	public void setNumeroPagine(String numeroPagine) {
		this.numeroPagine = numeroPagine;
	}
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
	public void setTipoFonte(String tipoFonte) {
		this.tipoFonte = tipoFonte;
	}
	public void setRelatore(String relatore) {
		this.relatore = relatore;
	}
	public void setHttp(String http) {
		this.http = http;
	}
	public void setTitoloAccademico(String titoloAccademico) {
		this.titoloAccademico = titoloAccademico;
	}
	
	/**
	 * Inserisce il valore per uno dei parametri rappresentati dalla classe
	 * 
	 * @param icol offset in cui si trova il valore nel file csv o ods sorgente
	 * @param s il valore da attribuire al parametro
	 */
	public void insert(int icol, String s) {
		// TO-DO nelle classi più specifiche
	}
}
