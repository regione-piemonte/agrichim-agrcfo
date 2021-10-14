package it.csi.agrc;
//import it.csi.cuneo.*;
//import java.util.*;
//import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanRicerca implements Serializable
{
  private static final long serialVersionUID = -3949278512289043483L;

  private String idRichiestaDa;
  private String idRichiestaA;
  private String dataDaGiorno;
  private String dataDaMese;
  private String dataDaAnno;
  private String dataAGiorno;
  private String dataAMese;
  private String dataAAnno;
  private String tipoMateriale;
  private String codiceFiscale;
  private String cognome;
  private String nome;
  private String etichetta;
  private char organizzazione = 'P';
  private String iuv;
  private String statoPagamento;

  public BeanRicerca(){}

  public String getIdRichiestaDa()
  {
    return idRichiestaDa;
  }
  public String getIdRichiestaA()
  {
    return idRichiestaA;
  }
  public String getDataDaGiorno()
  {
    return dataDaGiorno;
  }
  public String getDataDaMese()
  {
    return dataDaMese;
  }
  public String getDataDaAnno()
  {
    return dataDaAnno;
  }
  public String getDataAGiorno()
  {
    return dataAGiorno;
  }
  public String getDataAMese()
  {
    return dataAMese;
  }
  public String getDataAAnno()
  {
    return dataAAnno;
  }
  public String getTipoMateriale()
  {
    return tipoMateriale;
  }
  public String getCodiceFiscale()
  {
    return codiceFiscale;
  }
  public String getCognome()
  {
    return cognome;
  }
  public String getNome()
  {
    return nome;
  }
  public String getEtichetta()
  {
    return etichetta;
  }
  public char getOrganizzazione()
  {
    return organizzazione;
  }
  public void setNonValido()
  {
    setCodiceFiscale("");
    setCognome("");
    setDataAAnno("");
    setDataAGiorno("");
    setDataAMese("");
    setDataDaAnno("");
    setDataDaGiorno("");
    setDataDaMese("");
    setEtichetta("");
    setIdRichiestaA("");
    setIdRichiestaDa("");
    setNome("");
    setOrganizzazione('P');
    setTipoMateriale("");
  }

  public void setTipoMateriale(String tipoMateriale) {
    if (tipoMateriale==null) tipoMateriale="";
    this.tipoMateriale = tipoMateriale;
  }
  public void setOrganizzazione(char organizzazione) {
    this.organizzazione = organizzazione;
  }
  public void setNome(String nome) {
    if (nome==null) nome="";
    this.nome = nome;
  }
  public void setIdRichiestaDa(String idRichiestaDa) {
    if (idRichiestaDa==null) idRichiestaDa="";
    this.idRichiestaDa = idRichiestaDa;
  }
  public void setIdRichiestaA(String idRichiestaA) {
    if (idRichiestaA==null) idRichiestaA="";
    this.idRichiestaA = idRichiestaA;
  }
  public void setEtichetta(String etichetta) {
    if (etichetta==null) etichetta="";
    this.etichetta = etichetta;
  }
  public void setDataDaMese(String dataDaMese) {
    if (dataDaMese==null) dataDaMese="";
    this.dataDaMese = dataDaMese;
  }
  public void setDataDaGiorno(String dataDaGiorno) {
    if (dataDaGiorno==null) dataDaGiorno="";
    this.dataDaGiorno = dataDaGiorno;
  }
  public void setDataDaAnno(String dataDaAnno) {
    if (dataDaAnno==null) dataDaAnno="";
    this.dataDaAnno = dataDaAnno;
  }
  public void setDataAMese(String dataAMese) {
    if (dataAMese==null) dataAMese="";
    this.dataAMese = dataAMese;
  }
  public void setDataAGiorno(String dataAGiorno) {
    if (dataAGiorno==null) dataAGiorno="";
    this.dataAGiorno = dataAGiorno;
  }
  public void setDataAAnno(String dataAAnno) {
    if (dataAAnno==null) dataAAnno="";
    this.dataAAnno = dataAAnno;
  }
  public void setCognome(String cognome) {
    if (cognome==null) cognome="";
    this.cognome = cognome;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    if (codiceFiscale==null) codiceFiscale="";
    this.codiceFiscale = codiceFiscale;
  }

public String getIuv() {
	return iuv;
}

public void setIuv(String iuv) {
	this.iuv = iuv;
}

public String getStatoPagamento() {
	return statoPagamento;
}

public void setStatoPagamento(String statoPagamento) {
	this.statoPagamento = statoPagamento;
}
}